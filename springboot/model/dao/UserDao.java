package blog.com.ex.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.ex.model.entity.UserEntity;


public interface UserDao extends JpaRepository<UserEntity, Long>{
	UserEntity save(UserEntity userEntity);
	UserEntity findByEmail(String userEmail);
	UserEntity findByEmailAndPassword(String email,String password);
}
