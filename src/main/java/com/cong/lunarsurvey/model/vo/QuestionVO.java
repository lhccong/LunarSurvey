package com.cong.lunarsurvey.model.vo;

import cn.hutool.json.JSONUtil;
import com.cong.lunarsurvey.model.dto.question.QuestionContentDTO;
import com.cong.lunarsurvey.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 题目视图
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class QuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 问题内容
     */
    private List<QuestionContentDTO> questionContent;

    /**
     * 应用 ID
     */
    private Long appId;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 创建用户信息
     */
    private UserVO user;


    /**
     * VO 为 OBJ
     *
     * @param questionVO 问题 VO
     * @return {@link Question }
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<QuestionContentDTO> questionContentDTO = questionVO.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDTO));
        return question;
    }


    /**
     * obj 为 vo
     *
     * @param question 问题
     * @return {@link QuestionVO }
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        String questionContent = question.getQuestionContent();
        if (questionContent != null) {
            questionVO.setQuestionContent(JSONUtil.toList(questionContent, QuestionContentDTO.class));
        }
        return questionVO;
    }
}
