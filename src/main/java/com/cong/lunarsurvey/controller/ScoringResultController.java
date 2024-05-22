package com.cong.lunarsurvey.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.cong.lunarsurvey.common.BaseResponse;
import com.cong.lunarsurvey.common.DeleteRequest;
import com.cong.lunarsurvey.common.ErrorCode;
import com.cong.lunarsurvey.common.ResultUtils;
import com.cong.lunarsurvey.constant.UserConstant;
import com.cong.lunarsurvey.exception.BusinessException;
import com.cong.lunarsurvey.exception.ThrowUtils;
import com.cong.lunarsurvey.model.dto.scoringresult.ScoringResultAddRequest;
import com.cong.lunarsurvey.model.dto.scoringresult.ScoringResultEditRequest;
import com.cong.lunarsurvey.model.dto.scoringresult.ScoringResultQueryRequest;
import com.cong.lunarsurvey.model.dto.scoringresult.ScoringResultUpdateRequest;
import com.cong.lunarsurvey.model.entity.ScoringResult;
import com.cong.lunarsurvey.model.entity.User;
import com.cong.lunarsurvey.model.vo.ScoringResultVO;
import com.cong.lunarsurvey.service.ScoringResultService;
import com.cong.lunarsurvey.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评分结果接口
 *
 * @author <a href="https://github.com/lhccong">聪</a>

 */
@RestController
@RequestMapping("/scoringResult")
@Slf4j
@Api(value = "评分结果接口")
public class ScoringResultController {

    @Resource
    private ScoringResultService scoringResultService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建评分结果
     *
     * @param scoringResultAddRequest 创建评分结果请求
     * @return {@link BaseResponse }<{@link Long }>
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建评分结果")
    public BaseResponse<Long> addScoringResult(@RequestBody ScoringResultAddRequest scoringResultAddRequest) {
        ThrowUtils.throwIf(scoringResultAddRequest == null, ErrorCode.PARAMS_ERROR);
        //在此处将实体类和 DTO 进行转换
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultAddRequest, scoringResult);
        List<String> resultProp = scoringResultAddRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // 数据校验
        scoringResultService.validScoringResult(scoringResult, true);
        //填充默认值
        User loginUser = userService.getLoginUser();
        scoringResult.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = scoringResultService.save(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newScoringResultId = scoringResult.getId();
        return ResultUtils.success(newScoringResultId);
    }

    /**
     * 删除评分结果
     *
     * @param deleteRequest 删除评分结果请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除评分结果")
    public BaseResponse<Boolean> deleteScoringResult(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser();
        long id = deleteRequest.getId();
        // 判断是否存在
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldScoringResult.getId().equals(user.getId()) && !userService.isAdmin()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = scoringResultService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新评分结果（仅管理员可用）
     *
     * @param scoringResultUpdateRequest 更新评分结果请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新评分结果（仅管理员可用）")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateScoringResult(@RequestBody ScoringResultUpdateRequest scoringResultUpdateRequest) {
        if (scoringResultUpdateRequest == null || scoringResultUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //在此处将实体类和 DTO 进行转换
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultUpdateRequest, scoringResult);
        List<String> resultProp = scoringResultUpdateRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // 数据校验
        scoringResultService.validScoringResult(scoringResult, false);
        // 判断是否存在
        long id = scoringResultUpdateRequest.getId();
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = scoringResultService.updateById(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取评分结果（封装类）
     *
     * @param id 评分结果 id
     * @return {@link BaseResponse }<{@link ScoringResultVO }>
     */
    @GetMapping("/get/vo")
    @ApiOperation(value = "根据 id 获取评分结果（封装类）")
    public BaseResponse<ScoringResultVO> getScoringResultVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        ScoringResult scoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(scoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(scoringResultService.getScoringResultVO(scoringResult));
    }

    /**
     * 分页获取评分结果列表（仅管理员可用）
     *
     * @param scoringResultQueryRequest 分页获取评分结果列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link ScoringResult }>>
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "分页获取评分结果列表（仅管理员可用）")
    public BaseResponse<Page<ScoringResult>> listScoringResultByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest) {
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // 查询数据库
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        return ResultUtils.success(scoringResultPage);
    }

    /**
     * 分页获取评分结果列表（封装类）
     *
     * @param scoringResultQueryRequest 分页获取评分结果列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link ScoringResultVO }>>
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取评分结果列表（封装类）")
    public BaseResponse<Page<ScoringResultVO>> listScoringResultVOByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest) {
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        // 获取封装类
        return ResultUtils.success(scoringResultService.getScoringResultVOPage(scoringResultPage));
    }

    /**
     * 分页获取当前登录用户创建的评分结果列表
     *
     * @param scoringResultQueryRequest 分页获取评分结果列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link ScoringResultVO }>>
     */
    @PostMapping("/my/list/page/vo")
    @ApiOperation(value = "分页获取当前登录用户创建的评分结果列表")
    public BaseResponse<Page<ScoringResultVO>> listMyScoringResultVOByPage(@RequestBody ScoringResultQueryRequest scoringResultQueryRequest) {
        ThrowUtils.throwIf(scoringResultQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser();
        scoringResultQueryRequest.setUserId(loginUser.getId());
        long current = scoringResultQueryRequest.getCurrent();
        long size = scoringResultQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<ScoringResult> scoringResultPage = scoringResultService.page(new Page<>(current, size),
                scoringResultService.getQueryWrapper(scoringResultQueryRequest));
        // 获取封装类
        return ResultUtils.success(scoringResultService.getScoringResultVOPage(scoringResultPage));
    }

    /**
     * 编辑评分结果（给用户使用）
     *
     * @param scoringResultEditRequest 编辑评分结果请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑评分结果（给用户使用）")
    public BaseResponse<Boolean> editScoringResult(@RequestBody ScoringResultEditRequest scoringResultEditRequest) {
        if (scoringResultEditRequest == null || scoringResultEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //在此处将实体类和 DTO 进行转换
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultEditRequest, scoringResult);
        List<String> resultProp = scoringResultEditRequest.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        // 数据校验
        scoringResultService.validScoringResult(scoringResult, false);
        User loginUser = userService.getLoginUser();
        // 判断是否存在
        long id = scoringResultEditRequest.getId();
        ScoringResult oldScoringResult = scoringResultService.getById(id);
        ThrowUtils.throwIf(oldScoringResult == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldScoringResult.getId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = scoringResultService.updateById(scoringResult);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
