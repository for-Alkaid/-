package zjut.qll.covidproject.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjut.qll.covidproject.constant.URL;
import zjut.qll.covidproject.domain.CovidHistoryInfo;
import zjut.qll.covidproject.domain.CovidInfo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;


@RestController
public class CovidController {

    @RequestMapping("/covid-info")
    public CovidInfo requestCovidInfo(){
        String s = HttpUtil.get(URL.Tencent.CURRENT);
        String data = JSONUtil.parseObj(s).getStr("data");
        CovidInfo covidInfo = JSONObject.parseObject(data, CovidInfo.class);
        System.out.println(covidInfo);
        return covidInfo;
    }

    @RequestMapping("/covid-info/list")
    public CovidHistoryInfo requestCovidDayList(){
        String s = HttpUtil.get(URL.Tencent.HISTORY);
        CovidHistoryInfo covidHistoryInfo = JSONObject.parseObject(JSONObject.parseObject(s).getString("data"), CovidHistoryInfo.class);
        //covidHistoryInfos.forEach(covidHistoryInfo -> System.out.println(covidHistoryInfo.toString()));
        return covidHistoryInfo;
    }
}
