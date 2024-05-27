package com.cong.lunarsurvey.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cong.lunarsurvey.model.dto.question.AiGenerateQuestionRequest;
import com.cong.lunarsurvey.model.dto.question.QuestionContentDTO;
import com.cong.lunarsurvey.model.dto.question.QuestionQueryRequest;
import com.cong.lunarsurvey.model.entity.Question;
import com.cong.lunarsurvey.model.vo.QuestionVO;

import java.util.List;

/**
 * 题目服务
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
public interface QuestionService extends IService<Question> {

    /**
     * 校验数据
     *
     * @param question 数据
     * @param add 对创建的数据进行校验
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest 查询条件
     * @return QueryWrapper
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param question 题目实体
     * @return QuestionVO
     */
    QuestionVO getQuestionVO(Question question);

    /**
     * 分页获取题目封装
     *
     * @param questionPage 分页数据
     * @return Page<QuestionVO>
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);

    /**
     * AI 生成问题
     *
     * @param aiGenerateQuestionRequest AI 生成问题请求
     * @return {@link List }<{@link QuestionContentDTO }>
     */
    List<QuestionContentDTO> aiGenerateQuestion(AiGenerateQuestionRequest aiGenerateQuestionRequest);
}
