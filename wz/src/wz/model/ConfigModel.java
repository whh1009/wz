package wz.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * 获取用户的配置字段
 * show_column 显示字段
 * export_column 导出字段
 * search_column 检索字段
 * Created by wanghonghui on 2015/4/20.
 *
 */
public class ConfigModel extends Model<ConfigModel> {
    public static final ConfigModel dao = new ConfigModel();

    public List<ConfigModel> getDbConfig() {
        return find("select * from wz_config");
    }

    public ConfigModel getDbConfigByUserId(int userId) {
        List<ConfigModel> list = find("select * from wz_config where user_id = ?", userId);
        if(list==null||list.isEmpty()){
            return null;
        } else {
            return list.get(0);
        }
    }

    public String getDbConfigXml(){
        String xml= "<root>";
        List<ConfigModel> list = getDbConfig();
        for(ConfigModel cm : list) {
            xml+="<item userId='"+cm.get("user_id")+"' showColumn='"+cm.getStr("show_column")+"' exportColumn='"+cm.getStr("export_column")+"' searchColumn='"+cm.getStr("search_column")+"' />";
        }
        xml+="</root>";
        return xml;
    }

}
