package com.leo.factory;

import com.leo.enums.PageTemplateType;
import com.leo.template.*;

/**
 * 页面数据模板工厂
 */
public class PageTemplateFactory {
    public static PageSearchTemplate createTemplate(PageTemplateType type){
        switch (type){
            case USER_PAGE:
                return new UserPage();
            case MEMBER_PAGE:
                return new MemberPage();
            case ROLE_PAGE:
                return new RolePage();
            case USER_CONDITION_PAGE:
                return new UserConditionPage();
            case MEMBER_CONDITION_PAGE:
                return new MemberConditionPage();
            case ROLE_CONDITION_PAGE:
                return new RoleConditionPage();
                default:
                    return null;
        }
    }
}
