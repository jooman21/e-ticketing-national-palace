package com.example.e_ticketing.sys.system.service;

import com.palace.museum.system.domain.SysConfig;

import java.util.List;

public interface ISysConfigService
{
    SysConfig selectConfigById(Long configId);
    String selectConfigByKey(String configKey);
    boolean selectCaptchaEnabled();
    List<SysConfig> selectConfigList(SysConfig config);
    int insertConfig(SysConfig config);
    int updateConfig(SysConfig config);
    void deleteConfigByIds(Long[] configIds);
    void loadingConfigCache();
    void clearConfigCache();
    void resetConfigCache();
    boolean checkConfigKeyUnique(SysConfig config);
}
