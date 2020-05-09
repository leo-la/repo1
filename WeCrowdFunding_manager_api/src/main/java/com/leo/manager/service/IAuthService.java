package com.leo.manager.service;

import com.leo.pojo.*;

import java.util.List;
import java.util.Map;

public interface IAuthService {

    boolean addAuthAccttype(String type,Member member);

    Integer findAuthCount(Member member);

    Integer findStatus(Member member);

    AuthProcess findAuthByMid(Member member);

    Integer updateStatus(Integer mid,String status);

    boolean updateAuthCertImg(Integer certid,String uri,Member member);

    void clearCertImg(Member member);

    boolean updateCode(AuthProcess authProcess,Member member);

    boolean updateProcess(AuthProcess authProcess,Member member);

    String findCode(Member member);

    Member findMemberNameByprocess(String proid);

    Map<String, String> findCertImgPage(Integer mid);
}
