package com.healtt.model.log;

import lombok.Data;

/**
 * app版本表信息
 */
@Data
public class TabAppVersion {
    private int id;
    private int deviceType;//设备类型1-店员版Android 2-店员版iOS  3-店员版iPad 4-PC 5-Logistic 6-SCMAndroid 7-SCMiOS
    private String version;//应用版本
    private int updateTime;//更新时间
    private String content;//更新说明
    private String img;//更新说明图片 可罗列多个 分号分开
    private int isMustupdate;//是否需要强制更新		0否  1必须
    private String downUrl;//更新地址
    private String forceUpdateVer;//强制升级版本
    private int ctime;//注册时间
    private String packageUrl;//软件包地址
}
