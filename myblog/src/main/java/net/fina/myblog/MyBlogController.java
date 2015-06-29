package net.fina.myblog;

import net.fina.myblog.domain.User;
import net.fina.myblog.repository.BlogPostRepository;
import net.fina.myblog.repository.SessionRepository;
import net.fina.myblog.repository.UserRepository;
import net.fina.myblog.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyBlogController {

    @Autowired
    private DbService dbService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private BlogPostRepository blogPostRepository;

    @RequestMapping("/test")
    public String test(Model model) {
        model.addAttribute("name", "test");
        return "test";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(User user) {
        //TODO
        return "user";
    }
}
