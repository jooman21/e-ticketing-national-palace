package com.example.e_ticketing.sys.common.utils.ip;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.e_ticketing.sys.common.config.NationalPalaceConfig;
import com.example.e_ticketing.sys.common.constant.Constants;
import com.example.e_ticketing.sys.common.utils.StringUtils;
import com.example.e_ticketing.sys.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        if (IpUtils.internalIp(ip))
        {
            return "Private Network IP";
        }
        if (NationalPalaceConfig.isAddressEnabled())
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    log.error("Error occurred while retrieving the geographic location {}", ip);                    return UNKNOWN;
                }
                JSONObject obj = JSON.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                log.error("Error occurred while retrieving the geographic location {}", ip);
            }
        }
        return UNKNOWN;
    }
}
