package com.cong.lunarsurvey.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑应用请求
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class AppEditRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用类型
     */
    private Integer appType;

    /**
     * 评分策略
     */
    private Integer scoringStrategy;


    private static final long serialVersionUID = 1L;
}