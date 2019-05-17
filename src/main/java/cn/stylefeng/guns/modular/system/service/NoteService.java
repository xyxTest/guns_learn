/**
 * 
 */
package cn.stylefeng.guns.modular.system.service;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.state.ManagerStatus;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.modular.system.entity.Note;
import cn.stylefeng.guns.modular.system.mapper.NoteMapper;
import cn.stylefeng.guns.modular.system.model.NoteDto;
import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * @author xyx
 * @date  2019年4月11日下午2:25:19
 * @version 1.0
 */
@Service
public class NoteService extends ServiceImpl<NoteMapper, Note> {
	/**
	 *添加记事
	 */
	public void addNote(NoteDto noteDto){
		if (noteDto != null) {
			Note note = new Note();
            BeanUtils.copyProperties(noteDto, note);
            note.setStatus("ENABLE");
            note.setCreateDate(new Date());
			note.setFileUrl(noteDto.getImgs());
            this.save(note);
	     }
	}
	
	/**
	 *删除记事 
	 */
	public void deleteNote(Long noteId){
        this.setStatus(noteId, ManagerStatus.DELETED.getCode());
	}
	
	/**
	 *修改记事状态 
	 */
	public int setStatus(Long noteId,String status){
		 return this.baseMapper.setStatus(noteId, status);
	}
	/**
	 *修改记事 
	 */
	public void editNote(Note note){
		Note oldNote = this.getById(note.getNoteId());
		if(ShiroKit.hasRole(Const.ADMIN_NAME)){
			if (oldNote != null && note != null) {
	            if (ToolUtil.isNotEmpty(note.getContent())) {
	            	oldNote.setContent(note.getContent());
	            }
	            if (ToolUtil.isNotEmpty(note.getTitle())) {
	            	oldNote.setTitle(note.getTitle());
	            }
	            if (ToolUtil.isNotEmpty(note.getFileUrl())) {
	            	oldNote.setFileUrl(note.getFileUrl());
	            }
	            this.updateById(oldNote);
	        }
		}
	}
	/**
     * 判断当前登录的用户是否有操作这个用户的权限
     *
     */
    public void assertAuth(Long userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
    }
    /**
     *获取所有记事列表 
     */
    public Page<Map<String, Object>> list(String content ,String title, String beginTime, String endTime){
    	Page page = LayuiPageFactory.defaultPage();
    	return this.baseMapper.selectNotes(page, content ,title, beginTime, endTime);
    }
}
