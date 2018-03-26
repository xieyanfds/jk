package com.xy.utils.distributed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author xieyan
 * @description
 * @date 2018/3/26.
 */
public class PlatformWebFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(PlatformWebFilter.class);

    private String[] skipPath;

    /**
     * 是否在滤过路径中
     *
     * @param path 当前url路径
     * @return true:在过滤路径中 false:不在过滤路径中
     */
    private boolean inSkipPath(String path) {
        for (int i = 0; i < skipPath.length; i++) {
            if (StringUtil.contains(path, skipPath[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.skipPath = filterConfig.getInitParameter("skipPath").split(",");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String currPath = request.getRequestURI();	//当前请求的URL
        String paramStr = request.getQueryString()==null?"":"?"+request.getQueryString();	//请求参数

//        logger.info("currPath===========" + currPath);
//        if ((skipPath != null && inSkipPath(currPath)) || session.getAttribute(Constants.LOGINED_KEY) != null || session.getAttribute(Constants.BUSINESS_USER_LOGIN_KEY) != null) {
//                logger.debug("在skipPath中............" + currPath);
//
//    			if(session.getAttribute(Constants.LOGINED_KEY) != null || session.getAttribute(Constants.BUSINESS_USER_LOGIN_KEY) != null ){
//    				// 循环系统所有页面资源，查找当前页面的一二级菜单groupId
//    				for (Iterator iterator = Constants.RESOURCE_PAGE_LIST.iterator(); iterator.hasNext();) {
//    					ResourcePagePojo resourcePagePojo = (ResourcePagePojo)iterator.next();
//    					if(resourcePagePojo.getUrl().contains(currPath+paramStr)||(currPath+paramStr).contains(resourcePagePojo.getUrl())){
//    						session.setAttribute("currMenuPageId", resourcePagePojo.getId());
//    						Integer currMenuGroupId = resourcePagePojo.getGroupId();
//    						Integer curr2ndMenuGroupId = 0;
//    						for (Iterator resouceGroup = Constants.RESOURCE_GROUP_LIST.iterator(); resouceGroup.hasNext();) {
//    							ResourceGroupPojo rGroup = (ResourceGroupPojo) resouceGroup.next();
//    							if (currMenuGroupId.equals(rGroup.getId()) && rGroup.getLevel() == Const.RESOURCE_GROUP_LEVEL_TWO) {
//    								curr2ndMenuGroupId = resourcePagePojo.getGroupId();
//    								currMenuGroupId = rGroup.getParentId();
//    								break;
//    							}
//    						}
//    						session.setAttribute("curr2ndMenuGroupId", curr2ndMenuGroupId); // 二级菜单id
//    						session.setAttribute("currMenuGroupId", currMenuGroupId); // 一级菜单id
//    						break;
//    					}
//    				}
//
//    			}
//                filterChain.doFilter(servletRequest, servletResponse);
//          } else {
//                logger.debug("不在skipPath中............" + currPath);
//                logger.debug("用户没有登陆............去登陆...");
//
//                if(session.getAttribute(Constants.BUSINESS_ID) != null){
//                	response.sendRedirect("/framework/index.jsp");
//                }else{
//                	response.sendRedirect("/framework/login.jsp");
//                }
//
//
//
//              先取消掉cookie这块儿的使用，cookie的使用待定
//              String cookieValue = CookieUtil.getCookie(request);
//              if (null == cookieValue) {
//                  response.sendRedirect("/framework/login.jsp");
//              } else {
//                  String msg = CookieUtil.validateCookie(cookieValue,request,response);
//                  logger.debug("msg======"+msg);
//                  if(null!=msg&&"success".equals(msg)){
//                      filterChain.doFilter(servletRequest, servletResponse);
//                  }else{
//                      response.sendRedirect("/framework/login.jsp");
//                  }
//              }
//            request.getRequestDispatcher("/jws/sessionTimeout.jsp").forward(servletRequest, servletResponse);
//            }
    }

    @Override
    public void destroy() {

    }
}
