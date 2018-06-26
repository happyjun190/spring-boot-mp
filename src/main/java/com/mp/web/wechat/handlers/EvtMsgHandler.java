package com.mp.web.wechat.handlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import java.util.Map;

/**
 * 事件消息处理器
 * Created by wushenjun on 2018/6/8.
 */
public class EvtMsgHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> map,
                                    WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) throws WxErrorException {
        //针对不同的eventKey进行处理
        switch (wxMpXmlMessage.getEventKey()) {
            case "":
                break;
            default:
                break;
        }

        return null;
        /*WxMpXmlOutTextMessage wxMpXmlOutTextMessage = WxMpXmlOutMessage.TEXT().content("测试消息发送").fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser()).build();
        return wxMpXmlOutTextMessage;*/
    }
}
