package com.cong.lunarsurvey.scoring;


import com.cong.lunarsurvey.model.entity.App;
import com.cong.lunarsurvey.model.entity.UserAnswer;

import java.util.List;

/**
 * 评分策略
 *
 * @author cong
 * @date 2024/05/22
 */
public interface ScoringStrategy {


    /**
     * 做分数
     *
     * @param choices 选择
     * @param app     应用程序
     * @return {@link UserAnswer }
     * @throws Exception 例外
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}