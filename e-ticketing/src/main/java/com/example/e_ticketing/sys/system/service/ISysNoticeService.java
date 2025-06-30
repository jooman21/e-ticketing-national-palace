package com.example.e_ticketing.sys.system.service;

import com.palace.museum.system.domain.SysNotice;

import java.util.List;


public interface ISysNoticeService
{
    SysNotice selectNoticeById(Long noticeId);

   List<SysNotice> selectNoticeList(SysNotice notice);

    int insertNotice(SysNotice notice);
    int updateNotice(SysNotice notice);

    int deleteNoticeById(Long noticeId);
    
    int deleteNoticeByIds(Long[] noticeIds);
}
