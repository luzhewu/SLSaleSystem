package org.slsale.controller.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.controller.BaseController;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

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

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping("/backend/userlist.html")
	public ModelAndView userList(
			Model model,
			HttpSession session,
			@RequestParam(value = "currentPage", required = false) String currentPage,
			@RequestParam(value = "s_loginCode", required = false) String s_loginCode,
			@RequestParam(value = "s_referCode", required = false) String s_referCode,
			@RequestParam(value = "s_roleId", required = false) String s_roleId,
			@RequestParam(value = "s_isStart", required = false) String s_isStart) {
		Map<String, Object> baseModel = (Map<String, Object>) session
				.getAttribute(Constants.SESSION_BASE_MODEL);
		if (baseModel == null) {
			return new ModelAndView("redirect:/");
		} else {
			// 获取roleList
			List<Role> roleList = null;
			try {
				roleList = roleService.getRoleList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 设置查询条件
			User user = new User();
			if (null != s_loginCode) {
				user.setLoginCode("%" + SQLTools.transfer(s_loginCode) + "%");
			}
			if (null != s_referCode) {
				user.setReferCode("%" + SQLTools.transfer(s_referCode) + "%");
			}
			if (!StringUtils.isNullOrEmpty(s_isStart)) {
				user.setIsStart(Integer.parseInt(s_isStart));
			} else {
				user.setIsStart(null);
			}
			if (!StringUtils.isNullOrEmpty(s_roleId)) {
				user.setRoleId(Integer.parseInt(s_roleId));
			} else {
				user.setRoleId(null);
			}
			// page分页查询
			PageSupport page = new PageSupport();
			try {
				page.setTotalCount(userService.count(user));
			} catch (Exception e) {
				e.printStackTrace();
				page.setTotalCount(0);
			}
			if (page.getTotalCount() > 0) {
				if (currentPage != null) {
					page.setPage(Integer.parseInt(currentPage));
				}
				if (page.getPage() <= 0) {
					page.setPage(1);
				}
				if (page.getPage() > page.getPageCount()) {
					page.setPage(page.getPageCount());
				}
				// mysql分页查询
				user.setStarNum((page.getPage() - 1) * page.getPageSize());
				user.setPageSize(page.getPageSize());
				List<User> userList = null;
				try {
					userList = userService.getUserList(user);
					page.setItems(userList);
				} catch (Exception e) {
					e.printStackTrace();
					userList = null;
					if (page == null) {
						page = new PageSupport();
						page.setItems(null);
					}
				}
			} else {
				page.setItems(null);
			}
			// 放入到model中
			model.addAllAttributes(baseModel);
			model.addAttribute("page", page);
			model.addAttribute("s_loginCode", s_loginCode);
			model.addAttribute("s_referCode", s_referCode);
			model.addAttribute("s_isStart", s_isStart);
			model.addAttribute("s_roleId", s_roleId);
			model.addAttribute("roleList", roleList);
			return new ModelAndView("/backend/userlist");
		}
	}
}
