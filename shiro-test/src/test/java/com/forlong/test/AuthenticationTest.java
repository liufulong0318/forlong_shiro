package com.forlong.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2018/10/17.
 */
public class AuthenticationTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("forlong","123456","admin","user");
    }
    @Test
    public void testAuthentication(){
        //1.构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);
        //2.主体提交认证请求e
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("forlong","123456");
        subject.login(token);
        //3、认证
        System.out.println("isAuthenticated: "+ subject.isAuthenticated());
        //4.
        subject.checkRoles("admin","user");
//        subject.logout();
//        System.out.println("isAuthenticated: "+ subject.isAuthenticated());
    }
}
