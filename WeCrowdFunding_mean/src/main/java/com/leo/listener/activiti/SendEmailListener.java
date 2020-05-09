package com.leo.listener.activiti;

import com.leo.utils.MailUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class SendEmailListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("发送邮件...");
        String email = (String) delegateExecution.getVariable("email");
        String  code = (String) delegateExecution.getVariable("code");
        MailUtils.sendMail(email,code,"验证码");
    }
}
