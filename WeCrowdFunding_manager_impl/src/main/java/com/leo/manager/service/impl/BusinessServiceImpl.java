package com.leo.manager.service.impl;

import com.leo.manager.dao.IAuthDao;
import com.leo.manager.dao.IBusinessDao;
import com.leo.manager.dao.IPermissionDao;
import com.leo.manager.dao.IUserDao;
import com.leo.manager.service.IBusinessService;
import com.leo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements IBusinessService {
    @Autowired
    IUserDao userDao;
    @Autowired
    IPermissionDao permissionDao;
    @Autowired
    IBusinessDao businessDao;
    @Autowired
    IAuthDao authDao;


    @Override
    public PageBean findAllCerts(PageBean pageBean) {
        List<Cert> certs = businessDao.findAllCerts();
        pageBean.setPageData(certs);
        List<TypeCert> typeCerts = businessDao.findAllTypeCert();
        pageBean.setPageData2(typeCerts);
        return pageBean;
    }

    @Override
    public List<Cert> findCerts() {
        List<Cert> certs = businessDao.findAllCerts();
        return certs;
    }

    @Override
    public Integer updateTypeCert(List<TypeCert> typeCert) {
        businessDao.deleteAllTypeCert();
        int count = 0;
        for (TypeCert cert : typeCert) {
            Integer integer = businessDao.addTypeCert(cert);
            count ++;
        }
        return count;
    }

    @Override
    public List<Integer> findTypeCert(Member member) {
        String accttype = member.getAccttype();
        if(Integer.parseInt(accttype)==0){
            accttype = "商业公司";
        }else if(Integer.parseInt(accttype)==1){
            accttype = "个体工商户";
        }else if(Integer.parseInt(accttype)==2){
            accttype = "个人经营";
        }else{
            accttype = "政府及非营利组织";
        }
        List<Integer> certids = businessDao.findCertids(accttype);
        return certids;
    }
}

