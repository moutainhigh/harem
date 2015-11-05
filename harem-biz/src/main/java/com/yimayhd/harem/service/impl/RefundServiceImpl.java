package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Refund;
import com.yimayhd.harem.service.RefundService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class RefundServiceImpl implements RefundService {
    @Override
    public List<Refund> getList(Refund refund) throws Exception{
        List<Refund> refundList = new ArrayList<Refund>();
        int j = 10;

        for (int i = 0;i <= j;i++){
            Refund refundData = new Refund();
            refundData.setRefundNO("2008100109" + i);//交易编号
            refundData.setUserName("张三" + i);
            refundData.setPhone("18618162075");
            refundData.setRefundMoney(BigDecimal.valueOf(488.88));
            refundData.setShouldRefundPoint(BigDecimal.valueOf(400));
            refundData.setAvailablePoint(BigDecimal.valueOf(200));
            refundData.setDeductMoneyOffsetPoint(BigDecimal.valueOf(20));
            refundData.setFactRefundMoney(BigDecimal.valueOf(468.88));
            refundData.setRefundTime(new Date());
            refundData.setRefundStatus(1);
            refundData.setOperatorName("李四");
            refundData.setRemark("重复下单");
            refundList.add(refundData);
        }
        return refundList;
    }

    @Override
    public Refund getById(long refundId) throws Exception {
        Refund refundData = new Refund();
        refundData.setRefundNO("2008100109");//交易编号
        refundData.setUserName("张三");
        refundData.setPhone("18618162075");
        refundData.setRefundMoney(BigDecimal.valueOf(488.88));
        refundData.setShouldRefundPoint(BigDecimal.valueOf(400));
        refundData.setAvailablePoint(BigDecimal.valueOf(200));
        refundData.setDeductMoneyOffsetPoint(BigDecimal.valueOf(20));
        refundData.setFactRefundMoney(BigDecimal.valueOf(468.88));
        refundData.setRefundTime(new Date());
        refundData.setRefundStatus(1);
        refundData.setOperatorName("李四");
        refundData.setRemark("重复下单");
        return refundData;
    }

    @Override
    public Refund getRefundDataByRefundMoney(double refundMoney) throws Exception {

        Refund refundData = new Refund();
        refundData.setRefundNO("2008100109");//交易编号
        refundData.setUserName("张三");
        refundData.setPhone("18618162075");
        refundData.setRefundMoney(BigDecimal.valueOf(refundMoney + 1));
        refundData.setShouldRefundPoint(BigDecimal.valueOf(refundMoney + 1));
        refundData.setAvailablePoint(BigDecimal.valueOf(refundMoney + 1));
        refundData.setDeductMoneyOffsetPoint(BigDecimal.valueOf(refundMoney + 1));
        refundData.setFactRefundMoney(BigDecimal.valueOf(refundMoney + 1));
        refundData.setRefundTime(new Date());
        refundData.setRefundStatus(1);
        refundData.setOperatorName("李四");
        return refundData;
    }

    @Override
    public void addRefund(Refund refund) throws Exception {

    }
}
