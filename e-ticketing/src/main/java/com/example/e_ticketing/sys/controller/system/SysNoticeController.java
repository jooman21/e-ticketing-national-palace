package com.example.e_ticketing.sys.controller.system;

import com.palace.museum.common.annotation.Log;
import com.palace.museum.common.core.controller.BaseController;
import com.palace.museum.common.core.domain.AjaxResult;
import com.palace.museum.common.core.page.TableDataInfo;
import com.palace.museum.common.enums.RequestType;
import com.palace.museum.system.domain.SysNotice;
import com.palace.museum.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController
{
    @Autowired
    private ISysNoticeService noticeService;

    @PreAuthorize("@ss.hasPermi('system:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice)
    {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId)
    {
        return success(noticeService.selectNoticeById(noticeId));
    }

    @PreAuthorize("@ss.hasPermi('system:notice:add')")
    @Log(title = "Notification Announcement", businessType = RequestType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice)
    {
        notice.setCreateBy(getUsername());
        return toAjax(noticeService.insertNotice(notice));
    }

    @PreAuthorize("@ss.hasPermi('system:notice:edit')")
    @Log(title = "Notification Announcement", businessType = RequestType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice)
    {
        notice.setUpdateBy(getUsername());
        return toAjax(noticeService.updateNotice(notice));
    }

    @PreAuthorize("@ss.hasPermi('system:notice:remove')")
    @Log(title = "Notification Announcement", businessType = RequestType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds)
    {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
