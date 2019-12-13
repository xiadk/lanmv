package com.dk.lanmv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author DK
 * @since 2019-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mv")
public class Mv implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 电影主键id
     */
    @TableId(value = "mv_id", type = IdType.AUTO)
    private Long mvId;

    /**
     * 电影名称
     */
    private String mvName;

    /**
     * 电影简介
     */
    private String mvSynopsis;

    /**
     * 主演
     */
    private String mvStar;

    /**
     * 国家
     */
    private String mvCountry;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 图片链接
     */
    private String mvPicture;

    /**
     * 导演
     */
    private String mvDirector;

    /**
     * 分类
     */
    private Integer categoryId;

    /**
     * 语言
     */
    private String mvLanguage;
    /**
     * 年份
     */
    private String mvShotYear;
    /**
     * 信息来源网页链接
     */
    private String mvOrigin;




}
