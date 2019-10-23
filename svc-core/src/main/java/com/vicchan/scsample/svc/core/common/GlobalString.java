package com.vicchan.scsample.svc.core.common;

import com.vicchan.scsample.svc.core.swagger.model.ApiSingleParam;

/**
 * @author VicChan
 * @date 2019/10/23 2:44 PM
 */
public class GlobalString {

  @ApiSingleParam(value = "响应码", example = "0")
  public static final String JSON_ERRNO = "errno";

  @ApiSingleParam(value = "响应信息", example = "成功")
  public static final String JSON_ERRMSG = "errmsg";

  @ApiSingleParam(value = "日志动作", example = "登录")
  public static final String JSON_ACTION = "action";

  @ApiSingleParam(value = "用户名", example = "admin123")
  public static final String JSON_USERNAME = "username";

  @ApiSingleParam(value = "密码", example = "admin123")
  public static final String JSON_PASSWORD = "password";

  @ApiSingleParam(value = "用户昵称", example = "管理员123")
  public static final String JSON_NICKNAME = "nickname";

  @ApiSingleParam(value = "头像url", example = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
  public static final String JSON_AVATAR = "avatar";

  @ApiSingleParam(value = "token验证码", example = "2e25197f-e917-45ae-9c44-c4fadc749c87")
  public static final String JSON_TOKEN = "token";


}
