package com.yimayhd.palace.repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.*;
import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.result.ComRateResult;
import com.yimayhd.commentcenter.client.service.ComRateService;
import com.yimayhd.palace.error.PalaceReturnCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.query.TagPageQuery;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.line.TagDTO;
import com.yimayhd.palace.util.RepoUtils;

/**
 * 标签Repo
 *
 * @author yebin
 */
public class CommentRepo {
    private Logger log = LoggerFactory.getLogger(getClass());
    public static final int STATUS_DISABLE = 2;
    public static final int STATUS_ENABLE = 1;
    @Autowired
    private ComCenterService comCenterServiceRef;
    @Autowired
    private ComTagCenterService comTagCenterServiceRef;
    @Autowired
    private ComRateService comRateServiceRef;

    /**
     * 保存标签
     *
     * @param outId
     * @param tagType
     * @param tagDTOs
     */
    @Deprecated
    public void saveTags(long outId, TagType tagType, List<? extends TagDTO> tagDTOs) {
        if (outId <= 0 || tagType == null || CollectionUtils.isEmpty(tagDTOs)) {
            log.warn("save tags params error");
            throw new BaseException("参数异常");
        }
        List<Long> tagIds = new ArrayList<Long>();
        for (TagDTO tagDTO : tagDTOs) {
            if (tagDTO.getId() > 0) {
                tagIds.add(tagDTO.getId());
            } else {
                TagInfoAddDTO tagInfoAddDTO = new TagInfoAddDTO();
                tagInfoAddDTO.setDomain(Constant.DOMAIN_JIUXIU);
                tagInfoAddDTO.setTagId(tagDTO.getId());
                tagInfoAddDTO.setName(tagDTO.getName());
                tagInfoAddDTO.setOutType(tagType.getType());
                ComTagDO comTagDO = saveTag(tagInfoAddDTO);
                tagIds.add(comTagDO.getId());
            }
        }
        saveTagRelation(outId, tagType, tagIds);
    }

    /**
     * 保存标签
     *
     * @param tagInfoAddDTO
     * @return
     */
    public ComTagDO saveTag(TagInfoAddDTO tagInfoAddDTO) {
        if (tagInfoAddDTO == null || tagInfoAddDTO == null) {
            return null;
        }
        RepoUtils.requestLog(log, "comCenterServiceRef.saveTagInfo", tagInfoAddDTO);
        BaseResult<ComTagDO> result = comTagCenterServiceRef.saveTagInfo(tagInfoAddDTO);
        RepoUtils.resultLog(log, "comCenterServiceRef.saveTagInfo", result);
        return result.getValue();
    }

    /**
     * 保存关联关系
     *
     * @param outId
     * @param outType
     * @param tagIds
     */
    public void saveTagRelation(long outId, TagType outType, List<Long> tagIds) {
        if (outId <= 0 || outType == null || CollectionUtils.isEmpty(tagIds)) {
            log.warn("save tag relation params error");
            throw new BaseException("参数异常");
        }
        TagRelationInfoDTO tagRelationInfoDTO = new TagRelationInfoDTO();
        tagRelationInfoDTO.setOutType(outType.getType());
        tagRelationInfoDTO.setOutId(outId);
        tagRelationInfoDTO.setOrderTime(new Date());
        tagRelationInfoDTO.setList(tagIds);
        tagRelationInfoDTO.setDomain(Constant.DOMAIN_JIUXIU);
        RepoUtils.requestLog(log, "comCenterServiceRef.addTagRelationInfo", tagRelationInfoDTO);
        BaseResult<Boolean> addTagRelationInfo = comCenterServiceRef.addTagRelationInfo(tagRelationInfoDTO);
        RepoUtils.resultLog(log, "comCenterServiceRef.addTagRelationInfo", addTagRelationInfo);
    }

    /**
     * 查询标签
     *
     * @param outId
     * @param tagType
     * @return
     */
    public List<ComTagDO> getTagsByOutId(long outId, TagType tagType) {
        if (outId <= 0 || tagType == null) {
            log.error("CommentRepo.getTagsByOutId warn: 参数异常");
            throw new BaseException("参数异常");
        }
        Map<Long, List<ComTagDO>> tagsByOutIds = getTagsByOutIds(Arrays.asList(outId), tagType);
        if (tagsByOutIds == null) {
            return new ArrayList<ComTagDO>(0);
        }
        return tagsByOutIds.get(outId);
    }

    public Map<Long, List<ComTagDO>> getTagsByOutIds(List<Long> outIds, TagType tagType) {
        if (CollectionUtils.isEmpty(outIds) || tagType == null) {
            log.error("CommentRepo.getTagsByOutIds warn: 参数异常");
            throw new BaseException("参数异常");
        }
        TagInfoByOutIdDTO tagInfoByOutIdDTO = new TagInfoByOutIdDTO();
        tagInfoByOutIdDTO.setIdList(outIds);
        tagInfoByOutIdDTO.setDomain(Constant.DOMAIN_JIUXIU);
        tagInfoByOutIdDTO.setOutType(tagType.name());
        RepoUtils.requestLog(log, "comTagCenterServiceRef.getComTag", tagInfoByOutIdDTO);
        BaseResult<Map<Long, List<ComTagDO>>> comTag = comTagCenterServiceRef.getComTag(tagInfoByOutIdDTO);
        RepoUtils.resultLog(log, "comTagCenterServiceRef.getComTag", comTag);
        return comTag.getValue();
    }

    public PageVO<ComTagDO> pageQueryTag(TagInfoDTO tagInfoDTO) {
        RepoUtils.requestLog(log, "comCenterServiceRef.selectTagInfoPage", tagInfoDTO);
        BasePageResult<ComTagDO> result = comCenterServiceRef.selectTagInfoPage(tagInfoDTO);
        RepoUtils.resultLog(log, "comCenterServiceRef.selectTagInfoPage", result);
        int totalCount = result.getTotalCount();
        List<ComTagDO> itemList = result.getList();
        if (itemList == null) {
            itemList = new ArrayList<ComTagDO>();
        }
        return new PageVO<ComTagDO>(tagInfoDTO.getPageNo(), tagInfoDTO.getPageSize(), totalCount, itemList);
    }

    /**
     * 查询标签信息
     *
     * @param id
     * @return
     */
    public ComTagDO getTagById(long id) {
        RepoUtils.requestLog(log, "comCenterServiceRef.selectByPrimaryKey", id);
        BaseResult<ComTagDO> result = comCenterServiceRef.selectByPrimaryKey(id);
        RepoUtils.resultLog(log, "comCenterServiceRef.selectByPrimaryKey", result);
        return result.getValue();
    }

    /**
     * 更新标签
     *
     * @param id
     * @param state
     * @return
     */
    public ComTagDO updateTagStateById(long id, int state) {
        TagPageQuery query = new TagPageQuery();
        query.setList(Arrays.asList(id));
        query.setState(state);
        RepoUtils.requestLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", query);
        BaseResult<ComTagDO> result = comCenterServiceRef.updateTagInfoStateByIdList(query);
        RepoUtils.resultLog(log, "comCenterServiceRef.updateTagInfoStateByIdList", result);
        return result.getValue();
    }

    /**
     * 批量修改标签状态
     *
     * @param ids
     * @param state
     * @return
     */
    public ComTagDO batchUpdateTagsState(List<Long> ids, int state) {
        TagPageQuery query = new TagPageQuery();
        query.setList(ids);
        query.setState(state);
        RepoUtils.requestLog(log, "comTagCenterServiceRef.updateTagInfoByIdList", query);
        BaseResult<ComTagDO> result = comTagCenterServiceRef.updateTagInfoByIdList(query);
        RepoUtils.resultLog(log, "comTagCenterServiceRef.updateTagInfoByIdList", result);
        return result.getValue();
    }

    /**
     * 更新标签
     *
     * @return
     */
    public ComTagDO updateTag(TagInfoAddDTO tagInfoAddDTO) {
        if (tagInfoAddDTO == null) {
            return null;
        }
        RepoUtils.requestLog(log, "comTagCenterServiceRef.updateTagInfo", tagInfoAddDTO);
        BaseResult<ComTagDO> result = comTagCenterServiceRef.updateTagInfo(tagInfoAddDTO);
        RepoUtils.resultLog(log, "comTagCenterServiceRef.updateTagInfo", result);
        return result.getValue();
    }

    /**
     * 根据TagType获取标签列表
     *
     * @param tagType 标签类型
     * @return 标签列表
     */
    public List<ComTagDO> getTagsByTagType(TagType tagType) {
        if (tagType == null) {
            return new ArrayList<ComTagDO>(0);
        }
        TagRelationDomainDTO tagRelationDomainDTO = new TagRelationDomainDTO();
        tagRelationDomainDTO.setDomain(Constant.DOMAIN_JIUXIU);
        tagRelationDomainDTO.setOutType(tagType.name());
        tagRelationDomainDTO.setStatus(BaseStatus.AVAILABLE.getType());
        RepoUtils.requestLog(log, "comTagCenterServiceRef.selectTagListByTagType", tagRelationDomainDTO);
        BaseResult<List<ComTagDO>> baseResult = comTagCenterServiceRef.getTagListByTagType(tagRelationDomainDTO);
        RepoUtils.resultLog(log, "comTagCenterServiceRef.selectTagListByTagType", baseResult);
        return baseResult.getValue();
    }

    public ComTagDO getTagByName(TagType outType, String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        TagNameTypeDTO tagNameTypeDTO = new TagNameTypeDTO();
        tagNameTypeDTO.setName(name);
        tagNameTypeDTO.setOutType(outType.name());
        tagNameTypeDTO.setDomain(Constant.DOMAIN_JIUXIU);
        RepoUtils.requestLog(log, "comTagCenterServiceRef.getTagByName", tagNameTypeDTO);
        BaseResult<ComTagDO> tagByName = comTagCenterServiceRef.getTagByName(tagNameTypeDTO);
        RepoUtils.resultLog(log, "comTagCenterServiceRef.getTagByName", tagByName);
        return tagByName.getValue();
    }

    public BasePageResult<ComRateResult> getRatePageList(RatePageListDTO var1) {
        return comRateServiceRef.getRatePageList(var1);
    }

    public BaseResult<Boolean> deleteComRate(RateStatusDTO var1) {
        BaseResult<Boolean> result = new BaseResult<Boolean>();
        try {
            result = comRateServiceRef.batchUpdateStatue(var1);
        } catch (Exception e) {
            log.error("Exception e = {}", e.getMessage());
        }
        return result;
    }

    public BaseResult<ComRateDO> addRateReploy(RateReployDTO var1) {
        return comRateServiceRef.addRateReploy(var1);
    }

    public BaseResult<Boolean> batchAddRateReploy(BatchRateReployDTO var1) {
        return comRateServiceRef.batchAddRateReploy(var1);
    }

    public ComTagDO selectById(long id) {
        try {
            if (0 == id) {
                log.error("comCenterServiceRef.selectByPrimaryKey id={}", id);
                return null;
            }
            BaseResult<ComTagDO> result = comCenterServiceRef.selectByPrimaryKey(id);
            if (null == result || !result.isSuccess()) {
                log.info("comCenterServiceRef.selectByPrimaryKey id={}, result={}", id, JSON.toJSONString(result));
                return result.getValue();
            } else {
                log.error("comCenterServiceRef.selectByPrimaryKey id={}, result={}", id, JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("comCenterServiceRef.selectByPrimaryKey id={}, Exception e = {}", id, e);
            return null;
        }
    }


    public List<ComTagDO> getTagInfoByOutIdAndType(long outId, TagType tagType) {
        try {
            BaseResult<List<ComTagDO>> result = comCenterServiceRef.getTagInfoByOutIdAndType(outId, tagType.name());
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getTagInfoByOutIdAndType outId={},tagType={}, result={}", outId, JSON.toJSONString(tagType), JSON.toJSONString(result));
                    return result.getValue();
                } else {
                    log.info("getTagInfoByOutIdAndType outId={},tagType={}, result={}", outId, JSON.toJSONString(tagType), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.info("getTagInfoByOutIdAndType outId={},tagType={}, exception={}", outId, JSON.toJSONString(tagType), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 根据外部id集合和类型获取标签信息集合
     * @param tagOutIdAndTypeDTO
     *
     * @return
     */
    public Map<Long,List<ComTagDO>> getTagInfoByOutIdsAndType(TagOutIdAndTypeDTO tagOutIdAndTypeDTO){
        try {
            BaseResult<Map<Long,List<ComTagDO>>> result = comCenterServiceRef.getTagInfoByOutIdsAndType(tagOutIdAndTypeDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getTagInfoByOutIdsAndType tagOutIdAndTypeDTO={}, result={}", JSON.toJSONString(tagOutIdAndTypeDTO), JSON.toJSONString(result));
                    return result.getValue();
                } else {
                    log.info("getTagInfoByOutIdAndType tagOutIdAndTypeDTO={}, result={}", JSON.toJSONString(tagOutIdAndTypeDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.info("getTagInfoByOutIdAndType tagOutIdAndTypeDTO={}, exception={}", JSON.toJSONString(tagOutIdAndTypeDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 根据标签id集合获取标签信息集合
     * @param ids
     * @return
     */
    public List<ComTagDO> selectTagsIn(List<Long> ids){
        try {
            BaseResult<List<ComTagDO>> result = comCenterServiceRef.selectTagsIn(ids);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("selectTagsIn ids={}, result={}", JSON.toJSONString(ids), JSON.toJSONString(result));
                    return result.getValue();
                } else {
                    log.info("selectTagsIn ids={}, result={}", JSON.toJSONString(ids), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.info("selectTagsIn ids={}, exception={}", JSON.toJSONString(ids), e);
            throw new BaseException(e, e.getMessage());
        }
    }
}
