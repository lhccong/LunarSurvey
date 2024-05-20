package com.cong.lunarsurvey.model.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App 评分策略枚举
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Getter
public enum AppScoringStrategyEnum {

    CUSTOM("自定义", 0),
    AI("AI", 1);

    private final String text;

    private final int value;

    AppScoringStrategyEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 根据 value 获取枚举
     *
     * @param value 价值
     * @return {@link AppScoringStrategyEnum }
     */
    public static AppScoringStrategyEnum getEnumByValue(Integer value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (AppScoringStrategyEnum anEnum : AppScoringStrategyEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 获取值
     *
     * @return {@link List }<{@link Integer }>
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

}