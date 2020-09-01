package com.dk.lanmv.controller;

import com.alibaba.fastjson.JSON;
import com.dk.lanmv.bean.MvCategoryInfo;
import com.dk.lanmv.bean.PageModel;
import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.Category;
import com.dk.lanmv.entity.DramaSeries;
import com.dk.lanmv.entity.Mv;
import com.dk.lanmv.service.IMvService;
import com.dk.lanmv.service.impl.CategoryServiceImpl;
import com.dk.lanmv.service.impl.DramaSeriesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	private CategoryServiceImpl categoryService;
	@Autowired
	private DramaSeriesServiceImpl dramaSeriesService;
	//欢迎页
	@GetMapping("/")
	public ModelAndView hello() {
		return index();
	}

	//首页
	@GetMapping("/index")
	public ModelAndView index() {
		int pageSize = 12;

		//动漫
		ReturnModel<List<Mv>> mvList1 = iMvService.getMvList(1,pageSize);
		Map<String, List<Mv>> map = new HashMap<>();
		map.put("comicLists", mvList1.getBodyMessage());
		//电影
		ReturnModel<List<Mv>> mvList2 = iMvService.getMvList(2, pageSize);
		map.put("moviceLists", mvList2.getBodyMessage());
		//电视剧
		ReturnModel<List<Mv>> mvList3 = iMvService.getMvList(3, pageSize);
		map.put("tvLists", mvList3.getBodyMessage());
		//综艺
		ReturnModel<List<Mv>> mvList4 = iMvService.getMvList(4, pageSize);
		map.put("varietyLists", mvList4.getBodyMessage());
		return new ModelAndView("index",map);
	}

	//详情页
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam(required=true)Long mvId) {
		Map<String, Object> map = new HashMap<>();

		ReturnModel<MvCategoryInfo> MvCategoryInfoList = iMvService.getMvInfo(mvId);
		map.put("mvInfo", MvCategoryInfoList.getBodyMessage());

		ReturnModel<List<DramaSeries>> dramaInfo = dramaSeriesService.getDramaList(mvId);
		List<DramaSeries> dramaSeriesList = dramaInfo.getBodyMessage();
		map.put("dramaSeries", dramaSeriesList);

		//获取喜欢影片
		ReturnModel<List<Mv>> loveLists = iMvService.getLoveMvList(mvId);
		List<Mv> mvList = loveLists.getBodyMessage();
		map.put("loveLists", mvList.subList(0, 10));
		map.put("briefLists", mvList.subList(10, 20));

		//获取热门影片
		ReturnModel<List<Mv>> holdMvList = iMvService.getHoldMvList();
		map.put("holdLists", holdMvList.getBodyMessage());

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

		//获取喜欢影片
		ReturnModel<List<Mv>> loveLists = iMvService.getLoveMvList(mvId);
		List<Mv> mvList = loveLists.getBodyMessage();
		map.put("loveLists", mvList.subList(0, 10));
		map.put("briefLists", mvList.subList(10, 20));

		//获取热门影片
		ReturnModel<List<Mv>> holdMvList = iMvService.getHoldMvList();
		map.put("holdLists", holdMvList.getBodyMessage());

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

	//播放器页
	@GetMapping("/category")
	public ModelAndView category(@RequestParam(required=false, defaultValue = "1")Integer pageIndex, @RequestParam(required=false, defaultValue = "0")Integer categoryId, @RequestParam(required=false,defaultValue = "1")Integer orderBy) {

		ReturnModel<PageModel<Mv>> mvList = iMvService.getMvInfoByCategory(pageIndex, categoryId, orderBy);
		ReturnModel<List<Category>> categorys = categoryService.getCategory();
		Map<String, Object> map = new HashMap<>();
		map.put("pageModel", mvList.getBodyMessage());
		map.put("categoryList", categorys.getBodyMessage());
		map.put("currentCategoryId", categoryId);

		return new ModelAndView("category", map);
	}

	//播放器页
	@GetMapping("/search")
	public ModelAndView search(@RequestParam(required=false, defaultValue = "1")Integer pageIndex, @RequestParam(required=false, defaultValue = "")String searchword) {

		ReturnModel<PageModel<MvCategoryInfo>> mvInfoBySearch = iMvService.getMvInfoBySearch(pageIndex, searchword);
		Map<String, Object> map = new HashMap<>();
		map.put("pageModel", mvInfoBySearch.getBodyMessage());
		map.put("keyWord", searchword);

		return new ModelAndView("search", map);
	}


}



