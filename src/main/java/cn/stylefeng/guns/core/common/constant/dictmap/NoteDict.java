/**
 * 
 */
package cn.stylefeng.guns.core.common.constant.dictmap;

import cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * @author xyx
 * @date  2019年4月11日下午3:29:12
 * @version 1.0
 */
public class NoteDict extends AbstractDictMap {

	/* (non-Javadoc)
	 * @see cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap#init()
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		put("noteId","编号");
		put("content","内容");
		put("createUserId","创建人");
	}

	/* (non-Javadoc)
	 * @see cn.stylefeng.guns.core.common.constant.dictmap.base.AbstractDictMap#initBeWrapped()
	 */
	@Override
	protected void initBeWrapped() {
		 putFieldWrapperMethodName("noteId", "getNoteId");
	}

}
