/**
 * 
 */
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
@TableName("note")
@Data
public class Note implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *主键 id 
	 */
	@TableId(value = "NOTE_ID", type = IdType.ID_WORKER)
	private Long noteId;
	/**
	 *内容 
	 */
	@TableField("CONTENT")
	private String content;
	/**
	 * 创建人
	 */
	@TableField("CREATE_USER_ID")
	private Long createUserId;
	/**
	 *创建时间 
	 */
	@TableField("CREATE_DATE")
	private Date createDate;
	/**
     * 状态(字典)
     */
    @TableField("STATUS")
    private String status;
    /**
     *标题 
     */
    @TableField("TITLE")
    private String title;
    /**
     *附件id 
     */
    @TableField("FILE_URL")
    private String fileUrl;
}
