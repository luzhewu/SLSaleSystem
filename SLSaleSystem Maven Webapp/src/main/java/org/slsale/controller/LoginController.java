package org.slsale.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.User;
import org.slsale.service.function.FunctionService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class LoginController extends BaseController {
	private Logger logger = Logger.getLogger(LoginController.class);
	@Resource
	private UserService userService;
	@Resource
	private FunctionService functionService;

	@RequestMapping("/main.html")
	public ModelAndView main(HttpSession session) {
		User user = this.getCurrentUser();
		List<Menu> mList = null;
		if (user != null) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			// 根据当前用户获取菜单列表mList
			mList = getFunctionByCurrentUser(user.getRoleId());
			logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			for (Menu menu : mList) {
				logger.debug("menu=======functionName======>"
						+ menu.getMainMenu().getFunctionName());
				List<Function> subMenu = menu.getSubMenu();
				for (Function function : subMenu) {
					logger.debug("function=======functionName======>"
							+ function.getFunctionName());
				}
				logger.debug("---------------------------------------------------");
			}
			logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// json
			if (mList != null) {
				/*JSONArray fromObject = JSONArray.fromObject(mList);
				String jsonString = fromObject.toString();*/

				String jsonString = JSONArray.toJSONString(mList);
				logger.debug("jsonString------------->" + jsonString);
				model.put("mList", jsonString);
				session.setAttribute(Constants.SESSION_BASE_MODEL, model);
				return new ModelAndView("main", model);
			}
		}
		return new ModelAndView("redirect:/");
	}

	protected List<Menu> getFunctionByCurrentUser(Integer roleId) {
		List<Menu> menuList = new ArrayList<>();
		Authority authority = new Authority();
		authority.setRoleId(roleId);
		try {
			List<Function> mList = functionService
					.getMainFunctionList(authority);
			if (mList != null) {
				for (Function function : mList) {
					Menu menu = new Menu();
					menu.setMainMenu(function);
					function.setRoleId(roleId);
					List<Function> subList = functionService
							.getSubFunctionList(function);
					if (subList != null) {
						menu.setSubMenu(subList);
					}
					menuList.add(menu);
					logger.debug("menuList===============>>>>>>"
							+ menuList.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}

	@RequestMapping("/login.html")
	@ResponseBody
	public Object login(HttpSession session, @RequestParam String user) {
		if (user == null || "".equals(user)) {
			return "nodata";
		} else {
			User userObject = JSONObject.parseObject(user, User.class);
			try {
				if (userService.loginCodeIsExist(userObject) == 0) {
					return "nologincode";
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
						return "nopassword";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
	}
}
