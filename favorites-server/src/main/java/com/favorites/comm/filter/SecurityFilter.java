package com.favorites.comm.filter;//package com.favorites.comm.filter;
//
//import com.favorites.comm.Const;
//import com.favorites.domain.User;
//import com.favorites.repository.UserMapper;
//import com.favorites.utils.Des3EncryptionUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * 安全过滤器
// *
// * @author yanni
// * @date 2022/01/20
// */
//public class SecurityFilter implements Filter {
//
//    protected Logger logger = LoggerFactory.getLogger(this.getClass());
//    private static Set<String> GreenUrlSet = new HashSet<String>();
//
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException {
//        // TODO Auto-generated method stub
//        GreenUrlSet.add("/login");
//        GreenUrlSet.add("/register");
//        GreenUrlSet.add("/index");
//        GreenUrlSet.add("/forgotPassword");
//        GreenUrlSet.add("/newPassword");
//        GreenUrlSet.add("/tool");
//    }
//
//    @Override
//    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        // TODO Auto-generated method stub
//        HttpServletRequest request = (HttpServletRequest) srequest;
//        String uri = request.getRequestURI();
//        if (containsSuffix(uri) || GreenUrlSet.contains(uri) || containsKey(uri)) {
//            logger.debug("don't check  url , " + request.getRequestURI());
//            filterChain.doFilter(srequest, sresponse);
//
//        }
//    }
//
//
//    /**
//     * @param url
//     * @return
//     * @author neo
//     * @date 2016-5-4
//     */
//    private boolean containsSuffix(String url) {
//        if (url.endsWith(".js")
//                || url.endsWith(".css")
//                || url.endsWith(".jpg")
//                || url.endsWith(".gif")
//                || url.endsWith(".png")
//                || url.endsWith(".html")
//                || url.endsWith(".eot")
//                || url.endsWith(".svg")
//                || url.endsWith(".ttf")
//                || url.endsWith(".woff")
//                || url.endsWith(".ico")
//                || url.endsWith(".woff2")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * @param url
//     * @return
//     * @author neo
//     * @date 2016-5-4
//     */
//    private boolean containsKey(String url) {
//
//        if (url.contains("/media/")
//                || url.contains("/login") || url.contains("/user/login")
//                || url.contains("/register") || url.contains("/user/regist") || url.contains("/index")
//                || url.contains("/forgotPassword") || url.contains("/user/sendForgotPasswordEmail")
//                || url.contains("/newPassword") || url.contains("/user/setNewPassword")
//                || (url.contains("/collector") && !url.contains("/collect/detail/"))
//                || url.contains("/collect/standard/") || url.contains("/collect/simple/")
//                || url.contains("/user") || url.contains("/favorites") || url.contains("/comment")
//                || url.contains("/lookAround")
//                || url.startsWith("/user/")
//                || url.startsWith("/feedback")
//                || url.startsWith("/standard/")||url.startsWith("/swagger-ui")||url.startsWith("/v3")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    @Override
//    public void destroy() {
//        // TODO Auto-generated method stub
//    }
//
//    public String codeToString(String str) {
//        String strString = str;
//        try {
//            byte tempB[] = strString.getBytes("ISO-8859-1");
//            strString = new String(tempB);
//            return strString;
//        } catch (Exception e) {
//            return strString;
//        }
//    }
//
//    public String getRef(HttpServletRequest request) {
//        String referer = "";
//        String param = this.codeToString(request.getQueryString());
//        if (StringUtils.isNotBlank(request.getContextPath())) {
//            referer = referer + request.getContextPath();
//        }
//        if (StringUtils.isNotBlank(request.getServletPath())) {
//            referer = referer + request.getServletPath();
//        }
//        if (StringUtils.isNotBlank(param)) {
//            referer = referer + "?" + param;
//        }
//        request.getSession().setAttribute(Const.LAST_REFERER, referer);
//        return referer;
//    }
//
//    public String getUserId(String value) {
//        try {
//            String userId = Des3EncryptionUtil.decode(Const.DES3_KEY, value);
//            userId = userId.substring(0, userId.indexOf(Const.PASSWORD_KEY));
//            return userId;
//        } catch (Exception e) {
//            logger.error("解析cookie异常：", e);
//        }
//        return null;
//    }
//}