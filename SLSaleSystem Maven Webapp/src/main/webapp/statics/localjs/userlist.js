//给新增用户增加modal窗口
$(".adduser").click(function(e){
	$("#add_formtip").html('');
	//屏蔽默认事件
	e.preventDefault();
	//弹出modal窗口
	$("#adduserdiv").modal('show');
});

//检查email并匹配格式
function checkEmail(str){
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(str == null || str == '' || reg.test(str))
		return true;
	else
		return false;
}

//点击取消按钮后清空之前的输入
$('.addusercancel').click(function(e){
	$("#add_formtip").html('');
	$("#a_idPic").html('');
	$("#a_bankPic").html('');
	$("#selectrole").val('');
	$("#selectusertype").val('');
	$("#selectusertype").html("<option value='' selected>--请选择--</option>");
	$("#a_logincode").val('');
	$("#a_username").val('');
	$("#selectcardtype").val('');
	$("#a_idcard").val('');
	$("#a_mobile").val('');
	$("#a_email").val('');
	$("#a_postcode").val('');
	$("#a_bankname").val('');
	$("#a_bankaccount").val('');
	$("#a_accountholder").val('');
	$("#a_useraddress").val('');
});

//通过选择角色来异步加载用户类型列表
$("#selectrole").change(function(){
	$("#selectusertype").empty();
	$("#selectusertype").append("<option value='' selected>--请选择--</option>");
	var sel_role = $("#selectrole").val();
	if(sel_role == 2){
		$.post("/backend/loadUserTypeList.html",{"s_roleId":sel_role},function(data){
			if(data != null){
				for(var i=0;i<data.length;i++ ){
					$("#selectusertype").append("<option value='"+data[i].valueId+"'>"+data[i].valueName+"</option>");
				}
			}else{
				alert("用户列表加载失败!");
			}
		},"json");
	}
});

//ajax异步验证用户名是否可以使用(增加)
$("#a_logincode").blur(function(){
	var alc = $(this).val();
	if(alc != ""){
		//异步同名判断
		$.post("/backend/logincodeisexist.html",{"loginCode":alc,"id":"-1"},function(data){
			if(data == "repeat"){
				$("#add_formtip").css("color","red").html("<li>对不起，该用户名已存在</li>");
				$("#add_formtip").attr("key",1);
			}
			else if(data == "failed"){
				alert("操作超时！");
			}
			else if(data == "only"){
				$("#add_formtip").css("color","green").html("<li>该用户名可以正常使用</li>");
				$("#add_formtip").attr("key",0);
			}
		},"html");
	}
	
});

//email验证
$("#a_email").blur(function(){
	var flag = checkEmail($(this).val());
	if(flag){
		$("#add_formtip").html("");
		$("#add_formtip").attr("email",1);
	}else{
		$("#add_formtip").css("color","red").html("<li>邮箱格式不正确</li>");
		$("#add_formtip").attr("email",0);
	}
});

//添加用户信息验证
function adduserfunction(){
	$("#add_formtip").html("");
	var result = true;
	if($("#selectrole").val() == ""){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，角色不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_logincode").val()) == "" || $("#a_logincode").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，用户名不能为空。</li>");
		result = false;
	}else{
		if($("#add_formtip").attr("key") == 1){
			$("#add_formtip").append("<li>对不起，该用户名已存在。</li>");
			result = false;
		}
	}
	if($.trim($("#a_username").val()) == "" || $("#a_username").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，真实姓名不能为空。</li>");
		result = false;
	}
	if($("#selectcardtype").val() == ""){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，证件类型不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_idcard").val()) == "" || $("#a_idcard").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，证件号码不能为空。</li>");
		result = false;
	}else{
		if($("#a_idcard").val().length < 6){
			$("#add_formtip").css("color","red");
			$("#add_formtip").append("<li>对不起，证件号码长度必须超过6位。</li>");
			result = false;
		}
	}
	if($.trim($("#a_mobile").val()) == "" || $("#a_mobile").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，联系电话不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_bankname").val()) == "" || $("#a_bankname").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，开户行不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_bankaccount").val()) == "" || $("#a_bankaccount").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，开户卡号不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_accountholder").val()) == "" || $("#a_accountholder").val() == null){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>对不起，开户人不能为空。</li>");
		result = false;
	}
	if($.trim($("#a_email").val()) == "" || $("#a_email").val() == null || $("#add_formtip").attr("email") == 0){
		$("#add_formtip").css("color","red");
		$("#add_formtip").append("<li>email格式不正确</li>");
		result = false;
	}
	if(result == true) alert("添加成功 ");
	return result;
}

//给隐藏域赋值
$("#selectrole").change(function(){
	$("#selectrolename").val($(this).find("option:selected").text());
});
$("#selectusertype").change(function(){
	$("#selectusertypename").val($(this).find("option:selected").text());
});
$("#selectcardtype").change(function(){
	$("#selectcardtypename").val($(this).find("option:selected").text());
});

$("#m_roleid").change(function(){
	$("#m_rolename").val($(this).find("option:selected").text());
	$("#m_usertype").empty();
	$("#m_usertype").append('<option value="" selected>---请选择---</option>');
	var sel_role = $("#m_roleid").val();
	if(sel_role == 2){
		$.post('/backend/loadUserTypeList.html',{'s_role':sel_role},function(d){
			if(d != ""){
				for(var i=0;i<d.length;i++){
					$('#m_usertype').append('<option value="'+d[i].valueId+'" selected>'+d[i].valueName+'</option>');
				}
			}else{
				alert('用户类型加载失败');
			}
		},'json');
	}else{
		$('#usertypename').val('');
	}
});
$("#m_usertype").change(function(){
	$("#m_usertypename").val($(this).find("option:selected").text());
});
$("#m_cardtype").change(function(){
	$("#m_cardtypename").val($(this).find("option:selected").text());
});
//ajax异步验证用户名是否可以使用(修改)
$("#m_logincode").blur(function(){
	var mlc = $(this).val();
	if(mlc != ""){
		//异步同名判断
		$.post("/backend/logincodeisexist.html",{"loginCode":mlc,"id":$('#m_id').val()},function(data){
			if(data == "repeat"){
				$("#modify_formtip").css("color","red").html("<li>对不起，该用户名已存在</li>");
				$("#modify_formtip").attr("key",1);
			}
			else if(data == "failed"){
				alert("操作超时！");
			}
			else if(data == "only"){
				$("#modify_formtip").css("color","green").html("<li>该用户名可以正常使用</li>");
				$("#modify_formtip").attr("key",0);
			}
		},"html");
	}
	
});
//email验证
$("#m_email").blur(function(){
	var flag = checkEmail($(this).val());
	if(flag){
		$("#modify_formtip").html("");
		$("#modify_formtip").attr("email",1);
	}else{
		$("#modify_formtip").css("color","red").html("<li>邮箱格式不正确</li>");
		$("#modify_formtip").attr("email",0);
	}
});

//上传身份证照片(增加的时候)
$("#a_uploadbtnID").click(function(){
	TajaxFileUpload('0','a_fileInputID','a_uploadbtnID','a_idPic','a_fileInputIDPath');
});
//上传银行卡照片(增加的时候)
$("#a_uploadbtnBank").click(function(){
	TajaxFileUpload('0','a_fileInputBank','a_uploadbtnBank','a_bankPic','a_fileInputBankPath');
});
//上传身份证照片(修改的时候)
$("#m_uploadbtnID").click(function(){
	TajaxFileUpload($('#m_id').val(),'m_fileInputID','m_uploadbtnID','m_idPic','m_fileInputIDPath');
});
//上传银行卡照片(修改的时候)
$("#m_uploadbtnBank").click(function(){
	alert($('#m_id').val());
	TajaxFileUpload($('#m_id').val(),'m_fileInputBank','m_uploadbtnBank','m_bankPic','	fileInputBankPath');
});
/**
 * ajax上传图片的方法
 * @param flag 0代表增加操作，1代表修改操作
 * @param t1 	图片操作的标识id(file input框中的id)
 * @param t2	图片未上传。框后面是否显示"上传"的input  id
 * @param t3	图片上传成功后显示的div的id
 * @param t4	图片上传成功后，存放的图片地址的id
 */
function TajaxFileUpload(flag,t1,t2,t3,t4){
	if($("#"+t1+"").val()=='' || $("#"+t1+"").val()==null){
		alert("请上传文件");
	}else{
		$.ajaxFileUpload({
			url:'/backend/upload.html',
			secureuri:false,
			fileElementId:t1,
			dataType:"json",
			success:function(data){
				data=data.replace(/(^\s*)|(\s*$)/g,"") ;
				if(data == "1"){
					alert("上传图片大小不得超过50K");
					$("#uniform-"+t1+" span:first").html("无文件");
					$("input[name='"+t1+"']").change(function(){
						var fn = $("input[name='"+t1+"']").val();
						if($.browser.msie){
							fn = fn.substring(fn.lastIndexOf("\\")+1);
						}
						$("#uniform-"+t1+" span:first").html(fn);
					});
				}else if(data=="2"){
					alert("上传图片的格式不正确");
					$("#uniform-"+t1+" span:first").html("无文件");
					$("input[name='"+t1+"']").change(function(){
						var fn = $("input[name='"+t1+"']").val();
						if($.browser.msie){
							fn = fn.substring(fn.lastIndexOf("\\")+1);
						}
						alert(fn);
						$("#uniform-"+t1+" span:first").html(fn);
					});
				}else{
					$("#"+t3+"").append("<p><span onclick=\"delpic('"+flag
							+"','"+t3+"','"+t2+"',this,'"+data+"','"+t4+"','"+t1+
							"')\">x</span><img src=\""+data+"?m="+Math.random()+"\"/></p>");
					$("#"+t2+"").hide();
					$("#"+t4+"").val(data);
					$("input[name='"+t1+"']").change(function(){
						var fn = $("input[name='"+t1+"']").val();
						if($.browser.msie){
							fn = fn.substring(fn.lastIndexOf("\\")+1);
						}
						alert(fn);
						$("#uniform-"+t1+" span:first").html(fn);
					});
				}
			},
			error:function(){
				alert("上传失败");
			}
		});
	}
}

/**
 * 
 * @param id  要删除图片的用户的id
 * @param closeSpan  要关闭的图片所在的span的id
 * @param uploadBtn	上传的按钮id
 * @param obj	当前对象
 * @param picpath 图片地址
 * @param picText	
 * @param fileinputid	要上传文件的id
 */
function delpic(id,closeSpan,uploadBtn,obj,picpath,picText,fileinputid){
	$.post("/backend/delpic.html",{'id':id,'picpath':picpath},function(result){
		if("success"==result){
			alert("删除成功");
			$('#'+picText).val('');
			$('#uniform-'+fileinputid+' span:first').html('无文件');
			document.getElementById(closeSpan).removeChild(obj.parentElement);
			$('#'+uploadBtn).show();
		}else{
			alert('删除失败');
		}
	},'html');
}

/**
 * 查看用户信息
 */
$('.viewuser').click(function(e){
	var v_id = $(this).attr('id');
	alert("v_id==="+v_id);
	$.ajax({
		url:'/backend/getuser.html',
		data:{id:v_id},
		type:'POST',
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error');
		},
		success:function(data){
			if('failed' == data){
				alert('操作超时');
			}else if('nodata' == data){
				alert('没有数据');
			}else{
				alert("进入");
				$("#v_id").val(data.id);
				$("#v_roleName").val(data.roleName);
				$("#v_usertypename").val(data.userTypeName);
				$("#v_logincode").val(data.loginCode);
				$("#v_username").val(data.userName);
				$("#v_sex").val(data.sex);
				$("#v_cardtype").val(data.cardType);
				$("#v_idcard").val(data.idCard);
				$("#v_birthday").val(data.birthday);
				$("#v_country").val(data.country);
				$("#v_mobile").val(data.mobile);
				$("#v_email").val(data.email);
				$("#v_postcode").val(data.postCode);
				$("#v_bankname").val(data.bankName);
				$("#v_bankaccount").val(data.bankAccount);
				$("#v_accountholder").val(data.accountHolder);
				$("#v_referCode").val(data.referCode);
				$("#v_createTime").val(data.createTime);
				var isStart = data.isStart;
				if(isStart == 1){
					$("#v_isStart").append('<option value="1" selected>启用</option><option value="2">未启用</option>');
				}else{
					$("#v_isStart").append('<option value="1">启用</option><option value="2" selected>未启用</option>');
				}
				$("#v_useraddress").val(data.useraddress);
				$("#v_fileInputIDPath").val(data.idCardPicPath);
				var v_idcardpicpath = data.idCardPicPath;
				if(v_idcardpicpath == null || v_idcardpicpath == ''){
					$("#v_idPic").append('暂无');
				}else{
					$("#v_idPic").append('<p><img src="'+v_idcardpicpath+'?m='+Math.random()+'"/></p>');
				}
				$("#v_fileInputBankPath").val(data.bankPicPath);
				var v_bankpicpath = data.bankPicPath;
				if(v_bankpicpath == null || v_bankpicpath == ''){
					$("#v_bankPic").append('暂无');
				}else{
					$("#v_bankPic").append('<p><img src="'+v_bankpicpath+'?m='+Math.random()+'"/></p>');
				}
				e.preventDefault();//清除默认事件
				$('#viewUserDiv').modal('show');//显示模态窗口
			}
		}
	});
});

/**
 * 查看用户信息点击关闭时触发的事件
 */
$(".viewusercancel").click(function(e){
	$("#v_idPic").html('');
	$("#v_bankPic").html('');
	$("#v_isStart").html('');
});

$('.modifyuser').click(function(e){
	var m_id = $(this).attr('id');
	$.ajax({
		url:'/backend/getuser.html',
		type:'POST',
		data:{id:m_id},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('error');
		},
		success:function(data){
			if('failed' == data){
				alert('操作超时');
			}else if('nodata' == data){
				alert('没有数据');
			}else{
				$("#m_id").val(data.id);
				$("#m_username").val(data.userName);
				$("#m_logincode").val(data.loginCode);
				$("#m_birthday").val(data.birthday);
				$("#m_cardtype").val(data.cardType);
				$("#m_cardtypename").val(data.cardTypeName);
				var cardType = data.cardType;
				var cardTypeName = data.cardTypeName;
				$('#m_cardtype').html('');
				if(cardType == null || cardType == ''){
					$('#m_cardtype').append('<option value="" selected>---请选择---</option>');
				}
				for(var i=0;i<cardTypeListJson.length-1;i++){
					if(cardTypeListJson[i].valueId == cardType){
						$('#m_cardtype').append('<option value="'+cardType+'" selected>'+cardTypeName+'</option>');
					}else{
						$('#m_cardtype').append('<option value="'+cardTypeListJson[i].valueId+'">'+cardTypeListJson[i].valueName+'</option>');
					}
				}
				$('#m_roleid').html('');
				$('#m_rolename').val(data.roleName);
				var roleId=data.roleId;
				var roleName=data.roleName;
				if(roleId == null || roleId == ""){
					$('#m_roleid').append('<option value="" selected>---请选择---</option>');
				}
				for(var i=0;i<roleListJson.length-1;i++){
					if(roleListJson[i].id == roleId){
						$('#m_roleid').append('<option value="'+roleId+'" selected>'+roleName+'</option>');
					}else{
						$('#m_roleid').append('<option value="'+roleListJson[i].roleId+'">'+roleListJson[i].roleName+'</option>');
					}
				}
				$("#m_usertype").html('');
				$('#m_usertypename').val(data.userTypeName);
				if(roleId == '2'){
					var userType = data.userType;
					var userTypeName = data.userTypeName;
					if(userType == null || userType == ''){
						$('#m_usertype').append('<option value="" selected>---请选择---</option>');
					}
					$.post('/backend/loadUserTypeList.html',{'s_role':roleId},function(d){
							if(d != ""){
								for(var i=0;i<d.length;i++){
									if(d[i].valueId == userType){
										$('#m_usertype').append('<option value="'+userType+'" selected>'+userTypeName+'</option>');
									}else{
										$('#m_usertype').append('<option value="'+d[i].valueId+'">'+d[i].valueName+'</option>');
									}
								}
							}else{
								alert('用户类型加载失败');
							}
						},'json');
				}else if(roleId == '1'){
					$('#m_usertype').append('<option value="" selected>---请选择---</option>');
				}
				var sex =data.sex;
				$('#m_sex').html('');
				if(sex == ''){
					$('#m_sex').append('<option value="" selected>---请选择---</option><option value="男">男</option><option value="女" >女</option>');
				}else if(sex == '男'){
					$('#m_sex').append('<option value="">---请选择---</option><option value="男" selected>男</option><option value="女" >女</option>');
				}else if(sex == '女'){
					$('#m_sex').append('<option value="">---请选择---</option><option value="男">男</option><option value="女" selected>女</option>');
				}
				$("#m_idcard").val(data.idCard);
				$("#m_country").val(data.country);
				$("#m_mobile").val(data.mobile);
				$("#m_email").val(data.email);
				$("#m_postcode").val(data.postCode);
				$("#m_bankname").val(data.bankName);
				$("#m_bankaccount").val(data.bankAccount);
				$("#m_accountholder").val(data.accountHolder);
				var isStart = data.isStart;
				if(isStart == 1){
					$("#m_isstart").append('<option value="1" selected>启用</option><option value="2">未启用</option>');
				}else{
					$("#m_isstart").append('<option value="1">启用</option><option value="2" selected>未启用</option>');
				}
				$("#m_useraddress").val(data.userAddress);
				$("#m_referCode").val(data.referCode);
				$("#m_createTime").val(data.createTime);
				$("#m_fileInputIDPath").val(data.idCardPicPath);
				var m_idcardpicpath = data.idCardPicPath;
				if(m_idcardpicpath == null || m_idcardpicpath == ''){
					$('#m_uploadbtnID').show();
				}else{
					$('#m_idPic').append('<p><span onclick="delpic(\''+data.id+'\',\'m_idPic\',\'m_uploadbtnID\',this,\''
							+m_idcardpicpath+'\',\'m_fileInputIDPath\',\'m_fileInputID\');">x</span><img src="'+m_idcardpicpath+'?m='+Math.random()+'"/></p>');
					$('#m_uploadbtnID').hide();
				}
				$("#m_fileInputBankPath").val(data.bankPicPath);
				var m_bankpicpath = data.bankPicPath;
				if(m_bankpicpath == null || m_bankpicpath == ''){
					$('#m_uploadbtnBank').show();
				}else{
					$('#m_bankPic').append('<p><span onclick="delpic(\''+data.id+'\',\'m_bankPic\',\'m_uploadbtnBank\',this,\''
							+m_bankpicpath+'\',\'m_fileInputBankPath\',\'m_fileInputBank\');">x</span><img src="'+m_bankpicpath+'?m='+Math.random()+'"/></p>');
					$('#m_uploadbtnBank').hide();
				}
				e.preventDefault();//清除默认事件
				$('#modifyUserDiv').modal('show');//显示模态窗口
			}
		}
	});
});

$('.modifyusercancel').click(function(e){
	$('#m_idPic').html('');
	$('#m_bankPic').html('');
	$('#modify_formtip').html('');
	$('#m_isstart').html('');
	$('#m_usertypename').html('');
	$('#m_rolename').html('');
});

//修改用户信息
function modifyuserFunction(){
	$("#modify_formtip").html("");
	alert('进入修改');
	var result = true;
	if($("#m_roleid").val() == ""){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，角色不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_logincode").val()) == "" || $("#m_logincode").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，用户名不能为空。</li>");
		result = false;
	}else{
		if($("#modify_formtip").attr("key") == 1){
			$("#modify_formtip").append("<li>对不起，该用户名已存在。</li>");
			result = false;
		}
	}
	if($.trim($("#m_username").val()) == "" || $("#m_username").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，真实姓名不能为空。</li>");
		result = false;
	}
	if($("#m_cardtype").val() == ""){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，证件类型不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_idcard").val()) == "" || $("#m_idcard").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，证件号码不能为空。</li>");
		result = false;
	}else{
		if($("#m_idcard").val().length < 6){
			$("#modify_formtip").css("color","red");
			$("#modify_formtip").append("<li>对不起，证件号码长度必须超过6位。</li>");
			result = false;
		}
	}
	if($.trim($("#m_mobile").val()) == "" || $("#m_mobile").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，联系电话不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_bankname").val()) == "" || $("#m_bankname").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，开户行不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_bankaccount").val()) == "" || $("#m_bankaccount").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，开户卡号不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_accountholder").val()) == "" || $("#m_accountholder").val() == null){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>对不起，开户人不能为空。</li>");
		result = false;
	}
	if($.trim($("#m_email").val()) == "" || $("#m_email").val() == null || $("#modify_formtip").attr("email") == 0){
		$("#modify_formtip").css("color","red");
		$("#modify_formtip").append("<li>email格式不正确</li>");
		result = false;
	}
	if(result == true) alert("修改成功 ");
	return result;
}

//删除用户
$('.deluser').click(function(e){
	var d = $(this);
	var d_usertype= d.attr('usertype');
	var d_id= d.attr('id');
	var d_usertypename = d.attr('usertypename');
	var d_logincode = d.attr('logincode');
	var d_idcardpicpath = d.attr('idcardpicpath');
	var d_bankpicpath = d.attr('bankpicpath');
	if(confirm('您确定要删除【'+d_logincode+'】这个用户吗？')){
		$.post('/backend/deluser.html',{'delId':d_id,'delIdCardPicPath':d_idcardpicpath,'delBankPicPath':d_bankpicpath,'delUserType':d_usertype},function(data){
			if("success" == data){
				alert('删除成功！');
				window.location.href='/backend/userlist.html';
			}else if('noallow' == data){
				alert('该用户类型为：【'+d_usertypename+'】不允许被删除！');
			}else{
				alert('删除失败');
			}
		},'html');
	}
});