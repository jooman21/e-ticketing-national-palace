package com.example.e_ticketing.sys.system.mapper;

import com.example.e_ticketing.sys.system.domain.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysOperLogMapper
{
    void insertOperlog(SysOperLog operLog);

    List<SysOperLog> selectOperLogList(SysOperLog operLog);

    int deleteOperLogByIds(Long[] operIds);

    SysOperLog selectOperLogById(Long operId);

    void cleanOperLog();
}
