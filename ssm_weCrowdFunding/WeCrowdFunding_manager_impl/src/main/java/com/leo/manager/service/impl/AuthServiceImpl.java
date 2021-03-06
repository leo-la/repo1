package com.leo.manager.service.impl;

import com.leo.manager.dao.IAuthDao;
import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IAuthService;
import com.leo.manager.service.IMainService;
import com.leo.pojo.*;
import com.leo.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    IAuthDao authDao;
    @Autowired
    IUserDao userDao;


    @Override
    public boolean addAuthAccttype(String type) {
        String accttype = "0";
        if(type.equals("商业公司")){
            accttype = "0";
        }else if(type.equals("个体工商户")){
            accttype = "1";
        }else if(type.equals("个人经营")){
            accttype = "2";
        }else if(type.equals("政府及非营利组织")){
            accttype = "3";
        }
        Integer mid = ApplicationUtils.getUserInfo().getId();
        AuthProcess auth = authDao.findAuthByMid(mid);
        Integer integer = 0;
        if(auth==null){
            integer = authDao.addAuthprocess(mid, accttype,"1");
            userDao.updateAccttype(accttype,mid);
        }else{
            integer = authDao.updateAuthCount(mid, accttype,"1");
            userDao.updateAccttype(accttype,mid);
        }

        if(integer==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Integer findAuthCount() {
        Integer mid = ApplicationUtils.getUserInfo().getId();
        AuthProcess auth = authDao.findAuthByMid(mid);
        if(auth==null){
            return 0;
        }else{
            return authDao.findAuthCount(mid);
        }
    }

    @Override
    public Integer findStatus() {
        Integer mid = ApplicationUtils.getUserInfo().getId();
        AuthProcess auth = authDao.findAuthByMid(mid);
        if(auth==null){
            return 0;
        }else{
            return Integer.parseInt(authDao.findStatus(mid));
        }
    }

    @Override
    public AuthProcess findAuthByMid() {
        AuthProcess authByMid = authDao.findAuthByMid(ApplicationUtils.getUserInfo().getId());
        return authByMid;
    }

    @Override
    public Integer updateStatus(Integer mid, String status) {
        Integer integer = authDao.updateStatus(mid, status);
        return integer;
    }

    @Override
    public boolean updateAuthCertImg(Integer certid,String uri) {
        Integer memberid = ApplicationUtils.getUserInfo().getId();
        Integer integer1 = authDao.addAuthCertImg(memberid, certid, uri);
        if(integer1>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void clearCertImg() {
        Integer memberid = ApplicationUtils.getUserInfo().getId();
        Integer integer = authDao.deleteAuthCertImg(memberid);
    }

    @Override
    public boolean updateCode(AuthProcess authProcess) {
        authProcess.setMid(ApplicationUtils.getUserInfo().getId());
        Integer integer = authDao.updateCode(authProcess);
        if(integer==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateProcess(AuthProcess authProcess) {
        authProcess.setMid(ApplicationUtils.getUserInfo().getId());
        Integer integer = authDao.updateProcess(authProcess);
        if(integer==1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public String findCode() {
        return authDao.findCode(ApplicationUtils.getUserInfo().getId());
    }

    @Override
    public Member findMemberNameByprocess(String proid) {
        Member member = authDao.findMemberNameByprocess(proid);
        return member;
    }

    @Override
    public Map<String, String> findCertImgPage(Integer mid) {
        List<MemberCert> certs = authDao.findMemberCertByMid(mid);
        List<Cert> allCerts = authDao.findAllCerts();
        Map<String, String> map = new HashMap<>();
        for (MemberCert cert : certs) {
            for (Cert allCert : allCerts) {
                if(cert.getCertid()==allCert.getId()){
                    map.put(allCert.getName(),cert.getIconpath());
                }
            }
        }
        return map;
    }
}
