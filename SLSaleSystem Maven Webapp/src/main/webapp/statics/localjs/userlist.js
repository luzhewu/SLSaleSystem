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

