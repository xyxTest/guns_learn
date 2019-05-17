/**
 * 
 */
package cn.stylefeng.guns.modular.system.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author xyx
 * @date  2019年4月11日下午1:55:41
 * @version 1.0
 */
@Data
public class SysFilesDto {

	private Long fileId;
	private String intro;
	private Long createUser;
	private String name;
	private String realName;
	private String url;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
}
