package com.mp.service.version;

import com.mp.commons.result.JsonResult;
import com.mp.dao.log.AppVersionDAO;
import com.mp.model.log.TabAppVersion;
import com.mp.web.model.in.version.VersionCheckInModel;
import com.mp.web.model.out.version.VersionUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final AppVersionDAO appVersionDAO;

    public JsonResult<VersionUpdateDTO> checkVersionUpdate(VersionCheckInModel inModel) {

        TabAppVersion tabAppVersion = appVersionDAO.getNewAppVersionInfoByType(inModel.getAppType());

        boolean forceUpdate = false;//默认不强制升级
        VersionUpdateDTO versionUpdateDTO = new VersionUpdateDTO();
        versionUpdateDTO.setAppType(inModel.getAppType());
        if(tabAppVersion==null) {
            versionUpdateDTO.setForceUpdate(false);
            versionUpdateDTO.setMessage("已经是最新版本");
            versionUpdateDTO.setIsLatestVersion(1);
        } else {
            versionUpdateDTO.setAppVersion(tabAppVersion.getVersion());
            versionUpdateDTO.setAppType(tabAppVersion.getDeviceType());
            versionUpdateDTO.setUpdateTime(getStringTime(tabAppVersion.getUpdateTime(), "yyyy-MM-dd"));
            versionUpdateDTO.setUrl(tabAppVersion.getDownUrl());
            versionUpdateDTO.setUpdateDec(tabAppVersion.getContent());
            versionUpdateDTO.setPackageUrl(tabAppVersion.getPackageUrl());

            String imgUrl = tabAppVersion.getImg();
            if (StringUtils.isNotBlank(imgUrl)) {
                List<Map<String, String>> updateUrl = new ArrayList<Map<String, String>>();
                Map<String, String> updateUrlMap = new HashMap<String, String>();
                String[] imgUrlArray = imgUrl.split(";");
                for (String imgUrlItem : imgUrlArray) {
                    //TODO
                    updateUrl.add(updateUrlMap);
                    updateUrlMap.clear();
                }
                versionUpdateDTO.setUpdatePic(updateUrl);
            }

            boolean isNewVersion = isNewVersion(inModel.getAppVersion(), tabAppVersion.getVersion());

            if(tabAppVersion.getIsMustupdate()==1) {//需要强制更新
                forceUpdate = true;
            } else {//不需要强制更新,通过检查更新版本号来判断
                if(StringUtils.isNotBlank(tabAppVersion.getForceUpdateVer())) {
                    //判断app版本与强制更新版本哪一个版本号更新
                    //version:1.6.7    ForceUpdateVer 2.1.0  返回为false,用户app当前版本小于强制升级版本,需要强制升级
                    //version:2.1.0    ForceUpdateVer 1.6.7 返回为true,用户app当前版本大于强制升级版本,不需要强制升级
                    //attention:当两个参数相同时,则返回false
                    forceUpdate = !isNewVersion(inModel.getAppVersion(), tabAppVersion.getForceUpdateVer());
                    //如果用户app当前版本与最低强制更新版本一致,则需要更新
                    if(inModel.getAppVersion().equals(tabAppVersion.getForceUpdateVer())) {
                        forceUpdate = true;
                    }
                }
            }
            versionUpdateDTO.setForceUpdate(forceUpdate);

            if (isNewVersion) {
                versionUpdateDTO.setMessage("已经是最新版本");
                versionUpdateDTO.setIsLatestVersion(1);
            } else {
                versionUpdateDTO.setMessage("最新版本为："+tabAppVersion.getVersion());
                versionUpdateDTO.setIsLatestVersion(0);
            }

        }

        return JsonResult.success("检查版本更新成功", versionUpdateDTO);
    }



    /**
     * 断前端的版本号是否是最新版本
     * @param oldVersion 前端传过来的版本号
     * @param newVersion App最新的版本号
     * @return boolean  true：是最新版本  false：不是最新版本
     */
    public static boolean isNewVersion(String oldVersion, String newVersion) {
        boolean isNew = true;
        String[] oldVersions = oldVersion.split("\\.");
        String[] newVersions = newVersion.split("\\.");
        for(int i=0; i<4; i++) {
            if(((oldVersions.length-1)>=i)&&((newVersions.length-1)>=i)) {
                int old = Integer.parseInt(oldVersions[i]);
                int new1 = Integer.parseInt(newVersions[i]);
                if(old<new1) {
                    isNew = false;
                    break;
                } else if (old > new1) {  // 已经是最新版本
                    break;
                }
            } else {
                if((newVersions.length-1)>=i) {
                    isNew = false;
                    break;
                }
            }
        }
        return isNew;
    }


    private static String getStringTime(long timestamp, String format) {
        if (timestamp == 0) {
            return "";
        }
        Instant instant = Instant.ofEpochMilli(timestamp * 1000);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

}
