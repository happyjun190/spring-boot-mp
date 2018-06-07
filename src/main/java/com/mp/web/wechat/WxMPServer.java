package com.mp.web.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mp.dao.WechatUserDAO;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wushenjun on 2018/6/7.
 */
@Component
@WebServlet("/wechat-mp")
public class WxMPServer extends HttpServlet {

    private static WxMpConfigStorage wxMpConfigStorage;
    private static WxMpService wxMpService;
    private static WxMpMessageRouter wxMpMessageRouter;


    Logger logger = LoggerFactory.getLogger(WxMPServer.class);
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    private static final long serialVersionUID = 1L;
    // 实例化 统一业务API入口

    private WechatUserDAO wechatUserDAO;

    /**
     * servlet 无法通过@Autowired等方式注入bean，只好通过servlet生命周期开始时(init方式)，注入需要的bean
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {
            ServletContext servletContext = this.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            this.wechatUserDAO = (WechatUserDAO) webApplicationContext.getBean("wechatUserDAO");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求:校验服务器有效性
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
            }
        }

    }

    /**
     * post请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 返回消息给微信服务器
        PrintWriter out = response.getWriter();
        // 获取encrypt_type 消息加解密方式标识
        String encrypt_type = request.getParameter("encrypt_type");
        logger.info("doPost 请求， encrypt_type:{}", encrypt_type);

        try {

            wxMpMessageRouter = new WxMpMessageRouter(wxMpService);


        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            out.close();
        }
    }

}
