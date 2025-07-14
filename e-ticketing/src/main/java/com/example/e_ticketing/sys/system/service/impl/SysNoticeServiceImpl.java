package com.example.e_ticketing.sys.system.service.impl;

import com.example.e_ticketing.sys.system.domain.SysNotice;
import com.example.e_ticketing.sys.system.domain.SysNoticeTarget;
import com.example.e_ticketing.sys.system.mapper.SysNoticeMapper;
import com.example.e_ticketing.sys.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public List<SysNotice> selectNoticesForUser(SysNotice notice, Long userId) {
        return noticeMapper.selectNoticesForUser(notice, userId);
    }

    @Override
    public void insertIfNotExists(Long noticeId, Long userId) {
        noticeMapper.insertIfNotExists(noticeId, userId);
    }

    @Override
    public boolean markSeen(Long noticeId, Long userId) {
        return noticeMapper.markSeen(noticeId, userId) > 0;
    }

    @Override
    public boolean isSeen(Long noticeId, Long userId) {
        Boolean seen = noticeMapper.isSeen(noticeId, userId);
        return seen != null && seen;
    }

    @Override
    @Transactional
    public int insertNotice(SysNotice notice)
    {
        // Step 1: Insert the notice and get the auto-generated noticeId
        int rows = noticeMapper.insertNotice(notice);
        Long noticeId = notice.getNoticeId(); // should now be set

        // Step 2: Set the noticeId to all targets
        if (notice.getTargets() != null) {
            for (SysNoticeTarget target : notice.getTargets()) {
                target.setNoticeId(noticeId); // this overrides any null value
            }
            noticeMapper.insertNoticeTargets(notice.getTargets());
        }

        return rows;
    }

    @Override
    @Transactional
    public int updateNotice(SysNotice notice)
    {
        // 1. Update the notice
        int rows = noticeMapper.updateNotice(notice);

        // 2. Delete previous targets
        noticeMapper.deleteNoticeTargetsByNoticeId(notice.getNoticeId());

        // 3. Insert new targets if provided
        if (notice.getTargets() != null && !notice.getTargets().isEmpty()) {
            for (SysNoticeTarget target : notice.getTargets()) {
                target.setNoticeId(notice.getNoticeId());
            }
            noticeMapper.insertNoticeTargets(notice.getTargets());
        }

        return rows;
    }

    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
