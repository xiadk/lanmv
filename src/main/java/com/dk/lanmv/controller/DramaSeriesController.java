package com.dk.lanmv.controller;


import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.DramaSeries;
import com.dk.lanmv.service.impl.DramaSeriesServiceImpl;
import com.dk.lanmv.service.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
@Controller
@RequestMapping(value = "/dramaSeries", produces={"application/json;charset=UTF-8"})
public class DramaSeriesController {
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private DramaSeriesServiceImpl dramaSeriesService;
    //播放器页
    @GetMapping("/{dramaId}")
    public void getDramaSeries(@PathVariable("dramaId") Long dramaId, HttpServletResponse response) throws IOException {
        ReturnModel<DramaSeries> dramaInfo = dramaSeriesService.getDramaInfo(dramaId);
        DramaSeries bodyMessage = dramaInfo.getBodyMessage();
        String str = httpUtil.doGet("https://valipl.cp31.ott.cibntv.net/6773386A52039717666FA655D/03000700005DFD5BB38BB7800000006132D779-4FF2-4BD3-88E2-75FB27956F06-1-46575.m3u8?ccode=0502&duration=2013&expire=18000&psid=38f40dc38a4c380773208896ebbb36ac&ups_client_netip=b70fb189&ups_ts=1577367401&ups_userid=1075431810&utid=K0qVFCS9%2BSgCAbcOhgUXSc7u&vid=XNDQ1ODk5MzYwMA&vkey=Af7f8dafc00f7a3a8d5093474b6e5aae3&sm=1&operate_type=1&dre=u37&si=73&iv=0&s=bf53f3061cee4c8e888d&bc=2");

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);
    }

    //播放器页
    @GetMapping("/s/{fileName}")
    public void getDramaSeries(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        ReturnModel<DramaSeries> dramaInfo = dramaSeriesService.getDramaInfo(3241);
        DramaSeries bodyMessage = dramaInfo.getBodyMessage();
        String str = httpUtil.doGet("http://vfile1.grtn.cn/2018/1542/0254/3368/154202543368.ssm/154202543368.m3u8");

        response.setContentType("multipart/form-data;charset=UTF-8");
        response.getWriter().print(str);
    }
}

