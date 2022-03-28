package zjut.qll.covidproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidHistoryInfo implements Serializable {
    private List<ChinaDayAdd> chinaDayAddList;
    private List<ChinaDay> chinaDayList;
    @JsonIgnore
    private Object nowConfirmStatis;
    @JsonIgnore
    private Object provinceCompare;
}

@Data
class ChinaDayAdd implements Serializable {
    private Long confirm;
    private String date;
    private Long dead;
    private String deadRate;
    private Long heal;
    private String healRate;
    private Long importedCase;
    private Long infect;
    private Long localConfirmadd;
    private Long localinfectionadd;
    private Long suspect;
    private String y;
}

@Data
class ChinaDay implements Serializable {
    private Long confirm;
    private String date;
    private Long dead;
    private String deadRate;
    private Long heal;
    private String healRate;
    private Long importedCase;
    private Long localConfirm;
    private Long localConfirmH5;
    private Long local_acc_confirm;
    private Long noInfect;
    private Long noInfectH5;
    private Long nowConfirm;
    private Long nowSevere;
    private Long suspect;
    private String y;
}
