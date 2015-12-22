package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.domain.CategoryPropertyValueDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2015/12/19.
 * sku用
 */
public class CategoryPropertyValueVO extends CategoryPropertyValueDO {
    private CategoryPropertyVO categoryPropertyVO;
    public static CategoryPropertyValueVO getCategoryPropertyValueVO(CategoryPropertyValueDO categoryPropertyValueDO){
        CategoryPropertyValueVO categoryPropertyValueVO = new CategoryPropertyValueVO();
        BeanUtils.copyProperties(categoryPropertyValueDO,categoryPropertyValueVO);
        categoryPropertyValueVO.setCategoryPropertyVO(CategoryPropertyVO.getCategoryPropertyVO(categoryPropertyValueVO.getCategoryPropertyDO()));
        return categoryPropertyValueVO;
    }
    /*public static CategoryPropertyValueDO getCategoryPropertyValueDO(CategoryPropertyValueVO categoryPropertyValueVO){
        CategoryPropertyValueDO categoryPropertyValueDO = new CategoryPropertyValueDO();
        BeanUtils.copyProperties(categoryPropertyValueVO,categoryPropertyValueDO);
        return categoryPropertyValueDO;
    }*/

    public CategoryPropertyVO getCategoryPropertyVO() {
        return categoryPropertyVO;
    }

    public void setCategoryPropertyVO(CategoryPropertyVO categoryPropertyVO) {
        this.categoryPropertyVO = categoryPropertyVO;
    }
}
