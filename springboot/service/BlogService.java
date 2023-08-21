package blog.com.ex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.ex.model.dao.BlogDao;
import blog.com.ex.model.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

public List<BlogEntity> findAllBlogPost(Long userId){
	if(userId == null) {
		return null;
	}else {
		return blogDao.findByUserId(userId);
	

	}
}

	public boolean createBlogPost(String blogTitle, LocalDate,String fileName,String blogDetail,String category,Long userId) {
		if(BlogList == null) {
			blogDao.save(new BlogEntity(blogTitle,registerDate,fileName,blogDetail,category,userId));
			return true;
		}else {
			return false;
		}
	}

	
}
