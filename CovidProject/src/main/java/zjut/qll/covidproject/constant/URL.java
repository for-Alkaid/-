package zjut.qll.covidproject.constant;

public interface URL {
    String NetEase = "https://c.m.163.com/ug/api/wuhan/app/data/list-total";
    interface Tencent {
        String CURRENT = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
        String HISTORY = "https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=chinaDayList,chinaDayAddList,nowConfirmStatis,provinceCompare";
    };
}
