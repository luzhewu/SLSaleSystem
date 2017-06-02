package org.slsale.controller.user;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.slsale.pojo.user.User;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;

	@RequestMapping("/login.html")
	public String login() {
		logger.debug("login-------->");
		return "login";
	}

	@RequestMapping("/index.html")
	public String index() {
		logger.debug("index-------->");
		return "index";
	}

	@RequestMapping("/doLogin.html")
	public ModelAndView doLogin(User user) {
		logger.debug("doLogin--------------->" + user.getUserCode());
		try {
			user = userService.login(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("loginsuccess");
	}

	@RequestMapping("/exit.html")
	public String exit() {
		logger.debug("exit-------->");
		return "exit";
	}

	@RequestMapping("/register.html")
	public String register() {
		logger.debug("register-------->");
		return "register";
	}

	@RequestMapping("/doRegister.html")
	public ModelAndView doRegister(User user) {
		logger.debug("doRegister-------->");
		try {
			int num = userService.register(user);
			if (num > 0) {
				user = userService.login(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("regsuccess");
	}
}
