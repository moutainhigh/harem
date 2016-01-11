package com.yimayhd.palace.service;

import com.yimayhd.palace.model.query.VoucherListQuery;
import com.yimayhd.palace.model.vo.VoucherTemplateVO;

import java.util.List;

/**
 * Created by czf on 2016/1/11.
 */
public interface VoucherTemplateService {
    /**
     * 根据ID查询
     *
     * @param voucherListQuery
     *            查询条件
     * @throws Exception
     */
    List<VoucherTemplateVO> getList(VoucherListQuery voucherListQuery) throws Exception;

    /**
     * 修改优惠券模板
     *
     * @param entity
     *            需要修改的字段和主键
     * @throws Exception
     */
    void modify(VoucherTemplateVO entity) throws Exception;

    /**
     * 添加优惠券模板
     *
     * @return 优惠券模板
     * @param entity
     *            数据实体
     * @throws Exception
     */
    VoucherTemplateVO add(VoucherTemplateVO entity) throws Exception;

    /**
     * 根据主键获取优惠券模板
     *
     * @param id
     *            主键long类型的
     * @throws Exception
     */
    VoucherTemplateVO getById(long id) throws Exception;
}
