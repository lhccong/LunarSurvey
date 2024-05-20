package com.cong.lunarsurvey.model.dto.scoringresult;

import com.cong.lunarsurvey.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询评分结果请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScoringResultQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用 ID
     */
    private Long appId;


    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}