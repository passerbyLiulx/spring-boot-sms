package com.example.sms.controller;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.sms.model.SmsModel;
import com.example.sms.model.SmsQueryModel;
import com.example.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 短信发送
     * @param smsModel
     * @return
     */
    @PostMapping("/send")
    @ResponseBody
    public SendSmsResponse send(@RequestBody SmsModel smsModel) {
        return smsService.sendSms(smsModel.getPhoneNumbers(),
                smsModel.getTemplateCode(),
                smsModel.getTemplateParamJson(),
                smsModel.getOutId());
    }

    /**
     * 短信查询
     * @param smsQueryModel
     * @return
     */
    @PostMapping("/query")
    @ResponseBody
    public QuerySendDetailsResponse query(@RequestBody SmsQueryModel smsQueryModel) {
        return smsService.querySendDetails(smsQueryModel.getBizId(),
                smsQueryModel.getPhoneNumber(), smsQueryModel.getPageSize(), smsQueryModel.getCurrentPage());
    }
}
