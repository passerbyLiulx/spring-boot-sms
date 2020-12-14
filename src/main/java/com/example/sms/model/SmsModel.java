package com.example.sms.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class SmsModel implements Serializable {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 阿里云模板管理code
     */
    private String templateCode;

    /**
     * 模板参数 格式："{\"code\":\"123456\"}"
     */
    private String templateParamJson;

    /**
     * outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
     */
    private String outId;
}
