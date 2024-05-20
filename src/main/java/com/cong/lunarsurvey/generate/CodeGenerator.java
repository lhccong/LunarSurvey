package com.cong.lunarsurvey.generate;

import com.cong.lunarsurvey.model.entity.*;

/**
 * 代码生成器
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
public class CodeGenerator {

    /**
     * 用法：追加process(数据类.class, "数据别名");
     */
    public static void main(String[] args) {
        // 代码生成处理器
        new GenerateProcessor()
                // 生成项目路径
                .packageName("com.cong.lunarsurvey")
                // 排除字段策略
                .exclusionStrategy("serialVersionUID", "isDelete","updateTime","createTime")
                // 继续追加process(数据类.class, "数据别名")
                .process(App.class, "应用")
                .process(Question.class, "题目")
                .process(ScoringResult.class, "评分结果")
                .process(UserAnswer.class, "用户答题记录");

    }
}
