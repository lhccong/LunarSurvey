package com.cong.lunarsurvey.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cong.lunarsurvey.model.dto.scoringresult.ScoringResultQueryRequest;
import com.cong.lunarsurvey.model.entity.ScoringResult;
import com.cong.lunarsurvey.model.vo.ScoringResultVO;

/**
 * 评分结果服务
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
public interface ScoringResultService extends IService<ScoringResult> {

    /**
     * 校验数据
     *
     * @param scoringResult 数据
     * @param add           对创建的数据进行校验
     */
    void validScoringResult(ScoringResult scoringResult, boolean add);

    /**
     * 获取查询条件
     *
     * @param scoringResultQueryRequest 查询条件
     * @return QueryWrapper
     */
    QueryWrapper<ScoringResult> getQueryWrapper(ScoringResultQueryRequest scoringResultQueryRequest);

    /**
     * 获取评分结果封装
     *
     * @param scoringResult 评分结果实体
     * @return ScoringResultVO
     */
    ScoringResultVO getScoringResultVO(ScoringResult scoringResult);

    /**
     * 分页获取评分结果封装
     *
     * @param scoringResultPage 分页数据
     * @return Page<ScoringResultVO>
     */
    Page<ScoringResultVO> getScoringResultVOPage(Page<ScoringResult> scoringResultPage);
}
