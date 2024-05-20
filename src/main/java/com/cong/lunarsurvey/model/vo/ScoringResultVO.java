package com.cong.lunarsurvey.model.vo;

import cn.hutool.json.JSONUtil;
import com.cong.lunarsurvey.model.entity.ScoringResult;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评分结果视图
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@Data
public class ScoringResultVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 结果名称，如物流师
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
     * 结果得分范围，如 80，表示 80及以上的分数命中此结果
     */
    private Integer resultScoreRange;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
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
     * @param scoringResultVO 评分结果 VO
     * @return {@link ScoringResult }
     */
    public static ScoringResult voToObj(ScoringResultVO scoringResultVO) {
        if (scoringResultVO == null) {
            return null;
        }
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultVO, scoringResult);
        scoringResult.setResultProp(JSONUtil.toJsonStr(scoringResultVO.getResultProp()));
        return scoringResult;
    }


    /**
     * obj 为 vo
     *
     * @param scoringResult 评分结果
     * @return {@link ScoringResultVO }
     */
    public static ScoringResultVO objToVo(ScoringResult scoringResult) {
        if (scoringResult == null) {
            return null;
        }
        ScoringResultVO scoringResultVO = new ScoringResultVO();
        BeanUtils.copyProperties(scoringResult, scoringResultVO);
        scoringResultVO.setResultProp(JSONUtil.toList(scoringResult.getResultProp(), String.class));
        return scoringResultVO;
    }
}
