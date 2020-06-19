package com.docker.common.vo;

import java.io.Serializable;

public class UserInfoVo implements Serializable {

    public String uid;  //用户id
    public String uuid;
    public String avatar; // 用户头像
    public String reg_type; // 注册类型 1 个人 2企业
    public String fullName; // 用户姓名
    public String nickname; // 用户昵称
    public String mobile;    // 用户手机号
    public String circleid;  // 默认主圈子ID
    public String utid;      // 主圈子UTID
    public String islock; // 是否锁定 0否 1是
    public String source; // 注册来源 0后台 1APP 2WEB 3WAP 4邀请
    public String isOBT; // 是否公测用户 0否 1是
    public String ismobile; // 是否绑定手机 0否 1是
    public String perfectData; //完善信息状态 0已完善 1未完善
    public boolean ispay; // 支付模块显示
    public int company_role; // 企业角色 0普通 1管理者 首页页面展未有所不同
    public String circleName; //
    public String logoUrl;
    public String wordid;
    public String wordStr;
    public String gradle;
    public String isattend; // 1 已经签到  0 未签到
    public String groupid; // 群组ID
    public String lat; // 群组ID
    public String lng; // 群组ID
    public int isReg = -1;
    public String groupName;
    public String is_company;
    public String udid;
    public String memberid;
}
