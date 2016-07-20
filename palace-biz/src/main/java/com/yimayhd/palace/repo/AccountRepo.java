package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBalanceQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccBalanceResult;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;
import com.yimayhd.pay.client.service.eleaccount.EleAccBillService;

/**
 * 账户管理Repo
 * 
 * @author hongfei.guo
 *
 */
public class AccountRepo{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected EleAccBillService eleAccBillServiceRef;
	
	/**
	 * 账户余额查询
	 * @param query
	 * @return
	 */
	public EleAccBalanceResult queryEleAccBalance(EleAccBalanceQuery query) {
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBalance", query);
		EleAccBalanceResult result = null;//eleAccBillServiceRef.queryEleAccBalance(query);
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBalance", result);
		return result;
	}
	
	/**
	 * 账户余额明细
	 * @param query
	 * @return
	 */
	public PayPageResultDTO<EleAccountBillDTO> queryEleAccBillDetail(EleAccBillDetailQuery query) {
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", query);
		PayPageResultDTO<EleAccountBillDTO> result = null;//eleAccBillServiceRef.queryEleAccBillDetail(query);
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", result);
		return result;
	}
	
}
