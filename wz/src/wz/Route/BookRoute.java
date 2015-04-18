package wz.Route;

import com.jfinal.config.Routes;
import wz.controller.BookController;

/**
 * Created by Administrator on 2015/4/18.
 */
public class BookRoute extends Routes{

    /**
     * you must implement config method and use add method to config route
     */
    @Override
    public void config() {
        add("/wznewbook", BookController.class, "/wznewbook");
        add("/wzbookman", BookController.class, "/wzbookman");
    }
}
