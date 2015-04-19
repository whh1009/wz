package wz.routes;

import com.jfinal.config.Routes;
import wz.controller.UserController;

/**
 * Created by Administrator on 2015/4/18.
 */
public class UserRoute extends Routes {

    /**
     * you must implement config method and use add method to config route
     */
    @Override
    public void config() {
        //用户
        add("/user", UserController.class, "/wzuser");
        add("/register", UserController.class);
    }
}
