package org.slsale.pojo;

import java.util.Date;

public class Function extends Base {

	private String functionCode;// 功能编码
	private String functionName;// 功能名称
	private String funcUrl;// 功能URL
	private Integer parentId;//
	private Date creationTime;// 创建时间
	private Integer roleId;

	public Function() {
	}

	public Function(Integer id, String functionCode, String functionName,
			String funcUrl, Integer parentId, Date creationTime, Integer roleId) {
		super(id);
		this.functionCode = functionCode;
		this.functionName = functionName;
		this.funcUrl = funcUrl;
		this.parentId = parentId;
		this.creationTime = creationTime;
		this.roleId = roleId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFuncUrl() {
		return funcUrl;
	}

	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

}
