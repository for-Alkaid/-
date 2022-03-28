package zjut.qll.covidproject.domain;


import com.alibaba.fastjson.annotation.JSONField;
import com.sun.jmx.remote.internal.ArrayQueue;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    @JSONField(name = "children")
    private List<Area> areaChild;

    private String name;

    private TodayConfirm today;

    private TotalConfirm total;

}

@Data
class TodayConfirm{

    private Long confirm;

    @JSONField(name = "isUpdated")
    private Boolean updated;
}

@Data
class TotalConfirm{
    private Long confirm;
    private Long dead;
    private Long heal;
    private Long nowConfirm;
    private Long provinceLocalConfirm;
    private Boolean showHeal;
    private Boolean showRate;
    private Long wzz;
}