package com.vicchan.scsample.svc.demo.admin.service;

import com.vicchan.scsample.svc.core.util.ResponseUtil;
import com.vicchan.scsample.svc.demo.admin.dao.AdminDAO;
import com.vicchan.scsample.svc.demo.admin.pojo.AdminDO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.vicchan.scsample.svc.core.common.GlobalString.*;

@Service
public class AdminService {
    private final Log logger = LogFactory.getLog(AdminService.class);

    public static final Integer ADMIN_INVALID_ACCOUNT = 605;

    @Autowired
    private AdminDAO adminDAO;

    public Object login(String username, String password,String ip) {

        if (StringUtils.isEmpty( username ) || StringUtils.isEmpty( password )) {
            return ResponseUtil.badArgument();
        }

        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login( new UsernamePasswordToken( username, password ) );
        } catch (UnknownAccountException uae) {
            // logHelper.logAuthFail("登录", "用户帐号或密码不正确");
            return ResponseUtil.fail( ADMIN_INVALID_ACCOUNT, "用户帐号或密码不正确" );
        } catch (LockedAccountException lae) {
            // logHelper.logAuthFail("登录", "用户帐号已锁定不可用");
            return ResponseUtil.fail( ADMIN_INVALID_ACCOUNT, "用户帐号已锁定不可用" );

        } catch (AuthenticationException ae) {
            // logHelper.logAuthFail("登录", "认证失败");
            return ResponseUtil.fail( ADMIN_INVALID_ACCOUNT, "认证失败" );
        }

        currentUser = SecurityUtils.getSubject();
        AdminDO admin = (AdminDO) currentUser.getPrincipal();
        // admin.setLastLoginIp( IpUtil.getIpAddr( request ) );
        admin.setLastLoginIp( ip);
        admin.setLastLoginTime( new Date() );
        adminDAO.updateById( admin );

        // logHelper.logAuthSucceed("登录");

        // userInfo
        Map<String, Object> adminInfo = new HashMap<String, Object>();
        adminInfo.put( JSON_NICKNAME, admin.getUsername() );
        adminInfo.put( JSON_AVATAR, admin.getAvatar() );

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put( JSON_TOKEN, currentUser.getSession().getId() );
        result.put( JSON_ADMIN_INFO, adminInfo );
        return ResponseUtil.ok( result );
    }

}
