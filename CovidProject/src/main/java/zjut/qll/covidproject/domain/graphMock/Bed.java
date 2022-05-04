package zjut.qll.covidproject.domain.graphMock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bed extends Point{

    /**
     * 是否占用了该床位
     */
    private boolean isEmpty = true;

    Bed(int x, int y){
        super(x,y);
    }


}
