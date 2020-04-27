package com.gaoc.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author Gaoc
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommonMeta extends Model<CommonMeta> {

	private static final long serialVersionUID = 1L;

	/**
	 * 开发主键
	 */
	private Long id;

	/**
	 * 字典code
	 */
	@NotBlank(message = "字典CODE不能为空")
	@Length(max = 64, message = "字典CODE长度不能超过64位")
	private String code;

	/**
	 * 字典值
	 */
	@NotBlank(message = "字典值不能为空")
	@Length(max = 512, message = "字典值长度不能超过512位")
	private String value;

	/**
	 * 描述
	 */
	@Length(max = 128, message = "描述长度不能超过128位")
	private String description;

	/**
	 * 排序值
	 */
	private Integer sort;

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

}
