package wz.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/4/18.
 */
public class UserRoleModel extends Model<UserRoleModel> {
    public static final UserRoleModel dao = new UserRoleModel();

    /**
     * 根据用户ID获取角色ID
     * @param userId
     * @return
     */
    public int getRoleIdByUserId(int userId) {
        List<UserRoleModel> list =  find("select role_id from wz_user_role where user_id = ?", userId);
        if(list==null||list.isEmpty()){
            return 0;
        } else {
            return list.get(0).get("role_id");
        }
    }
}
