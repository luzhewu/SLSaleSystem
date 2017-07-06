package org.slsale.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JsonConfig;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.common.JsonDateValueProcessor;
import org.slsale.common.PageSupport;
import org.slsale.common.SQLTools;
import org.slsale.controller.BaseController;
import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.Role;
import org.slsale.pojo.User;
import org.slsale.service.role.RoleService;
import org.slsale.service.user.UserService;
import org.slsale.service.user.datadictionary.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;

@Controller
public class UserController extends BaseController {
	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private DataDictionaryService dataDictionaryService;

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
			// 获取roleList and cardTypeList
			List<Role> roleList = null;
			DataDictionary dataDictionary = new DataDictionary();
			dataDictionary.setTypeCode("CARD_TYPE");
			List<DataDictionary> cardTypeList = null;
			try {
				roleList = roleService.getRoleIdAndNameList();
				cardTypeList = dataDictionaryService
						.getDataDictionaries(dataDictionary);
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
			model.addAttribute("cardTypeList", cardTypeList);
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

	@RequestMapping(value = "/backend/loadUserTypeList.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object loadUserTypeList(
			@RequestParam(value = "s_roleId", required = false) String s_roleId) {
		String cjson = "";
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeCode("USER_TYPE");
		try {
			List<DataDictionary> userTypeList = dataDictionaryService
					.getDataDictionaries(dataDictionary);
			cjson = JSONArray.toJSONString(userTypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cjson;
	}

	@RequestMapping(value = "/backend/logincodeisexist.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String loginCodeIsExist(
			@RequestParam(value = "loginCode", required = false) String loginCode,
			@RequestParam(value = "id", required = false) String id) {
		String result = "failed";
		User _user = new User();
		_user.setLoginCode(loginCode);
		if (!"-1".equals(id)) {
			_user.setId(Integer.parseInt(id));
		}
		try {

			if (userService.loginCodeIsExist(_user) == 0)
				result = "only";
			else
				result = "repeat";
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/backend/adduser.html", method = RequestMethod.POST)
	public ModelAndView addUser(HttpSession session,
			@ModelAttribute("addUser") User addUser) {
		if (session.getAttribute(Constants.SESSION_BASE_MODEL) == null) {
			return new ModelAndView("redirect:/");
		} else {
			String idCard = addUser.getIdCard();
			String ps = idCard.substring(idCard.length() - 6);
			addUser.setPassword(ps);
			addUser.setPassword2(ps);
			addUser.setCreateTime(new Date());
			addUser.setReferId(this.getCurrentUser().getId());
			addUser.setReferCode(this.getCurrentUser().getLoginCode());
			addUser.setLastUpdateTime(new Date());
			try {
				userService.addUser(addUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:/backend/userlist.html");
		}
	}

	@RequestMapping(value = "/backend/upload.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public Object upload(
			@RequestParam(value = "a_fileInputID", required = false) MultipartFile cardFile,
			@RequestParam(value = "a_fileInputBank", required = false) MultipartFile bankFile,
			@RequestParam(value = "m_fileInputID", required = false) MultipartFile mCardFile,
			@RequestParam(value = "m_fileInputBank", required = false) MultipartFile mBankFile,
			@RequestParam(value = "loginCode", required = false) String loginCode,
			HttpSession session, HttpServletRequest request) {
		logger.debug("upload================");
		// 根据服务器的操作系统，自动获取物理路径，自适应
		String path = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadFiles");
		logger.debug("path=================" + path);
		DataDictionary dataDictionary = new DataDictionary();
		dataDictionary.setTypeName("PERSONALFILE_SIZE");
		List<DataDictionary> list = null;
		try {
			list = dataDictionaryService.getDataDictionaries(dataDictionary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int filesSize = 50000;
		if (list != null) {
			if (list.size() == 1) {
				filesSize = Integer.valueOf(list.get(0).getValueName());
			}
		}
		/**
		 * ajax上传图片
		 */
		if (cardFile != null) {
			return addPic(cardFile, filesSize, path, request);
		}
		if (bankFile != null) {
			return addPic(bankFile, filesSize, path, request);
		}
		if (mCardFile != null) {
			return addPic(mCardFile, filesSize, path, request);
		}
		if (mBankFile != null) {
			return addPic(mBankFile, filesSize, path, request);
		}
		return null;
	}

	/**
	 * 上传图片
	 * @param file
	 * @param filesSize
	 * @param path
	 * @param request
	 * @return
	 */
	private String addPic(MultipartFile file, int filesSize, String path,
			HttpServletRequest request) {
		String oldFileName = file.getOriginalFilename();
		String prefix = FilenameUtils.getExtension(oldFileName);
		logger.debug("cardFile  prefix========" + prefix);
		if (file.getSize() > filesSize) {
			logger.debug("111111111111111111111");
			return "1";// 超出文件大小
		} else if (prefix.equalsIgnoreCase("jpg")
				|| prefix.equalsIgnoreCase("jpeg")
				|| prefix.equalsIgnoreCase("pneg")
				|| prefix.equalsIgnoreCase("png")) {
			// 给文件进行重命名:系统毫秒数+100W以内的随机数
			String fileName = System.currentTimeMillis()
					+ new Random().nextInt(1000000) + "_IDcard.jpg";
			logger.debug("newFileName============" + fileName);
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存、上传
			try {
				file.transferTo(targetFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			String url = request.getContextPath() + "/statics/uploadFiles/"
					+ fileName;
			return url;
		} else {
			return "2";
		}
	}

	/**
	 * 删除图片
	 */
	@RequestMapping(value = "/backend/delpic.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String delPic(
			@RequestParam(value = "picpath", required = false) String picpath,
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request, HttpSession session) {
		String result = "failed";
		logger.debug("进入delPic===================");
		if (picpath == null || "".equals(picpath)) {
			result = "success";
		} else {
			String[] paths = picpath.split("/");
			String path = request
					.getSession()
					.getServletContext()
					.getRealPath(
							paths[0] + File.separator + paths[1]
									+ File.separator + paths[2]
									+ File.separator + paths[3]);
			File file = new File(path);
			if (file.exists()) {
				if (file.delete()) {// 删除成功
					if (id.equals("0")) {// 用户添加时删除图片操作
						result = "success";
					} else {// 修改用户时，删除删除上传的图片
						User _user = new User();
						_user.setId(Integer.parseInt(id));
						if (picpath.indexOf("_IDcard.jpg") != -1) {
							_user.setIdCardPicPath(picpath);
						} else if (picpath.indexOf("_bank.jpg") != -1) {
							_user.setBankPicPath(picpath);
						}
						try {
							if (userService.delUserPic(_user) > 0) {
								logger.debug("进入修改时图片删除");
								result = "success";
							}
						} catch (Exception e) {
							e.printStackTrace();
							return result;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 查看用户信息
	 */
	@RequestMapping(value = "/backend/getuser.html", produces = { "text/html;charset=UTF-8" }, method = RequestMethod.POST)
	@ResponseBody
	public Object getUser(
			@RequestParam(value = "id", required = false) String id) {
		String cjson = "";
		if (id == null || "".equals(id)) {
			return "nodata";
		} else {
			try {
				User user = new User();
				user.setId(Integer.parseInt(id));
				user = userService.getUserById(user);
				logger.debug("birthday=================>>>>"
						+ user.getBirthday());
				// 对象内所以日期属性，都会按照此日期格式进行json转化
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessor());
				cjson = net.sf.json.JSONObject.fromObject(user, jsonConfig)
						.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "failed";
			}
		}
		logger.debug("cjson======>" + cjson);
		return cjson;
	}

	@RequestMapping(value = "/backend/modifyuser.html", method = RequestMethod.POST)
	public ModelAndView modifyUser(HttpSession session,
			@ModelAttribute("modifyUser") User modifyUser) {
		logger.debug("========================进入modify=================================");
		if (session.getAttribute(Constants.SESSION_USER) == null) {
			return new ModelAndView("redirect:/");
		} else {
			try {
				logger.debug("roleId=========================>"
						+ modifyUser.getRoleId());
				modifyUser.setLastUpdateTime(new Date());
				userService.modifyUser(modifyUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/backend/userlist.html");
	}

	@RequestMapping(value = "/backend/deluser.html", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String delUser(
			@RequestParam(value = "delId", required = false) String delId,
			@RequestParam(value = "delIdCardPicPath", required = false) String delIdCardPicPath,
			@RequestParam(value = "delBankPicPath", required = false) String delBankPicPath,
			@RequestParam(value = "delUserType", required = false) String delUserType,
			HttpServletRequest request, HttpSession session) {
		String data = "false";
		User delUser = new User();
		delUser.setId(Integer.parseInt(delId));
		try {
			logger.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			// 如果用户为：普通消费会员、VIP会员、加盟店 则不可被删除
			if (delUserType.equals("2") || delUserType.equals("3")
					|| delUserType.equals("4")) {
				data = "noallow";
			} else {
				if (this.delPic(delIdCardPicPath, delId, request, session)
						.equals("success")
						&& this.delPic(delBankPicPath, delId, request, session)
								.equals("success")) {
					logger.debug("----------------------------------------------");
					if (userService.deleteUser(delUser) > 0)
						data = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
