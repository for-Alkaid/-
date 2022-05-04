package zjut.qll.covidproject.domain.graphMock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    private int x;
    private int y;

    /**
     * 位移
     *
     * @param x
     * @param y
     */
    public void moveTo(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
