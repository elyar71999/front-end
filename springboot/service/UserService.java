package blog.com.ex.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.ex.model.dao.UserDao;
import blog.com.ex.model.entity.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean createAccount(String userName,String email,String password) {
		//登録した日付つまり本日の日時を取得する
		LocalDateTime regitserDate = LocalDateTime.now();
		UserEntity userEntity = userDao.findByEmail(email);
		if(userEntity==null) {
			userDao.save(new UserEntity(userName,email,password,regitserDate));
			return true;
		}else {
			return false;
		}
	}
	public UserEntity loginAccount(String email,String password) {
		UserEntity userEntity = userDao.findByEmailAndPassword(email, password);
		if(userEntity == null) {
			return null;
		}else {
			return userEntity;
		}
	}
}
