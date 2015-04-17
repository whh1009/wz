package wz.controller;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;


/**
 * Created by wanghonghui on 2015/4/17.
 */
public class IndexController extends Controller{

    Logger log = Logger.getLogger("");

    @ClearInterceptor(ClearLayer.ALL)
    public void index(){
        render("/index.jsp");
    }
}
