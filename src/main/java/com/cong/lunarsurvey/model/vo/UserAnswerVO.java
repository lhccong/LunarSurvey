package com.cong.lunarsurvey.model.vo;

import cn.hutool.json.JSONUtil;
import com.cong.lunarsurvey.model.entity.UserAnswer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户答题记录视图
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class UserAnswerVO implements Serializable {

    /**
     *
     */
    private Long id;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 应用类型（0-得分类，1-角色测评类）
     */
    private Integer appType;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringStrategy;

    /**
     * 用户答案（JSON 数组）
     */
    private List<String> choices;

    /**
     * 评分结果 id
     */
    private Long resultId;

    /**
     * 结果名称，如物流师
     */
    private String resultName;

    /**
     * 结果描述
     */
    private String resultDesc;

    /**
     * 结果图标
     */
    private String resultPicture;

    /**
     * 得分
     */
    private Integer resultScore;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户信息
     */
    private UserVO user;


    /**
     * VO 为 OBJ
     *
     * @param userAnswerVO 用户答案 VO
     * @return {@link UserAnswer }
     */
    public static UserAnswer voToObj(UserAnswerVO userAnswerVO) {
        if (userAnswerVO == null) {
            return null;
        }
        UserAnswer userAnswer = new UserAnswer();
        BeanUtils.copyProperties(userAnswerVO, userAnswer);
        userAnswer.setChoices(JSONUtil.toJsonStr(userAnswerVO.getChoices()));
        return userAnswer;
    }


    /**
     * obj 为 vo
     *
     * @param userAnswer 用户回答
     * @return {@link UserAnswerVO }
     */
    public static UserAnswerVO objToVo(UserAnswer userAnswer) {
        if (userAnswer == null) {
            return null;
        }
        UserAnswerVO userAnswerVO = new UserAnswerVO();
        BeanUtils.copyProperties(userAnswer, userAnswerVO);
        userAnswerVO.setChoices(JSONUtil.toList(userAnswer.getChoices(), String.class));
        return userAnswerVO;
    }
}
