package com.leo.template;

import com.leo.manager.dao.BaseDao;
import com.leo.manager.dao.IPermissionDao;
import com.leo.manager.dao.IUserDao;
import com.leo.pojo.Member;
import com.leo.pojo.PageBean;
import com.leo.pojo.UserInfo;

import java.util.List;

/**
 * 客户页面数据查询模板
 */
public class MemberPage extends PageSearchTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean,BaseDao dao) {
        IPermissionDao permissionDao = (IPermissionDao) dao;
        pageBean.setTotalCount(permissionDao.findMembersCount());
        return pageBean;
    }

    @Override
    public PageBean getCurrentPage(PageBean pageBean) {
        if(pageBean.getCurrentPage()==null){
            pageBean.setCurrentPage(1);
        }
        return pageBean;
    }

    @Override
    public PageBean getTotalPage(PageBean pageBean) {
        Integer totalPage = pageBean.getTotalCount() % pageBean.getPageSize() == 0 ?
                pageBean.getTotalCount() / pageBean.getPageSize() : (pageBean.getTotalCount() / pageBean.getPageSize() + 1);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public PageBean getData(PageBean pageBean,BaseDao dao) {
        IPermissionDao permissionDao = (IPermissionDao) dao;
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<Member> members = permissionDao.searchMemberPage(start, size);
        pageBean.setPageData(members);
        return pageBean;
    }
}
