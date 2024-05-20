package com.cong.lunarsurvey.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新题目请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容
     */
    private List<QuestionContentDTO> questionContent;

    /**
     * 应用 ID
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}