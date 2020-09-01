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

    public ReturnModel getMvList(int categoryId, int pageSize);

    public ReturnModel getMvInfo(long mvId);

    public ReturnModel getMvInfoBySearch(Integer pageIndex, String keyWord);

    public ReturnModel getMvInfoByCategory(Integer pageIndex, Integer categoryId, Integer orderBy);

    /**
     * 猜你喜欢
     * @return
     */
    public ReturnModel getLoveMvList(long mvId);

    /**
     * 热榜影片
     * @return
     */
    public ReturnModel getHoldMvList();
}
