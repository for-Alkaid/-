package zjut.qll.covidproject.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjut.qll.covidproject.constant.URL;
import zjut.qll.covidproject.domain.CovidHistoryInfo;
import zjut.qll.covidproject.domain.CovidInfo;
import zjut.qll.covidproject.domain.graphMock.GraphMock;
import zjut.qll.covidproject.service.PanelService;


@RestController
@RequiredArgsConstructor(onConstructor=@__(@Autowired) )
public class CovidController {


    private final PanelService panelService;

    @RequestMapping("/covid-info")
    public CovidInfo requestCovidInfo(){
        String s = HttpUtil.get(URL.Tencent.CURRENT);
        String data = JSONUtil.parseObj(s).getStr("data");
        CovidInfo covidInfo = JSONObject.parseObject(data, CovidInfo.class);
//        System.out.println(covidInfo);
        return covidInfo;
    }

    @RequestMapping("/covid-info/list")
    public CovidHistoryInfo requestCovidDayList(){
        String s = HttpUtil.get(URL.Tencent.HISTORY);
        CovidHistoryInfo covidHistoryInfo = JSONObject.parseObject(JSONObject.parseObject(s).getString("data"), CovidHistoryInfo.class);
        //covidHistoryInfos.forEach(covidHistoryInfo -> System.out.println(covidHistoryInfo.toString()));
        return covidHistoryInfo;
    }
    
    @PostMapping("/covid-mock")
    public GraphMock mock(){
        GraphMock graphMock = panelService.run();
        return graphMock;
    }


}
