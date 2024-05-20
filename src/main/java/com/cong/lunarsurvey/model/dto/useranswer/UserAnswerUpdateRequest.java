package com.cong.lunarsurvey.model.dto.useranswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新用户答题记录请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class UserAnswerUpdateRequest implements Serializable {

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
     * 用户答题结果（JSON 数组）
     */
    private List<String> choices;

    /**
     * 结果 ID
     */
    private Long resultId;

    /**
     * 结果名称
     */
    private String resultName;

    /**
     * 结果描述
     */
    private String resultDesc;

    /**
     * 结果图片
     */
    private String resultPicture;

    /**
     * 结果得分
     */
    private Integer resultScore;

    private static final long serialVersionUID = 1L;
}