package com.vicchan.scsample.svc.demo.admin.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonObject;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonProperty;
import com.vicchan.scsample.svc.core.swagger.model.ApiJsonResult;
import com.vicchan.scsample.svc.core.util.IpUtil;
import com.vicchan.scsample.svc.core.util.JacksonUtil;
import com.vicchan.scsample.svc.core.util.ResponseUtil;
import com.vicchan.scsample.svc.demo.admin.feign.LogFeignClient;
import com.vicchan.scsample.svc.demo.admin.service.AdminService;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.vicchan.scsample.svc.core.common.GlobalString.*;


@RestController
@RequestMapping("/auth")
@Validated
@Api("用户验证API")
public class AdminAuthController {
  private final Log logger = LogFactory.getLog( AdminAuthController.class );

  @Autowired
  private AdminService adminService;
  //编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
  @Autowired
  private LogFeignClient logFC;

  @ApiOperation(value = "登录验证", notes = "用户通过用户名密码登陆")
  @ApiJsonObject(
      name = "authLogin",
      value={
          @ApiJsonProperty( name = JSON_USERNAME,required = true),
          @ApiJsonProperty( name = JSON_PASSWORD,required = true)
      },
      result = @ApiJsonResult(type = RESULT_TYPE_OBJECT, name = RESULT_NAME_DATA,value = {JSON_ADMIN_INFO,JSON_TOKEN})
  )
  @ApiImplicitParam(name = "body",required = true,dataType = "authLogin")
  @ApiResponses({
      @ApiResponse( code=200,message="OK",reference = "authLogin"),
      @ApiResponse(code = -1, message = "错误"),
      @ApiResponse(code = 605, message = "用户帐号或密码不正确"),
  })
  @PostMapping("/login")
  public Object login(@RequestBody String body, HttpServletRequest request) {
    String username = JacksonUtil.parseString( body, JSON_USERNAME );
    String password = JacksonUtil.parseString( body, JSON_PASSWORD );

    String ip = IpUtil.getIpAddr( request );
    //登录
    Map<String, Object> map = (HashMap<String, Object>) adminService.login( username, password, ip );
    Integer errno = (Integer) map.get( JSON_ERROR_CODE );
    //调用svc-demo-log服务，写入日志
    Map<String, Object> logMap = new HashMap<>();
    logMap.put( JSON_ACTION, "登录" );
    logMap.put( JSON_USERNAME, username );
    if (errno != null && errno == 0) {
      //成功
      Object obj = logFC.authSucceed( JSONUtils.toJSONString( logMap ) );
      logger.info( obj.toString() );
    } else {
      //失败
      logMap.put( JSON_ERROR_MSG, map.get( JSON_ERROR_MSG ) );
      Object obj = logFC.authFail( JSONUtils.toJSONString( logMap ) );
      logger.info( obj.toString() );
    }
    return map;
  }

  // /*
  //  *
  //  */
  // @RequiresAuthentication
  // @PostMapping("/logout")
  // public Object logout() {
  //     Subject currentUser = SecurityUtils.getSubject();
  //
  //     logHelper.logAuthSucceed("退出");
  //     currentUser.logout();
  //     return ResponseUtil.ok();
  // }
  //
  //
  // @RequiresAuthentication
  // @GetMapping("/info")
  // public Object info() {
  //     Subject currentUser = SecurityUtils.getSubject();
  //     AdminDO admin = (AdminDO) currentUser.getPrincipal();
  //
  //     Map<String, Object> data = new HashMap<>();
  //     data.put("name", admin.getUsername());
  //     data.put("avatar", admin.getAvatar());
  //
  //     Integer[] roleIds = admin.getRoleIds();
  //     Set<String> roles = roleService.queryByIds(roleIds);
  //     Set<String> permissions = permissionService.queryByRoleIds(roleIds);
  //     data.put("roles", roles);
  //     // NOTE
  //     // 这里需要转换perms结构，因为对于前端而已API形式的权限更容易理解
  //     data.put("perms", toApi(permissions));
  //     return ResponseUtil.ok(data);
  // }
  //
  // @Autowired
  // private ApplicationContext context;
  // private HashMap<String, String> systemPermissionsMap = null;
  //
  // private Collection<String> toApi(Set<String> permissions) {
  //     if (systemPermissionsMap == null) {
  //         systemPermissionsMap = new HashMap<>();
  //         final String basicPackage = "org.linlinjava.litemall.admin";
  //         List<Permission> systemPermissions = PermissionUtil.listPermission(context, basicPackage);
  //         for (Permission permission : systemPermissions) {
  //             String perm = permission.getRequiresPermissions().value()[0];
  //             String api = permission.getApi();
  //             systemPermissionsMap.put(perm, api);
  //         }
  //     }
  //
  //     Collection<String> apis = new HashSet<>();
  //     for (String perm : permissions) {
  //         String api = systemPermissionsMap.get(perm);
  //         apis.add(api);
  //
  //         if (perm.equals("*")) {
  //             apis.clear();
  //             apis.add("*");
  //             return apis;
  //             //                return systemPermissionsMap.values();
  //
  //         }
  //     }
  //     return apis;
  // }

  @GetMapping("/401")
  public Object page401() {
    return ResponseUtil.unlogin();
  }

  @GetMapping("/index")
  public Object pageIndex() {
    return ResponseUtil.ok();
  }

  @GetMapping("/403")
  public Object page403() {
    return ResponseUtil.unauthz();
  }
}
