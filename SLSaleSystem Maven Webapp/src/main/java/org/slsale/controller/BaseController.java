package org.slsale.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.slsale.common.Constants;
import org.slsale.pojo.User;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {
	private Logger logger = Logger.getLogger(BaseController.class);

	private User currentUser;

	public User getCurrentUser() {
		if (null == this.currentUser) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			HttpSession session = request.getSession(false);
			if (session != null) {
				currentUser = (User) session
						.getAttribute(Constants.SESSION_USER);
			} else {
				currentUser = null;
			}
		}
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	// 时间国际化
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Date.class,
				new PropertyEditorSupport() {

					@Override
					public String getAsText() {
						return new SimpleDateFormat("yyyy-MM-dd")
								.format((Date) getValue());
					}

					@Override
					public void setAsText(String text)
							throws IllegalArgumentException {
						try {
							setValue(new SimpleDateFormat("yyyy-MM-dd")
									.parse(text));
						} catch (ParseException e) {
							e.printStackTrace();
							setValue(null);
						}
					}

				});
	}

}