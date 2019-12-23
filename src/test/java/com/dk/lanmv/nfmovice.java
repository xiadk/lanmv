package com.dk.lanmv;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dk.lanmv.entity.Category;
import com.dk.lanmv.entity.DramaSeries;
import com.dk.lanmv.entity.Mv;
import com.dk.lanmv.mapper.CategoryMapper;
import com.dk.lanmv.mapper.DramaSeriesMapper;
import com.dk.lanmv.mapper.MvMapper;
import com.dk.lanmv.service.impl.DramaSeriesServiceImpl;
import com.dk.lanmv.service.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 这是描述
 * @author: DK
 * @create: 2019-12-09 20:06
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= LanmvApplication.class)
public class nfmovice {
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private CategoryMapper categoryMapper ;
    @Autowired
    private MvMapper mvMapper ;
    @Autowired
    private DramaSeriesServiceImpl dramaSeriesService;

    @Test
    public void getM3u8(){

    }

    /**
     * 爬取https://www.nfmovies.com网站的video链接
     */
    @Test
    public  void nfMoviesVideo(){
        int i = 0;
        Map<String, String> header = new HashMap<>();
        header.put("referer", "https://www.nfmovies.com/js/player/mp4.html?19112529");
        while(1==1){
            String mainHtml = httpUtil.doGet("https://www.nfmovies.com/video/?40166-2-1.html");
            Document parse = Jsoup.parse(mainHtml);
            Element element = parse.getElementsByClass("info clearfix").get(0);
            Element element1 = element.select("script").get(1);
            String[] split = element1.data().split("now");
            String mp4Url = split[1].split( "\"")[1];
            if (mp4Url==null|| "".equals(mp4Url)){
                return;
            }
            System.out.println(mp4Url);

            String mp4 = httpUtil.doGet("https://www.nfmovies.com/api/vproxy.php?url=" + mp4Url + "&type=json", header);
            String data = JSON.parseObject(mp4).getString("data");
            System.out.println(data);
            i++;
        }
    }

    /**
     * 爬取https://www.nfmovies.com网站的m3u8链接
     */
    @Test
    public  void nfMoviesM3u8(){
        try {
        Map<String, String> header = new HashMap<>();
        header.put("referer", "https://www.nfmovies.com/js/player/mp4.html?19112529");
        QueryWrapper<Mv> queryWrapper = new QueryWrapper();
        queryWrapper.select("mv_origin");
        List<Mv> mvs = mvMapper.selectList(queryWrapper);
        Map<String, String> existMvOrigin = mvs.stream().collect(Collectors.toMap(Mv::getMvOrigin, Mv::getMvOrigin));

        for (int page = 1; page <= 300; page++){
            String searchUrl = "https://www.nfmovies.com/search.php?page="+page+"&searchtype=5&order=time&player=ckm3u8";
            String search = httpUtil.doGet(searchUrl);
            Document searchParse = Jsoup.parse(search);
            Element searchElement = searchParse.getElementsByClass("hy-video-list").get(0);
            List<String> hrefList = searchElement.getElementsByTag("a").stream().filter(el-> el.hasClass("videopic lazy")).map(ele -> ele.attr("href")).collect(Collectors.toList());

            //获取已存的分类
            List<Category> categories = categoryMapper.selectList(null);
            Map<String, Integer> categoryMap = categories.stream().collect(Collectors.toMap(Category::getCategoryName, Category::getCategoryId));
            for (int i = 0, j = hrefList.size(); i < j ; i++) {
                String detailHref = hrefList.get(i);
                Mv mv = new Mv();
                String deltailUrl = "https://www.nfmovies.com" + detailHref;
                if (existMvOrigin.get(deltailUrl) != null){
                    continue;
                }

                String detail = httpUtil.doGet(deltailUrl);
                Document detailParse = Jsoup.parse(detail);
                mv.setMvOrigin(deltailUrl);
                //名称
                Element mvName = detailParse.getElementsByTag("h3").get(0);
                mv.setMvName(mvName.text());

                //简介
                Element intruduce = detailParse.getElementsByClass("plot").get(0);
                mv.setMvSynopsis(intruduce.text());

                //图片
                String style = detailParse.getElementsByClass("videopic").get(0).attr("style");
                mv.setMvPicture("https://www.nfmovies.com" + style.split("[(]")[1].split("[)]")[0]);

                Elements muted = detailParse.getElementsByClass("content").get(0).getElementsByTag("li");
                for (Element el : muted) {
                    String str = el.text();
                    String[] split = str.split("：");
                    String key = split[0];
                    String val = "";
                    if (split.length > 1) {
                        val = split[1];
                    }

                    if ("主演".equals(key)) {
                        mv.setMvStar(val);
                        continue;
                    }
                    if ("导演".equals(key)) {
                        mv.setMvDirector(val);
                        continue;
                    }
                    if ("地区".equals(key)) {
                        mv.setMvCountry(val);
                        continue;
                    }
                    if ("类型".equals(key)) {
                        Integer categoryId = categoryMap.get(val);
                        if (categoryId == null) {
                            Category category = new Category();
                            category.setCategoryName(val);
                            categoryMapper.insert(category);
                            categoryId = category.getCategoryId();
                        }
                        mv.setCategoryId(categoryId);
                        continue;
                    }
                    if ("语言".equals(key)) {
                        mv.setMvLanguage(val);
                        continue;
                    }
                    if ("年份".equals(key)) {
                        mv.setMvShotYear(val);
                        continue;
                    }
                }
                mv.setCreateTime(LocalDateTime.now());
                mvMapper.insert(mv);

                //线路
                Elements panelElement = detailParse.getElementsByClass("panel clearfix");
                Map<String, Elements> panelElementMap = new HashMap<>();
                panelElement.stream().forEach(el -> {
                    Element option = el.getElementsByClass("option").get(0);
                    panelElementMap.put(option.attr("title"), el.getElementsByTag("ul").get(0).getElementsByTag("a"));
                });

                //集数：播放页url
                Map<String, String> videoUrlMap = new LinkedHashMap<>();
                Elements ckm3u8Element = panelElementMap.get("ckm3u8");
                Elements onlyElement = panelElementMap.get("奈菲独家高清片源");
                if (ckm3u8Element != null) {
                    ckm3u8Element.stream().forEach(element -> {
                        String href = "https://www.nfmovies.com" + element.attr("href");
                        String numberStr = element.text();
                        videoUrlMap.put(numberStr, href);
                    });
                } else if (onlyElement != null) {
                    ckm3u8Element.stream().forEach(element -> {
                        String href = "https://www.nfmovies.com" + element.attr("href");
                        String numberStr = element.text();
                        videoUrlMap.put(numberStr, href);
                    });
                } else {
                    log.error("线路垃圾" + deltailUrl);

                    continue;
                }

                List<DramaSeries> list = new ArrayList<>();
                int sort = 1;
                for (Map.Entry<String, String> map : videoUrlMap.entrySet()) {
                    //集数
                    String numberStr = map.getKey();
                    //播放页面
                    String url = map.getValue();
                    String mainHtml = httpUtil.doGet(url);
                    String m3u8Url = "";
                    if (mainHtml != null){
                        Document parse = Jsoup.parse(mainHtml);
                        //获取集数
                   /* Elements footer_clearfix = parse.getElementsByClass("footer clearfix");
                    String numberStr = footer_clearfix.text().split("第")[1].split("集")[0];*/

                        //获取url
                        Element element = parse.getElementsByClass("info clearfix").get(0);
                        Element element1 = element.select("script").get(1);
                        String[] split = element1.data().split("now");
                        if (split.length == 0) {
                            log.error(url + "解析now失败");

                            break;
                        }
                         m3u8Url = split[1].split("\"")[1];
                        if (m3u8Url == null || "".equals(m3u8Url)) {

                            break;
                        }
                        if (!m3u8Url.endsWith("m3u8")) {
                            String resp = httpUtil.doGet("https://www.nfmovies.com/api/vproxy.php?url=" + m3u8Url + "&type=json", header);
                            if (resp != null){
                                if (m3u8Url.endsWith("mp4")) {
                                    try {
                                        m3u8Url = JSON.parseObject(resp).getString("data");
                                    } catch (Exception e) {
                                        log.error("解析data数据失败:" + url);

                                        break;
                                    }
                                } else {
                                    String[] mains = resp.split("main");
                                    if (mains.length == 0) {
                                        log.error(url + "获取url失败");

                                        break;
                                    }

                                    String[] mainM3u8 = mains[1].split("\"");
                                    if (mains.length == 0) {
                                        log.error(url + "获取url失败");

                                        break;
                                    }
                                    m3u8Url = mainM3u8[1];
                                }
                            } else {
                                m3u8Url = "";
                            }
                        }
                        m3u8Url = URLDecoder.decode(m3u8Url, "UTF-8");
                    } else {
                        log.error(url+"请求超时");
                    }

                    DramaSeries dramaSeries = new DramaSeries();
                    dramaSeries.setDramaUrl(m3u8Url);
                    dramaSeries.setMvId(mv.getMvId());
                    dramaSeries.setDramaNumber(numberStr);
                    dramaSeries.setDramaOrigin(url);
                    dramaSeries.setSort(sort);
//                    dramaSeriesService.save(dramaSeries);
                    list.add(dramaSeries);
                    TimeUnit.MILLISECONDS.sleep(500);
                    sort++;
                }
                dramaSeriesService.saveBatch(list);
            }
            log.info("已运行第"+ page +"页");
        }
        }catch (Exception e){
            log.error("发生异常",e);
        }
    }
}
