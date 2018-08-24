;(function(){
	var _bindEvent = {
		changeBg:function(){
			$(".look_more").on("click",function(){
				$(this).addClass("look_more_bg").parents("dl").siblings().find(".look_more").removeClass("look_more_bg")
			})
		},
		changeXian:function(){
			$(".w_a_one").on("click",function(){
				var _index=$(this).index();
				console.log(_index)
				$(".w_footer_xian").animate({
					"left":_index*16.6667+"%"
				})
			})
		}
	}



	_bindEvent.changeBg();
	_bindEvent.changeXian();

})(jQuery)

function changeInputcontent(){
	var a = $("#jijian").next("span").find("span:eq(1)").html();
	var a1 = $("#jijian").next("span").find("span:eq(1)").text();
 	var b = $("#shoujian").next("span").find("span:eq(1)").html();
 	var b1 = $("#shoujian").next("span").find("span:eq(1)").text();
 	$("#jijian").val(b1);
	$("#shoujian").val(a1);
 	if(a!=""){
	 	$("#shoujian").next("span").find("span:eq(1)").html(a);
 		$("#shoujian").next("span").find("span:eq(1)").show();
 		$("#shoujian").next("span").find("span:eq(0)").hide();	
 	}else{
 		$("#shoujian").next("span").find("span:eq(1)").html("");
 		$("#shoujian").next("span").find("span:eq(0)").show();
 		$("#shoujian").next("span").find("span:eq(1)").hide();
 	}
 	if(b!=""){
	 	$("#jijian").next("span").find("span:eq(1)").html(b);
	 	$("#jijian").next("span").find("span:eq(1)").show();
 		$("#jijian").next("span").find("span:eq(0)").hide();
 	}else{
 		$("#jijian").next("span").find("span:eq(1)").html("");
	 	$("#jijian").next("span").find("span:eq(1)").hide();
 		$("#jijian").next("span").find("span:eq(0)").show();
 	}
}