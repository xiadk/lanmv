package com.dk.lanmv.service;

import com.dk.lanmv.common.ReturnModel;
import com.dk.lanmv.entity.Mv;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
public interface IMvService extends IService<Mv> {

    public ReturnModel getMvList(int categoryId);

    public ReturnModel getMvInfo(long mvId);
}
