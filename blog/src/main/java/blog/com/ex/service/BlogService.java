package blog.com.ex.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.ex.model.dao.BlogDao;
import blog.com.ex.model.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	public List<BlogEntity> findAllBlogPost(Long userId) {
		if (userId == null) {
			return null;
		} else {
			return blogDao.findByUserId(userId);
		}
	}

	public boolean createBlogPost(String blogTitle, LocalDate registerDate, String fileName, String blogDetail,
			String category, Long userId) {
		BlogEntity blogList = blogDao.findByBlogTitleAndRegisterDate(blogTitle, registerDate);
		if (blogList == null) {
			blogDao.save(new BlogEntity(blogTitle, registerDate, fileName, blogDetail, category, userId));
			return true;
		} else {
			return false;
		}
	}

	public BlogEntity getBlogPost(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	public boolean editBlogPost(String blogTitle, LocalDate registerDate, String fileName, String blogDetail,
			String category, Long userId, Long blogId) {
		BlogEntity blogList = blogDao.findByBlogId(blogId);
		if (userId == null) {
			return false;
		} else {
			blogList.setBlogId(blogId);
			blogList.setBlogTitle(blogTitle);
			blogList.setBlogImage(fileName);
			blogList.setRegisterDate(registerDate);
			blogList.setCategory(category);
			blogList.setBlogDetail(blogDetail);
			blogList.setUserId(userId);
			blogDao.save(blogList);
			return true;
		}
	}

	public boolean editBlogImage(Long blogId, String fileName, Long userId) {

		BlogEntity blogList = blogDao.findByBlogId(blogId);

		if (fileName == null || blogList.getBlogImage().equals(fileName)) {
			return false;
		} else {

			blogList.setBlogId(blogId);
			blogList.setBlogImage(fileName);
			blogList.setUserId(userId);
			blogDao.save(blogList);
			return true;
		}
	}

	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}
}
