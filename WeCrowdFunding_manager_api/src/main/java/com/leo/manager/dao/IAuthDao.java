package com.leo.manager.dao;


import com.leo.pojo.AuthProcess;
import com.leo.pojo.Cert;
import com.leo.pojo.Member;
import com.leo.pojo.MemberCert;

import java.util.List;

public interface IAuthDao extends BaseDao {

    Integer addAuthprocess(Integer mid,String accttype,String status);

    Integer updateAuthCount(Integer mid,String accttype,String status);

    Integer findAuthCount(Integer mid);

    Integer findAllAuthCount();

    String findStatus(Integer mid);

    AuthProcess findAuthByMid(Integer mid);

    Integer updateStatus(Integer mid, String status);

    Integer deleteAuthCertImg(Integer memberid);

    Integer addAuthCertImg(Integer memberid, Integer certid,String iconpath);

    Integer updateProcess(AuthProcess authProcess);

    Integer updateCode(AuthProcess authProcess);

    String findCode(Integer mid);

    Member findMemberNameByprocess(String proid);

    List<MemberCert> findMemberCertByMid(Integer mid);

    List<Cert> findAllCerts();

}
