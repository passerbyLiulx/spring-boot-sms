package com.example.sms.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.sms.config.SmsConfig;
import com.example.sms.model.SmsModel;
import com.example.sms.model.SmsQueryModel;
import com.example.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送短信封装服务实现类
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Resource
    private SmsConfig smsConfig;

    /**
     * 阿里云短信服务实现短信发送
     * 发送验证码类的短信时，每个号码每分钟最多发送一次，每个小时最多发送5次。其它类短信频控请参考阿里云
     * @param phoneNumbers 手机号
     * @param templateCode 阿里云短信模板code
     * @param templateParamJson 模板参数json {"code":"123456"}
     * @return
     */
    @Override
    public SendSmsResponse sendSms(String phoneNumbers, String templateCode, String templateParamJson, String outID) {

        // 封装短信发送对象
        SmsModel smsModel = new SmsModel();
        smsModel.setPhoneNumbers(phoneNumbers);
        smsModel.setTemplateParamJson(templateParamJson);
        smsModel.setTemplateCode(templateCode);
        smsModel.setOutId(outID);

        // 获取短信发送服务机
        IAcsClient acsClient = getClient();

        //获取短信请求
        SendSmsRequest request = getSmsRequest(smsModel);
        SendSmsResponse sendSmsResponse = new SendSmsResponse();

        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("发送短信发生错误。错误代码是 [{}]，错误消息是 [{}]，错误请求ID是 [{}]，错误Msg是 [{}]，错误类型是 [{}]",
                    e.getErrCode(),
                    e.getMessage(),
                    e.getRequestId(),
                    e.getErrMsg(),
                    e.getErrorType());
        }
        return sendSmsResponse;
    }

    /**
     * 查询发送短信的内容
     * @param bizId 短信对象的对应的bizId
     * @param phoneNumber 手机号
     * @param pageSize 分页大小
     * @param currentPage 当前页码
     * @return
     */
    @Override
    public QuerySendDetailsResponse querySendDetails(String bizId, String phoneNumber, Long pageSize, Long currentPage) {

        // 查询实体封装
        SmsQueryModel smsQueryModel = new SmsQueryModel();
        smsQueryModel.setBizId(bizId);
        smsQueryModel.setPhoneNumber(phoneNumber);
        smsQueryModel.setCurrentPage(currentPage);
        smsQueryModel.setPageSize(pageSize);
        smsQueryModel.setSendDate(new Date());

        // 获取短信发送服务机
        IAcsClient acsClient = getClient();
        QuerySendDetailsRequest request = getSmsQueryRequest(smsQueryModel);
        QuerySendDetailsResponse querySendDetailsResponse = null;
        try {
            querySendDetailsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            log.error("查询发送短信发生错误。错误代码是 [{}]，错误消息是 [{}]，错误请求ID是 [{}]，错误Msg是 [{}]，错误类型是 [{}]",
                    e.getErrCode(),
                    e.getMessage(),
                    e.getRequestId(),
                    e.getErrMsg(),
                    e.getErrorType());
        }
        return querySendDetailsResponse;
    }

    /**
     * 短信发送实体
     * @param smsModel
     * @return
     */
    private SendSmsRequest getSmsRequest(SmsModel smsModel) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(smsModel.getPhoneNumbers());
        request.setSignName(smsConfig.getSignName());
        request.setTemplateCode(smsModel.getTemplateCode());
        request.setTemplateParam(smsModel.getTemplateParamJson());
        request.setOutId(smsModel.getOutId());
        return request;
    }

    /**
     * 封装查询阿里云短信请求对象
     * @param smsQueryModel
     * @return
     */
    private QuerySendDetailsRequest getSmsQueryRequest(SmsQueryModel smsQueryModel) {
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber(smsQueryModel.getPhoneNumber());
        request.setBizId(smsQueryModel.getBizId());
        request.setSendDate(new SimpleDateFormat(smsConfig.getDateFormat()).format(smsQueryModel.getSendDate()));
        request.setPageSize(smsQueryModel.getPageSize());
        request.setCurrentPage(smsQueryModel.getCurrentPage());
        return request;
    }

    /**
     * 获取短信发送服务机
     * @return
     */
    private IAcsClient getClient() {

        IClientProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(),
                smsConfig.getAccessKeyId(),
                smsConfig.getAccessKeySecret());

        try {
            DefaultProfile.addEndpoint(smsConfig.getEndpointName(),
                    smsConfig.getRegionId(),
                    smsConfig.getProduct(),
                    smsConfig.getDomain());
        } catch (ClientException e) {
            log.error("获取短信发送服务机发生错误。错误代码是 [{}]，错误消息是 [{}]，错误请求ID是 [{}]，错误Msg是 [{}]，错误类型是 [{}]",
                    e.getErrCode(),
                    e.getMessage(),
                    e.getRequestId(),
                    e.getErrMsg(),
                    e.getErrorType());
        }
        return new DefaultAcsClient(profile);
    }
}

