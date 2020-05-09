package com.leo.manager.controller;

import com.leo.manager.service.IAuthService;
import com.leo.manager.service.IBusinessService;
import com.leo.manager.service.IPermissionService;
import com.leo.manager.service.IUserService;
import com.leo.pojo.AuthProcess;
import com.leo.pojo.Cert;
import com.leo.pojo.Member;
import com.leo.pojo.PageBean;
import com.leo.pojo.Process;
import com.leo.utils.FileUtils;
import com.leo.utils.LogUtils;
import com.leo.utils.MailUtils;
import com.leo.utils.StorageUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 认证控制器
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    IAuthService authService;
    @Autowired
    IUserService userService;
    @Autowired
    IPermissionService permissionService;
    @Autowired
    IBusinessService businessService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    /**
     * 上传文件磁盘地址
     */
    @Value("${certs}")
    private String staticCertsPath;

    /**
     * 跳转认证流程-认证账户分类
     * @param model
     * @return
     */
    @RequestMapping("/toAccountType")
    public String toAccountType(Model model,HttpServletRequest request){
        Integer authCount = authService.findAuthCount(StorageUtils.getMember(request));
        model.addAttribute("authCount",authCount);
        return "accttype";
    }

    /**
     * 跳转认证流程-认证真实用户信息
     * @param model
     * @return
     */
    @RequestMapping("/toAuthDer")
    public String toAuthDer(Model model, HttpServletRequest request){
        Member user = (Member) request.getSession().getAttribute("user");
        Member member = userService.findMemberById(user.getId());
        model.addAttribute("member",member);
        return "apply";
    }

    /**
     * 跳转认证流程-认证资质文件
     * @param model
     * @return
     */
    @RequestMapping("/toApplyDer")
    public String toApplyDer(Model model,HttpServletRequest request){
        Member user = (Member) request.getSession().getAttribute("user");
        List<Cert> certs = businessService.findCerts();
        List<Integer> certids = businessService.findTypeCert(user);
        List<Cert> list = new ArrayList<>();
        for (Cert cert : certs) {
            for (Integer certid : certids) {
                if(cert.getId()==certid){
                    list.add(cert);
                }
            }
        }
        model.addAttribute("certs",list);
        return "apply-1";
    }

    /**
     * 跳转认证流程-认证邮箱
     * @param model
     * @return
     */
    @RequestMapping("/toEmailDer")
    public String toEmailDer(Model model, HttpServletRequest request){
        Member user = (Member) request.getSession().getAttribute("user");
        String email = user.getEmail();
        model.addAttribute("email",email);
        return "apply-2";
    }

    /**
     * 跳转认证流程-提交认证
     * @return
     */
    @RequestMapping("/toFinishDer")
    public String toFinishDer(){
        return "apply-3";
    }

    /**
     * 跳转认证流程-开始认证流程
     * 根据状态判断流程进度
     * @param model
     * @return
     */
    @RequestMapping("/toAuth")
    public String toAuth(Model model,HttpServletRequest request){
        Integer status = authService.findStatus(StorageUtils.getMember(request));
        Member user = (Member) request.getSession().getAttribute("user");
        model.addAttribute("member",user);
        if(status==0){
            return "accttype";
        }else if(status==1){
            return "redirect:/auth/toAuthDer";
        }else if(status==2){
            return "redirect:/auth/toApplyDer";
        }else if(status==3){
            return "redirect:/auth/toEmailDer";
        }else{
            return "redirect:/auth/toFinishDer";
        }
    }

    /**
     * 认证流程-跳转认证审核页面
     * @param s
     * @param model
     * @return
     */
    @RequestMapping("/toAuthCert/{userid}")
    public String toAuthCert(@PathVariable("userid") String s,Model model){
        String mid = s.split(",")[0].substring(1);
        String taskid = s.split(",")[1].substring(0,s.split(",")[1].length()-1);
        Member member = userService.findMemberById(Integer.parseInt(mid));
        model.addAttribute("member",member);
        Map<String, String> map = authService.findCertImgPage(Integer.parseInt(mid));
        model.addAttribute("map",map);
        model.addAttribute("taskid",taskid);
        return "authCert";
    }

    /**
     * 过期方法-待验证
     * @param member
     * @return
     */
    @RequestMapping("/applyNext")
    public String applyNext(Member member,HttpServletRequest request){
        Member user = (Member) request.getSession().getAttribute("user");

        authService.updateStatus(user.getId(),"3");
        member.setId(user.getId());
        userService.updateMemberByMid(member);
        return "redirect:/auth/toApplyDer";

    }

    /**
     * 认证流程-认证分类-提交分类申请
     * @param title
     * @return
     */
    @RequestMapping("/authAccttype/{title}")
    public String authAccttype(@PathVariable String title,HttpServletRequest request){
        title = title.substring(1,title.length()-1);
        authService.addAuthAccttype(title,StorageUtils.getMember(request));
        return "redirect:/auth/toAuthDer";
    }

    /**
     * 认证流程-认证资质文件-图片文件显示
     * @param image_name
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/image/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String image_name) throws Exception{ byte[] imageContent ;
        String path = staticCertsPath+image_name+".png";
        imageContent = FileUtils.fileToByte(new File(path));

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
    }

    /**
     * 认证流程-认证资质文件-更新资质文件
     * @param file
     * @param certid
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateCertImg")
    public boolean updateAvatar(MultipartFile file,Integer certid,HttpServletRequest request){
        boolean isSuccess = false;
        try{
            String filename = file.getOriginalFilename();
            filename = UUID.randomUUID().toString().replace("-","") + "_" + filename;
            String realPath = staticCertsPath;
            isSuccess = authService.updateAuthCertImg(certid,filename,StorageUtils.getMember(request));
            FileUtils.uploadFile(file,filename,realPath);
            isSuccess = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**
     * 认证流程-认证资质文件-清空资质文件
     * @return
     */
    @ResponseBody
    @RequestMapping("/clearCertImg")
    public void clearCertImg(HttpServletRequest request){
        authService.clearCertImg(StorageUtils.getMember(request));
    }

    /**
     * 认证流程-启动认证流程实例
     * @param authProcess
     * @return
     */
    @ResponseBody
    @RequestMapping("/starProcess")
    public boolean starProcess(AuthProcess authProcess,HttpServletRequest request){
        Member user = StorageUtils.getMember(request);
        Map<String,Object> variable = new HashMap<>();
        String code = "";
        for(int i=0;i<4;i++){
            code += new Random().nextInt(10);
        }
        variable.put("code",code);
        variable.put("email",user.getEmail());
        authProcess.setCode(code);
        String code1 = authService.findCode(user);
        if(code1==null){
            ProcessDefinition auth = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();
            ProcessInstance instance = runtimeService.startProcessInstanceById(auth.getId(), variable);
            authProcess.setProid(instance.getId());
            LogUtils.getLogger().info("启动流程实例："+auth.getId());
            return authService.updateProcess(authProcess,user);
        }else {
            LogUtils.getLogger().info("发送验证码");
            MailUtils.sendMail(user.getEmail(),code,"验证码");
            return authService.updateCode(authProcess,user);
        }

    }

    /**
     * 认证流程-认证提交-审核验证码
     * @param incode
     * @return
     */
    @ResponseBody
    @RequestMapping("/apply")
    public boolean apply(@RequestBody String incode,HttpServletRequest request){
        incode = incode.split(":")[1].substring(1,incode.split(":")[1].length()-2);
        AuthProcess auth = authService.findAuthByMid(StorageUtils.getMember(request));
        if(incode.equals(auth.getCode())){
            Task auth2 = taskService.createTaskQuery().processInstanceId(auth.getProid()).taskDefinitionKey("auth2").singleResult();
            taskService.complete(auth2.getId());
            userService.updateStatus("1");

            userService.updateUserInfo();
            return true;
        }else {
            return false;
        }
    }

    /**
     * 认证流程-认证审核-通过审核
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/passAuth")
    public boolean passAuth(@RequestBody String s){
        String s1 = s.split(",")[0].split(":")[1];
        String memberid = s1.substring(1,s1.length()-1);
        String s2 = s.split(",")[1].split(":")[1];
        String taskid = s2.substring(1,s2.length()-2);

        try{
            taskService.setVariable(taskid,"flag",true);
            taskService.setVariable(taskid,"memberid",Integer.parseInt(memberid));
            taskService.complete(taskid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 认证流程-认证审核-审核未通过
     * @param s
     * @return
     */
    @ResponseBody
    @RequestMapping("/refuseAuth")
    public boolean refuseAuth(@RequestBody String s){
        String s1 = s.split(",")[0].split(":")[1];
        String memberid = s1.substring(1,s1.length()-1);
        String s2 = s.split(",")[1].split(":")[1];
        String taskid = s2.substring(1,s2.length()-2);

        try{
            taskService.setVariable(taskid,"flag",false);
            taskService.setVariable(taskid,"memberid",Integer.parseInt(memberid));
            taskService.complete(taskid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 认证流程-认证审核-查询认证流程管理页面数据
     * @param pageBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchAuthCertPage")
    public PageBean searchAuthCertPage(@RequestBody PageBean pageBean){
        TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth").taskCandidateGroup("backuser");
        ProcessDefinition auth = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();
        String authName = auth.getName();
        int version = auth.getVersion();
        Integer pageSize = pageBean.getPageSize();
        Integer currentPage = pageBean.getCurrentPage();
        //count
        long taskCount = taskQuery.count();
        //currentPage
        if(currentPage==null){
            currentPage = 1;
            pageBean.setCurrentPage(1);
        }
        //totalPage
        long totalPage = taskCount % pageSize == 0 ? taskCount / pageSize : (taskCount / pageSize + 1);
        //data
        Integer start = (currentPage-1) * pageSize;
        Integer size = pageSize;
        List<Task> tasks = taskQuery.listPage(start, size);
        List<Process> processDefinition = new ArrayList<>();
        Process definition = null;
        for (Task task : tasks) {
            Member member = authService.findMemberNameByprocess(task.getProcessInstanceId());
            definition = new Process();
            definition.setName(authName);
            definition.setKey(task.getName());
            definition.setVersion(version);
            definition.setUsername(member.getUsername());
            definition.setUserid(member.getId());
            definition.setTaskid(task.getId());
            processDefinition.add(definition);
        }
        pageBean.setPageData(processDefinition);
        pageBean.setTotalPage((int) totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setTotalCount((int)taskCount);
        return pageBean;
    }
}
