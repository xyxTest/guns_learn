/**
 * 
 */
package cn.stylefeng.guns.modular.system.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.config.properties.GunsProperties;
import cn.stylefeng.guns.core.common.annotion.BussinessLog;
import cn.stylefeng.guns.core.common.annotion.Permission;
import cn.stylefeng.guns.core.common.constant.Const;
import cn.stylefeng.guns.core.common.constant.dictmap.DeptDict;
import cn.stylefeng.guns.core.common.constant.dictmap.NoteDict;
import cn.stylefeng.guns.core.common.constant.dictmap.UserDict;
import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.common.page.LayuiPageFactory;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.system.entity.Dept;
import cn.stylefeng.guns.modular.system.entity.Note;
import cn.stylefeng.guns.modular.system.entity.SysFiles;
import cn.stylefeng.guns.modular.system.entity.User;
import cn.stylefeng.guns.modular.system.factory.UserFactory;
import cn.stylefeng.guns.modular.system.model.NoteDto;
import cn.stylefeng.guns.modular.system.model.UserDto;
import cn.stylefeng.guns.modular.system.service.NoteService;
import cn.stylefeng.guns.modular.system.service.SysFilesService;
import cn.stylefeng.guns.modular.system.warpper.NoteWrapper;
import cn.stylefeng.guns.modular.system.warpper.UserWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
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
@RequestMapping("/sys_files")
public class SysFilesController extends BaseController{
    
    @Autowired
    private SysFilesService fileService;
    
    private static String filePath="/sysFiles";
    
    /**
     *文件上传 
     */
    @RequestMapping(value = "/upload_files")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData add(
    		HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ShiroUser shiroUser = ShiroKit.getUserNotNull();
        SysFiles files = new SysFiles();
        files.setCreateUser(shiroUser.getId());
        files.setCreateDate(new Date());
        files=this.fileService.uploadFile(filePath, file, 0, request);
        ResponseData results = new ResponseData();
        results.setData(files.getUrl());
        return results;
    }
   
}
