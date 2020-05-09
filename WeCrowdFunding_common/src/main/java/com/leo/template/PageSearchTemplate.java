package com.leo.template;

import com.leo.manager.dao.BaseDao;
import com.leo.manager.dao.IUserDao;
import com.leo.pojo.PageBean;

/**
 * 页面数据查询模板骨架
 */
public abstract class PageSearchTemplate {

    /**
     * 设置总记录数
     * @param pageBean
     * @return
     */
    public abstract PageBean getTotalCount(PageBean pageBean,BaseDao dao);

    /**
     * 设置当前页码
     * @param pageBean
     * @return
     */
    public abstract PageBean getCurrentPage(PageBean pageBean);
    /**
     * 设置总页数
     * @param pageBean
     * @return
     */
    public abstract PageBean getTotalPage(PageBean pageBean);

    /**
     * 设置数据
     * @param pageBean
     * @return
     */
    public abstract PageBean getData(PageBean pageBean,BaseDao dao);
}
