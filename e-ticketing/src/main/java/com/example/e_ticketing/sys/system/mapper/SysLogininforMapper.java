package com.example.e_ticketing.sys.system.mapper;

import com.example.e_ticketing.sys.system.domain.SysLogininfor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLogininforMapper
{
    void insertLogininfor(SysLogininfor logininfor);

    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    int deleteLogininforByIds(Long[] infoIds);

    int cleanLogininfor();
}
