package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.model.query.ScenicSpotListQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;

/**
 * 资源选择理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ScenicService scenicSpotService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private UserService userService;

	/**
	 * 选择景点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody Map<String, Object> queryRestaurantForSelect(RestaurantListQuery query) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择餐厅
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRestaurant")
	public String selectRstaurant() throws Exception {
		return "/system/resource/forSelect/selectRestaurant";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScenicForSelect")
	public @ResponseBody Map<String, Object> queryScenicForSelect(ScenicSpotListQuery query) throws Exception {
		PageVO<ScenicDO> pageVo = scenicSpotService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择景区
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenic")
	public String selectScnicSpot() throws Exception {
		return "/system/resource/forSelect/selectScenic";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody Map<String, Object> queryHotelForSelect(HotelListQuery query) throws Exception {
		PageVO<HotelDO> pageVo = hotelService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/resource/forSelect/selectHotel";
	}

	/**
	 * 选择用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserForSelect")
	public @ResponseBody Map<String, Object> queryUserForSelect(UserListQuery query) throws Exception {
		PageVO<User> pageVo = userService.getUserListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser() throws Exception {
		return "/system/resource/forSelect/selectUser";
	}
}
