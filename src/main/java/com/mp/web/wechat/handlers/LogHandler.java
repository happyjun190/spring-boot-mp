package com.mp.web.wechat.handlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 日志处理handler
 * Created by wushenjun on 2018/6/8.
 */
public class LogHandler implements WxMpMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogHandler.class);
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> map,
                                    WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) throws WxErrorException {
        //目前只是打印log日志消息
        logger.info("wxMpXmlMessage:{}", wxMpXmlMessage.toString());
        return null;
    }
}
