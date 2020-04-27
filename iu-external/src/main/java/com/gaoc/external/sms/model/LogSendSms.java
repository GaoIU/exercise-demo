package com.gaoc.external.sms.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
 * @since 2020-03-31
 */
@Data
@NoArgsConstructor
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
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public LogSendSms(String mobile, String content) {
		super();
		this.mobile = mobile;
		this.content = content;
	}

}
