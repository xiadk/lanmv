package com.dk.lanmv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dk.lanmv.bean.MvCategoryInfo;
import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.Category;
import com.dk.lanmv.entity.Mv;
import com.dk.lanmv.mapper.MvMapper;
import com.dk.lanmv.service.IMvService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public ReturnModel getMvList(int categoryId) {
        ReturnModel returnModel = new ReturnModel();

        QueryWrapper<Mv> queryWrapper = new QueryWrapper();
        queryWrapper.eq("category_id", categoryId);
        Page<Mv> mvPage = new Page<>(1, 12);
        List<Mv> mvs = page(mvPage, queryWrapper).getRecords();

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
}
