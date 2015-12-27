package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.convert.OrderConverter;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.trade.MainOrder;
import com.yimayhd.harem.model.trade.OrderDetails;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author yebin, zhaozhaonan
 *
 */
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TcQueryService tcQueryServiceRef;
	@Autowired
	private UserService userServiceRef;

	@Override
	public PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception {
		long userId = 0;
		List<BizOrderDO> list = null;
		if (StringUtils.isNotEmpty(orderListQuery.getBuyerName()) || StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())){
			UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
			userDOPageQuery.setPageNo(1);
			userDOPageQuery.setPageSize(1);
			if (StringUtils.isNotEmpty(orderListQuery.getBuyerName())){
				userDOPageQuery.setNickname(orderListQuery.getBuyerName());
			}
			if (StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())){
				userDOPageQuery.setMobile(orderListQuery.getBuyerPhone());
			}
			BasePageResult<UserDO> basePageResult = userServiceRef.findPageResultByCondition(userDOPageQuery);
			if (basePageResult.isSuccess()){
				if (!CollectionUtils.isEmpty(basePageResult.getList())){
					UserDO userDO = basePageResult.getList().get(0);
					userId = userDO.getId();
				}
			}
		}

		OrderQueryDTO orderQueryDTO = OrderConverter.orderListQueryToOrderQueryDTO(orderListQuery,userId);
		List<MainOrder> mainOrderList = new ArrayList<MainOrder>();
		if (orderQueryDTO!=null){
			BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrders(orderQueryDTO);
			if (batchQueryResult.isSuccess()){
				//订单信息
				List<BizOrderDO> bizOrderDOList = batchQueryResult.getBizOrderDOList();
				//如果使用名称查询，查询出的全部是子订单，需要把子订单放入父订单中。
				if (!CollectionUtils.isEmpty(bizOrderDOList) && StringUtils.isNotEmpty(orderQueryDTO.getItemName())){
					OrderQueryDTO orderQueryDTOMain = new OrderQueryDTO();
					List<Long> bizOrderIds = new ArrayList<Long>();
					List<BizOrderDO> bizOrderDOListMain = new ArrayList<BizOrderDO>();
					for (BizOrderDO bizOrderDO : bizOrderDOList) {
						//判断是否是主订单
						if(bizOrderDO.getIsMain() == MainDetailStatus.NO.getType()){
							bizOrderIds.add(bizOrderDO.getParentId());
							orderQueryDTOMain.setBizOrderIds(bizOrderIds);
						}else{
							bizOrderDOListMain.add(bizOrderDO);
						}
					}
					BatchQueryResult batchQueryResultMain = tcQueryServiceRef.queryOrders(orderQueryDTOMain);
					if (batchQueryResultMain.isSuccess() && !CollectionUtils.isEmpty(batchQueryResultMain.getBizOrderDOList())){
						bizOrderDOListMain.addAll(batchQueryResultMain.getBizOrderDOList());
					}

					if (bizOrderDOListMain.size()>0){
						for (BizOrderDO bizOrderDOMain : bizOrderDOListMain){
							List<BizOrderDO> bizOrderDOTempList = new ArrayList<BizOrderDO>();
							for (BizOrderDO bizOrderDO : bizOrderDOList){
								if (bizOrderDO.getParentId() == bizOrderDOMain.getBizOrderId()){
									bizOrderDOTempList.add(bizOrderDO);
								}
							}
							bizOrderDOMain.setDetailOrderList(bizOrderDOTempList);
						}
					}
					list = bizOrderDOListMain;
				}else{
					list = bizOrderDOList;
				}
			}
			if (!CollectionUtils.isEmpty(list)){
				for (BizOrderDO bizOrderDO : list) {
					MainOrder mo = OrderConverter.orderVOConverter(bizOrderDO);
//					mo = OrderConverter.mainOrderStatusConverter(mo,bizOrderDO);
					mainOrderList.add(mo);
				}
			}
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(),
					(int)batchQueryResult.getTotalCount(),mainOrderList);
			return orderPageVO;
		}else{
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(),
					0,mainOrderList);
			return orderPageVO;
		}

	}

	@Override
	public OrderDetails getOrderById(long id) throws Exception {
		OrderQueryOption orderQueryOption = new OrderQueryOption();
		orderQueryOption.setAll();
		SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(id,orderQueryOption);
		if (singleQueryResult.isSuccess()){
			OrderDetails orderDetails = new OrderDetails();
			MainOrder mainOrder = OrderConverter.orderVOConverter(singleQueryResult.getBizOrderDO());
//			mainOrder = OrderConverter.mainOrderStatusConverter(mainOrder,singleQueryResult.getBizOrderDO());
			if (mainOrder!=null){
				orderDetails.setMainOrder(mainOrder);
			}
			if (mainOrder.getBizOrderDO()!=null){
				long buyerId = mainOrder.getBizOrderDO().getBuyerId();
				UserDO buyer = userServiceRef.getUserDOById(buyerId);
				orderDetails.setBuyerName(buyer.getName());
				orderDetails.setBuyerNiceName(buyer.getNickname());
				orderDetails.setBuyerPhoneNum(buyer.getMobileNo());
			}

			if (singleQueryResult.getPayOrderDO()!=null){
				orderDetails.setTotalFee(singleQueryResult.getPayOrderDO().getTotalFee());
				orderDetails.setActualTotalFee(singleQueryResult.getPayOrderDO().getActualTotalFee());
			}

			//参加人
			List<ContactUser> contactUserList = BizOrderUtil.getCheckInUserList(mainOrder.getBizOrderDO());
			if (!CollectionUtils.isEmpty(contactUserList)){
				orderDetails.setTourists(contactUserList);
			}
			//联系人
			ContactUser contactUser = BizOrderUtil.getContactUser(mainOrder.getBizOrderDO());
			if (contactUser!=null){
				orderDetails.setContacts(contactUser);
			}
			//卖家备忘录
			String buyerMemo = BizOrderUtil.getBuyerMemo(mainOrder.getBizOrderDO());
			orderDetails.setBuyerMemo(buyerMemo);
			return orderDetails;
		}
		return null;
	}




}
