package com.cong.lunarsurvey.model.enums;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * App 应用类型枚举
 * # @author <a href="https://github.com/lhccong">程序员聪</a>
 */
@Getter
public enum AppTypeEnum {

    SCORE("得分类", 0),
    TEST("测评类", 1);

    private final String text;

    private final int value;

    AppTypeEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 按值获取枚举
     *
     * @param value 价值
     * @return {@link AppTypeEnum }
     */
    public static AppTypeEnum getEnumByValue(Integer value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (AppTypeEnum anEnum : AppTypeEnum.values()) {
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