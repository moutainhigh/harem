package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceManage")
public class ResourceManageController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantRPCService restaurantRPCService;

	@RequestMapping(value = "/restaurant/list", method = RequestMethod.GET)
	public String restaurantList(RestaurantListQuery restaurantListQuery) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantService.getListByPage(restaurantListQuery);
		put("pageVo", pageVo);
		put("query", restaurantListQuery);
		return "/system/resource/restaurant/list";
	}

	@RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
	public String restaurant(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantService.getById(id);
		put("restaurant", restaurant);
		return "/system/resource/restaurant/Info";
	}
	
	@RequestMapping(value = "/restaurant/list2", method = RequestMethod.GET)
	public String restaurantList2(RestaurantListQuery restaurantListQuery) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantRPCService.pageQueryHotel(restaurantListQuery);
		System.out.println(JSON.toJSONString(pageVo));
		put("pageVo", pageVo);
		put("query", restaurantListQuery);
		return "/system/restaurant/list";
	}

	@RequestMapping(value = "/restaurant2/{id}", method = RequestMethod.GET)
	public String restaurant2(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantService.getById(id);
		put("restaurant", restaurant);
		return "/system/restaurant/view";
	}
}
