package com.dk.lanmv.mapper;

import com.dk.lanmv.bean.MvCategoryInfo;
import com.dk.lanmv.entity.Mv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
@Mapper
@Component
public interface MvMapper extends BaseMapper<Mv> {

    @Select("select * from t_mv as tm left join t_category as tc on tm.category_id = tc.category_id where tm.mv_name like concat('%',#{keyWord},'%') limit #{offset}, #{pageSize}")
    List<MvCategoryInfo> getMvInfoBySearch( @Param("pageSize")int pageSize, @Param("offset")int offset, @Param("keyWord")String keyWord);

    @Select("select * from t_mv  order by mv_id desc limit 1")
    Mv getOneLastMvInfo();

    @Select("select * from t_mv where mv_hold = 1 order by mv_views desc")
    List<Mv> getHoldMvInfo();

}
