
	$("#loginBtn").click(function(){
	var user = new Object();
	user.loginCode = $.trim($("#loginCode").val());
	user.password = $.trim($("#password").val());
	user.isStart=1;
	
	if(user.loginCode == "" || user.loginCode == null){
		$("#loginCode").focus;
		$("#formtip").html("登录名不能为空").css("color","red");
	}else if(user.password == "" || user.password == null){
		$("#password").focus;
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
					$("#loginCode").val('');
					$("#password").val('');
				}else if(data == "nologincode"){
					$("#formtip").html("登录账号不存在，请核对后登录").css("color","red");
				}else if(data == "nopassword"){
					$("#formtip").html("登录密码错误，请核对后登录").css("color","red");
				}else if("nodata" == data){
					$("#formtip").html("对不起，没有任何数据需要处理，请重新输入").css("color","red");
				}
			}
		});
	} 
});
