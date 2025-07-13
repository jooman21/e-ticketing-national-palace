package com.example.e_ticketing.sys.system.service;

import com.example.e_ticketing.sys.system.domain.SysLogininfor;

import java.util.List;

public interface ISysLogininforService
{
    void insertLogininfor(SysLogininfor logininfor);

    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    int deleteLogininforByIds(Long[] infoIds);

    void cleanLogininfor();
}
