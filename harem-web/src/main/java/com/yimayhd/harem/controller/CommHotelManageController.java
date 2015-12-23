package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.CategoryVO;
import com.yimayhd.harem.model.ItemResultVO;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.service.CategoryService;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.ic.client.model.domain.item.CategoryFeature;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 酒店管理（商品）
 * @author czf
 */
@Controller
@RequestMapping("/B2C/comm/hotelManage")
public class CommHotelManageController extends BaseController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public
    String toAdd(Model model,long categoryId) throws Exception {
        CategoryVO categoryVO = categoryService.getCategoryVOById(categoryId);
        CategoryFeature categoryFeature = categoryVO.getCategoryFeature();
        int itemType = categoryFeature.getItemType();//不可能有空值，就不判断空了
        model.addAttribute("category", categoryVO);
        model.addAttribute("itemType",itemType);
        return "/system/comm/hotel/edit";
    }
    /**
     * 编辑酒店（商品）
     * @return 酒店（商品）详情
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public
    String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {

        ItemResultVO itemResultVO = commodityService.getCommodityById(id);
        model.addAttribute("itemResult", itemResultVO);
        model.addAttribute("commHotel", itemResultVO.getItemVO());
        model.addAttribute("category", itemResultVO.getCategoryVO());
        model.addAttribute("itemType",ItemType.HOTEL.getValue());

        return "/system/comm/hotel/edit";
    }

    /**
     * 编辑酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public
    String edit(ItemVO itemVO,@PathVariable("id") long id) throws Exception {
        itemVO.setId(id);
        commodityService.modifyCommHotel(itemVO);
        return "/success";
    }

    /**
     * 新增酒店（商品）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    String add(ItemVO itemVO) throws Exception {
        commodityService.addCommHotel(itemVO);
        return "/success";
    }
}
