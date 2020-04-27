package com.gaoc.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 发送短信记录表
 * </p>
 *
 * @author Gaoc
 * @since 2020-04-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LogSendSms extends Model<LogSendSms> {

	private static final long serialVersionUID = 1L;

	/**
	 * 开发主键，唯一标识
	 */
	private Long id;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 发送内容
	 */
	private String content;

	/**
	 * 发送状态：0-失败，1-成功
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public LogSendSms(String mobile, String content, Integer status) {
		super();
		this.mobile = mobile;
		this.content = content;
		this.status = status;
	}

}
