package com.cong.lunarsurvey.model.dto.app;

import com.cong.lunarsurvey.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询应用请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppQueryRequest extends PageRequest implements Serializable {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用类型
     */
    private Integer appType;

    /**
     * 审核状态
     */
    private Integer reviewStatus;

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

    /**
     * 评分策略
     */
    private Integer scoringStrategy;

    private static final long serialVersionUID = 1L;
}