package com.mp.web.model.out;

import lombok.Data;

import java.util.List;

/**
 * Created by ziye on 2018/3/17.
 */
@Data
public class WechatUserInfoDTO {
    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private int subscribe_time;
    private String remark;
    private int groupid;
    private List<String> tagid_list;
    private String subscribe_scene;
    private int qr_scene;
    private String qr_scene_str;

    @Override
    public String toString() {
        return "WechatUserInfoDTO{" +
                "subscribe=" + subscribe +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", subscribe_time=" + subscribe_time +
                ", remark='" + remark + '\'' +
                ", groupid=" + groupid +
                ", tagid_list=" + tagid_list +
                ", subscribe_scene='" + subscribe_scene + '\'' +
                ", qr_scene=" + qr_scene +
                ", qr_scene_str='" + qr_scene_str + '\'' +
                '}';
    }
}
