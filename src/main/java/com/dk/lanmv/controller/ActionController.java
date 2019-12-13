package com.dk.lanmv.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ActionController{

	//欢迎页
	@GetMapping("/hello")
	public String index() {
		return "hello";
	}

	//欢迎页
	@GetMapping("/index")
	public String www() {
		return "index";
	}

	//详情页
	@GetMapping("/detail")
	public String detail() {
		return "detail";
	}

	//详情页
	@GetMapping("/video")
	public String video() {
		return "video";
	}

}



