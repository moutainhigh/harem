package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public interface ArticleService {
	/**
	 * 获取H5列表(可带查询条件)
	 * 
	 * @param liveListQuery
	 *            查询条件
	 * @return H5列表
	 */
	PageVO<ArticleVO> getList(ArticleListQuery articleListQuery);

	/**
	 * 获取H5详情
	 * 
	 * @param id
	 *            H5ID
	 * @return H5详情
	 */
	ArticleVO getById(long id) throws Exception;

	/**
	 * 新增H5
	 * 
	 * @param ArticleVO
	 *            H5内容
	 * @return H5对象
	 * @throws Exception
	 */
	ArticleVO add(ArticleVO articleVO) throws Exception;

	/**
	 * 修改H5
	 * 
	 * @param ArticleVO
	 *            H5内容
	 * @throws Exception
	 */
	void modify(ArticleVO articleVO) throws Exception;

	/**
	 * H5恢复
	 * 
	 * @param id
	 *            H5ID
	 */
	void regain(long id) throws Exception;

	/**
	 * H5违规
	 * 
	 * @param id
	 *            H5ID
	 */
	void violation(long id) throws Exception;

	/**
	 * H5违规（批量）
	 * 
	 * @param idList
	 *            H5idList
	 */
	void batchViolation(List<Long> idList);

}
