$(function(){
	
	//$("#billcode").focus();
	
	$("#billcode").blur(function(){
	    var str=$("#billcode").val()
	    var reg=/,$/gi;
	    str=str.replace(reg,"");
	    $("#billcode").val(str)
	})
	
	$("#billcode").keydown(function (event){
		if($("input[name=bllock]").length == 10){
			ui.alert("最多输入10个单号");
			return false;
		}
        qingchudouhao();
        // if(event.keyCode==32){
        //     var a=$("#billcode").val();
        //     if(a.length>11){
        //         $("#billcode").before("<div style='display:inline-block;background:#f1f1f1;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
        //         $("#billcode").val("");
        //         setTimeout("qi()",200);
        //     }else{
        //         $("#billcode").before("<div style='display:inline-block;border-bottom:1px dotted #f33;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
        //         $("#billcode").val("");
        //         setTimeout("qi()",200);
        //     }
        // }else 
        if(event.keyCode==188) {
            var a=$("#billcode").val();
            if(a.length>11){
                $("#billcode").before("<div style='display:inline-block;background:#f1f1f1;margin-right:8px'><input type='text' name='bllock' maxlength='13' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                $("#billcode").val("");
                setTimeout("qi()",200);
            }else{
                $("#billcode").before("<div style='display:inline-block;border-bottom:1px dotted #f33;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                $("#billcode").val("");
                setTimeout("qi()",200);
            }
        } else if(event.keyCode==13) {
            // var a=$("#billcode").val();
            // if(a.length>11){
                // $("#billcode").before("<div style='display:inline-block;background:#f1f1f1;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                // $("#billcode").val("");
                // setTimeout("qi()",200);
                queryBillByNo();
                return false;
            // }else{
                // $("#billcode").before("<div style='display:inline-block;border-bottom:1px dotted #f33;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                // $("#billcode").val("");
                // setTimeout("qi()",200);
                // return false;
            // }
        } else if(event.keyCode==44) {
            var a=$("#billcode").val();
            if(a.length>11){
                $("#billcode").before("<div style='display:inline-block;background:#f1f1f1;margin-right:8px'><input type='text' maxlength='13' name='bllock' readonly onclick='bianji(this)' onchange='bianhua(this)' style='color:#666;border:0;text-transform:uppercase;;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                $("#billcode").val("");
                setTimeout("qi()",200);
                return false;
            }else{
                $("#billcode").before("<div style='display:inline-block;border-bottom:1px dotted #f33;margin-right:8px'><input type='text' maxlength='13' name='bllock' onclick='bianji(this)' onchange='bianhua(this)' readonly style='color:#666;border:0;text-transform:uppercase;width:135px;cursor:text' value='"+a+"'><span class='wp'onclick='wp(this)' style='margin:5px;cursor: pointer;'>×<span></div>");
                $("#billcode").val("");
                setTimeout("qi()",200);
                return false;
            }
        }
    })
	
	
	$('#phone').keyup(function() {
        var phone = $('#phone').val();
        $("#phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}
    });
	
	$('#phone').on('input propertychange', function() {
        var phone = $('#phone').val();
        $("#phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}
    });
	
	$("#codes").focus(function(){
		var as=$(this).val();
		if(as=="请输入图片中的验证码"){
			$(this).val("");
			$(this).css("color","#666");
		}
	})
	$("#codes").blur(function(){
		var as=$(this).val();
		if(as==""){
			$(this).val("请输入图片中的验证码")
		 	$(this).css("color","#aaa")
		}
	})
	
	$('#send_phone').keyup(function() {
        var phone = $('#send_phone').val();
        $("#send_phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph1").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph1").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}
    });
	
	$('#send_phone').on('input propertychange', function() {
        var phone = $('#send_phone').val();
        $("#send_phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph1").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph1").css({"background":"url("+ctxImgStaticPath+"/images/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}
    });
	
	$("#code").focus(function(){
		var as=$(this).val();
		if(as=="请输入图片中的验证码"){
			$(this).val("");
			$(this).css("color","#666");
		}
	})
	$("#code").blur(function(){
		var as=$(this).val();
		if(as==""){
			$(this).val("请输入图片中的验证码")
		 	$(this).css("color","#aaa")
		}
	})
	
	$('#receipt_phone').keyup(function() {
        var phone = $('#receipt_phone').val();
        $("#receipt_phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph2").css({"background":"url("+ctxImgStaticPath+"/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph2").css({"background":"url("+ctxImgStaticPath+"/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}

    });
	
	$('#receipt_phone').on('input propertychange', function() {
        var phone = $('#receipt_phone').val();
        $("#receipt_phone").attr({maxlength:"11"});
        if(phone.length>0){ $("#ph2").css({"background":"url("+ctxImgStaticPath+"/iconmonstr-x-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}
        if(phone.length==11){if((/^1(3|5|4|7|8)\d{9}$/.test(phone))) { $("#ph2").css({"background":"url("+ctxImgStaticPath+"/iconmonstr-check-mark-icon-24.png)","background-repeat":" no-repeat","background-position":"100% 50%"})}else{ui.alert("请输入正确的手机号码");return false;}}
    })
    
    $("#che").click(function(){
		if($(this).is(':checked')) {
			$("#but").removeAttr("disabled");
			$("#but").css("background","#ff9600");
			$("#but").removeAttr('onclick');
			$("#but").attr('onclick','homepage.saveRecord();');
		}else{
			$("#but").css("background","#DDDDDD");
			$("#but").removeAttr('onclick');
			$("#but").attr("disabled","true");
		}
    })
    
});

function bianji(e){
    e.setAttribute("class", "abc");
	$(".abc").removeAttr("readOnly");
    e.focus()
    event.stopPropagation()
	return false;
}

function bianhua(e){
    var a=$(".abc").val()
    //console.log(a);
    if(a.length>11){
        $(".abc").closest("div").css({"border-bottom":"0","background":"#f1f1f1"})
        $(".abc").removeClass("abc");
    }else{
        $(".abc").closest("div").css({"border-bottom":"1px dotted #f33","background":"#fff"})
        $(".abc").removeClass("abc");
    }
    if(a.length==0){$(".abc").closest("div").remove()}
}

function shu(){
	$("#billcode").focus();
}

function qi(){
    $("#billcode").val("");
}
function wp(e){
  	e.parentNode.parentNode.removeChild(e.parentNode);
}
function wpa(){
    $("#ls").find("div").remove();
    $("#billcode").val("");
    $("#tupian").show();
    $("#yundan").hide();
}
function qingchudouhao(){
    var str=$("#billcode").val();
    var reg=/,$/gi;
    str=str.replace(reg,"");
    $("#billcode").val(str);
}

function guanbi(){
	$("#yanzheng").css("display","none")
}

function xiangqing01(){
	$("#xiangqing02").css("display","none")
}

function sxcab(){
	$('#jiageshixiao')[0].reset();
    $("#kg").attr("value","1");
    $(".title").css("display","none");
    $(".placeholder").css("display","inline");
    $(".city-select .clearfix a").removeClass("active");
    var y=[];
    y=$(".city-select .clearfix a")
    for(var b=0;b<y.length;b++){
        if(y[b].getAttribute("class")=='active'){
            y[b].className="";
        }
    }
    
    $("#dizhisu").html("");
	$("#yssx").html("");
	$("#jgimg").show();
	$("#yssxs").hide();
}

function qs(){
	$("#qusongfanwei")[0].reset();
	$("#qusongfanwei1")[0].reset();
}

function ditu(id,lng,lat,img,img1,img2,company,detail,contacts,phone_one,office_hours,district,type){
	img = ctxImgStaticPath+"/images/leixing4.jpg";
    var leixing = "<img src='"+ctxImgStaticPath+"/images/123456789.png'>"
    if(type == '自营经营机构'){
        // leixing = "<img src='"+ctxImgStaticPath+"/images/leixing1.jpg'>";//自营图片
        img = ctxImgStaticPath+"/images/leixing1.jpg";
    }else if(type=='专营经营机构'){
        //leixing = "<img src='${ctxImgStaticPath}/images/leixing2.png'>";//加盟店铺
        // leixing = "<img src='"+ctxImgStaticPath+"/images/leixing2.jpg'>";//专营
        img = ctxImgStaticPath+"/images/leixing2.jpg";
    }else if(type=='代理经营机构'){
        // leixing = "<img src='"+ctxImgStaticPath+"/images/leixing3.jpg'>";//代理
        img = ctxImgStaticPath+"/images/leixing3.jpg";
        //leixing = "<img src='${ctxImgStaticPath}/images/leixing3.png'>";//分公司
    }else if(type=='作业机构'){
        //leixing = "<img src='${ctxImgStaticPath}/images/leixing4.png'>";//总公司
        // leixing = "<img src='"+ctxImgStaticPath+"/images/leixing4.jpg'>";//作业机构
    }

//	 百度地图API功能
//  var sContent ="天安门坐落在中国北京市中心,故宫的南侧,与天安门广场隔长安街相望,是清朝皇城的大门...";
    var str = '';
    str +="<div><div><img src="+img+"></div>";
    str +="<div><p>"+leixing+"<span id='wdmc'>"+company+"</span></p>";
    str +="<p><img src='"+ctxImgStaticPath+"/images/123456789.png'>"+"<span id='xxdz'>"+detail+"</span></p>";
    str +="<ul><li><p>手机：</p>"+"<span id='shoujihao'>"+contacts+"</span></li>";
    str +="<li><p>座机：</p>"+"<span id='zuojihao'>"+phone_one+"</span></li>";
    str +="<li><p>营业时间：</p>"+"<span id='yysj'>"+office_hours+"</span></li></ul>";
    str +="</div><div>"
    str +="<p><a target='_Blank' href='http://api.map.baidu.com/geocoder?address="+district+detail+"&output=html&src=yourCompanyName|yourAppName'>导 &nbsp; &nbsp; 航</a><a href='javascript:;' onclick='homepage.sendMsgWindow();'>发送到手机</a></p></div></div>";
    var map = new BMap.Map("l-map", {enableMapClick:false});//构造底图时，关闭底图可点功能
    var point = new BMap.Point(lng,lat);
    var marker = new BMap.Marker(point);  // 创建标注
    var infoWindow = new BMap.InfoWindow(str);  // 创建信息窗口对象
    marker.addEventListener("click",function(e){
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	});
    map.addOverlay(marker);              // 将标注添加到地图中
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    map.addControl(navigationControl);
    map.openInfoWindow(infoWindow,point); //开启信息窗口

//    // 创建地址解析器实例
//	var myGeo = new BMap.Geocoder();
//	// 将地址解析结果显示在地图上,并调整地图视野
//	myGeo.getPoint(detail, function(point){
//		if (point) {
////			var point = new BMap.Point(lng,lat);
//		    var marker = new BMap.Marker(point);  // 创建标注
//		    map.addOverlay(marker);              // 将标注添加到地图中
//		    map.centerAndZoom(point, 15);
//		    var infoWindow = new BMap.InfoWindow(str);  // 创建信息窗口对象
//		    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
//		    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
//		    var navigationControl = new BMap.NavigationControl({
//			    // 靠左上角位置
//			    anchor: BMAP_ANCHOR_TOP_LEFT,
//			    // LARGE类型
//			    type: BMAP_NAVIGATION_CONTROL_LARGE,
//			    // 启用显示定位
//			    enableGeolocation: true
//		  	});
//		    map.addControl(navigationControl);
//		    map.openInfoWindow(infoWindow,point); //开启信息窗口
//		}else{
//			ui.alert("您选择地址没有解析到结果!");
//		}
//	}, district);
}

function openInfo(content,e){
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}

function guanbi1(){
	$("#yanzheng1").css("display","none")
}

function gbfhtk(){
	$("#fahuo2").css("display","none");
}

function fhtk(){
	$("#fahuo2").css("display","inline-block");
}

function xs1(){
    $("#a1").hide();
}

function xa1(){
    $("#b1").hide();
}

function fahuomember(name,phone,province,city,district,detail){
	$("#send_user").val(name);
	$("#send_phone").val(phone);
	$("#send_address").val(detail);
	$("#send_region").val(province+" / "+city+" / "+district);
	$(".wyfh-02 .city-picker-span .placeholder").html(province+" / "+city+" / "+district);
	$(".wyfh-02 .city-picker-span .title").children("span").eq(0).html(province);
	$(".wyfh-02 .city-picker-span .title").children("span").eq(1).html(city);
	$(".wyfh-02 .city-picker-span .title").children("span").eq(2).html(district);
	$(".wyfh-02 .city-picker-span .placeholder").css({"font-size":"12px","color":"#333"})
    xs1();
}

function shouhuomember(name,phone,province,city,district,detail){
	$("#receipt_name").val(name);
	$("#receipt_phone").val(phone);
	$("#receipt_address").val(detail);
	$("#receipt_region").val(province+" / "+city+" / "+district);
	$(".wyfh-03 .city-picker-span .placeholder").html(province+" / "+city+" / "+district);
	$(".wyfh-03 .city-picker-span .title").children("span").eq(0).html(province);
	$(".wyfh-03 .city-picker-span .title").children("span").eq(1).html(city);
	$(".wyfh-03 .city-picker-span .title").children("span").eq(2).html(district);
	$(".wyfh-03 .city-picker-span .placeholder").css({"font-size":"12px","color":"#333"})
    xa1();
}

//新增地址页面(主页)
function addAddress(sor){
    var w = $(window).width();
	var h = $(window).height(); 
    var config = {
		width:400, 
		height:430,
		data:{sor:sor},
		offset:[(h/2-340/2)+"px",(w/2-580/2)+"px"],
		title:'添加地址',
		url:ctxPath+'/crepc/crepc/addAddress.html'
	};
	ui.createWind(config);
}


function guanbi_dl(){
	$("#yanzheng_dl").css("display","none")
}

function queryBillByNo() { // 得到物流信息
    var orderId = $("#billcode").val();
    if (orderId != "") {
        $.ajax({
            type: "post",
            url: "/getWuliuDetail.jspx?orderId=" + orderId,
            dataType: "json",
            success: function (deliverInfo) {
                getWuliuInfo (deliverInfo);
            },
            error: function () {
                ui.alertFail("数据传输失败，原因：1、用户未登录，2、用户无关联公司，3、请求查看的物流信息不存在");
            }
        });
    } else {
        ui.alertFail("请输入运单号！");
    }
}

function getWuliuInfo (deliverInfoJson) {
    var deleverOperations = [];
    var deleverOperationtimes = [];
    if (deliverInfoJson == undefined) {
        ui.alertFail("请输入合法运单号！");
        return;
    } else {
        $("#tupian").hide();
        $("#yundan").show();
        if (deliverInfoJson.length > 0) {
            for (var i = 0; i < deliverInfoJson[0].operations.length; i++) {
                deleverOperations.push(deliverInfoJson[0].operations[i].operationdes);
                deleverOperationtimes.push(deliverInfoJson[0].operations[i].operationtime);
            }
            if (deleverOperations.length == 1) { // 制票
                $("#NoLogisticsInfo").hide();
                $("#Ticket").show();
                $("#Ticket").attr("class", "first");
                $("#TicketTxt").html("【" + deleverOperations[0] + "】");
                $("#TicketTime").html(formatDeleverTime(deleverOperationtimes[0]));
            } else if (deleverOperations.length == 2) { // 装车
                $("#NoLogisticsInfo").hide();
                $("#Ticket").show();
                $("#TicketTxt").html("【" + deleverOperations[0] + "】");
                $("#TicketTime").html(formatDeleverTime(deleverOperationtimes[0]));
                $("#Loading").show();
                $("#Loading").attr("class", "first");
                $("#LoadingTxt").html("【" + deleverOperations[1] + "】");
                $("#LoadingTime").html(formatDeleverTime(deleverOperationtimes[1]));
            } else if (deleverOperations.length == 3) { // 卸车
                $("#NoLogisticsInfo").hide();
                $("#Ticket").show();
                $("#TicketTxt").html("【" + deleverOperations[0] + "】");
                $("#TicketTime").html(formatDeleverTime(deleverOperationtimes[0]));
                $("#Loading").show();
                $("#LoadingTxt").html("【" + deleverOperations[1] + "】");
                $("#LoadingTime").html(formatDeleverTime(deleverOperationtimes[1]));
                $("#Unload").show();
                $("#Unload").attr("class", "first");
                $("#UnloadTxt").html("【" + deleverOperations[2] + "】");
                $("#UnloadTime").html(formatDeleverTime(deleverOperationtimes[2]));
            } else if (deleverOperations.length == 4) { // 确认
                $("#NoLogisticsInfo").hide();
                $("#Ticket").show();
                $("#TicketTxt").html("【" + deleverOperations[0] + "】");
                $("#TicketTime").html(formatDeleverTime(deleverOperationtimes[0]));
                $("#Loading").show();
                $("#LoadingTxt").html("【" + deleverOperations[1] + "】");
                $("#LoadingTime").html(formatDeleverTime(deleverOperationtimes[1]));
                $("#Unload").show();
                $("#UnloadTxt").html("【" + deleverOperations[2] + "】");
                $("#UnloadTime").html(formatDeleverTime(deleverOperationtimes[2]));
                $("#confirm").show();
                $("#confirm").attr("class", "first");
                $("#confirmTxt").html("【" + deleverOperations[3] + "】");
                $("#confirmTime").html(formatDeleverTime(deleverOperationtimes[3]));
            } else if (deleverOperations.length == 5) { // 交付
                $("#NoLogisticsInfo").hide();
                $("#Ticket").show();
                $("#TicketTxt").html("【" + deleverOperations[0] + "】");
                $("#TicketTime").html(formatDeleverTime(deleverOperationtimes[0]));
                $("#Loading").show();
                $("#LoadingTxt").html("【" + deleverOperations[1] + "】");
                $("#LoadingTime").html(formatDeleverTime(deleverOperationtimes[1]));
                $("#Unload").show();
                $("#UnloadTxt").html("【" + deleverOperations[2] + "】");
                $("#UnloadTime").html(formatDeleverTime(deleverOperationtimes[2]));
                $("#confirm").show();
                $("#confirmTxt").html("【" + deleverOperations[3] + "】");
                $("#confirmTime").html(formatDeleverTime(deleverOperationtimes[3]));
                $("#deliver").show();
                $("#deliver").attr("class", "first");
                $("#deliverTxt").html("【" + deleverOperations[4] + "】");
                $("#deliverTime").html(formatDeleverTime(deleverOperationtimes[4]));
            }
        } else { // 暂无订单信息
            $("#Ticket").hide();
            $("#Loading").hide();
            $("#Unload").hide();
            $("#confirm").hide();
            $("#deliver").hide();
            $("#NoLogisticsInfo").show();
        }
    }
}