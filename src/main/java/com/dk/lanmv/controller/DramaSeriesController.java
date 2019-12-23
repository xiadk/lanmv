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
        String str = httpUtil.doGet(bodyMessage.getDramaUrl());

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(str);
    }
}

