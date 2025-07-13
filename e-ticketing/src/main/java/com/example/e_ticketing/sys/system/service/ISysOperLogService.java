package com.example.e_ticketing.sys.system.service;

import com.example.e_ticketing.sys.system.domain.SysOperLog;

import java.util.List;

public interface ISysOperLogService
{
    void insertOperlog(SysOperLog operLog);

    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    int deleteOperLogByIds(Long[] operIds);

    SysOperLog selectOperLogById(Long operId);

    void cleanOperLog();
}
