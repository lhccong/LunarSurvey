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
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerAddRequest;
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerEditRequest;
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerQueryRequest;
import com.cong.lunarsurvey.model.dto.useranswer.UserAnswerUpdateRequest;
import com.cong.lunarsurvey.model.entity.UserAnswer;
import com.cong.lunarsurvey.model.entity.User;
import com.cong.lunarsurvey.model.vo.UserAnswerVO;
import com.cong.lunarsurvey.service.UserAnswerService;
import com.cong.lunarsurvey.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户答题记录接口
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
@RestController
@RequestMapping("/userAnswer")
@Slf4j
@Api(value = "用户答题记录接口")
public class UserAnswerController {

    @Resource
    private UserAnswerService userAnswerService;

    @Resource
    private UserService userService;



    // region 增删改查

    /**
     * 创建用户答题记录
     *
     * @param userAnswerAddRequest 创建用户答题记录请求
     * @return {@link BaseResponse }<{@link Long }>
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建用户答题记录")
    public BaseResponse<Long> addUserAnswer(@RequestBody UserAnswerAddRequest userAnswerAddRequest) {
        long newUserAnswerId = userAnswerService.addUserAnswer(userAnswerAddRequest);

        return ResultUtils.success(newUserAnswerId);
    }

    /**
     * 删除用户答题记录
     *
     * @param deleteRequest 删除用户答题记录请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除用户答题记录")
    public BaseResponse<Boolean> deleteUserAnswer(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser();
        long id = deleteRequest.getId();
        // 判断是否存在
        UserAnswer oldUserAnswer = userAnswerService.getById(id);
        ThrowUtils.throwIf(oldUserAnswer == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldUserAnswer.getId().equals(user.getId()) && !userService.isAdmin()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = userAnswerService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新用户答题记录（仅管理员可用）
     *
     * @param userAnswerUpdateRequest 更新用户答题记录请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户答题记录（仅管理员可用）")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUserAnswer(@RequestBody UserAnswerUpdateRequest userAnswerUpdateRequest) {
        if (userAnswerUpdateRequest == null || userAnswerUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //在此处将实体类和 DTO 进行转换
        UserAnswer userAnswer = new UserAnswer();
        BeanUtils.copyProperties(userAnswerUpdateRequest, userAnswer);
        List<String> choices = userAnswerUpdateRequest.getChoices();
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        // 数据校验
        userAnswerService.validUserAnswer(userAnswer, false);
        // 判断是否存在
        long id = userAnswerUpdateRequest.getId();
        UserAnswer oldUserAnswer = userAnswerService.getById(id);
        ThrowUtils.throwIf(oldUserAnswer == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = userAnswerService.updateById(userAnswer);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户答题记录（封装类）
     *
     * @param id 用户答题记录 id
     * @return {@link BaseResponse }<{@link UserAnswerVO }>
     */
    @GetMapping("/get/vo")
    @ApiOperation(value = "根据 id 获取用户答题记录（封装类）")
    public BaseResponse<UserAnswerVO> getUserAnswerVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        UserAnswer userAnswer = userAnswerService.getById(id);
        ThrowUtils.throwIf(userAnswer == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(userAnswerService.getUserAnswerVO(userAnswer));
    }

    /**
     * 分页获取用户答题记录列表（仅管理员可用）
     *
     * @param userAnswerQueryRequest 分页获取用户答题记录列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link UserAnswer }>>
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "分页获取用户答题记录列表（仅管理员可用）")
    public BaseResponse<Page<UserAnswer>> listUserAnswerByPage(@RequestBody UserAnswerQueryRequest userAnswerQueryRequest) {
        long current = userAnswerQueryRequest.getCurrent();
        long size = userAnswerQueryRequest.getPageSize();
        // 查询数据库
        Page<UserAnswer> userAnswerPage = userAnswerService.page(new Page<>(current, size),
                userAnswerService.getQueryWrapper(userAnswerQueryRequest));
        return ResultUtils.success(userAnswerPage);
    }

    /**
     * 分页获取用户答题记录列表（封装类）
     *
     * @param userAnswerQueryRequest 分页获取用户答题记录列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link UserAnswerVO }>>
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取用户答题记录列表（封装类）")
    public BaseResponse<Page<UserAnswerVO>> listUserAnswerVOByPage(@RequestBody UserAnswerQueryRequest userAnswerQueryRequest) {
        long current = userAnswerQueryRequest.getCurrent();
        long size = userAnswerQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<UserAnswer> userAnswerPage = userAnswerService.page(new Page<>(current, size),
                userAnswerService.getQueryWrapper(userAnswerQueryRequest));
        // 获取封装类
        return ResultUtils.success(userAnswerService.getUserAnswerVOPage(userAnswerPage));
    }

    /**
     * 分页获取当前登录用户创建的用户答题记录列表
     *
     * @param userAnswerQueryRequest 分页获取用户答题记录列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link UserAnswerVO }>>
     */
    @PostMapping("/my/list/page/vo")
    @ApiOperation(value = "分页获取当前登录用户创建的用户答题记录列表")
    public BaseResponse<Page<UserAnswerVO>> listMyUserAnswerVOByPage(@RequestBody UserAnswerQueryRequest userAnswerQueryRequest) {
        ThrowUtils.throwIf(userAnswerQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser();
        userAnswerQueryRequest.setUserId(loginUser.getId());
        long current = userAnswerQueryRequest.getCurrent();
        long size = userAnswerQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<UserAnswer> userAnswerPage = userAnswerService.page(new Page<>(current, size),
                userAnswerService.getQueryWrapper(userAnswerQueryRequest));
        // 获取封装类
        return ResultUtils.success(userAnswerService.getUserAnswerVOPage(userAnswerPage));
    }

    /**
     * 编辑用户答题记录（给用户使用）
     *
     * @param userAnswerEditRequest 编辑用户答题记录请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑用户答题记录（给用户使用）")
    public BaseResponse<Boolean> editUserAnswer(@RequestBody UserAnswerEditRequest userAnswerEditRequest) {
        if (userAnswerEditRequest == null || userAnswerEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //在此处将实体类和 DTO 进行转换
        UserAnswer userAnswer = new UserAnswer();
        BeanUtils.copyProperties(userAnswerEditRequest, userAnswer);
        List<String> choices = userAnswerEditRequest.getChoices();
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        // 数据校验
        userAnswerService.validUserAnswer(userAnswer, false);
        User loginUser = userService.getLoginUser();
        // 判断是否存在
        long id = userAnswerEditRequest.getId();
        UserAnswer oldUserAnswer = userAnswerService.getById(id);
        ThrowUtils.throwIf(oldUserAnswer == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldUserAnswer.getId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = userAnswerService.updateById(userAnswer);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion
}
