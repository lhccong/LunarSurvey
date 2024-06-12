package com.cong.lunarsurvey.model.dto.question;

import lombok.Data;

/**
 * 问题答案 DTO
 *
 * @author cong
 * @date 2024/06/12
 */
@Data
public class QuestionAnswerDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 用户答案
     */
    private String userAnswer;
}