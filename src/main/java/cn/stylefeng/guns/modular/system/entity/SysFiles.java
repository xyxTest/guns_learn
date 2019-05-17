package cn.stylefeng.guns.modular.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author xyx
 * @date  2019年4月11日下午2:01:23
 * @version 1.0
 */
@TableName("sys_files")
@Data
public class SysFiles implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *主键 id 
	 */
	@TableId(value = "FILE_ID", type = IdType.ID_WORKER)
	private Long fileId;
	/**
	 *文件名称 
	 */
	@TableField("NAME")
    private String name;
	/**
	 *文件简介 
	 */
	@TableField("INTRO")
    private String intro;
	/**
	 *文件类型 
	 */
	@TableField("FILE_TYPE")
    private Integer fileType;//////0.模型文件 1.图纸文件  2.问题文件 3.留言文件  4.交底 5.图片 6.构件excel 7.预制化文件 8.要生成二维码的文件9.项目工程资料
    /**
     *文件服务器相对地址
     */
	@TableField("URL")
	private String url;
	/**
	 *文件原名称 
	 */
	@TableField("REAL_NAME")
    private String realName;//////文件原名称
	/**
	 *文件上传时间
	 */
	@TableField("CREATE_DATE")
	private Date createDate;
	/**
	 *文件创建者id 
	 */
	@TableField("CREATE_USER")
	private Long createUser;
	
}
