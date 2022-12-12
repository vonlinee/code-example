package io.devpl.auth.service;

import io.devpl.auth.domain.Identity;

import java.util.List;

/**
 * 身份/角色Service接口
 * @author Xu Jiabao
 * @since 2022/2/13
 */
public interface IIdentityService {

    /**
     * 根据用户ID获取身份/角色，基础平台和本系统用户统一调用本接口
     * 检查某用户是否有权限时，应通过Subject subject = SecurityUtils.getSubject()判断角色和权限
     * @param userId 用户ID
     * @param token 登录令牌，不为null时代表基础平台用户
     * @param moduleId 模块ID，基础平台用户有效
     * @return 身份列表
     */
    Identity listIdentityByUserId(String userId, String token, String moduleId) throws Exception;

    /**
     * 设置用户身份
     * @param userId 用户ID
     * @param IdentityCode 身份代码
     * @return 影响的行数
     */
    int setIdentity(String userId, String IdentityCode);

    /**
     * 查询身份
     * @param param 查询参数
     * @return 身份列表
     */
    List<Identity> listByCondition(IdentityListParam param);
}