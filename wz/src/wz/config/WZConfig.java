package wz.config;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import wz.controller.IndexController;
import wz.controller.UserController;
import wz.interceptor.LoginInterceptor;
import wz.model.MenuModel;
import wz.model.UserModel;

public class WZConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("db_config.properties");
        me.setViewType(ViewType.JSP);
        me.setDevMode(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        me.add("/user", UserController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim(), getProperty("driverName"));
        me.add(c3p0Plugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("wz_user", "user_id", UserModel.class);
        arp.addMapping("wz_menu", "menu_id", MenuModel.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //定义全局拦截器
        me.add(new LoginInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("ctx"));

    }

}
