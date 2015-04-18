package wz.controller;

import com.jfinal.core.Controller;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2015/4/18.
 */
public class DashBorderController extends Controller {

    Logger log = Logger.getLogger("");


    public void index() {
        render("DashBorder.jsp");
    }
}
