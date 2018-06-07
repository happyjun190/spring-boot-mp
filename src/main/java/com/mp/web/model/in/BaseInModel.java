package com.mp.web.model.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wushenjun on 2016/12/1.
 */
@Data
@ApiModel
public class BaseInModel {

    @ApiModelProperty(value = "用户token", position=-2, example="iamatoken7b14a1986df8888")
    private String userToken;

    @ApiModelProperty(value = "authcode", required = true, position=-1, example="123456")
    private String authcode;

    /** 终端类型：Android, iOS, web, pc */
    @ApiModelProperty(hidden = true)
    private String plateform;

    /** 前端软件版本号 */
    @ApiModelProperty(hidden = true)
    private String version;

    /** 设备id */
    @ApiModelProperty(hidden = true)
    private String ueDeviceId;

    /** 经度 */
    @ApiModelProperty(hidden = true)
    private Double ueLon;

    /** 纬度 */
    @ApiModelProperty(hidden = true)
    private Double ueLat;

    /** --------------------分割线，以下为log所需参数------------------------  */
    @ApiModelProperty(hidden = true)
    private String userId;

    @ApiModelProperty(hidden = true)
    private String loginName;

    public BaseInModel() {
    }

}
