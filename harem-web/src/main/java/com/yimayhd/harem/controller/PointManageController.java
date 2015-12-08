package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.IMallPointRuleVO;
import com.yimayhd.harem.model.SendPointRule;
import com.yimayhd.harem.model.UsePointRule;
import com.yimayhd.harem.service.PointRuleService;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;
import com.yimayhd.user.session.manager.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 积分规则
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/PointManage")
public class PointManageController extends BaseController {

	@Autowired
	private PointRuleService pointRuleService;


	/**
	 * 积分发送规则
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/list", method = RequestMethod.GET)
	public String sendPointRule(HttpServletRequest request,Model model,BaseQuery baseQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId = 1;
		IMallPointRuleResult iMallPointRuleResult = pointRuleService.getSendPointRuleNow(sellerId);
		PageVO<IMallPointRuleResult> pageVO = pointRuleService.getSendPointRuleHistory(sellerId,baseQuery);
		model.addAttribute("sendPointRule", iMallPointRuleResult);
		model.addAttribute("sendPointRuleList", pageVO.getItemList());
		return "/system/tradeUser/sendPointRule";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/toAdd", method = RequestMethod.GET)
	public String sendPointRuleToAdd() throws Exception {
		return "/system/tradeUser/sendPointRuleAdd";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/add", method = RequestMethod.POST)
	public String sendPointRuleAdd(HttpServletRequest request,IMallPointRuleVO iMallPointRuleVO) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId = 1;
		boolean success = pointRuleService.add(sellerId,iMallPointRuleVO);
		if(success){
			return "/success";
		}
		return "/error";
	}
}
