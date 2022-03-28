package zjut.qll.covidproject.controller;


import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zjut.qll.covidproject.constant.URL;
@Controller
@ResponseBody
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping()
    public String hello(){
        return HttpUtil.get(URL.NetEase).replace("\\","");
    }
}
