package com.cong.lunarsurvey.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用视图
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class AppVO implements Serializable {

    /**
     * 同上
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

    /**
     * 审核状态
     */
    private Integer reviewStatus;

    /**
     * 审核消息
     */
    private String reviewMessage;

    /**
     * 审阅者 ID
     */
    private Long reviewerId;

    /**
     * 复习时间
     */
    private Date reviewTime;

    /**
     * 用户 ID
     */
    private Long userId;


    /**
     * 创建用户信息
     */
    private UserVO user;


}
