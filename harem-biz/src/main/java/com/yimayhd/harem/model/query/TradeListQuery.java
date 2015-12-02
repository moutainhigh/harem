package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TradeListQuery extends BaseQuery {
    private Long bizOrderId;//交易号
    private String phone;//手机号
    private String userName;//会员名
    private String terminalName;//终端编号
    private String beginDate;//交易开始时间
    private String endDate;//交易结束时间

    public Long getBizOrderId() {
        return bizOrderId;
    }

    public void setBizOrderId(Long bizOrderId) {
        this.bizOrderId = bizOrderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
