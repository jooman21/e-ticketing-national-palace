package com.example.e_ticketing.sys.controller.monitor;

import com.example.e_ticketing.sys.common.annotation.Log;
import com.example.e_ticketing.sys.common.core.controller.BaseController;
import com.example.e_ticketing.sys.common.core.domain.AjaxResult;
import com.example.e_ticketing.sys.common.core.page.TableDataInfo;
import com.example.e_ticketing.sys.common.enums.RequestType;
import com.example.e_ticketing.sys.common.utils.poi.ExcelUtil;
import com.example.e_ticketing.sys.system.domain.SysOperLog;
import com.example.e_ticketing.sys.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController
{
    @Autowired
    private ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog)
    {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "Operation log", businessType = RequestType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog)
    {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "Operation log");
    }

    @Log(title = "Operation log", businessType = RequestType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds)
    {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "Operation log", businessType = RequestType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        operLogService.cleanOperLog();
        return success();
    }
}
