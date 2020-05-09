package com.leo.manager.service;

import com.leo.pojo.*;

import java.util.List;

public interface IBusinessService {
    PageBean findAllCerts(PageBean pageBean);

    List<Cert> findCerts();

    Integer updateTypeCert(List<TypeCert> typeCert);

    List<Integer> findTypeCert();
}
