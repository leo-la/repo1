package com.leo.listener.activiti;

import com.leo.manager.dao.IAuthDao;
import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IAuthService;
import com.leo.manager.service.IUserService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class NoListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        Integer memberid = (Integer) delegateExecution.getVariable("memberid");
        System.out.println("审查通过---memberid:"+memberid);
        WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
        IAuthService authService = applicationContext.getBean(IAuthService.class);
        authService.updateStatus(memberid,"4");
        IUserService userService = applicationContext.getBean(IUserService.class);
        userService.updateStatusByid("0",memberid);
    }
}
