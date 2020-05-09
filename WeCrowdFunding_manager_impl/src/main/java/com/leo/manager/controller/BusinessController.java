package com.leo.manager.controller;

import com.leo.manager.service.IBusinessService;
import com.leo.pojo.PageBean;
import com.leo.pojo.Process;
import com.leo.pojo.TypeCert;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务控制器
 */
@Controller
@RequestMapping("/business")
public class BusinessController {
    /**
     *流程库服务
     */
    @Autowired
    RepositoryService repositoryService;
    /**
     * 业务服务
     */
    @Autowired
    IBusinessService businessService;

    /**
     * 跳转资质维护
     * @return
     */
    @RequestMapping("/toCert")
    public String toCert(){
        return "cert";
    }

    /**
     * 跳转分类管理
     * @return
     */
    @RequestMapping("/toType")
    public String toType(){
        return "type";
    }

    /**
     * 跳转流程管理
     * @return
     */
    @RequestMapping("/toProcess")
    public String toProcess(){
        return "process";
    }

    /**
     * 跳转广告管理
     * @return
     */
    @RequestMapping("/toAdvertisement")
    public String toAdvertisement(){
        return "advertisement";
    }

    /**
     * 跳转消息模板
     * @return
     */
    @RequestMapping("/toMessage")
    public String toMessage(){
        return "message";
    }

    /**
     * 跳转项目分类
     * @return
     */
    @RequestMapping("/toProjectType")
    public String toProjectType(){
        return "project_type";
    }

    /**
     * 跳转项目标签
     * @return
     */
    @RequestMapping("/toTag")
    public String toTag(){

        return "tag";
    }

    /**
     * 跳转显示流程图页面
     * @param s
     * @param model
     * @return
     */
    @RequestMapping("/toShowProcess/{id}")
    public String toShowProcess(@PathVariable("id") String s,Model model){
        String id = s.substring(1,s.length()-1);
        model.addAttribute("id",id);
        return "showProcess";
    }

    /**
     * 流程管理-显示流程图
     * @param s
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/showProcessIMG/{id}")
    public void showProcess(@PathVariable("id") String s, HttpServletResponse response) throws IOException {
        String id = s.substring(1,s.length()-1);
        ProcessDefinition query = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();

        InputStream resourceAsStream = repositoryService.getResourceAsStream(query.getDeploymentId(),query.getDiagramResourceName());
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(resourceAsStream,outputStream);
    }

    /**
     * 流程管理-查找流程页面信息
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchProcessPage")
    public PageBean searchUserPage(@RequestBody PageBean pageBean){
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        Integer pageSize = pageBean.getPageSize();
        Integer currentPage = pageBean.getCurrentPage();
        //count
        long processCount = query.count();
        //currentPage
        if(currentPage==null){
            currentPage = 1;
            pageBean.setCurrentPage(1);
        }
        //totalPage
        long totalPage = processCount % pageSize == 0 ? processCount / pageSize : (processCount / pageSize + 1);
        //data
        Integer start = (currentPage-1) * pageSize;
        Integer size = pageSize;
        List<ProcessDefinition> processes = query.listPage(start, size);

        List<Process> processDefinition = new ArrayList<>();
        Process definition = null;
        for (ProcessDefinition process : processes) {
            definition = new Process();
            definition.setName(process.getName());
            definition.setKey(process.getKey());
            definition.setVersion(process.getVersion());
            definition.setId(process.getId());
            processDefinition.add(definition);
        }
        pageBean.setPageData(processDefinition);
        pageBean.setTotalPage((int) totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalCount((int)processCount);
        return pageBean;
    }

    /**
     * 路程管理-删除流程
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteProcess/{id}")
    public boolean deleteProcess(@PathVariable("id") String id){
        id = id.substring(1,id.length()-1);
        try{
            ProcessDefinition query = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
            repositoryService.deleteDeployment(query.getDeploymentId(),true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 流程管理-添加流程
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping("/addProcess")
    public boolean addProcess(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            repositoryService.createDeployment().addInputStream(originalFilename,inputStream).deploy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分类管理-查找分类管理页面信息
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchCertTypePage")
    public PageBean searchCertTypePage(PageBean pageBean){
        pageBean = businessService.findAllCerts(pageBean);
        return pageBean;
    }

    /**
     * 分类管理-更新分类管理数据
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateCertType")
    public boolean updateCertType(@RequestBody List<TypeCert> s){
        businessService.updateTypeCert(s);
        return true;
    }
}
