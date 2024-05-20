package com.cong.lunarsurvey.model.dto.useranswer;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑用户答题记录请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class UserAnswerEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用 ID
     */
    private Long appId;

    /**
     * 应用类型
     */
    private Integer appType;

    /**
     * 评分策略
     */
    private Integer scoringStrategy;
    /**
     * 用户答案（JSON 数组）
     */
    private String choices;

    private static final long serialVersionUID = 1L;
}