package com.dk.lanmv.service;

import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.DramaSeries;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
public interface IDramaSeriesService extends IService<DramaSeries> {

    public ReturnModel getDramaInfo(long dramaId);

    public ReturnModel getDramaList(long mvId);
}
