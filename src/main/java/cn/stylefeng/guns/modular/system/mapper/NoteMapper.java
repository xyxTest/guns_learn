/**
 * 
 */
package cn.stylefeng.guns.modular.system.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.stylefeng.guns.modular.system.entity.Note;
import cn.stylefeng.roses.core.datascope.DataScope;

/**
 * @author xyx
 * @date  2019年4月11日下午2:00:56
 * @version 1.0
 */
public interface NoteMapper extends BaseMapper<Note> {
	/**
	 *根据条件查询记事列表 
	 */
	Page<Map<String,Object>> selectNotes(@Param("page") Page page,@Param("content") String content,@Param("title") String title, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 修改记事状态
	 * @param noteId
	 * @param status
	 * @return
	 */
	int setStatus(@Param("noteId") Long noteId, @Param("status") String status);
}
