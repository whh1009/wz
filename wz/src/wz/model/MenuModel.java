package wz.model;

import com.jfinal.plugin.activerecord.Model;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import wz.util.StringUtil;

import java.util.List;

public class MenuModel extends Model<MenuModel> {
    public static final MenuModel dao = new MenuModel();

    public List<MenuModel> getMenuList() {
        return find("select * from wz_menu");
    }

    public String getMenuNavByRoleId(UserModel user) throws Exception {
        int userId = Integer.parseInt(StringUtil.ObjectToString(user.get("user_id")));
        int roleId = UserRoleModel.dao.getRoleIdByUserId(userId);
        List<MenuModel> list = find("select * from wz_menu where menu_id in (select menu_id from wz_role_menu where role_id = ?)", roleId);
        String menuXml = "<menu>";
        for (MenuModel menuEntity : list) {
            menuXml = menuXml + "<item menuId='" + menuEntity.get("menu_id") + "' menuName='" + menuEntity.get("menu_name") + "' menuAction = '"+StringUtil.ObjectToString(menuEntity.get("menu_action")).replace(".action", "")+"' menuFlag = '"+menuEntity.get("menu_flag")+"' menuLeval='" + menuEntity.get("menu_leval") + "' menuPid='" + menuEntity.get("menu_pid") + "' />";
        }
        menuXml = menuXml + "</menu>";
        String out = getMenuNavByMenuXml(menuXml, user);
        return out;
    }

    /**
     * 根据menu xml生成menu导航
     *
     * @param xmlStr
     * @return
     */
    private String getMenuNavByMenuXml(String xmlStr, UserModel user) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("<nav class='navbar navbar-default' role='navigation'><div class='navbar-header' style='margin-right:90px'><button type='button' class='navbar-toggle' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'><span class='sr-only'>菜单导航</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><span class='navbar-brand' href='javascript:;'><img src='../images/logo/logo.png' style='float:left;'/></span></div><div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1' style='margin-top:5px'><ul class='nav navbar-nav'>");
        Document doc = DocumentHelper.parseText(xmlStr);
        parserItem(doc, "0", sb);
        sb.append("</ul>");
        sb.append("<ul class='nav navbar-nav navbar-right'> <li> <a href='javascript:;' class='dropdown-toggle' data-toggle='dropdown'><strong> " + user.get("nick_name") + " </strong> <span class='caret'></span>&nbsp;&nbsp;&nbsp;&nbsp;</a> <ul class='dropdown-menu'><li><a href='javascript:modifyPwd();'> 密码修改 </a></li><li><a href='../user/logout'>退出</a></li></ul>  </li> </ul>");
        sb.append("</div></nav>");
        return sb.toString();
    }

    /**
     * 解析item，生成菜单
     *
     * @param doc
     * @param mId
     * @param sb
     */
    private void parserItem(Document doc, String mId, StringBuffer sb) throws Exception {
        List<Element> itemList = doc.selectNodes("//menu/item[@menuPid='" + mId + "']");
        for (Element itemEle : itemList) {
            String menuId = itemEle.attributeValue("menuId");
            String menuAction = itemEle.attributeValue("menuAction");
            String menuName = itemEle.attributeValue("menuName");
            String menuFlag = itemEle.attributeValue("menuFlag");
            List<Element> subItemList = doc.selectNodes("//menu/item[@menuPid='" + menuId + "']");
            if (subItemList == null || subItemList.size() == 0) {
                sb.append("<li><a href='").append(menuAction).append("'>").append("<span class='" + menuFlag + "' />&nbsp;&nbsp;&nbsp;").append(menuName).append(" </a></li>");
            } else {
                sb.append("<li class='dropdown'><a href='javascript:;' class='dropdown-toggle' data-toggle='dropdown'>")
                        .append(menuName).append(" <b class='caret'></b> </a><ul class='dropdown-menu'>");
                parserItem(doc, menuId, sb);
                sb.append("</ul></li>");
            }
        }
    }
}
