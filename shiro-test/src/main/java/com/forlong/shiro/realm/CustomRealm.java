package com.forlong.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/17.
 */
public class CustomRealm extends AuthorizingRealm{

    Map<String,String> userMap  = new HashMap<String, String>(16);
    {
        userMap.put("forlong","123456");
        super.setName("customRealm");
    }
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从认证信息中获取用户
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //2.通过用户名获取角色信息
        Set<String> roles = getRolesByUserName(userName);
        
        Set<String> permissions  = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo  = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> set = new HashSet<String>();
        set.add("user:add");
        set.add("user:delete");
        set.add("user:update");
        return set;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> set = new HashSet<String>();
        set.add("admin");
        set.add("user");
        return set;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();
        //2.通过用户名去到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("forlong",password,"customRealm");
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
