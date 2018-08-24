
var ui ={
		popProcess:function(text, eclipseObj){
			var index = layer.load(0, {
			    shade: [0.1,'#ccc'] //0.1透明度的白色背景
			});
		},
		shutProcess:function(eclipseObj){
			layer.closeAll("loading");
			
		},
		closeAll:function(){
			layer.closeAll();
		},
		reloadUi:function(){
			/**
			var l = document.createElement("script");
			l.type = "text/javascript";
			l.src = ctxPath + "/static/script/framework/ui.js";
			$("#tempScript").html("");
			$("#tempScript").append(l);
			**/
			//initUi();
		},
		alert:function(msg){
				/*layer.alert(msg, {
				    skin: 'layui-layer-molv' //样式类名layui-layer-lan
				    ,closeBtn: 1,shift: 6,icon:7,offset: ['38%', '38%']
				});
				return;*/
				layer.alert(msg, {
  					icon: 1,
  					shift: -1,
  				skin: 'layui-layer-molv' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
				})
				
		},
		alertFail:function(msg){
				/*layer.alert(msg, {
				    skin: 'layui-layer-molv' //样式类名layui-layer-lan
				    ,closeBtn: 1,shift: 6,icon:7,offset: ['38%', '38%']
				});
				return;*/
				layer.alert(msg, {
  					icon: 7,
  					shift: -1,
  				skin: 'layui-layer-molv' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
				})
				
		},
		msg:function(msg){
			layer.msg(msg, {
			    offset: 0,
			    shift: 6
			});
			return;
		},
		lodding:function(){
			layer.load(1, { shade: [0.1,'#fff'] ,offset: ['45%', '49%'], }); 
		},
		closeWin:function(){
			layer.closeAll('loading');
			layer.closeAll('page');
		},
		tips:function(obj,msg,loc,color){//对象，消息，位置 上1 下3 右2 左4
			if(msg==""||msg==null){
				msg = "&nbsp;";
			}
			layer.tips(msg, obj, {
			    tips: [loc, (color==undefined||color==null)?'#52B3EA':color],
			    time:4000,
			    closeBtn:[0,true]
			});
		},
		tipsImg:function(obj,loc){//对象，消息，位置 上1 下3 右2 左4
			var img = "<img src='"+ obj.src +"' width='100%'>";
			 ui.tips(obj,img,loc);
		},
		confirm:function(msg,fun){
			var index = layer.confirm(msg, {
			  skin: 'layui-layer-lan',
			  icon:3,
			  shift: -1,
			  btn: ['确认','取消']//按钮
			}, function(){
			    eval(fun);
				layer.close(index);
			}, function(){
			  layer.close(index);
			});
		},
		confirm1:function(msg,fun){
			var index = layer.confirm(msg, {
			  skin: 'layui-layer-lan',
			  icon:3,
			  shift: -1,
			  btn: ['确认','取消']//按钮
			}, function(){
			    fun();
				layer.close(index);
			}, function(){
			  layer.close(index);
			});
		},
		confirmBoolean:function(msg){
			var index=layer.confirm(msg, {
				skin: 'layui-layer-lan',
				offset: ['38%', '38%'],
				icon:3,
			    btn: ['确认','取消']//按钮
			}, function(){ 
				layer.close(index);
				return true;
			}, function(){
			    layer.close(index);
			    return false;
			});
		},
		confirmDoAjax:function(msg,config){
			var index=layer.confirm(msg, {
				skin: 'layui-layer-lan',
				offset: ['38%', '38%'],
				icon:3,
			    btn: ['确认','取消']//按钮
			}, function(){
				HD.doAjax(config);
				layer.close(index);
			}, function(){
			    layer.close(index);
			});
		},
		createWind:function(config){
			if(config){
				var cfg = {
						type : "POST"
					};
					jQuery.extend(cfg, config);
					if (typeof(cfg.data)=='undefined')
						cfg.data={}; 
					if(config.title === undefined){
						config.title = '	窗口';
					}	
					cfg.data.isAjax=true;//标记Ajax请求
				    $.ajax({ 
					  	type:cfg.type, 
					  	url:cfg.url,
					  	data:cfg.data,
					  	dataType: "text", 
					  	contentType:'application/x-www-form-urlencoded; charset=UTF-8',
					  	success:function(res){
					  		var h = $(document).height();
					  		var w = $(document.body).width();
					  		//top.Dialog.open({InnerHtml:res,Title:"普通窗口"});
					  		layer.open({
					            type: 1,
					            title: config.title,
					            fix: false, //不固定
					            maxmin: config.maxmin==undefined?false:config.maxmin,
					            offset: config.offset==undefined?[(h/2-(config.height+150)/2)+"px",(w/2-config.width/2)+"px"]:config.offset,
					            skin: 'layui-layer-molv', //加上边框
							    area: [config.width==undefined?"800":config.width+'px', config.height==undefined?"600":config.height+'px'],
					            content: res,
					            shift:1,
					        });
					        
					  		//如果有回调函数则回调
					  		if (cfg.callBackFunc){  
			                   cfg.callBackFunc(res);
			                }
					  		ui.reloadUi()
					  	},
					  	error:function(XMLHttpRequest, textStatus){ 
					  		ui.alert("加载页面异常"+textStatus);
					  	}
				  	});
			}
		},
		createWind1:function(config){
		    var h = $(document).height();
	  		var w = $(document.body).width();
			layer.open({
	            type: 2,
	            title: config.title,
	            fix: false, //不固定
	            maxmin: config.maxmin==undefined?false:config.maxmin,
	            offset: config.offset==undefined?[(h/2-(config.height+150)/2)+"px",(w/2-config.width/2)+"px"]:config.offset,
	            skin: 'layui-layer-molv', //加上边框
			    area: [config.width==undefined?"800":config.width+'px', config.height==undefined?"600":config.height+'px'],
	            content: config.url,
	            shift:1,
	        });
		},
		
};