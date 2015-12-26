package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by czf on 2015/12/25.
 */
public class ScenicVO extends ScenicDO {
    private MasterRecommend masterRecommend;
    private String picListStr;//图片集的str
    private String picturesStr;//详情页展示图(对应pictures)


    private NeedKnow needKnowOb;

    public static ScenicDO getScenicDO(ScenicVO scenicVO){
        ScenicDO scenicDO = new ScenicVO();
        BeanUtils.copyProperties(scenicVO, scenicDO);
        //masterRecommend
        //pictures
        if(StringUtils.isNotBlank(scenicVO.getPicturesStr())){
            scenicDO.setPictures(Arrays.asList(scenicVO.getPicturesStr().split("\\|")));
        }
        //NeedKnowOb 在serviceImpl中处理
        //图片集处理(因为有outId还是,只处理新增的)


        return scenicDO;
    }
    public static ScenicVO getScenicVO(ScenicDO scenicDO){
        ScenicVO scenicVO = new ScenicVO();
        BeanUtils.copyProperties(scenicDO,scenicVO);
        return scenicVO;
    }

    public String getPicListStr() {
        return picListStr;
    }

    public void setPicListStr(String picListStr) {
        this.picListStr = picListStr;
    }

    public MasterRecommend getMasterRecommend() {
        return masterRecommend;
    }

    public void setMasterRecommend(MasterRecommend masterRecommend) {
        this.masterRecommend = masterRecommend;
    }

    public NeedKnow getNeedKnowOb() {
        return needKnowOb;
    }

    public void setNeedKnowOb(NeedKnow needKnowOb) {
        this.needKnowOb = needKnowOb;
    }

    public String getPicturesStr() {
        return picturesStr;
    }

    public void setPicturesStr(String picturesStr) {
        this.picturesStr = picturesStr;
    }
}
