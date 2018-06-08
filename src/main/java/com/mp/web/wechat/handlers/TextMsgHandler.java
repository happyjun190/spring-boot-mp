package com.mp.web.wechat.handlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

import java.util.Map;

/**
 * 文本消息处理Handler
 * Created by wushenjun on 2018/6/8.
 */
public class TextMsgHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> map,
                                    WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) throws WxErrorException {
        WxMpXmlOutTextMessage wxMpXmlOutTextMessage = WxMpXmlOutMessage.TEXT().content("测试消息发送").fromUser(wxMpXmlMessage.getToUser())
                .toUser(wxMpXmlMessage.getFromUser()).build();
        return wxMpXmlOutTextMessage;
    }
}
