package com.forlong.test;

import com.forlong.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by Administrator on 2018/10/17.
 */
public class CustomRealmTest {
    @Test
    public void testAuthentication(){

       CustomRealm customRealm = new CustomRealm();
        //1.构建SecurityManage环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        //2.主体提交认证请求e
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("forlong","123456");
        subject.login(token);
        //3、是否认证
        System.out.println("isAuthenticated: "+ subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkRoles("admin","user");
        //4.权限
        subject.checkPermission("user:add");
    }
}
