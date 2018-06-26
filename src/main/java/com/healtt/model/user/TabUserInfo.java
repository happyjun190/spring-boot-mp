package com.healtt.model.user;

import lombok.Data;


/**
 * Created by wushenjun on 2017/3/6.
 */
@Data
public class TabUserInfo {

    private int id; // id
    private String login; // 登录账号
    private String password; // 密码
    private String head_url; // 头像地址
    private String phone; // 手机
    private String real_name;
    private String loginSalt;// 用户密码加密干盐值

}
