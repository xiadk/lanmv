package com.dk.lanmv.mapper;

import com.dk.lanmv.entity.Mv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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

}
