package zjut.qll.covidproject.domain.graphMock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveTarget extends Point{

    private boolean arrived = false;//是否到达目标点

    MoveTarget(int x,int y){
        super(x,y);
    }
}
