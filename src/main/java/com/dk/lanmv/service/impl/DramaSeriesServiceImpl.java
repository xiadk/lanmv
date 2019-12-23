package com.dk.lanmv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.DramaSeries;
import com.dk.lanmv.mapper.DramaSeriesMapper;
import com.dk.lanmv.service.IDramaSeriesService;
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
public class DramaSeriesServiceImpl extends ServiceImpl<DramaSeriesMapper, DramaSeries> implements IDramaSeriesService {

    @Override
    public ReturnModel getDramaInfo(long dramaId) {
        ReturnModel returnModel = new ReturnModel();
        returnModel.setBodyMessage(getById(dramaId));

        return returnModel;
    }

    @Override
    public ReturnModel getDramaList(long mvId) {
        ReturnModel returnModel = new ReturnModel();
        QueryWrapper<DramaSeries> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mv_id", mvId);
        List<DramaSeries> list = list(queryWrapper);
        returnModel.setBodyMessage(list);

        return returnModel;
    }
}
