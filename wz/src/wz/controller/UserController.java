package wz.controller;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
import wz.config.Constrant;
import wz.model.ConfigModel;
import wz.model.MenuModel;
import wz.model.UserModel;
import wz.util.StringUtil;

public class UserController extends Controller {
    private static Logger log = Logger.getLogger("");

    public void index() {
        render("Login.jsp");
    }

    public void registerPage() {
        render("Register.jsp");
    }

    /**
     * 登录
     * 清空所有拦截器
     */
    @ClearInterceptor(ClearLayer.ALL)
    public void login() throws Exception{
        String userName = getPara("userName");
        String pwd = getPara("userPassword");
        UserModel user = UserModel.dao.login(userName, pwd);
        if (user != null) {
            log.info("【" + user.get("user_name") + "】登录系统");
            //缓存当前用户对象
            setSessionAttr("user", user);
            try{
                //缓存当前用户的菜单
                setSessionAttr("menuNav", MenuModel.dao.getMenuNavByRoleId(user));
            } catch(Exception e) {
                log.error("生成权限菜单出错", e);
                throw e;
            }
            try{
                ConfigModel cm = ConfigModel.dao.getDbConfigByUserId(Integer.parseInt(StringUtil.ObjectToString(user.get("user_id"))));
                if(cm==null) {
                    setSessionAttr("showColumn", Constrant.SHOW_COLUMN);
                    setSessionAttr("exportColumn", Constrant.EXPORT_COLUMN);
                    setSessionAttr("searchColumn", Constrant.SEARCH_COLOMN);
                } else {
                    if(cm.get("show_column")==null||"null".equals(cm.getStr("show_column"))||"".equals(cm.getStr("show_column"))) {
                        setSessionAttr("showColumn", Constrant.SHOW_COLUMN);
                    } else {
                        setSessionAttr("showColumn", cm.getStr("show_column"));
                    }
                    if(cm.get("export_column")==null||"null".equals(cm.getStr("export_column"))||"".equals(cm.getStr("export_column"))) {
                        setSessionAttr("exportColumn", Constrant.EXPORT_COLUMN);
                    } else {
                        setSessionAttr("exportColumn", cm.getStr("export_column"));
                    }
                    if(cm.get("search_column")==null||"null".equals(cm.getStr("search_column"))||"".equals(cm.getStr("search_column"))) {
                        setSessionAttr("searchColumn", Constrant.SEARCH_COLOMN);
                    } else {
                        setSessionAttr("searchColumn", cm.getStr("search_column"));
                    }
                }
            }catch(Exception e) {
                log.error("获取当前用户的show_column, export_column, search_column出错", e);
            }
            renderJson("1");//登录成功
        } else {
            renderJson("0");//登录失败
        }
    }

    public void logout(){
        removeSessionAttr("user");
        this.forwardAction("index");
    }

    /**
     * 用户列表
     *
     */
    public void myUserList() {
        render("UserList.jsp");
    }

    /**
     * 获取用户列表
     *
     */
    public void getUserList() {
        String userPage = StringUtil.ObjectToString(getPara("userList"));
        int page = Integer.parseInt(userPage);
        renderJson(UserModel.dao.getUserList(page));
    }

    /**
     * 删除用户
     *
     */
    public void getDelUser() {
        Integer userId = getParaToInt("delUserId", 0);
        if (UserModel.dao.deleteById(userId)) {
            renderJson("1");
        } else {
            renderJson("0");
        }
    }

}
