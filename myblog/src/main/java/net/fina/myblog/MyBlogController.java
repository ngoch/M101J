package net.fina.myblog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.fina.myblog.domain.User;
import net.fina.myblog.repository.BlogPostRepository;
import net.fina.myblog.repository.UserRepository;

@Controller
public class MyBlogController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogPostRepository blogPostRepository;

	@RequestMapping("/test")
	public String test(Model model) {
		model.addAttribute("name", "test");
		return "test";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupSubmit(@ModelAttribute User user, Model model) {
		userRepository.addUser(user);
		return "home";
	}
}
