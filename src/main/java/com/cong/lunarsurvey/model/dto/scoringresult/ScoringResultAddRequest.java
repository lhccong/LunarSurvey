package com.cong.lunarsurvey.model.dto.scoringresult;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建评分结果请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class ScoringResultAddRequest implements Serializable {


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
     * 结果属性集合 JSON，如 [I,S,T,J]
     */
    private List<String> resultProp;

    /**
     * 结果分数范围
     */
    private Integer resultScoreRange;

    /**
     * 应用 ID
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}