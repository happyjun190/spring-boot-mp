package com.mp.dao;

import com.mp.commons.annotation.MapperRepository;
import com.mp.web.model.out.WechatUserInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * Created by ziye on 2018/3/17.
 */
@MapperRepository("wechatUserDAO")
public interface WechatUserDAO {
    /**
     * 新增/修改微信用户信息(绑定)
     * @param wechatUserInfoDTO
     */
    void addOrUpdateWechatUserInfo(WechatUserInfoDTO wechatUserInfoDTO);


    /**
     * 通过openId获取微信用户信息
     * @param openId
     * @return
     */
    WechatUserInfoDTO getWechatUserInfoByOpenId(@Param("openId") String openId);


    /**
     * 解绑时，清空用户绑定关系
     * @param openId
     */
    @Update("update TS_USER_INFO set OPENID = NULL where OPENID=#{openId}")
    void clearUserInfoOpenId(@Param("openId") String openId);

}
