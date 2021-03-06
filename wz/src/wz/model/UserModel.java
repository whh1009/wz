package wz.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import wz.config.Constrant;

import java.util.List;

public class UserModel extends Model<UserModel> {
	public static final UserModel dao= new UserModel();

	/**
	 * 登录
	 */
	public UserModel login(String userName, String pwd) {
		String sql="select * from wz_user where user_name = '"+userName+"' and user_pwd = '"+ pwd+"'";
		List<UserModel> tagUser = find(sql);
		if(tagUser!=null&&!tagUser.isEmpty()){
			return tagUser.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 判断用户名是否重复
	 * @param userName
	 * @return
	 */
	public boolean checkUserName(String userName) {
		String sql = "select * from wz_user where user_name ='"+userName+"'";
		List<UserModel> tagUser = find(sql);
		if(tagUser!=null&&!tagUser.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	

	
	/**
	 * 获取用户列表
	 * @param userPage
	 * @return
	 * @author sunlei
	 */
	public Page<Record> getUserList(int userPage){
		return Db.paginate(userPage, Constrant.PAGE_SIZE, "select * ", " from wz_user");
	}
	
}
