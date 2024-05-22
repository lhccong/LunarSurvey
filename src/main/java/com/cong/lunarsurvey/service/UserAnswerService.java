package com.cong.lunarsurvey.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerAddRequest;
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerQueryRequest;
import com.cong.lunarsurvey.model.entity.UserAnswer;
import com.cong.lunarsurvey.model.vo.UserAnswerVO;

/**
 * 用户答题记录服务
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
public interface UserAnswerService extends IService<UserAnswer> {

    /**
     * 校验数据
     *
     * @param userAnswer 数据
     * @param add        对创建的数据进行校验
     */
    void validUserAnswer(UserAnswer userAnswer, boolean add);

    /**
     * 获取查询条件
     *
     * @param userAnswerQueryRequest 查询条件
     * @return QueryWrapper
     */
    QueryWrapper<UserAnswer> getQueryWrapper(UserAnswerQueryRequest userAnswerQueryRequest);

    /**
     * 获取用户答题记录封装
     *
     * @param userAnswer 用户答题记录实体
     * @return UserAnswerVO
     */
    UserAnswerVO getUserAnswerVO(UserAnswer userAnswer);

    /**
     * 分页获取用户答题记录封装
     *
     * @param userAnswerPage 分页数据
     * @return Page<UserAnswerVO>
     */
    Page<UserAnswerVO> getUserAnswerVOPage(Page<UserAnswer> userAnswerPage);

    /**
     * 添加用户答案
     *
     * @param userAnswerAddRequest 用户应答添加请求
     * @return long
     */
    long addUserAnswer(UserAnswerAddRequest userAnswerAddRequest);
}
