$(document).ready(function(){
	$(".intro").hover(function(){
		$(".intro-box").animate({top:0},300);
	},function(){
		$(".intro-box").animate({top:"215px"},300);
	});
	
	$(".tab-menu li").click(function(){
		var id=$(this).attr("id");
		$(".frame").hide();
		$("#tab"+id).fadeIn();
		
		$(this).siblings().removeClass("current");
		$(this).addClass("current");
	});
});
