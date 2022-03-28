package zjut.qll.covidproject.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CovidInfo implements Serializable {

    @JSONField(name = "areaTree")
    private List<Area> areaList;

    private ChinaAdd chinaAdd;

    private ChinaTotal chinaTotal;

    private Boolean isShowAdd;

    @JSONField(name = "lastUpdateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}

@Data
class ChinaAdd{
    private Long confirm;
    private Long dead;
    private Long heal;
    private Long importedCase;
    private Long localConfirm;
    private Long localConfirmH5;
    private Long noInfect;
    private Long noInfectH5;
    private Long nowConfirm;
    private Long nowSevere;
    private Long suspect;
}

@Data
class ChinaTotal{
    private Long confirm;
    @JSONField(name = "dead")
    private Long deadCount;
    private Long heal;
    private Long importedCase;
    private Long localConfirm;
    private Long localConfirmH5;
    private Long local_acc_confirm;
    private Long noInfect;
    private Long noInfectH5;
    private Long nowConfirm;
    private Long nowSevere;
    private Long showLocalConfirm;
    private Long showlocalinfeciton;
    private Long suspect;
}
