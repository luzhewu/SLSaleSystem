package org.slsale.controller.user;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.slsale.controller.BaseController;
import org.slsale.pojo.User;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@RequestMapping("/backend/modifyPwd.html")
	@ResponseBody
	public Object modifyPwd(@RequestParam String userJson) {
		User sessionUser = this.getCurrentUser();
		if (userJson == null || userJson.equals("")) {
			return "nodata";
		} else {
			User user = JSONObject.parseObject(userJson, User.class);
			user.setId(sessionUser.getId());
			user.setLoginCode(sessionUser.getLoginCode());
			try {
				if (userService.getLoginUser(user) != null) {
					user.setPassword(user.getPassword2());
					user.setPassword2(null);
					userService.modifyUser(user);
				} else {
					return "oldpwdwrong";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}

		}
		return "success";
	}
}
