var type = "2";//PC端

var homepage= {
	// 运单号查询
	queryBillByNo : function() {
		$("#yundan").show();
		$("#tupian").hide();
	},

	//寄件
	saveRecord : function(){
		var RECORD_JJNAME = $("#send_user").val();
		var RECORD_JJTEL = $("#send_phone").val();
		var send_region = $("#send_region").val();
		var RECORD_JJRPRO = $.trim(send_region.split("/")[0]);
		var RECORD_JJRCITY = $.trim(send_region.split("/")[1]);
		var RECORD_JJRAREA = $.trim(send_region.split("/")[2]);
		var RECORD_JJDETAIL = $("#send_address").val();
		var RECORD_SJNAME = $("#receipt_name").val();
		var RECORD_SJTEL = $("#receipt_phone").val();
		var receipt_region = $("#receipt_region").val();
		var RECORD_SJPRO = $.trim(receipt_region.split("/")[0]);
		var RECORD_SJCITY = $.trim(receipt_region.split("/")[1]);
		var RECORD_SJAREA = $.trim(receipt_region.split("/")[2]);
		var RECORD_SJDETAIL = $("#receipt_address").val();
		var RECORD_WEIGHT = $("#zl").val();
		var RECORD_TYPE = type;
		var RECORD_USERTEL = $("#telNum").val();
		var yzm = $("#send_sms").val();
		
		var sendyzm = $("#sendyzm").val();
		
		/** PC端新增字段 **/
		var RECORD_GOODS = $("#sendgoods").val();//品名
		var RECORD_JIANSHU = $("#sendnum").val();//件数
		var RECORD_REMARK = $("#beizhu").val();//备注
		var RECORD_YYQJSJ = $("#yyqjsj").val();//预约取件时间
		
		
		if(RECORD_JJNAME == ""){
			ui.alert("请输入寄件人姓名");
			return false;
		}
		if(send_region.split("/").length != 3){
			ui.alert("请选择正确的寄件地址");
			return false;
		}
		if(RECORD_JJDETAIL == ""){
			ui.alert("请输入寄件详细地址");
			return false;
		}
		if(RECORD_JJDETAIL.length>50){
			ui.alert("寄件详细地址值不能大于50");
			return ;
		}
		if(!(/^1[34578]\d{9}$/.test(RECORD_JJTEL))){
			ui.alertFail("请输入正确的手机号");
			return false;
		}
		if(RECORD_SJNAME == ""){
			ui.alert("请输入收件人姓名");
			return false;
		}
		if(receipt_region.split("/").length != 3){
			ui.alert("请选择正确的收件地址");
			return false;
		}
		if(RECORD_SJDETAIL == ""){
			ui.alert("请输入收件详细地址");
			return false;
		}
		if(RECORD_SJDETAIL.length>50){
			ui.alert("收件详细地址值不能大于50");
			return;
		}
		if(!(/^1[34578]\d{9}$/.test(RECORD_SJTEL))){
			ui.alert("请输入正确的手机号");
			return false;
		}
		if(RECORD_GOODS == ""){
			ui.alert("请选择品名");
			return false;
		}
		
		$("#but").css("background","#DDDDDD");
		$("#but").removeAttr('onclick');
		$("#but").attr("disabled","true");
		
		// 询问框
  		layer.open({
    		content: '确定提交吗？'
    		,btn: ['确定', '取消']
    		,yes: function(index){
				var config = {
					data:{RECORD_JJNAME:RECORD_JJNAME,RECORD_JJTEL:RECORD_JJTEL,RECORD_JJRPRO:RECORD_JJRPRO,RECORD_JJRCITY:RECORD_JJRCITY,RECORD_JJRAREA:RECORD_JJRAREA,
					RECORD_JJDETAIL:RECORD_JJDETAIL,RECORD_SJNAME:RECORD_SJNAME,RECORD_SJTEL:RECORD_SJTEL,RECORD_SJPRO:RECORD_SJPRO,RECORD_SJCITY:RECORD_SJCITY,
					RECORD_SJAREA:RECORD_SJAREA,RECORD_SJDETAIL:RECORD_SJDETAIL,RECORD_WEIGHT:RECORD_WEIGHT,RECORD_TYPE:RECORD_TYPE,RECORD_USERTEL:RECORD_USERTEL,yzm:yzm,
					RECORD_GOODS:RECORD_GOODS,RECORD_JIANSHU:RECORD_JIANSHU,RECORD_REMARK:RECORD_REMARK,RECORD_YYQJSJ:RECORD_YYQJSJ,sendyzm:sendyzm},
					url:ctxPath+"/crepc/sendpc!saveJJRecord.action",
					callBackFunc:function(obj){
						if(obj == "3"){//提交成功
							ui.alert('订单提交成功，我们将尽快与您联系');
							$("#send_user").val("");
							$("#send_phone").val("");
							$("#send_region").next().children("span").eq(0).attr("style","");
							$("#send_region").next().children("span").eq(0).html("请选择所在区域");
							$("#send_region").next().children("span").eq(0).show();
							$("#send_region").next().children("span").eq(1).html("");
							$("#send_region").next().children("span").eq(1).hide();
							$("#send_address").val("");
							$("#receipt_name").val("");
							$("#receipt_phone").val("");
							$("#receipt_region").next().children("span").eq(0).attr("style","");
							$("#receipt_region").next().children("span").eq(0).html("请选择所在区域");
							$("#receipt_region").next().children("span").eq(0).show();
							$("#receipt_region").next().children("span").eq(1).html("");
							$("#receipt_region").next().children("span").eq(1).hide();
							$("#receipt_address").val("");
							$("#send_sms").val("");
							$("#zl").val(1);
							$("#sendgoods option").eq(0).attr('selected','selected');
							$("#sendnum").val(1);
							$("#beizhu").val("");
							$("#yyqjsj").val("");
							$("#ph1").css("background","none");
							$("#ph2").css("background","none");
						}else if(obj == "1"){//验证码不正确
							ui.alert("提交失败,验证码不正确");
							return false;
						}else if(obj == "2"){//该手机号没有可用的验证码
							ui.alert("提交失败,该手机号没有可用的验证码");
							return false;
						}else if(obj == "-1"){
							ui.alert("提交失败,每天最多下单10次");
							return false;
						}else{}
						$("#but").removeAttr("disabled");
						$("#but").css("background","#ff9600");
						$("#but").removeAttr('onclick');
						$("#but").attr('onclick','homepage.saveRecord();');
					},
					callBackErrorFunc:function(obj){
						ui.alertFail('订单提交失败！');
						$("#but").removeAttr("disabled");
						$("#but").css("background","#ff9600");
						$("#but").removeAttr('onclick');
						$("#but").attr('onclick','homepage.saveRecord();');
					}
				};
				HD.doAjax(config);	
      			layer.close(index);
    		}
  		});
	},
	
	xs : function(){
		var userId = $("#telNum").val();
		if(userId == ""){//未登陆
			
		}else{//已登陆
			homepage.qformAddr();
		}
        $("#a1").show();
        $("#b1").hide();
    }
};

$(function(){
	if($("#telNum").val() != ""){
		var config = {
			url:ctxPath+'/crepc/crepc!getDefaultAddrByUser.action',
			data:{},
			callBackFunc : function(obj){
				if(obj != undefined){
					$("#send_user").val(obj.ADDRESS_NAME);
					$("#send_phone").val(obj.ADDRESS_TEL);
					$("#send_address").val(obj.ADDRESS_DETAIL);
					$("#send_region").val(obj.ADDRESS_PROVICE+" / "+obj.ADDRESS_CITY+" / "+obj.ADDRESS_AREA);
					$(".wyfh-02 .city-picker-span .placeholder").html(obj.ADDRESS_PROVICE+" / "+obj.ADDRESS_CITY+" / "+obj.ADDRESS_AREA);
					$(".wyfh-02 .city-picker-span .title").children("span").eq(0).html(obj.ADDRESS_PROVICE);
					$(".wyfh-02 .city-picker-span .title").children("span").eq(1).html(obj.ADDRESS_CITY);
					$(".wyfh-02 .city-picker-span .title").children("span").eq(2).html(obj.ADDRESS_AREA);
					$(".wyfh-02 .city-picker-span .placeholder").css({"font-size":"12px","color":"#333"});
				}
			},
			callBackErrorFunc: function fialInfo(){
				
			}
		};
		HD.doAjax(config);
	}
});