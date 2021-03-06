package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.RestaurantVO;
import com.yimayhd.palace.model.query.RestaurantListQuery;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

public interface RestaurantService {
	/**
	 * 分页查询餐厅资源
	 * 
	 * @param restaurantListQuery
	 * @return
	 */
	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantListQuery restaurantListQuery);

	/**
	 * 获取餐厅资源详情
	 * 
	 * @param id
	 * @return
	 */
	public RestaurantDO getRestaurantById(long id);

	/**
	 * 发布餐厅资源
	 * 
	 * @param restaurantDO
	 */
	public void publish(RestaurantVO restaurantVO);

	/**
	 * 状态变化
	 * 
	 * @param id
	 * @param status
	 */
	public void changeStatus(long id, int status);
}
