package com.leo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class test {
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:spring/spring*.xml");
    ProcessEngine processEngine = (ProcessEngine) ioc.getBean("processEngine");

    @Test
    public void test1() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("acti1.xml").deploy();
        System.out.println(deploy);
    }
    @Test
    public void test2(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = query.list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getId());
            System.out.println(processDefinition.getVersion());
            System.out.println(processDefinition.getKey());
            System.out.println();
        }
    }
}
