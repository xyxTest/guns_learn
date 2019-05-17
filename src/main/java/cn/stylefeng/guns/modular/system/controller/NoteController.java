/**
 * 
 */
package cn.stylefeng.guns.modular.system.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.NoteDict;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.system.entity.Note;
import cn.stylefeng.guns.modular.system.model.NoteDto;
import cn.stylefeng.guns.modular.system.service.NoteService;
import cn.stylefeng.guns.modular.system.warpper.NoteWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * @author xyx
 * @date  2019年4月11日下午3:04:27
 * @version 1.0
 */
@Controller
@RequestMapping("/work_note")
public class NoteController extends BaseController{
    private static String PREFIX = "/modular/system/note/";
    
    @Autowired
    private GunsProperties gunsProperties;
    
    @Autowired
    private NoteService noteService;
    
    /**
     * 跳转到查看工作记事列表的页面
     */
    @RequestMapping("")
    public String index(){
    	return PREFIX + "work_note.html";
    }
    
    /**
     *跳转到工作记事新增页面 
     */
    @RequestMapping("/note_add")
    public String addNote(){
    	return PREFIX + "work_note_add.html";
    }
    
    /**
     *跳转到工作记事编辑页面 
     */
    @RequestMapping("/note_edit")
    public String editNote(@RequestParam Long noteId){
    	 if (ToolUtil.isEmpty(noteId)) {
             throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
         }
         Note note = this.noteService.getById(noteId);
         LogObjectHolder.me().set(note);
         return PREFIX + "work_note_edit.html";
    }
    /**
     *获取工作记事详情 
     */
    @RequestMapping("/getNoteInfo")
    @ResponseBody
    public Object getNoteInfo(@RequestParam Long noteId){
    	 if (ToolUtil.isEmpty(noteId)) {
             throw new RequestEmptyException();
         }
         Note note = this.noteService.getById(noteId);
         HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
         if (note != null) {
             Map<String, Object> map = BeanUtil.beanToMap(note);
             map.put("createDate", DateUtil.formatDate(note.getCreateDate()));
             hashMap.putAll(map);
         }
         return ResponseData.success(hashMap);
    	
    }
    /**
     *查询工作记事列表 
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(
    		@RequestParam(required = false) String content,
    		@RequestParam(required = false) String title,
    		@RequestParam(required = false) String timeLimit){
    	//拼接查询条件
        String beginTime = "";
        String endTime = "";

        if (ToolUtil.isNotEmpty(timeLimit)) {
            String[] split = timeLimit.split(" - ");
            beginTime = split[0];
            endTime = split[1];
        }
        Page<Map<String, Object>> notes = noteService.list(content, title,beginTime, endTime);
        Page wrapped = new NoteWrapper(notes).wrap();
        return LayuiPageFactory.createPageInfo(wrapped);
    }
    /**
     *添加工作记事 
     */
    @BussinessLog(value = "添加工作记事", key = "content", dict = NoteDict.class)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(
    		@Valid NoteDto note, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser shiroUser = ShiroKit.getUserNotNull();
        note.setCreateUserId(shiroUser.getId());
        this.noteService.addNote(note);
        return SUCCESS_TIP;
    }
    /**
     * 删除工作记事（逻辑删除）
     *
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除工作记事", key = "noteId", dict = NoteDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData delete(@RequestParam Long noteId) {
        if (ToolUtil.isEmpty(noteId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.noteService.deleteNote(noteId);
        return SUCCESS_TIP;
    }
    
    /**
     * 修改工作记事
     *
     */
    @BussinessLog(value = "修改工作记事", key = "content", dict = NoteDict.class)
    @RequestMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData update(Note note) {
        noteService.editNote(note);
        return SUCCESS_TIP;
    }
}
