package com.leo.manager.dao;

import com.leo.pojo.*;

import java.util.List;

public interface IBusinessDao extends BaseDao {
    List<Cert> findAllCerts();

    List<TypeCert> findAllTypeCert();

    Integer deleteAllTypeCert();

    Integer addTypeCert(TypeCert typeCerts);

    List<Integer> findCertids(String accttype);
}
