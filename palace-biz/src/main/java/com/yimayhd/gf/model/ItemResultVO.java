package com.yimayhd.gf.model;

import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.palace.model.CategoryVO;
import com.yimayhd.palace.model.ItemSkuVO;
import com.yimayhd.palace.model.ItemVO;

import java.util.List;

/**
 * Created by czf on 2016/01/29.
 */
public class ItemResultVO extends ItemResult {
    private ItemVO itemVO;
    private List<ItemSkuVO> itemSkuVOList;
    private CategoryVO categoryVO;

    public ItemVO getItemVO() {
        return itemVO;
    }

    public void setItemVO(ItemVO itemVO) {
        this.itemVO = itemVO;
    }

    public List<ItemSkuVO> getItemSkuVOList() {
        return itemSkuVOList;
    }

    public void setItemSkuVOList(List<ItemSkuVO> itemSkuVOList) {
        this.itemSkuVOList = itemSkuVOList;
    }

    public CategoryVO getCategoryVO() {
        return categoryVO;
    }

    public void setCategoryVO(CategoryVO categoryVO) {
        this.categoryVO = categoryVO;
    }
}
