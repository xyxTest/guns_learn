package cn.stylefeng.guns.modular.system.service.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.modular.system.entity.SysFiles;
import cn.stylefeng.guns.modular.system.mapper.SysFilesMapper;
import cn.stylefeng.guns.modular.system.utils.MD5Util;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;

@Service
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFiles>  {


	public SysFiles getById(Long id) {
		// TODO Auto-generated method stub
		return this.getById(id);
	}

	public boolean deleteFileById(Long id) {
		// TODO Auto-generated method stub
		return this.deleteFileById(id);
	}

	public boolean addFile(SysFiles file) {
		// TODO Auto-generated method stub
		return this.save(file);
	}

	@Transactional(rollbackFor = Exception.class)
	public SysFiles uploadFile(String filePath, MultipartFile file, Integer fileType, HttpServletRequest request) {
		if (file == null || filePath == null || filePath.equals("")) {
			return null;
		}
		String rootPath = request.getSession().getServletContext().getRealPath("/");
        String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                    + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //批量导入。参数：文件名，文件。
//        boolean b = itemService.batchImport(newFileName,file);
        File fileDir = new File(rootPath + "/" + filePath);
        SysFiles files=new SysFiles();
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(rootPath + "/" + filePath + "/"
                    + newFileName);
                // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
            files.setIntro(time);
            String realPath=filePath + "/"+ newFileName;
            files.setName(newFileName);//////构件的url
            files.setUrl(realPath);
            files.setCreateDate(date);
            files.setFileType(0);///默认图片
            ShiroUser currentUser = ShiroKit.getUser();
            if (currentUser == null) {
                throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
            }
            files.setCreateUser(currentUser.getId());
            files.setFileType(fileType);
            files.setRealName(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));
            this.save(files);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return files;
	}

}
