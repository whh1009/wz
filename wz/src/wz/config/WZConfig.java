package wz.config;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import wz.controller.DashBorderController;
import wz.controller.IndexController;
import wz.handle.JspHandle;
import wz.interceptor.LoginInterceptor;
import wz.model.*;
import wz.routes.BookRoute;
import wz.routes.UserRoute;

public class WZConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        loadPropertyFile("db_config.properties");
        me.setViewType(ViewType.JSP);
        me.setDevMode(true);
        me.setError404View("/error404.html");

    }

    @Override
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
        me.add(new UserRoute());
        me.add(new BookRoute());
        me.add("/dashborder", DashBorderController.class, "/dashborder");
    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim(), getProperty("driverName"));
        me.add(c3p0Plugin);
        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);

        arp.setShowSql(true);

        arp.addMapping("wz_user", "user_id", UserModel.class);
        arp.addMapping("wz_user_role", "user_role_id", UserRoleModel.class);
        arp.addMapping("wz_role", "role_id", RoleModel.class);
        arp.addMapping("wz_menu", "menu_id", MenuModel.class);
        arp.addMapping("wz_role_menu", "role_menu_id", RoleMenuModel.class);
        arp.addMapping("wz_book", "book_id", BookModel.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //定义全局拦截器
        me.add(new LoginInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("ctx"));
        me.add(new JspHandle());
    }


    public void afterJFinalStart() {
        //全局缓存角色信息
//        String roleXml = RoleModel.dao.getRoleXml();
//        JFinal.me().getServletContext().setAttribute("roleXml", roleXml);


    }

}
