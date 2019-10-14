package com.vicchan.svc.demo.admin.web;

import com.vicchan.svc.demo.admin.service.AdminService;
import com.vicchan.svc.demo.admin.util.IpUtil;
import com.vicchan.svc.demo.admin.util.JacksonUtil;
import com.vicchan.svc.demo.admin.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
@Validated
public class AdminAuthController {
  private final Log logger = LogFactory.getLog( AdminAuthController.class );

  @Autowired
  private AdminService adminService;
  // @Autowired
  // private LogHelper logHelper;

  @PostMapping("/login")
  public Object login(@RequestBody String body, HttpServletRequest request) {
    String username = JacksonUtil.parseString( body, "username" );
    String password = JacksonUtil.parseString( body, "password" );
    String ip = IpUtil.getIpAddr( request );
    return adminService.login( username, password, ip );
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
