package org.slsale.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.User;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController {
	private Logger logger = Logger.getLogger(LoginController.class);
	@Resource
	private UserService userService;

	@RequestMapping("/main.html")
	public ModelAndView main(HttpSession session) {
		User currentUser = (User) session.getAttribute(Constants.SESSION_USER);
		logger.debug("main()++++++++++++++++++++++++>>>>>userCode:"
				+ currentUser.getUserCode());
		return new ModelAndView("main");
	}

	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session, @RequestParam String user) {
		if (user == null || "".equals(user)) {
			return "nodata";
		} else {
			User userObject = JSONObject.parseObject(user, User.class);
			try {
				if (userService.userCodeIsExist(userObject) == 0) {
					return "nousercode";
				} else {// 登录成功
					User _user = userService.getLoginUser(userObject);
					if (_user != null) {
						// 存到session中
						session.setAttribute(Constants.SESSION_USER, _user);
						// 更新当前用户lastLoginTime
						User updateLastLoginTime = new User();
						updateLastLoginTime.setId(_user.getId());
						updateLastLoginTime.setLastLoginTime(new Date());
						userService.modifyUser(updateLastLoginTime);
						updateLastLoginTime = null;
						return "success";
					} else {// 密码错误
						return "nouserpassword";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
}
