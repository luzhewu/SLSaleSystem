package org.slsale.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.RedisAPI;
import org.slsale.pojo.Authority;
import org.slsale.pojo.Function;
import org.slsale.pojo.Menu;
import org.slsale.pojo.Role;
import org.slsale.pojo.RoleFunctions;
import org.slsale.pojo.User;
import org.slsale.service.authority.AuthorityService;
import org.slsale.service.function.FunctionService;
import org.slsale.service.role.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 权限管理controller
 * @author lzw
 * 2017-7-11
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
@Controller
public class AuthorityController extends BaseController {
	private Logger logger = Logger.getLogger(AuthorityController.class);
	@Resource
	private RoleService roleService;
	@Resource
	private FunctionService functionService;
	@Resource
	private AuthorityService authorityService;
	@Resource
	private RedisAPI redisAPI;
	@Resource
	private LoginController loginController;

	/**
	 * 进入到权限管理首页
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backend/authoritymanage.html")
	public ModelAndView authorityManage(HttpSession session, Model model) {
		Map<String, Object> baseModel = (Map<String, Object>) session
				.getAttribute(Constants.SESSION_BASE_MODEL);
		if (baseModel == null) {
			return new ModelAndView("redirct:/");
		} else {
			List<Role> roleList = null;
			try {
				roleList = roleService.getRoleIdAndNameList();
			} catch (Exception e) {
				e.printStackTrace();
				roleList = null;
			}
			model.addAllAttributes(baseModel);
			model.addAttribute(roleList);
		}
		return new ModelAndView("/backend/authoritymanage");
	}

	/**
	 * 获取菜单功能列表
	 */
	@RequestMapping(value = "backend/functions.html", produces = { "text/html;charset=utf-8" })
	@ResponseBody
	public Object functions() {
		String result = "nodata";
		Function function = new Function();
		try {
			function.setId(0);
			List<Function> fList = functionService.getSubFuncList(function);
			List<RoleFunctions> rList = new ArrayList<>();
			if (fList != null) {
				for (Function func : fList) {
					RoleFunctions rFunctions = new RoleFunctions();
					rFunctions.setMainFunction(func);
					rFunctions.setSubFunctions(functionService
							.getSubFuncList(func));
					rList.add(rFunctions);
				}
			}
			result = JSONArray.fromObject(rList).toString();
			logger.debug("rList=============>>>>" + result);
		} catch (Exception e) {
			e.printStackTrace();
			result = "nodata";
		}
		return result;
	}

	/**
	 * 获取原有权限下的功能菜单
	 */
	@RequestMapping(value = "/backend/getAuthorityDefault.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object getAuthorityDefault(@RequestParam Integer rid,
			@RequestParam Integer fid) {
		String result = "nodata";
		try {
			Authority authority = new Authority();
			authority.setRoleId(rid);
			authority.setFunctionId(fid);
			if (authorityService.getAuthority(authority) != null) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "backend/modifyAuthority.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object modifyAuthority(HttpSession session, @RequestParam String ids) {
		String result = "nodata";
		try {
			String[] idsArrays = StringUtils.split(ids, "-");
			if (idsArrays.length > 0) {
				User user = this.getCurrentUser();
				authorityService
						.lu_addAuthority(idsArrays, user.getLoginCode());
				List<Menu> mList = null;
				mList = loginController.getFunctionByCurrentUser(Integer
						.parseInt(idsArrays[0]));
				String jsonString = JSONArray.fromObject(mList).toString();
				redisAPI.set("menuList" + idsArrays[0], jsonString);

				// 获取所有的role url放到redis里
				Authority authority = new Authority();
				authority.setRoleId(Integer.parseInt(idsArrays[0]));
				List<Function> functions = functionService
						.getFunctionsByRoleId(authority);

				if (null != functions || functions.size() >= 0) {
					StringBuffer sBuffer = new StringBuffer();
					for (Function function : functions) {
						sBuffer.append(function.getFuncUrl());
					}
					redisAPI.set("Role" + idsArrays[0] + "UrlList",
							sBuffer.toString());
				}
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
