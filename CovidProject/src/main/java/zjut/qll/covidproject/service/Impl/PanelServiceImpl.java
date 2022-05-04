package zjut.qll.covidproject.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zjut.qll.covidproject.constant.Constants;
import zjut.qll.covidproject.domain.graphMock.*;
import zjut.qll.covidproject.service.PanelService;

import java.util.List;
import java.util.Random;


@Service
public class PanelServiceImpl implements PanelService {

    private GraphMock mockData(){
        GraphMock graphMock = new GraphMock();
        // 医院实例
        Hospital hospital = Hospital.getInstance();
        graphMock.setHospital(hospital);

        PersonPool personPool = PersonPool.getInstance();
        graphMock.setPersonPool(personPool);

        graphMock.setCity(personPool.getCity());

        graphMock.setPeopleInfo(new PeopleInfo());

        //绘制代表人类的圆点
        List<Person> people = PersonPool.getInstance().getPersonList();
        if (CollectionUtils.isEmpty(people)) {
            return null;
        }

        // 根据状态赋值颜色
        for (Person person : people) {
            switch (person.getState()) {
                case Person.State.NORMAL: {
                    //健康人,灰
                    person.setColor("#DDDDDD");
                    break;
                }
                case Person.State.SHADOW: {
                    //潜伏期感染者，黄
                    person.setColor("#FFEE00");
                    break;
                }
                case Person.State.CONFIRMED: {
                    //确诊患者，红
                    person.setColor("#FF0000");
                    break;
                }
                case Person.State.FREEZE: {
                    //已隔离者，蓝
                    person.setColor("#48FFFC");
                    break;
                }
                case Person.State.DEATH: {
                    //死亡患者，黑
                    person.setColor("#000000");
                    break;
                }
            }
            //对各种状态的市民进行不同的处理
            person.update();
        }

        return graphMock;
    }

    /**
     * 初始化初始感染者
     */
    private void initInfected() {
        List<Person> peopleList = PersonPool.getInstance().getPersonList();//获取所有的市民
        for (int i = 0; i < Constants.ORIGINAL_COUNT; i++) {
            Person person;
            do {
                person = peopleList.get(new Random().nextInt(peopleList.size() - 1));//随机挑选一个市民
            } while (person.isInfected());//如果该市民已经被感染，重新挑选
            person.beInfected();//让这个幸运的市民成为感染者
        }
    }

    /**
     * 匹配所有人死亡时的条件
     *
     */
    private boolean isDeath(List<Person> personList){
        return personList.stream().noneMatch(person -> person.getState() == Person.State.DEATH);
    }


    @Override
    public GraphMock run(){
        if (Panel.worldTime == 0){
            this.initInfected();
        }
        GraphMock graphMock;
//        do {
        graphMock = this.mockData();
//        Panel.graphMockList.add(graphMock);
        System.out.println(Panel.worldTime);
        Panel.worldTime++;
        return graphMock;
//        }while (this.isDeath(graphMock.getPersonPool().getPersonList()));
    }
}
