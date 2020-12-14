package com.example.sms.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

/**
 * 阿里云短信发送service
 */
public interface SmsService {

    /**
     * 阿里云短信服务实现短信发送
     * 发送验证码类的短信时，每个号码每分钟最多发送一次，每个小时最多发送5次。其它类短信频控请参考阿里云
     * @param phoneNumbers 手机号
     * @param templateCode 阿里云短信模板code
     * @param templateParamJson 模板参数json {"sellerName":"123456","orderSn":"123456"}
     * @return
     */
    SendSmsResponse sendSms(String phoneNumbers, String templateCode, String templateParamJson, String outId);

    /**
     * 查询发送短信的内容
     * @param bizId 短信对象的对应的bizId
     * @param phoneNumber 手机号
     * @param pageSize 分页大小
     * @param currentPage 当前页码
     * @return
     */
    QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber, Long pageSize, Long currentPage);
}
