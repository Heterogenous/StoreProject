/*按加号数量增*/
function addNum(rid,cid,uid,pid) {
	var n = parseInt($("#goodsCount"+rid).val());
	$("#goodsCount"+rid).val(n + 1);
	calcRow(rid);
	//向服务器请求更新购物车商品的增加数量
	$.ajax({
		url: "/carts/update",
		type: "POST",
		data: {
			"cid": cid,
			"uid": uid,
			"pid": pid,
			"num": (n+1)
		},
		dataType: "JSON",
		success: function (res) {
			if(res.state === 400){
				console.log("商品数量+1");
			}else {
				alert("商品数量增加失败!自定义状态码:"+res.state);
			}
		},
		error: function (xhr) {
			alert("系统异常!状态码:"+xhr.status);
		}
	})
}
/*按减号数量减*/
function reduceNum(btn,rid,cid,uid,pid) {
	var n = parseInt($("#goodsCount"+rid).val());
	if (n === 0)
		return;
	$("#goodsCount"+rid).val(n - 1);
	calcRow(rid);
	if((n - 1) === 0){
		//调用删除函数
		//console.log("删除!");
		delCartItem(btn,cid);
	}else{
		//向服务器请求更新购物车商品的减少数量
		$.ajax({
			url: "/carts/update",
			type: "POST",
			data: {
				"cid": cid,
				"uid": uid,
				"pid": pid,
				"num": (n-1)
			},
			dataType: "JSON",
			success: function (res) {
				if(res.state === 400){
					console.log("商品数量-1");
				}else {
					alert("商品数量减少失败!自定义状态码:"+res.state);
				}
			},
			error: function (xhr) {
				alert("系统异常!状态码:"+xhr.status);
			}
		});
	}
}
/*全选全不选*/
function checkall(ckbtn) {
	$(".ckitem").prop("checked", $(ckbtn).prop("checked"));
	//calcTotal();

}
//删除按钮
function delCartItem(btn,cid) {
	// console.log("删除按钮:"+cid);
	//向服务器发送删除请求
	// let listCid = [];
	// listCid.push(parseInt(cid));
	let list = [];
	list.push(parseInt(cid));
	$.ajax({
		url: "/carts/delete",
		type: "POST",
		data: JSON.stringify(list),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType: "JSON",
		success: function (res) {
			if(res.state === 600){
				console.log(res.message);
				//移除tr标签
				$(btn).parents("tr").remove();
				//显示价格与数量
				checkBoxClick();
			}else{
				alert(res.message+",自定义状态码:"+res.state);
			}
		},
		error: function (xhr) {
			alert("系统异常!状态码:"+xhr.status);
		}
	});
	//calcTotal();
}
//批量删除按钮
function selDelCart() {
	//遍历所有按钮
	for (var i = $(".ckitem").length - 1; i >= 0; i--) {
		//如果选中
		if ($(".ckitem")[i].checked) {
			//删除
			$($(".ckitem")[i]).parents("tr").remove();
		}
	}
	//calcTotal();
}
$(function() {
	//单选一个也得算价格
	$(".ckitem").click(function() {
			//calcTotal();
		})
		//开始时计算价格
		//calcTotal();
})
//计算单行小计价格的方法
function calcRow(rid) {
	//取单价
	var vprice = parseFloat($("#goodsPrice"+rid).html());
	//取数量
	var vnum = parseFloat($("#goodsCount"+rid).val());
	//小计金额
	var vtotal = vprice * vnum;
	//赋值
	$("#goodsCast"+rid).html("¥" + vtotal);
}
//计算总价格的方法
/*
function calcTotal() {
	//选中商品的数量
	var vselectCount = 0;
	//选中商品的总价
	var vselectTotal = 0;

	//循环遍历所有tr
	for (var i = 0; i < $(".cart-body tr").length; i++) {
		//计算每个商品的价格小计开始
		//取出1行
		var $tr = $($(".cart-body tr")[i]);
		//取单价
		var vprice = parseFloat($tr.children(":eq(3)").children("span").html());
		//取数量
		var vnum = parseFloat($tr.children(":eq(4)").children(".num-text").val());
		//小计金额
		var vtotal = vprice * vnum;
		//赋值
		$tr.children(":eq(5)").children("span").html("¥" + vtotal);
		//计算每个商品的价格小计结束

		//检查是否选中
		if ($tr.children(":eq(0)").children(".ckitem").prop("checked")) {
			//计数
			vselectCount++;
			//计总价
			vselectTotal += vtotal;
		}
		//将选中的数量和价格赋值
		$("#selectTotal").html(vselectTotal);
		$("#selectCount").html(vselectCount);
	}
}*/