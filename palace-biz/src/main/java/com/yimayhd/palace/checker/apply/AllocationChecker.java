package com.yimayhd.palace.checker.apply;

import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.query.apply.ApplyQuery;
import com.yimayhd.palace.model.vo.apply.AllocationVO;
import com.yimayhd.palace.result.BizResultSupport;
import org.springframework.util.StringUtils;

/**
 * Created by hanlei on 2016/5/24.
 */
public class AllocationChecker {

    public static BizResultSupport checkAllocationVO(AllocationVO allocationVO){
        BizResultSupport result = new BizResultSupport() ;
        if( null == allocationVO || allocationVO.getExamineId() <=0 || null == allocationVO.getCategoryIds()
                || StringUtils.isEmpty(allocationVO.getCategoryIds())){
            result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
        }
        return result ;
    }
}
