package com.dk.lanmv.bean;

import com.dk.lanmv.entity.Mv;
import lombok.Data;

/**
 * @description: 电影详细信息
 * @author: DK
 * @create: 2019-12-23 19:41
 **/
@Data
public class MvCategoryInfo extends Mv {
    private String categoryName;
}
