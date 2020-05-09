package com.leo.template;

import com.leo.manager.dao.BaseDao;
import com.leo.manager.dao.IPermissionDao;
import com.leo.pojo.Member;
import com.leo.pojo.PageBean;

import java.util.List;

/**
 * 客户页面数据查询模板
 */
public class MemberConditionPage extends PageSearchTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean,BaseDao dao) {
        IPermissionDao permissionDao = (IPermissionDao) dao;
        String str = pageBean.getStr();

        if(str.contains("%")){
            str = str.replaceAll("%", "\\\\%");
        }
        String condition = "%"+str+"%";
        pageBean.setTotalCount(permissionDao.findMemberCountByCondition(condition));
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
        String str = pageBean.getStr();

        if(str.contains("%")){
            str = str.replaceAll("%", "\\\\%");
        }
        String condition = "%"+str+"%";
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<Member> members = permissionDao.findMemberByCondition(condition,start, size);
        pageBean.setPageData(members);
        return pageBean;
    }
}
