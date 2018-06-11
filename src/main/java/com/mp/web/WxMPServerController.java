package com.mp.web;

import com.mp.web.wechat.handlers.EvtMsgHandler;
import com.mp.web.wechat.handlers.LogHandler;
import com.mp.web.wechat.handlers.TextMsgHandler;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceHttpClientImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wushenjun on 2018/6/11.
 */
@RestController
public class WxMPServerController {

    Logger logger = LoggerFactory.getLogger(WxMPServerController.class);

    private static WxMpConfigStorage configStorage;
    private static WxMpService wxMpService = new WxMpServiceHttpClientImpl();
    private static WxMpMessageRouter router;


    /**
     * get请求:校验服务器有效性，以及oAuth验证
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @ApiOperation(value = "wechat get请求:校验服务器有效性，以及oAuth验证", tags="wushenjun", notes = "wechat get请求")
    @RequestMapping(value = "/wx-mp", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //验证是否是跳转链接过来
        String code = request.getParameter("code");
        String redirectUrl = request.getParameter("state");
        if(StringUtils.isNotBlank(code)) {//其他处理
            WxMpOAuth2AccessToken result = null;
            //获取openId
            try {
                result = wxMpService.oauth2getAccessToken(code);
                logger.info("code:{}，openId:{}, access_token:{}, scope:{}", code, result.getOpenId(), result.getAccessToken(), result.getScope());
            } catch (WxErrorException e) {
                logger.error(e.toString());
            } finally {
                String finalUrl = redirectUrl+"?openId="+result.getOpenId();
                logger.info("finalUrl:{}", finalUrl);
                response.sendRedirect(finalUrl);
            }
        } else {
            // 验证服务器的有效性
            PrintWriter out = response.getWriter();
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            logger.info("WeChat check signature information signature:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
            if (wxMpService.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            } else {
                logger.info("此处非法请求");
            }
        }

    }


    /**
     * post请求，用于处理微信的一些交互请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @ApiOperation(value = "post请求，用于处理微信的一些交互请求", tags="wushenjun", notes = "wechat post请求")
    @RequestMapping(value = "/wx-mp", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //返回消息给微信服务器
        PrintWriter out = response.getWriter();
        //获取encrypt_type 消息加解密方式标识
        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ? "raw" : request.getParameter("encrypt_type");
        logger.info("doPost 请求， encrypt_type:{}", encryptType);

        try {
            //1、创建路由器
            WxMpMessageHandler logHandler = new LogHandler();
            WxMpMessageHandler textMsgHandler = new TextMsgHandler();
            WxMpMessageHandler evtMsgHanlder = new EvtMsgHandler();

            //2、设置路由规则
            router = new WxMpMessageRouter(wxMpService);
            router.rule().handler(logHandler).next()
                    .rule().msgType(WxConsts.XmlMsgType.TEXT).handler(textMsgHandler).end()
                    .rule().msgType(WxConsts.XmlMsgType.EVENT).handler(evtMsgHanlder).end();


            //3、处理请求信息
            String nonce = request.getParameter("nonce");
            String timestamp = request.getParameter("timestamp");
            WxMpXmlMessage message;
            WxMpXmlOutMessage outMessage;

            //3.1、处理明文请求
            if ("raw".equals(encryptType)) {//明文传输的消息
                message = WxMpXmlMessage.fromXml(request.getInputStream());
                outMessage = router.route(message);
                if (outMessage != null) {
                    response.getWriter().write(outMessage.toXml());
                }
                return;
            }

            //3.2、处理aes加密请求
            if ("aes".equals(encryptType)) {//aes加密的消息
                String msgSignature = request.getParameter("msg_signature");
                message = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), configStorage, timestamp, nonce, msgSignature);
                outMessage = router.route(message);
                response.getWriter().write(outMessage.toEncryptedXml(configStorage));
                return;
            }

        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            out.close();
        }
    }

}
