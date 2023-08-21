package blog.com.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.ex.service.UserService;

@RequestMapping("/user")
@Controller
public class UserRegisterControlle {
	@Autowired
	private UserService userService;
	
	@GetMapping("/register")
	public String getUserRegisterPage() {
		return "register.html";
	}
	@PostMapping("/register/process")
	public String register(@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password) {
				if(userService.createAccount(name, email, password)) {
					return "redirect:/user/login";
			}else {
				return "register.html";
			}

	}
}
