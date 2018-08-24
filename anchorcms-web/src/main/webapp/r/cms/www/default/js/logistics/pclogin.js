var curCount = 60;//短信发送间隔
var ewmtatus = 0;//浏览器响应状态 如果是-1 已经超时,无需向服务器发送请求
var ewmMaxtime = 60//当重复访问超过60秒不再向服务器发送请求
var pclogin = {
	//注册
	register:function(){
			/*if($("#PERSONAL_LOGINNAME").css("background").indexOf('check-mark')==-1){
                ui.alert("请输入正确的用户名");
                return false;
            }*/
			
//			if($("#PERSONAL_TEL").css("background").indexOf('check-mark')==-1){
//                ui.alert("请输入正确的手机号");
//                return false;
//            }
			
			if(!(/^1[34578]\d{9}$/.test($("#PERSONAL_TEL").val()))){
				ui.alert("请输入正确的手机号");
				return false;
			}
			
			if(pclogin.telunique1() == "1"){
				ui.alert("手机号已占用,请重新输入");
				return false;
			}
            
            if(!/^\d{6}$/.test($("#sms").val())){
                ui.alert("请输入正确的验证码");
                return false;
            }
			
			if($("#PERSONAL_PASSWORD").css("background").indexOf('check-mark')==-1){
                ui.alert("请输入6到16位英文字母或数字的密码");
                return false;
            }

			if($("#PERSONAL_PASSWORD1").css("background").indexOf('check-mark')==-1){
                ui.alert("两次密码输入不一样请重新输入");
                return false;

            }
			
			if($("#PERSONAL_EMAIL").css("background").indexOf('check-mark')==-1){
                ui.alert("请输入正确的邮箱");
                return false;
            }
            
            $("#PERSONAL_LOGINNAME").val($("#PERSONAL_TEL").val())
			var data= $("#registerForm").serialize();
			var config = {
		  	url:ctxPath+'/crepc/personelpc/register.html',
		  	data:data,
			callBackFunc : function(obj){
				if(obj=="0"){
					ui.alert("手机号或验证码不能为空");
				}else if(obj=="1"){
					ui.alert("验证码不正确");
				}else if(obj=="2"){
					ui.alert("该手机号没有可用验证码");
				}else{
					ui.alert("注册成功");
					//window.location.href=ctxPath+"/crepc/personel/personal_center.htm";
					window.location.href=ctxPath+"/crepc/personelpc/center.html?type=1";
				}
			},
			callBackErrorFunc: function fialInfo(){
				ui.alert("注册失败");
			}
			};
			HD.doAjax(config);
	},
	//用户名唯一性验证
	nameunique:function(){
		var config = {
		  	url:ctxPath+'/crepc/personelpc/nameunique.html',
		  	data:{name:$("#PERSONAL_LOGINNAME").val()},
			callBackFunc : function(obj){
				pclogin.ifTrue(true,"PERSONAL_LOGINNAME");
			},
			callBackErrorFunc: function fialInfo(){
				pclogin.ifTrue(false,"PERSONAL_LOGINNAME");
				ui.alert("用户名已存在");
			}
		};
		HD.doAjax(config);
	},
	//电话唯一性验证
	telunique:function(){
		var config = {
		  	url:ctxPath+'/crepc/personelpc/telunique.html',
		  	data:{tel:$("#PERSONAL_TEL").val()},
			callBackFunc : function(obj){
				pclogin.ifTrue(true,"PERSONAL_TEL");
			},
			callBackErrorFunc: function fialInfo(){
				pclogin.ifTrue(false,"PERSONAL_TEL");
				ui.alert("手机号已占用,请重新输入");
			}
		};
		HD.doAjax(config);
	},
	
	//电话唯一性验证
	telunique1:function(){
		var exist = "0";
		var config = {
		  	url:ctxPath+'/crepc/personelpc/telunique1.html',
		  	data:{tel:$("#PERSONAL_TEL").val()},
			callBackFunc : function(obj){
		  		if(obj){
		  			exist = "1";//已存在
		  		}else{
		  			exist = "0"; 
		  		}
//				pclogin.ifTrue(true,"PERSONAL_TEL");
			},
			callBackErrorFunc: function fialInfo(){
//				pclogin.ifTrue(false,"PERSONAL_TEL");
			}
		};
		HD.doAjax(config);
		return exist;
	},
	
	//打开图形验证码
	txyz:function(){
		var phone = $("#PERSONAL_TEL").val();
		if(!(/^1[34578]\d{9}$/.test(phone))){
			ui.alert("请输入正确的手机号");
			return false;
		}
		if(pclogin.telunique1() == "1"){
			ui.alert("手机号已占用,请重新输入");
			return false;
		}
		$("#fhcheckpic").attr('src',ctxPath+'/sys/common/getImage.html?d='+new Date().getTime())
		$("#yanzheng").css("display","inline-block");
	},
	//图形验证 (注册)
	codes : function(){
		var validatecode = $("#code").val();
		if(validatecode==''){
			ui.alert("请输入验证码");
			return false;
		}
		var config = {
			data:{validatecode:validatecode},
			url:ctxPath+'/crepc/sendpc!checkCodes.action',
			callBackFunc:function(obj){
				if(obj == true){
					$("#zcyzm").val($("#code").val())
					$("#code").val("");
					$("#yanzheng").hide();
					pclogin.yzm();
				}else{
					ui.alert("输入错误");
					$("#fhcheckpic").attr('src',ctxPath+'/sys/common/getImage.html?d='+new Date().getTime());
					$("#code").val("");
				}
			},
			callBackErrorFunc:function(){
				
			}
		};
		HD.doAjax(config);
	},
	yzm:function() {
         var phone = $("#PERSONAL_TEL").val();
         if(!(/^1(3|5|4|7|8)\d{9}$/.test(phone))){
             ui.alert("请输入正确的手机号");
             return false;
         }
         sendMessage.send_validaYzm(phone,$("#zcyzm").val(),4,null);
	 },
	 dinshi:function()  
	{  
	   if (curCount == 0) {
                $("#btna1").removeAttr("disabled");//启用按钮
                $("#btna1").text("重新发送验证码");
                $("#btna1").css({"cursor":"pointer","color": "#ff9600"})
                curCoun = coun;

         }else{
        	 curCount--;
        	 $("#btna1").attr("disabled", "true");
           	 $("#btna1").css({"cursor":"not-allowed","color":"#aaa"});
		  	 $("#btna1").text( + curCount + "秒再获取");
		  	 setTimeout(pclogin.dinshi,1000); 
         }
	} , 
	 
	 ifTrue:function(iftrue,obj){
	 	 if(iftrue){
	 	 	$("#"+obj).css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})
	 	 }else{
	 	 	$("#"+obj).css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})
	 	 }
	 },
	 //登录 登录后刷新0首页 1个人中心
	login:function(index){
		var username = $.trim($("#username").val());
		var password = $.trim($("#password").val());
		if(username==""||password==""){
			ui.alert("用户名或密码不能为空");
		}else{
			var config = {
			  	url:ctxPath+'/login/login!pccheckLogin.html',
			  	data:{username:username,password:password},
				callBackFunc : function(){
					//window.location.href=ctxPath+"/crepc/personel/personal_center.htm";
					if(index==1){
						window.location.href=ctxPath+"/crepc/personelpc/center.html?type=1";
					}else{
						window.location.href=ctxPath+"/crepc/crepc/index.html";
					}
				},
				callBackErrorFunc: function fialInfo(){
					ui.alert("用户名或密码错误");
				}
				};
				HD.doAjax(config);
		}
	},
	//退出登录
	doLogout:function(){
		ui.confirm1("确定退出吗？",function(){
			var url = ctxPath+"/logout/logout!toLogout.action";
				var config = {
					url:url,
					callBackFunc : function(){
						window.location.href = ctxPath + "/crepc/index.htm";
					}
				};
				HD.doAjax(config);
		});
	}, 
	//登录后刷新0首页 1个人中心
	pcCheckLogin:function(index){
		var config = {
		  	url:ctxPath+'/cre/sysdl!pcSaoCheckLogin.html',
		  	data:{uuid:uuid},
			callBackFunc : function(obj){
				if(obj==-1){
					ewmtatus = -1;
					return;
				}else if(obj==1){
					if(index==0){
						window.location.href=ctxPath+"/crepc/crepc/index.html";
					}else{
						window.location.href=ctxPath+"/crepc/personelpc/center.html?type=1"
					}
				}
			}
		};
		HD.doAjax(config);
		if(ewmtatus!=-1&&ewmMaxtime>0){
			ewmMaxtime = ewmMaxtime-3
			setTimeout(function(){
				pclogin.pcCheckLogin(index);//3秒后继续响应
			},3000)
		}else{
			$("#w_index_ewm_mark").show();
		}
	},
	sysaging:function(){
		ewmtatus = 0;
		ewmMaxtime = 59;
		$("#w_index_ewm_mark").hide();
		pclogin.erweimasc();
	},
	erweimasc:function(){
		uuid = Math.uuidFast();
	 	uuid = "uu"+uuid.substring(0,6);
	 	//uuid = "http://www.baidu.com";
	 	$("#ecode").html("");
	 	//每次刷新二维码
	 	$("#ecode").qrcode({ 
	    width: 120, //宽度 
	    height:120, //高度 
	    text: 'http://www.95572.com?'+uuid //任意内容 
	 	}); 
	 	setTimeout(function(){
	 		pclogin.pcCheckLogin('0');
	 	},1000)
	},
	
	sysaging_1:function(){
		ewmtatus = 0;
		ewmMaxtime = 59;
		$("#w_index_ewm_mark").hide();
		pclogin.erweimasc_1();
	},
	erweimasc_1:function(){
		uuid = Math.uuidFast();
	 	uuid = "uu"+uuid.substring(0,6);
	 	//uuid = "http://www.baidu.com";
	 	$("#ecode").html("");
	 	//每次刷新二维码
	 	$("#ecode").qrcode({ 
	    width: 220, //宽度 
	    height:220, //高度 
	    text: 'http://www.95572.com?'+uuid //任意内容 
	 	}); 
	 	setTimeout(function(){
	 		pclogin.pcCheckLogin('1');
	 	},1000)
	},
	
	
	//微博登陆
	weiboLogin : function(){
		window.location.href = ctxPath+"/crepc/crepc/weiboLogin.html";
	},
	
	
	//登录 登录后刷新0首页 1个人中心
	loginByTel:function(index){
		var tel = $.trim($("#phone1").val());
		var code = $.trim($("#yzm_dl").val());
		var yzm = $.trim($("#sms_dl").val());
		if(!(/^1[34578]\d{9}$/.test(tel))){
			ui.alert("请输入正确的手机号");
			return false;
		}
		if(code == ""){
			ui.alert("请输入图形验证码");
			return false;
		}
		if(yzm == ""){
			ui.alert("请输入手机验证码");
			return false;
		}else{
			var config = {
			  	url:ctxPath+'/login/login!pccheckLoginByTel.html',
			  	data:{tel:tel,code:code,yzm:yzm},
				callBackFunc : function(){
					//window.location.href=ctxPath+"/crepc/personel/personal_center.htm";
					if(index==1){
						window.location.href=ctxPath+"/crepc/personelpc/center.html?type=1";
					}else{
						window.location.href=ctxPath+"/crepc/crepc/index.html";
					}
				},
				callBackErrorFunc: function fialInfo(){
					ui.alert("登陆失败");
					$("#checkpic_dl").attr('src',ctxPath+'/sys/common/getImage.html?d='+new Date().getTime());
				}
			};
			HD.doAjax(config);
		}
	},
	
	
}