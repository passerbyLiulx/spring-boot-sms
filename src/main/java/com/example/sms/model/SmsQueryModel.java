package com.example.sms.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class SmsQueryModel implements Serializable {

    /**
     * 短信对象的对应的bizId
     */
    private String bizId;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 发送时间
     */
    private Date sendDate;

    /**
     * 分页大小
     */
    private Long pageSize;

    /**
     * 当前页码
     */
    private Long currentPage;
}
