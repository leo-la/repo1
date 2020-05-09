package com.leo.manager.service;

import com.leo.pojo.*;

import java.util.List;
import java.util.Map;

public interface IAuthService {

    boolean addAuthAccttype(String type);

    Integer findAuthCount();

    Integer findStatus();

    AuthProcess findAuthByMid();

    Integer updateStatus(Integer mid,String status);

    boolean updateAuthCertImg(Integer certid,String uri);

    void clearCertImg();

    boolean updateCode(AuthProcess authProcess);

    boolean updateProcess(AuthProcess authProcess);

    String findCode();

    Member findMemberNameByprocess(String proid);

    Map<String, String> findCertImgPage(Integer mid);
}
