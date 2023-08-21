package blog.com.ex.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.ex.model.entity.BlogEntity;
import blog.com.ex.model.entity.UserEntity;
import blog.com.ex.service.BlogService;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user/blog")
@Controller
public class BlogController {
@Autowired
private BlogService blogService;

@Autowired
private HttpSession session;

@GetMapping("list")
public String getBlogListPage(Model model) {
	UserEntity userList = (UserEntity) session.getAttribute("user");
	Long userId = userList.getUserId();
	
	String userName = userList.getUserName();
	
	List<BlogEntity>blogList = blogService.findAllBlogPost(userId);
	
	model.addAttribute("userName",userName);
	model.addAttribute("blogList", blogList);
	return "blog.html";
	
}

@GetMapping("/register")
public String getBlogRegisterPage(Model moel) {
	UserEntity userList = (UserEntity) session.getAttribute("user");
	String userName = userList.getUserName();
	model.addAttribute("userName",userName);
	model.addAttribute("registerMessage","新規記事追加");
	return "blog_register.html";
}

@PostMapping("/register/process")

public String blogRegister(@RequestParam String blogTitle,
		@RequestParam LocalDate registerDate,
		@RequestParam String category,
		@RequestParam MultipartFile blogImage,
		@RequestParam String blogDetail,Model model) {
		
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId =userList.getUserId();
		
		String FileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + blogImage.getOriginalFilename();
		try {
			Files.copy(blogImage.getInputStream(),Path.of("/src/main/resources/static/image/" + FileName));
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
		if(blogService.createBlogPost(blogTitle,registerDate,FileName,blogDate,category,userId)) {
			return "blog_register.html";
		}else {
			model.addAttribute("registerMessage","既に登録済みです");
			return "blog_register.html";
		}
}

}
