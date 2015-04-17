package wz.controller;

import com.jfinal.core.Controller;
import org.apache.log4j.Logger;
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
     */
    public void login() {
        String userName = getPara("userName");
        String pwd = getPara("userPassword");
        UserModel user = UserModel.dao.login(userName, pwd);
        if (user != null) {
            log.info(user.get("user_name") + "登录系统");
            getSession().setAttribute("user", user);
            renderJson("1");//登录成功
        } else {
            renderJson("0");//登录失败
        }
    }

    /**
     * 注册
     */
    public void register() {
        String userName = getPara("userName");
        String pwd = getPara("userPassword");
        UserModel user = new UserModel();
        if (UserModel.dao.checkUserName(userName)) {
            user.set("user_name", userName);
            user.set("user_password", pwd);
            user.set("user_roleid", 1);
            user.save();
            renderJson("1");//注册成功
        } else {
            renderJson("0");//注册失败
        }
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
