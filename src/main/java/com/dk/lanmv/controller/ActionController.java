package com.dk.lanmv.controller;

import com.alibaba.fastjson.JSON;
import com.dk.lanmv.bean.MvCategoryInfo;
import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.DramaSeries;
import com.dk.lanmv.entity.Mv;
import com.dk.lanmv.service.IMvService;
import com.dk.lanmv.service.impl.DramaSeriesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class ActionController{

	@Autowired
	private IMvService iMvService;
	@Autowired
	private DramaSeriesServiceImpl dramaSeriesService;
	//欢迎页
	@GetMapping("/")
	public String hello() {
		return "index";
	}

	//首页
	@GetMapping("/index")
	public ModelAndView index() {

		ReturnModel<List<Mv>> mvList = iMvService.getMvList(1);
		Map<String, List<Mv>> map = new HashMap<>();
		map.put("inv", mvList.getBodyMessage());
		return new ModelAndView("index",map);
	}

	//详情页
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam(required=true)Long mvId) {
		ReturnModel<MvCategoryInfo> mvList = iMvService.getMvInfo(mvId);
		ReturnModel<List<DramaSeries>> dramaInfo = dramaSeriesService.getDramaList(mvId);
		List<DramaSeries> dramaSeriesList = dramaInfo.getBodyMessage();

		Map<String, Object> map = new HashMap<>();
		map.put("dramaSeries", dramaSeriesList);
		map.put("mvInfo", mvList.getBodyMessage());

		return new ModelAndView("detail",map);
	}

	//详情页
	@GetMapping("/video")
	public ModelAndView video(@RequestParam(required=true)Long mvId, @RequestParam(required=false)Long dramaSeriesId) {
		ReturnModel<List<DramaSeries>> dramaInfo = dramaSeriesService.getDramaList(mvId);
		List<DramaSeries> dramaSeriesList = dramaInfo.getBodyMessage();
		Map<String, Object> map = new HashMap<>();
		map.put("dramaSeries", dramaSeriesList);
		List<DramaSeries> currentDramaSeriesList = dramaSeriesList.stream().filter(dramaSeries-> dramaSeriesId != null && dramaSeries.getDramaId() == dramaSeriesId.longValue()).collect(Collectors.toList());
		DramaSeries currentDramaSeries = dramaSeriesList.get(0);
		if (currentDramaSeriesList.size() > 0){
			currentDramaSeries = currentDramaSeriesList.get(0);
		}
		map.put("currentDramaSeries", currentDramaSeries);
		ReturnModel<Mv> mv = iMvService.getMvInfo(mvId);
		map.put("mv", mv.getBodyMessage());

		return new ModelAndView("video", map);
	}

	//播放器页
	@GetMapping("/play")
	public ModelAndView play(@RequestParam(required=true)Long dramaId) {
		ReturnModel<DramaSeries> dramaInfo = dramaSeriesService.getDramaInfo(dramaId);
		Map<String, Object> map = new HashMap<>();
		map.put("dramaSeries", dramaInfo.getBodyMessage());

		return new ModelAndView("play", map);
	}

	//播放器页
	@GetMapping("/dkplay")
	public ModelAndView dkplay() {

		return new ModelAndView("dkplay");
	}


}



