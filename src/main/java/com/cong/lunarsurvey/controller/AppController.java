package com.cong.lunarsurvey.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.cong.lunarsurvey.common.*;
import com.cong.lunarsurvey.constant.UserConstant;
import com.cong.lunarsurvey.exception.BusinessException;
import com.cong.lunarsurvey.exception.ThrowUtils;
import com.cong.lunarsurvey.model.dto.app.AppAddRequest;
import com.cong.lunarsurvey.model.dto.app.AppEditRequest;
import com.cong.lunarsurvey.model.dto.app.AppQueryRequest;
import com.cong.lunarsurvey.model.dto.app.AppUpdateRequest;
import com.cong.lunarsurvey.model.entity.App;
import com.cong.lunarsurvey.model.entity.User;
import com.cong.lunarsurvey.model.enums.ReviewStatusEnum;
import com.cong.lunarsurvey.model.vo.AppVO;
import com.cong.lunarsurvey.service.AppService;
import com.cong.lunarsurvey.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 应用接口
 *
 * @author <a href="https://github.com/lhccong">聪</a>

 */
@RestController
@RequestMapping("/app")
@Slf4j
@Api(tags = "应用接口")
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建应用
     *
     * @param appAddRequest 创建应用请求
     * @return {@link BaseResponse }<{@link Long }>
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建应用")
    public BaseResponse<Long> addApp(@RequestBody AppAddRequest appAddRequest) {
        ThrowUtils.throwIf(appAddRequest == null, ErrorCode.PARAMS_ERROR);
        App app = new App();
        BeanUtils.copyProperties(appAddRequest, app);
        // 数据校验
        appService.validApp(app, true);
        User loginUser = userService.getLoginUser();
        app.setId(loginUser.getId());
        // 写入数据库
        boolean result = appService.save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newAppId = app.getId();
        return ResultUtils.success(newAppId);
    }

    /**
     * 删除应用
     *
     * @param deleteRequest 删除应用请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除应用")
    public BaseResponse<Boolean> deleteApp(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser();
        long id = deleteRequest.getId();
        // 判断是否存在
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldApp.getId().equals(user.getId()) && !userService.isAdmin()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = appService.removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新应用（仅管理员可用）
     *
     * @param appUpdateRequest 更新应用请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新应用（仅管理员可用）")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateApp(@RequestBody AppUpdateRequest appUpdateRequest) {
        if (appUpdateRequest == null || appUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        App app = new App();
        BeanUtils.copyProperties(appUpdateRequest, app);
        // 数据校验
        appService.validApp(app, false);
        // 判断是否存在
        long id = appUpdateRequest.getId();
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取应用（封装类）
     *
     * @param id 应用 id
     * @return {@link BaseResponse }<{@link AppVO }>
     */
    @GetMapping("/get/vo")
    @ApiOperation(value = "根据 id 获取应用（封装类）")
    public BaseResponse<AppVO> getAppVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        App app = appService.getById(id);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR);
        // 获取封装类
        return ResultUtils.success(appService.getAppVO(app));
    }

    /**
     * 分页获取应用列表（仅管理员可用）
     *
     * @param appQueryRequest 分页获取应用列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link App }>>
     */
    @PostMapping("/list/page")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    @ApiOperation(value = "分页获取应用列表（仅管理员可用）")
    public BaseResponse<Page<App>> listAppByPage(@RequestBody AppQueryRequest appQueryRequest) {
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // 查询数据库
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        return ResultUtils.success(appPage);
    }

    /**
     * 分页获取应用列表（封装类）
     *
     * @param appQueryRequest 分页获取应用列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link AppVO }>>
     */
    @PostMapping("/list/page/vo")
    @ApiOperation(value = "分页获取应用列表（封装类）")
    public BaseResponse<Page<AppVO>> listAppVOByPage(@RequestBody AppQueryRequest appQueryRequest) {
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        // 获取封装类
        return ResultUtils.success(appService.getAppVOPage(appPage));
    }

    /**
     * 分页获取当前登录用户创建的应用列表
     *
     * @param appQueryRequest 分页获取应用列表请求
     * @return {@link BaseResponse }<{@link Page }<{@link AppVO }>>
     */
    @PostMapping("/my/list/page/vo")
    @ApiOperation(value = "分页获取当前登录用户创建的应用列表")
    public BaseResponse<Page<AppVO>> listMyAppVOByPage(@RequestBody AppQueryRequest appQueryRequest) {
        ThrowUtils.throwIf(appQueryRequest == null, ErrorCode.PARAMS_ERROR);
        // 补充查询条件，只查询当前登录用户的数据
        User loginUser = userService.getLoginUser();
        appQueryRequest.setUserId(loginUser.getId());
        long current = appQueryRequest.getCurrent();
        long size = appQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        // 查询数据库
        Page<App> appPage = appService.page(new Page<>(current, size),
                appService.getQueryWrapper(appQueryRequest));
        // 获取封装类
        return ResultUtils.success(appService.getAppVOPage(appPage));
    }

    /**
     * 编辑应用（给用户使用）
     *
     * @param appEditRequest 编辑应用请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/edit")
    @ApiOperation(value = "编辑应用（给用户使用）")
    public BaseResponse<Boolean> editApp(@RequestBody AppEditRequest appEditRequest) {
        if (appEditRequest == null || appEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        App app = new App();
        BeanUtils.copyProperties(appEditRequest, app);
        // 数据校验
        appService.validApp(app, false);
        User loginUser = userService.getLoginUser();
        // 判断是否存在
        long id = appEditRequest.getId();
        App oldApp = appService.getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可编辑
        if (!oldApp.getId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 操作数据库
        boolean result = appService.updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    // endregion

    /**
     * 应用审核
     *
     * @param reviewRequest 审核请求
     * @return {@link BaseResponse }<{@link Boolean }>
     */
    @PostMapping("/review")
    @SaCheckRole(UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> doAppReview(@RequestBody ReviewRequest reviewRequest) {
        appService.doAppReview(reviewRequest);
        return ResultUtils.success(true);
    }
}
