package com.rimi.mall.security;

import com.rimi.mall.pojo.Permission;
import com.rimi.mall.pojo.Role;
import com.rimi.mall.pojo.User;
import com.rimi.mall.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义认证和授权
 *
 * @author admin
 * @date 2019-04-15 10:34
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /**
     * 授权，对用户的请求进行鉴权操作，决定用户在程序中能够访问的方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取当前的用户名
        String username = (String) principals.getPrimaryPrincipal();
        // 根据用户名查询用户所拥有的角色和权限
        User user = userService.findByUserName(username);
        // 获取当前用户的角色
        Set<Role> roleSet = user.getRoles();
        // 角色名的集合
        Set<String> roles = new HashSet<>(roleSet.size());
        // 权限名的结合
        Set<String> permissions = new HashSet<>();
        for (Role role : roleSet) {
            roles.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getExpression());
            }
        }
        // 把当前用户的角色和权限存入授权信息中
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 认证，用户的信息进行验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名
        String username = (String) token.getPrincipal();
        // 根据用户名查询用户是否存在
        User userLogin = userService.findByUserName(username);
        if (userLogin == null) {
            return null;
        }
        // 判断密码是否正确
        // 把用户名作为盐值
        ByteSource source = ByteSource.Util.bytes(username);

        return new SimpleAuthenticationInfo(userLogin.getUsername(), userLogin.getPassword(), source, getName());
    }
}
