package org.slsale.pojo;

import java.util.List;

/**
 * 权限功能类
 * @author lzw
 * 2017-7-11
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
public class RoleFunctions {
	private Function mainFunction;
	private List<Function> subFunctions;

	public Function getMainFunction() {
		return mainFunction;
	}

	public void setMainFunction(Function mainFunction) {
		this.mainFunction = mainFunction;
	}

	public List<Function> getSubFunctions() {
		return subFunctions;
	}

	public void setSubFunctions(List<Function> subFunctions) {
		this.subFunctions = subFunctions;
	}

}
