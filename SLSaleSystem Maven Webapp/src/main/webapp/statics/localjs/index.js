
	$("#loginBtn").click(function(){
	var user = new Object();
	user.userCode = $.trim($("#userCode").val());
	user.userPassword = $.trim($("#userPassword").val());
	user.isStart=1;
	
	if(user.userCode == "" || user.userCode == null){
		$("#userCode").focus;
		$("#formtip").html("登录名不能为空").css("color","red");
	}else if(user.userPassword == "" || user.userPassword == null){
		$("#userPassword").focus;
		$("#formtip").html("密码不能为空").css("color","red");
	}else{
		$.ajax({
			type:"POST",
			url:"/login.html",
			data:{user:JSON.stringify(user)},
			dataType:"html",
			timeout:1000,
			error:function(){
				$("#formtip").html("登录超时，请重新登录").css("color","red");
			},
			success:function(data){
				if(data != "" && data == "success"){//登录成功，跳转到main.html
					window.location.href="/main.html";
				}else if(data == "failed"){
					$("#formtip").html("登录失败，请重新登录").css("color","red");
					$("#userCode").val('');
					$("#userPassword").val('');
				}else if(data == "nousercode"){
					$("#formtip").html("登录账号不存在，请核对后登录").css("color","red");
				}else if(data == "nouserpassword"){
					$("#formtip").html("登录密码错误，请核对后登录").css("color","red");
				}else if("nodata" == data){
					$("#formtip").html("对不起，没有任何数据需要处理，请重新输入").css("color","red");
				}
			}
		});
	} 
});
