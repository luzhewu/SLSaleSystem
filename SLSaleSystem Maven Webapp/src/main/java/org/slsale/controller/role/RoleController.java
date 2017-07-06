package org.slsale.controller.role;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
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

/**
 * 角色Controller
 * @author lzw
 * 2017-7-6
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
@Controller
public class RoleController extends BaseController {
	private Logger logger = Logger.getLogger(RoleController.class);
	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;

	@RequestMapping("/backend/delRole.html")
	@ResponseBody
	public Object delRole(HttpSession session, @RequestParam String role) {
		if (null == role || "".equals(role)) {
			return "nodata";
		} else {
			Role role2 = JSONObject.parseObject(role, Role.class);
			try {
				User u = new User();
				List<User> uList = null;
				u.setRoleId(role2.getId());
				uList = userService.getUserListBySearch(u);
				if (uList == null || uList.size() == 0) {
					roleService.deleteRole(role2);
				} else {
					String flag = "";
					for (int i = 0; i < uList.size(); i++) {
						flag += uList.get(i).getLoginCode();
						flag += ",";
					}
					return flag;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping("/backend/modifyRole.html")
	@ResponseBody
	public Object modifyRole(HttpSession session, @RequestParam String role) {
		if (null == role || "".equals(role)) {
			return "nodata";
		} else {
			Role role2 = JSONObject.parseObject(role, Role.class);
			role2.setCreateDate(new Date());
			role2.setCreatedBy(((User) session
					.getAttribute(Constants.SESSION_USER)).getLoginCode());
			try {
				roleService.lu_modifyRole(role2);
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping("/backend/addRole.html")
	@ResponseBody
	public Object addRole(HttpSession session, @RequestParam String role) {
		if (null == role || "".equals(role)) {
			return "nodata";
		} else {
			Role role2 = JSONObject.parseObject(role, Role.class);
			role2.setCreateDate(new Date());
			role2.setIsStart(1);
			role2.setCreatedBy(((User) session
					.getAttribute(Constants.SESSION_USER)).getLoginCode());
			try {
				if (roleService.getRoleR(role2) != null) {
					return "rename";
				} else {
					roleService.addRole(role2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		return "success";
	}

	@RequestMapping("/backend/rolelist.html")
	public ModelAndView roleList(HttpSession session, Model model) {
		Map<String, Object> baseModel = (Map<String, Object>) session
				.getAttribute(Constants.SESSION_BASE_MODEL);
		if (baseModel == null) {
			return new ModelAndView("redirect:/");
		} else {
			List<Role> roleList = null;
			try {
				roleList = roleService.getRoleList();
			} catch (Exception e) {
				e.printStackTrace();
				roleList = null;
			}
			model.addAllAttributes(baseModel);
			model.addAttribute("roleList", roleList);
			return new ModelAndView("/backend/rolelist");
		}
	}
}
