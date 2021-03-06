package com.dk.lanmv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dk.lanmv.bean.MvCategoryInfo;
import com.dk.lanmv.bean.PageModel;
import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.Category;
import com.dk.lanmv.entity.Mv;
import com.dk.lanmv.mapper.MvMapper;
import com.dk.lanmv.service.IMvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
@Service
public class MvServiceImpl extends ServiceImpl<MvMapper, Mv> implements IMvService {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private MvMapper mvMapper;

    @Override
    public ReturnModel getMvList(int categoryId, int pageSize) {
        ReturnModel returnModel = new ReturnModel();

        Mv oneLastMvInfo = mvMapper.getOneLastMvInfo();

        long lastMvId = oneLastMvInfo.getMvId();
        Random random = new Random();
        int mvId = random.nextInt((int) lastMvId - pageSize);

        QueryWrapper<Mv> queryWrapper = new QueryWrapper<>();
        if (categoryId != 0){
            queryWrapper.eq("category_id", categoryId);
        }
        queryWrapper.gt("mv_id", mvId);
        Page<Mv> mvPage = new Page<>(1, pageSize);
        List<Mv> mvs = page(mvPage, queryWrapper).getRecords();
        Collections.shuffle(mvs);

        returnModel.setBodyMessage(mvs);
        return returnModel;
    }

    @Override
    public ReturnModel getMvInfo(long mvId) {
        ReturnModel returnModel = new ReturnModel();
        Mv mv = getById(mvId);
        MvCategoryInfo mvCategoryInfo = new MvCategoryInfo();
        BeanUtils.copyProperties(mv, mvCategoryInfo);

        Category category = categoryService.getById(mv.getCategoryId());
        mvCategoryInfo.setCategoryName(category.getCategoryName());

        returnModel.setBodyMessage(mvCategoryInfo);

        return returnModel;
    }

    @Override
    public ReturnModel getMvInfoBySearch(Integer pageIndex, String keyWord) {
        ReturnModel returnModel = new ReturnModel();

        int pageSize = 10;
        List<MvCategoryInfo> mvInfoBySearchs = mvMapper.getMvInfoBySearch(pageSize, (pageIndex - 1) * pageSize, keyWord);
        QueryWrapper<Mv> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("mv_name", "%"+keyWord+"%");
        int count = count(queryWrapper);
        PageModel<MvCategoryInfo> pageModel = new PageModel<>();
        pageModel.setPageDatas(mvInfoBySearchs);
        pageModel.setPageIndex(pageIndex);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalCount(count);

        //计算总页码
        int totalPage = count / pageSize;
        if (count % pageSize != 0){
            totalPage = totalPage + 1;
        }
        pageModel.setTotalPage(totalPage);


        returnModel.setBodyMessage(pageModel);

        return returnModel;
    }

    @Override
    public ReturnModel getMvInfoByCategory(Integer pageIndex, Integer categoryId, Integer orderBy) {
        ReturnModel returnModel = new ReturnModel();

        int pageSize = 36;
        QueryWrapper<Mv> queryWrapper = new QueryWrapper();
        if (categoryId != 0) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (orderBy == 1){
            queryWrapper.orderByDesc("create_time");
        }
        Page<Mv> mvPage = new Page<>(pageIndex, pageSize);
        List<Mv> mvs = page(mvPage, queryWrapper).getRecords();

        int count = count(queryWrapper);
        PageModel<Mv> pageModel = new PageModel<>();
        pageModel.setPageDatas(mvs);
        pageModel.setPageIndex(pageIndex);
        pageModel.setPageSize(pageSize);
        pageModel.setTotalCount(count);

        //计算总页码
        int totalPage = count / pageSize;
        if (count % pageSize != 0){
            totalPage = totalPage + 1;
        }
        pageModel.setTotalPage(totalPage);
        returnModel.setBodyMessage(pageModel);

        return returnModel;
    }

    @Override
    public ReturnModel getLoveMvList(long mvId) {
        ReturnModel returnModel = new ReturnModel();

        Mv mv = getById(mvId);

        ReturnModel<List<Mv>> mvReturnModel = getMvList(mv.getCategoryId(), 21);

        List<Mv> mvList = mvReturnModel.getBodyMessage();
        mvList = mvList.stream().filter(mv1->mv1.getMvId() != mvId).limit(20).collect(Collectors.toList());

        if  (mvList.size() < 20){
            mvList.addAll((List<Mv>)getHoldMvList().getBodyMessage());
        }

        returnModel.setBodyMessage(mvList);
        return returnModel;
    }

    @Override
    public ReturnModel getHoldMvList() {
        ReturnModel returnModel = new ReturnModel();

        List<Mv> holdMvInfo = mvMapper.getHoldMvInfo();

        returnModel.setBodyMessage(holdMvInfo);
        return returnModel;
    }
}
