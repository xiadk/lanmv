package com.dk.lanmv.service.impl;

import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.Category;
import com.dk.lanmv.mapper.CategoryMapper;
import com.dk.lanmv.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public ReturnModel getCategory() {
        ReturnModel returnModel = new ReturnModel();

        List<Category> list = list(null);
        returnModel.setBodyMessage(list);

        return returnModel;
    }
}
