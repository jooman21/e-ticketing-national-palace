package com.example.e_ticketing.sys.system.mapper;

import com.example.e_ticketing.sys.system.domain.SysNotice;
import com.example.e_ticketing.sys.system.domain.SysNoticeTarget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysNoticeMapper
{
    SysNotice selectNoticeById(Long noticeId);

    List<SysNotice> selectNoticeList(SysNotice notice);
    List<SysNotice> selectNoticesForUser(SysNotice notice, Long userId);

    int insertNotice(SysNotice notice);

    int insertNoticeTargets(@Param("list") List<SysNoticeTarget> targets);
    int deleteNoticeTargetsByNoticeId(Long noticeId);

    int updateNotice(SysNotice notice);

    int deleteNoticeById(Long noticeId);

    int deleteNoticeByIds(Long[] noticeIds);

    int markSeen(@Param("noticeId") Long noticeId, @Param("userId") Long userId);
    int insertIfNotExists(@Param("noticeId") Long noticeId, @Param("userId") Long userId);
    Boolean isSeen(@Param("noticeId") Long noticeId, @Param("userId") Long userId);
}
