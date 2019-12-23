/**
 * 
 */
package com.dk.lanmv.common;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 服务端统一返回模型
 * @author Wayne.M
 * 2019年2月15日
 */
@Data
@Accessors(chain = true)
public class ReturnModel<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int code = 0;
	protected String message = "操作成功";
	protected T bodyMessage;
}
