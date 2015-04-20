package wz.handle;


import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wanghonghui on 2015/4/18.
 */
public class JspHandle extends Handler {

    /**
     * Handle target
     *
     * @param target    url target of this web http request
     * @param request   HttpServletRequest of this http request
     * @param response  HttpServletRequest of this http request
     * @param isHandled JFinalFilter will invoke doFilter() method if isHandled[0] == false,
     */
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.endsWith(".jsp")){
            HandlerKit.renderError404("errorJsp.html", request, response, isHandled);
        } else {
            nextHandler.handle(target, request, response, isHandled);
        }
    }
}
