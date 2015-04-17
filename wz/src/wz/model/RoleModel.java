package wz.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/4/17.
 */
public class RoleModel extends Model<RoleModel> {

    public static final RoleModel dao = new RoleModel();

    /**
     *
     * @return
     */
    public List<RoleModel> getRoleList() {
        return this.find("select * from wz_role");
    }

    /**
     *
     * @return
     */
    public String getRoleXml() {
        String outXml = "<Root>";
        List<RoleModel> list = getRoleList();
        for(RoleModel rm : list) {
            outXml+="<item roleId='"+rm.get("role_id")+"' roleName='"+rm.get("role_name")+"' />";
        }
        return outXml+"</Root>";
    }
}
