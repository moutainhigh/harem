package com.yimayhd.harem.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.domain.ComCommentDO;
import com.yimayhd.commentcenter.client.dto.CommentDTO;
import com.yimayhd.commentcenter.client.result.BasePageResult;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.ComCommentVO;
import com.yimayhd.harem.model.query.EvaluationListQuery;
import com.yimayhd.harem.service.EvaluationService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOQuery;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by czf on 2015/12/31.
 */
public class EvaluationServiceImpl implements EvaluationService {

    private static final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    @Autowired
    private ComCenterService comCenterServiceRef;
    @Autowired
    private UserService userServiceRef;

    @Override
    public PageVO<ComCommentVO> getList(EvaluationListQuery evaluationListQuery) throws Exception {
        //返回结果
        PageVO<ComCommentVO> comCommentVOPageVO = new PageVO<ComCommentVO>(evaluationListQuery.getPageNumber(),evaluationListQuery.getPageSize(),0);
        //查询条件对接
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPageSize(evaluationListQuery.getPageSize());
        commentDTO.setPageNo(evaluationListQuery.getPageNumber());
        //开始结束时间
        if(StringUtils.isNotBlank(evaluationListQuery.getBeginDate())){
            commentDTO.setStartDate(DateUtil.formatMinTimeForDate(evaluationListQuery.getBeginDate()));
        }
        if(StringUtils.isNotBlank(evaluationListQuery.getEndDate())){
            commentDTO.setEndDate(DateUtil.formatMaxTimeForDate(evaluationListQuery.getEndDate()));
        }
        //用户id列表
        List<Long> userIdList = new ArrayList<Long>();
        //用户列表map
        Map<Long,UserDO> userDOMap = new HashMap<Long,UserDO>();
        //电话
        if(StringUtils.isNotBlank(evaluationListQuery.getTel())){
            // 查询用户
            BaseResult<UserDO> userResult =  userServiceRef.getUserDOByMobile(evaluationListQuery.getTel());
            if(null == userResult){
                log.error("EvaluationServiceImpl.getList-userServiceRef.getUserDOByMobile result is null and parame: " + evaluationListQuery.getTel());
                throw new BaseException("查询用户失败");
            } else if(!userResult.isSuccess()){
                log.error("EvaluationServiceImpl.getList-userServiceRef.getUserDOByMobile error:" + JSON.toJSONString(userResult) + "and parame: " + evaluationListQuery.getTel());
                throw new BaseException("查询用户失败," + userResult.getResultMsg());
            }
            if(userResult.getValue() != null && userResult.getValue().getId() != 0){
                userIdList.add(userResult.getValue().getId());
                userDOMap.put(userResult.getValue().getId(),userResult.getValue());
            }else{
                //没有查到用户，直接返回
                return comCommentVOPageVO;
            }

        }else{
            if(StringUtils.isNotBlank(evaluationListQuery.getNickName())){
                // 查询用户
                UserDOQuery userDOQuery = new UserDOQuery();
                userDOQuery.setNickname(evaluationListQuery.getNickName());
                com.yimayhd.user.client.result.BasePageResult<UserDO> userListResult =  userServiceRef.findByConditionNoPage(userDOQuery);
                if(null == userListResult){
                    log.error("EvaluationServiceImpl.getList-userServiceRef.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userDOQuery));
                    throw new BaseException("查询用户列表失败");
                } else if(!userListResult.isSuccess()){
                    log.error("EvaluationServiceImpl.getList-userServiceRef.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userDOQuery));
                    throw new BaseException("查询用户列表失败," + userListResult.getResultMsg());
                }
                if(CollectionUtils.isNotEmpty(userListResult.getList())){
                    for (UserDO userDO : userListResult.getList()){
                        userIdList.add(userDO.getId());
                        userDOMap.put(userDO.getId(),userDO);
                    }
                }else{
                    //没有查到用户，直接返回
                    return comCommentVOPageVO;
                }
            }
        }
        //昵称（用户id列表）
        commentDTO.setOutIdList(userIdList);

        //状态
        commentDTO.setState(evaluationListQuery.getEvaluationStatus());
        BasePageResult<ComCommentDO> commentDOBasePageResult = comCenterServiceRef.getCommentInfoPage(commentDTO);
        if(null == commentDOBasePageResult){
            log.error("EvaluationServiceImpl.getList-comCenterServiceRef.getCommentInfoPage result is null and parame: " + JSON.toJSONString(commentDTO));
            throw new BaseException("查询返回结果为空");
        } else if(!commentDOBasePageResult.isSuccess()){
            log.error("EvaluationServiceImpl.getList-comCenterServiceRef.getCommentInfoPage error:" + JSON.toJSONString(commentDOBasePageResult) + "and parame: " + JSON.toJSONString(commentDTO));
            throw new BaseException(commentDOBasePageResult.getResultMsg());
        }
        if(CollectionUtils.isNotEmpty(commentDOBasePageResult.getList())){
            List<ComCommentVO> comCommentVOList = new ArrayList<ComCommentVO>();
            //查询条件中没有查用户的情况下，要重新查询用户信息
            if(userDOMap.size() == 0){
                List<Long> userIds = new ArrayList<Long>();
                for (ComCommentDO comCommentDO : commentDOBasePageResult.getList()){
                    userIds.add(comCommentDO.getUserId());
                }
                // 查询用户 TODO
                BaseResult<List<UserDO>> userListResult =  userServiceRef.getUserDOList(userIds);
                if(null == userListResult){
                    log.error("EvaluationServiceImpl.getList-userServiceRef.findByConditionNoPage result is null and parame: " + JSON.toJSONString(userIds));
                    throw new BaseException("查询用户列表失败");
                } else if(!userListResult.isSuccess()){
                    log.error("EvaluationServiceImpl.getList-userServiceRef.findByConditionNoPage error:" + JSON.toJSONString(userListResult) + "and parame: " + JSON.toJSONString(userIds));
                    throw new BaseException("查询用户列表失败," + userListResult.getResultMsg());
                }
                if(CollectionUtils.isNotEmpty(userListResult.getValue())){
                    for (UserDO userDO : userListResult.getValue()){
                        userDOMap.put(userDO.getId(),userDO);
                    }
                }
            }
            for (ComCommentDO comCommentDO : commentDOBasePageResult.getList()){
                //转换类型
                ComCommentVO comCommentVO = ComCommentVO.getComCommentVO(comCommentDO);
                //设置user
                comCommentVO.setUserDO(userDOMap.get(comCommentDO.getUserId()));
                comCommentVOList.add(comCommentVO);
            }
            comCommentVOPageVO = new PageVO<ComCommentVO>(evaluationListQuery.getPageNumber(),evaluationListQuery.getPageSize(),commentDOBasePageResult.getTotalCount(),comCommentVOList);
        }
        return comCommentVOPageVO;
    }
}
