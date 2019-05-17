/**
 * 
 */
package cn.stylefeng.guns.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.stylefeng.guns.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;

/**
 * @author xyx
 * @date  2019年4月11日下午3:02:37
 * @version 1.0
 */
public class NoteWrapper extends BaseControllerWrapper {
	public NoteWrapper(Map<String, Object> single) {
        super(single);
    }

    public NoteWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public NoteWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public NoteWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Long creater = (Long) map.get("createUserId");
        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
    }
}
