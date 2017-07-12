//获取功能菜单列表
$('.roleNameAuthority').click(function(){
	var authority = $(this);
	var roleId = authority.attr('roleid');
	$('#selectrole').html('当前配置角色为：'+authority.attr('rolename'));
	$('#roleidhide').val(roleId);
	//获取functionList
	$.ajax({
		url:'/backend/functions.html',
		type:'POST',
		data:{rid:roleId},
		dataType:'json',
		timeout:1000,
		error:function(){
			alert('加载功能列表失败');
		},
		success:function(data){
			if(data == 'nodata'){
				alert('对不起，功能列表获取失败。');
			}else{
				listr = '';
				for(var i=0;i<data.length;i++){
					listr += '<li>';
					listr += '<ul id="subfuncul'+data[i].mainFunction.id+'" class="subfuncul">';
					listr += '<li class="functiontitle"><input id="functiontitle'
						+data[i].mainFunction.id+'" onchange="mainFunctionSelectChange(this,'
						+data[i].mainFunction.id+');" funcid="'+data[i].mainFunction.id+'" type="checkbox"/>'
						+data[i].mainFunction.functionName+'</li>';
					for(var j=0;j<data[i].subFunctions.length;j++){
						listr += '<li><input onchange="subFunctionSelectChange(this,'+data[i].mainFunction.id+');" funcid="'+
						data[i].subFunctions[j].id+'" type="checkbox"/>'+data[i].subFunctions[j].functionName+'</li>';
					}
					listr += '</ul></li>';
				}
				$('#functionList').html(listr);
				//获得每个角色原有权限
				$('#functionList :checkbox').each(function(){
					var checkbox = $(this);
					$.ajax({
						url:'/backend/getAuthorityDefault.html',
						type:'POST',
						data:{rid:$('#roleidhide').val(),fid:$(this).attr('funcid')},
						dataType:'html',
						timeout:1000,
						error:function(){
							alert('获取改权限功能失败');
						},
						success:function(data){
							if(data == 'success'){
								checkbox.attr('checked',true);
							}else{
								checkbox.attr('checked',false);
							}
						}
					});
				});
			}
		}
	});
});

//保存修改后的信息
$('#confirmsave').click(function(){
	if(confirm('您确定要修改当前角色的权限吗？')){
		ids = $('#roleidhide').val()+"-";
		$('#functionList :checkbox').each(function(){
			if($(this).attr('checked') == 'checked'){
				ids += $(this).attr('funcid')+'-';
			}
		});
		$.ajax({
			url:'/backend/modifyAuthority.html',
			type:'POST',
			data:{ids:ids},
			dataType:'html',
			timeout:1000,
			error:function(){
				alert('操作失败，请重试。');
			},
			success:function(data){
				if(data == 'nodata'){
					alert('对不起，功能列表获取失败，请重试。');
				}else{
					alert('恭喜您，权限修改成功');
				}
			}
		});
	}
});

function subFunctionSelectChange(obj,id){
	if(obj.checked){
		$('#functiontitle'+id).attr('checked',true);
	}
}

function mainFunctionSelectChange(obj,id){
	if(obj.checked){
		$('#subfuncul'+id+' :checkbox').attr('checked',true);
	}else{
		$('#subfuncul'+id+' :checkbox').attr('checked',false);
	}
}

$("#selectAll").click(function () {//全选  
    $("#functionList :checkbox").attr("checked", true);  
});  

$("#unSelect").click(function () {//全不选  
    $("#functionList :checkbox").attr("checked", false);  
});  

$("#reverse").click(function () {//反选  
    $("#functionList :checkbox").each(function () {  
        $(this).attr("checked", !$(this).attr("checked"));  
    });  
}); 