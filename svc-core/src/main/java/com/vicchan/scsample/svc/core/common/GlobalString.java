package com.vicchan.scsample.svc.core.common;

import com.vicchan.scsample.svc.core.swagger.model.ApiSingleParam;

/**
 * @author VicChan
 * @date 2019/10/23 2:44 PM
 */
public class GlobalString {

  /**
   * 返回通用类型normal：自动添加errorCode和errorMsg
   */
  public static final String RESULT_TYPE_NORMAL = "normal";
  /**
   * 返回对象类型Object：把数据封装成一个对象，并在对象外添加errorCode和errorMsg
   */
  public static final String RESULT_TYPE_OBJECT = "object";
  /**
   * 返回分页类型page：把数据封装成一个对象列表，并在列表外添加errorCode、errorMsg、startPageNum、pageSize、totalCount
   */
  public static final String RESULT_TYPE_PAGE = "page";
  /**
   * 返回列表类型list：把数据封装成一个对象列表，并在列表外添加errorCode、errorMsg
   */
  public static final String RESULT_TYPE_LIST = "list";
  /**
   * 返回其他（自定义）类型other：不会自动添加任何字段，纯自定义
   */
  public static final String RESULT_TYPE_OTHER = "other";
  /**
   * 返回对象的参数名称：data
   */
  public static final String RESULT_NAME_DATA = "data";

  @ApiSingleParam(value = "响应码", example = "0")
  public static final String JSON_ERROR_CODE = "errorCode";

  @ApiSingleParam(value = "响应信息", example = "成功")
  public static final String JSON_ERROR_MSG = "errorMsg";

  @ApiSingleParam(value = "开始页", example = "1")
  public static final String JSON_START_PAGE_NUM = "startPageNum";

  @ApiSingleParam(value = "单页数据条数", example = "1")
  public static final String JSON_PAGE_SIZE = "pageSize";

  @ApiSingleParam(value = "页数", example = "10")
  public static final String JSON_PAGE_COUNT = "pageCount";

  @ApiSingleParam(value = "数据总条数", example = "10")
  public static final String JSON_TOTAL_COUNT = "totalCount";






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

  /**
   * 对象引用，items为对象中的字段
   */
  @ApiSingleParam(value = "用户信息对象", items = {JSON_NICKNAME,JSON_AVATAR}, type =  Object.class)
  public static final String JSON_ADMIN_INFO = "adminInfo";

  // @ApiSingleParam(value = "用户信息对象2", items = {JSON_ADMIN_INFO,JSON_PASSWORD}, type =  Object.class)
  // public static final String JSON_ADMIN_INFO2 = "adminInfo2";



}
