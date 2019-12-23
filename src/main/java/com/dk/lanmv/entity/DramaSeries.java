package com.dk.lanmv.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_drama_series")
public class DramaSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "drama_id", type = IdType.AUTO)
    private Long dramaId;

    /**
     * 剧集名称
     */
    private String dramaNumber;

    /**
     * 剧集链接
     */
    private String dramaUrl;

    /**
     * 关联信息
     */
    private Long mvId;

    /**
     * 来源网页链接
     */
    private String dramaOrigin;

    /**
     * 排序
     */
    private Integer sort;



}
