package zjut.qll.covidproject.domain.graphMock;

import lombok.AllArgsConstructor;
import lombok.Data;
import zjut.qll.covidproject.constant.Constants;

@Data
@AllArgsConstructor
public class PeopleInfo {
    private Integer total;
    private Integer normalNum;
    private Integer shadowNum;
    private Integer confirmedNum;
    private Integer freezeNum;
    private Integer emptyBed;
    private Integer needBed;
    private Integer deathNum;
    private Integer worldTime;

    public PeopleInfo(){
        total = Constants.CITY_PERSON_SIZE;
        normalNum = PersonPool.getInstance().getPeopleSize(Person.State.NORMAL);
        shadowNum = PersonPool.getInstance().getPeopleSize(Person.State.SHADOW);
        confirmedNum = PersonPool.getInstance().getPeopleSize(Person.State.CONFIRMED);
        freezeNum = PersonPool.getInstance().getPeopleSize(Person.State.FREEZE);
        emptyBed = Math.max(Constants.BED_COUNT - PersonPool.getInstance().getPeopleSize(Person.State.FREEZE), 0);
        deathNum = PersonPool.getInstance().getPeopleSize(Person.State.DEATH);
        worldTime = (int) (Panel.worldTime / 10.0);
        if (Hospital.getInstance().getBeds().stream().anyMatch(Bed::isEmpty)){
            needBed = 0;
        } else{
          needBed = confirmedNum;
        }
    }

}