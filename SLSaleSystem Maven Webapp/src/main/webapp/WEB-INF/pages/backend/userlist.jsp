<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="/WEB-INF/pages/common/header.jsp"/>

<div>
	<ul class="breadcrumb">
		<li>
			<a href="#">后台管理</a> <span class="divider">/</span>
		</li>
		<li>
			<a href="/backend/userlist.html">用户管理</a>
		</li>
	</ul>
</div>

<div class="row-fluid sortable">		
	<div class="box span12">
		<div class="box-header well" data-original-title>
			<h2><i class="icon-user"></i> 用户列表</h2>
			<div class="box-icon">
				<span class="icon32 icon-color icon-add adduser"></span>
			</div>
		</div>
		<div class="box-content">
			<form action="/backend/userlist.html" method="post">
				<div class="searcharea">
					用户名称：<input type="text" name="s_loginCode" value="${s_loginCode}"/>
					推荐人：<input type="text" name="s_referCode" value="${s_referCode}"/>
					角色：<select name="s_roleId" style="width:100px;">
						<option value="" selected>--请选择--</option>
						<c:forEach items="${roleList}" var="role">
							<option <c:if test="${role.id == s_roleId}">selected</c:if> 
								value="${role.id}">${role.roleName}</option>
						</c:forEach>
					</select>
					是否启用：<select name="s_isStart" style="width:100px;">
						<option value="" selected>--请选择--</option>
						<c:if test="${s_isStart == 1}">
							<option value="1" selected>启用</option>
							<option value="2" >未启用</option>
						</c:if>
						<c:if test="${s_isStart == 2}">
							<option value="1" >启用</option>
							<option value="2" selected>未启用</option>
						</c:if>
						<c:if test="${s_isStart == null || s_isStart == ''}">
							<option value="1" >启用</option>
							<option value="2">未启用</option>
						</c:if>
					</select>
					<button class="btn btn-inverse"><i class="icon-search icon-white"></i> 查询</button>
				</div>
			</form>
			<table class="table table-striped table-bordered bootstrap-datatable">
			  <thead>
				  <tr>
					  <th>用户名</th>
					  <th>角色</th>
					  <th>会员类型</th>
					  <th>推荐人</th>
					  <th>启用状态(启用/禁用)</th>
					  <th>注册时间</th>
					  <th>操作</th>
				  </tr>
			  </thead>   
			  <tbody>
			  	<c:if test="${page.items != null}">
			  		<c:forEach items="${page.items}" var="user">
			  		<tr>
			  			<td class="center">${user.loginCode}</td>
			  			<td class="center">${user.roleName}</td>
			  			<td class="center">${user.userTypeName}</td>
			  			<td class="center">${user.referCode}</td>
			  			<td class="center">
							<c:if test="${user.isStart ==2}">
								<input type="checkbox" disabled/>
							</c:if>
							<c:if test="${user.isStart ==1}">
								<input type="checkbox" checked disabled/>
							</c:if>
						</td>
			  			<td class="center">
			  				<fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd"/>
			  			</td>
			  			<td class="center">
			  				<a class="btn btn-success viewuser" href="#" id="${user.id}">
							<i class="icon-zoom-in icon-white"></i>  
							查看                                           
							</a>
							<a class="btn btn-info modifyuser" href="#" id="${user.id}">
								<i class="icon-edit icon-white"></i>  
								修改                                            
							</a>
							<a class="btn btn-danger deluser" href="#" usertype="${user.userType}" 
								usertypename="${user.userTypeName}" logincode="${user.loginCode}" 
								id="${user.id}" idcardpicpath="${user.idCardPicPath}" 
								bankpicpath="${user.bankPicPath}">
								<i class="icon-trash icon-white"></i> 
								删除
							</a>
			  			</td>
			  		</tr>
			  		</c:forEach>
			  	</c:if>
  			</tbody>
  		</table> 
  			<!-- page分页 start -->
 <div class="pagination pagination-centered">
  <ul>
 	<c:choose>
 		<c:when test="${page.page == 1}">
 			<li class="active"><a href="javascript:;" title="首页">首页</a></li>
 		</c:when>
 		<c:otherwise>
 			<li><a href="/backend/userlist.html?currentPage=1&s_loginCode=${s_loginCode}&s_roleId=${s_roleId}&s_referCode=${s_referCode}&s_isStart=${s_isStart}" title="首页">首页</a></li>
 		</c:otherwise>
 	</c:choose>
	<c:if test="${page.prevPages != null}">
		<c:forEach items="${page.prevPages}" var="num">
 			<li><a href="/backend/userlist.html?currentPage=${num}&s_loginCode=${s_loginCode}&s_roleId=${s_roleId}&s_referCode=${s_referCode}&s_isStart=${s_isStart}" title="${num}">${num}</a></li>
		</c:forEach>
	</c:if>
	<li class="active"><a href="javascript:;" title="${page.page}">${page.page}</a></li>
	<c:if test="${page.nextPages != null}">
		<c:forEach items="${page.nextPages}" var="num">
 			<li><a href="/backend/userlist.html?currentPage=${num}&s_loginCode=${s_loginCode}&s_roleId=${s_roleId}&s_referCode=${s_referCode}&s_isStart=${s_isStart}" title="${num}">${num}</a></li>
		</c:forEach>
	</c:if>
	<c:if test="${page.pageCount != null}">
		<c:choose>
 		<c:when test="${page.page == page.pageCount}">
 			<li class="active"><a href="javascript:;" title="尾页">尾页</a></li>
 		</c:when>
 		<c:otherwise>
 			<li><a href="/backend/userlist.html?currentPage=${page.pageCount}&s_loginCode=${s_loginCode}&s_roleId=${s_roleId}&s_referCode=${s_referCode}&s_isStart=${s_isStart}" title="尾页">尾页</a></li>
 		</c:otherwise>
 	</c:choose>
	</c:if>
	<c:if test="${page.pageCount == null}">
		<li class="active"><a href="javascript:;" title="尾页">尾页</a></li>
	</c:if>
  </ul>
</div>  
<!-- page分页 end -->
		</div>
	</div><!--/span-->
</div><!--/row-->

<!-- userView strat -->
<div class="modal hide fade" id="viewUserDiv">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>查看用户信息</h3>
		</div>
		<div class="modal-body">
			<input type="hidden" id="v_id" value=""/>
			<ul class="topul">
				<li>
					<label>角色:</label>
					<input type="text" id="v_roleName" name="roleName" value="" readonly/>
				</li>
				<li>
					<label>会员类型:</label>
					<input type="text" id="v_usertypename" name="userTypeName" value="" readonly/>
				</li>
				<li>
					<label>用户名:</label>
					<input type="text" id="v_logincode" name="loginCode" value="" readonly/>
				</li>
				<li>
					<label>姓名:</label>
					<input type="text" id="v_username" name="userName" value="" readonly/>
				</li>
				<li>
					<label>性别:</label>
					<input type="text"  id="v_sex" name="sex" value="" readonly/>
				</li>
				<li>
					<label>证件类型:</label>
					<input type="text"  id="v_cardtype" name="cardType" style="width:100px;" value="" readonly/>
				</li>
				<li>
					<label>证件号码:</label>
					<input type="text"  id="v_idcard" name="idCard"  value="" readonly/>
				</li>
				<li>
					<label>生日:</label>
					<input type="text"  id="v_birthday"  value=""  name="birthday" size="15" readonly/>
				</li>
				<li>
					<label>收货国家:</label>
					<input type="text"  id="v_country" name="country" value="" readonly/>
				</li>
				<li>
					<label>联系电话:</label>
					<input type="text"  id="v_mobile" name="mobile"  value="" readonly />
				</li>
				<li>
					<label>Email:</label>
					<input type="text"  id="v_email" name="email"  value="" readonly/>
				</li>
				<li>
					<label>邮政编码:</label>
					<input type="text"  id="v_postcode" name="postCode"  value="" readonly />
				</li>
				<li>
					<label>开户行:</label>
					<input type="text"  id="v_bankname" name="bankName"  value="" readonly/>
				</li>
				<li>
					<label>开户卡号:</label>
					<input type="text"  id="v_bankaccount" name="bankAccount"  value="" readonly/>
				</li>
				<li>
					<label>开户人:</label>
					<input type="text"  id="v_accountholder" name="accountHolder"  value="" readonly/>
				</li>
				<li>
					<label>推荐人:</label>
					<input type="text" id="v_referCode" name="referCode" value="" readonly/>
				</li>
				<li>
					<label>注册时间:</label>
					<input type="text" name="createTime" id="v_createTime" value="" readonly/>
				</li>
				<li>
					<label>是否启用:</label>
					<select id="v_isStart" name="isStart" style="width: 100px" disabled></select>
				</li>
				<li class="lastli">
					<label>收货地址:</label>
					<textarea id="v_useraddress" name="userAddress" readonly></textarea>
				</li>
			</ul>
			<div class="clear"></div>
			<ul class="downul">
					<li>
					<label>上传身份证图片:</label>
						<input type="hidden" id="v_fileInputIDPath" value=""/>
						<div id="v_idPic"></div>
					 </li>
				</ul>
				<ul class="downul">
					<li>
					<label>上传银行卡图片：</label>
						<input type="hidden" id="v_fileInputBankPath" value=""/>
						<div id="v_bankPic"></div>
					 </li>
				</ul>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn viewusercancel" data-dismiss="modal">返回</a>
		</div>
</div>
<!-- userView end -->

<!-- adduser start -->
<div class="modal hide fade" id="adduserdiv">
	<form onsubmit="return adduserfunction();" action="/backend/adduser.html" method="post" enctype="multipart/form-data">
		<div class="modal-header">
			<button type="button" class="close addusercancel" data-dismiss="modal">×</button>
			<h3>添加用户信息</h3>
		</div>
		<div class="modal-body">
			<ul id="add_formtip"></ul>
			<ul class="topul">
				<li>
					<label>角色:</label>
					<input type="hidden" id="selectrolename" name="roleName" value=""/>
					<select id="selectrole" name="roleId" style="width:100px;">
						<option value="" selected>--请选择--</option>
						<c:if test="${roleList != null}">
							<c:forEach items="${roleList}" var="role">
								<option value="${role.id}" >${role.roleName}</option>
							</c:forEach>
						</c:if>
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>会员类型:</label>
					<input type="hidden" id="selectusertypename" name="userTypeName" value=""/>
					<select id="selectusertype" name="userType" style="width:100px;">
						<option value="" selected>--请选择--</option>
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>用户名:</label>
					<input type="text" id="a_logincode" name="loginCode" value="" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>姓名:</label>
					<input type="text" id="a_username" name="userName" value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>性别:</label>
					<select id="selectsex" name="sex" style="width:100px;">
						<option value="" selected>--请选择--</option>
						<option value="男" >男</option>
						<option value="女" >女</option>
					</select>
				</li>
				<li>
					<label>证件类型:</label>
					<input type="hidden" id="selectcardtypename" name="cardTypeName" value=""/>
					<select id="selectcardtype" name="cardType" style="width:100px;">
						<option value="" selected>--请选择--</option>
						<c:if test="${cardTypeList != null}">
							<c:forEach items="${cardTypeList}" var="cardType">
								<option value="${cardType.valueId}" >${cardType.valueName}</option>
							</c:forEach>
						</c:if>
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>证件号码:</label>
					<input type="text"  id="a_idcard" name="idCard"  value=""  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>生日:</label>
					<input type="text" class="Wdate" id="a_birthday"  value=""  name="birthday" size="15" readonly onclick="WdatePicker();"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>收货国家:</label>
					<input type="text"  id="a_country" name="country" value="${user.country}" readonly/>
				</li>
				<li>
					<label>联系电话:</label>
					<input type="text"  id="a_mobile" name="mobile"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>Email:</label>
					<input type="text"  id="a_email" name="email"  value="" />
				</li>
				<li>
					<label>邮政编码:</label>
					<input type="text"  id="a_postcode" name="postCode"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</li>
				<li>
					<label>开户行:</label>
					<input type="text"  id="a_bankname" name="bankName"  value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>开户卡号:</label>
					<input type="text"  id="a_bankaccount" name="bankAccount"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>开户人:</label>
					<input type="text"  id="a_accountholder" name="accountHolder"  value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>推荐人:</label>
					<input type="text"  name="referCode" value="${user.loginCode}" readonly/>
				</li>
				<li>
					<label>注册时间:</label>
					<input type="text" name="createTime" id="a_createTime" value="" readonly/>
				</li>
				<li>
					<label>是否启用:</label>
					<select name="isStart" style="width: 100px">
						<option value="1" selected>启用</option>
						<option value="2">不启用</option>
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li class="lastli">
					<label>收货地址:</label>
					<textarea id="a_useraddress" name="userAddress"></textarea>
				</li>
			</ul>
			<div class="clear"></div>
			<ul class="downul">
				<li>
					<label>上传身份证图片:</label>
					<input type="hidden" id="a_fileInputIDPath" name="idCardPicPath"  value="" />
					<input id="a_fileInputID" name="a_fileInputID" type="file"/>
					<input type="button" id="a_uploadbtnID" value="上传"/>
					<p><span style="color:red;font-weight: bold;">*注：1、正反面 .2、大小不得超过50k.3、图片格式jpg、jpeg、pneg</span></p>
					<div id="a_idPic"></div>
				</li>
			</ul>
			<ul class="downul">
				<li>
					<label>上传银行卡图片:</label>
					<input type="hidden" id="a_fileInputBankPath" name="bankPicPath"  value="" />
					<input id="a_fileInputBank" name="a_fileInputBank" type="file"/>
					<input type="button" id="a_uploadbtnBank" value="上传"/>
					<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式jpg、jpeg、pneg</span></p>
					<div id="a_bankPic"></div>
				</li>
			</ul>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn addusercancel" data-dismiss="modal">取消</a>
			<input type="submit" class="btn btn-primary" value="保存"/>
		</div>
	</form>
</div>
<!-- adduser end -->

<!-- modifyuser start -->
<div class="modal hide fade" id="modifyUserDiv">
	<form onsubmit="return modifyuserFunction();" action="/backend/modifyuser.html" method="post" enctype="multipart/form-data">
		<div class="modal-header">
			<button type="button" class="close modifyusercancel" data-dismiss="modal">×</button>
			<h3>修改用户信息</h3>
		</div>
		<div class="modal-body">
			<ul id="modify_formtip"></ul>
			<input type="hidden" id="m_id" name="id"/>
			<ul class="topul">
				<li>
					<label>角色:</label>
					<input type="hidden" id="m_rolename" name="roleName" value=""/>
					<select id="m_roleid" name="roleId" style="width:100px;">
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>会员类型:</label>
					<input type="hidden" id="m_usertypename" name="userTypeName" value=""/>
					<select id="m_usertype" name="userType" style="width:100px;">
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>用户名:</label>
					<input type="text" id="m_logincode" name="loginCode" value="" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>姓名:</label>
					<input type="text" id="m_username" name="userName" value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>性别:</label>
					<select id="m_sex" name="sex" style="width:100px;">
					</select>
				</li>
				<li>
					<label>证件类型:</label>
					<input type="hidden" id="m_cardtypename" name="cardTypeName" value=""/>
					<select id="m_cardtype" name="cardType" style="width:100px;">
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>证件号码:</label>
					<input type="text"  id="m_idcard" name="idCard"  value=""  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>生日:</label>
					<input type="text" class="Wdate" id="m_birthday"  value=""  name="birthday" size="15" readonly onclick="WdatePicker();"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>收货国家:</label>
					<input type="text"  id="m_country" name="country" value="${user.country}" readonly/>
				</li>
				<li>
					<label>联系电话:</label>
					<input type="text"  id="m_mobile" name="mobile"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>Email:</label>
					<input type="text"  id="m_email" name="email"  value="" />
				</li>
				<li>
					<label>邮政编码:</label>
					<input type="text"  id="m_postcode" name="postCode"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</li>
				<li>
					<label>开户行:</label>
					<input type="text"  id="m_bankname" name="bankName"  value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>开户卡号:</label>
					<input type="text"  id="m_bankaccount" name="bankAccount"  value=""  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>开户人:</label>
					<input type="text"  id="m_accountholder" name="accountHolder"  value="" />
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li>
					<label>推荐人:</label>
					<input type="text" id="m_refercode" name="referCode" value="${user.loginCode}" readonly/>
				</li>
				<li>
					<label>注册时间:</label>
					<input type="text" name="createTime" id="m_createTime" value="" readonly/>
				</li>
				<li>
					<label>是否启用:</label>
					<select id="m_isstart" name="isStart" style="width: 100px">
					</select>
					<span style="color:red;font-weight: bold;">*</span>
				</li>
				<li class="lastli">
					<label>收货地址:</label>
					<textarea id="m_useraddress" name="userAddress"></textarea>
				</li>
			</ul>
			<div class="clear"></div>
			<ul class="downul">
				<li>
					<label>上传身份证图片:</label>
					<input type="hidden" id="m_fileInputIDPath" name="idCardPicPath"  value="" />
					<input id="m_fileInputID" name="m_fileInputID" type="file"/>
					<input type="button" id="m_uploadbtnID" value="上传" style="display: none;"/>
					<p><span style="color:red;font-weight: bold;">*注：1、正反面 .2、大小不得超过50k.3、图片格式jpg、jpeg、pneg</span></p>
					<div id="m_idPic"></div>
				</li>
			</ul>
			<ul class="downul">
				<li>
					<label>上传银行卡图片:</label>
					<input type="hidden" id="m_fileInputBankPath" name="bankPicPath"  value="" />
					<input id="m_fileInputBank" name="m_fileInputBank" type="file"/>
					<input type="button" id="m_uploadbtnBank" value="上传" style="display: none;"/>
					<p><span style="color:red;font-weight: bold;">*注：1、大小不得超过50k.2、图片格式jpg、jpeg、pneg</span></p>
					<div id="m_bankPic"></div>
				</li>
			</ul>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn modifyusercancel" data-dismiss="modal">取消</a>
			<input type="submit" class="btn btn-primary" value="保存"/>
		</div>
	</form>
</div>
<!-- modifyuser end -->

<jsp:include page="/WEB-INF/pages/common/foot.jsp"/>
<script type="text/javascript">
	var cardTypeListJson = [<c:forEach items="${cardTypeList}" var="cardType">
						{"valueId":"${cardType.valueId}","valueName":"${cardType.valueName}"},
					</c:forEach>{"valueId":"over","valueName":"over"}];
	var roleListJson = [<c:forEach items="${roleList}" var="role">
						{"id":"${role.id}","roleName":"${role.roleName}"},
					</c:forEach>{"id":"over","roleName":"over"}];
</script>

<script type="text/javascript" src="/statics/localjs/userlist.js"></script>