$('.addrole').click(function(e){
	e.preventDefault();
	$('#addRoleDiv').modal('show');
});

$('#addRoleCancel').click(loadingRoleList);
$('#addRoleClose').click(loadingRoleList);

function loadingRoleList(){
	window.location.href="/backend/rolelist.html";
}

$('#addRoleBtn').click(function(){
	var role = new Object();
	role.roleCode = $.trim($('#roleCode').val());
	role.roleName = $.trim($('#roleName').val());
	if(role.roleCode == "" || role.roleCode == null){
		$('#roleCode').focus();
		$('#formtip').css('color','red').html('对不起，角色名称不能为空。');
	}else{
		$('#formtip').html('');
		$.ajax({
			url:'/backend/addRole.html',
			type:'POST',
			data:{role:JSON.stringify(role)},
			dataType:'html',
			timeout:1000,
			error:function(){
				alert('进入error');
				$('#formtip').css('color','red').html('角色添加失败！请重试。');
			},
			success:function(data){
				if('' != data && 'success' == data){
					$('#formtip').css('color','green').html('角色添加成功，继续添加请填写。');
					$('#roleCode').val('');
					$('#roleName').val('');
				}else if('failed' == data){
					$('#formtip').css('color','red').html('角色添加失败！请重试。');
				}else if('rename' == data){
					$('#formtip').css('color','red').html('角色添加失败！角色代码和角色名称不能重复，请重试。');
				}else if('nodata' == data){
					alert('对不起，没有任何数据需要继续处理！请重试。');
				}
			}
		});
	}
});

$('.modifyrole').click(function(){
	var modify = $(this);
	var id = modify.attr('roleid');
	var oldCode = modify.attr('rolecode');
	var oldName = modify.attr('rolename');
	var roleCode = $.trim($('#roleCode'+id).val());
	var roleName = $.trim($('#roleName'+id).val());
	if(roleCode == '' || roleCode == null){
		alert('对不起，角色代码不能为空');
	}else if(roleName == '' || roleName == null){
		alert('对不起，角色名称不能为空');
	}else{
		var tip = '您确定要将原来的\n'+oldCode+'角色代码:和角色名称:'+oldName+'\n,修改为\n角色代码：'+roleCode+'，和角色名称：'+roleName+'\n吗？';
		if(confirm(tip)){
			var role = new Object();
			role.id = id;
			role.roleCode = roleCode;
			role.roleName = roleName;
			$.ajax({
				url:'/backend/modifyRole.html',
				type:'POST',
				data:{role:JSON.stringify(role)},
				dataType:'html',
				timeout:1000,
				error:function(){
					alert('角色修改失败，请重试！');
				},
				success:function(data){
					if(data != '' && 'success' == data){
						alert('角色修改成功');
					}else if('failed' == data){
						alert('角色修改失败，请重试！');
					}else if('nodata' == data){
						alert('对不起，没有任何数据需要处理，请重试！');
					}
				}
			});
		}
	}
});

$('.modifyIsStart').click(function(){
	var modify = $(this);
	var id = modify.attr('roleid');
	var isstart = modify.attr('isstart');
	var roleIsStart = new Object();
	roleIsStart.id = id;
	roleIsStart.roleName = null;
	if(isstart == '1'){
		roleIsStart.isStart = 2;
	}else{
		roleIsStart.isStart = 1;
	}
	$.ajax({
		url:'/backend/modifyRole.html',
		type:'POST',
		data:{role:JSON.stringify(roleIsStart)},
		dataType:'html',
		timeout:1000,
		error:function(){
			alert('开启或关闭角色操作时失败，请重试！');
		},
		success:function(data){
			if(data != '' && data == 'success'){
				if(isstart == '1'){
					modify.attr('isstart',0);
				}else{
					modify.attr('isstart',1);
				}
			}else if('failed' == data){
				alert('开启或关闭角色操作时失败，请重试！');
			}else if('nodata' == data){
				alert('对不起，没有任何数据需要处理，请重试！');
			}
		}
	});
});

$('.delrole').click(function(){
	var del = $(this);
	var id = del.attr('roleid');
	var roleName = del.attr('rolename');
	var tip = '您确定要删除角色：'+roleName+'吗？';
	if(confirm(tip)){
		var role = new Object();
		role.id = id;
		$.ajax({
			url:'/backend/delRole.html',
			type:'POST',
			data:{role:JSON.stringify(role)},
			dataType:'html',
			timeout:1000,
			error:function(){
				alert('删除角色失败，请重试！');
			},
			success:function(data){
				if(data != '' && data == 'success'){
					alert('删除角色成功');
					loadingRoleList();
				}else if(data == 'failed'){
					alert('删除角色失败，请重试！');
				}else if('nodata' == data){
					alert('对不起，没有任何数据需要处理！请重试。');
				}else{
					if(data != null && data !=''){
						data = data.substring(0,data.length-1);
						alert('系统中有用户被授权该角色，不能删除！用户账号：【'+data+'】')
					}
				}
			}
		});
	}
});