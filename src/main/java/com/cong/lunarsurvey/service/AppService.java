package com.cong.lunarsurvey.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cong.lunarsurvey.common.ReviewRequest;
import com.cong.lunarsurvey.model.dto.app.AppQueryRequest;
import com.cong.lunarsurvey.model.entity.App;
import com.cong.lunarsurvey.model.vo.AppVO;

/**
 * 应用服务
 *
 * @author <a href="https://github.com/lhccong">聪</a>
 */
public interface AppService extends IService<App> {

    /**
     * 校验数据
     *
     * @param app 数据
     * @param add 对创建的数据进行校验
     */
    void validApp(App app, boolean add);

    /**
     * 获取查询条件
     *
     * @param appQueryRequest 查询条件
     * @return QueryWrapper
     */
    QueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest);

    /**
     * 获取应用封装
     *
     * @param app 应用实体
     * @return AppVO
     */
    AppVO getAppVO(App app);

    /**
     * 分页获取应用封装
     *
     * @param appPage 分页数据
     * @return Page<AppVO>
     */
    Page<AppVO> getAppVOPage(Page<App> appPage);

    /**
     * 做应用审核
     *
     * @param reviewRequest 审核请求
     */
    void doAppReview(ReviewRequest reviewRequest);
}
