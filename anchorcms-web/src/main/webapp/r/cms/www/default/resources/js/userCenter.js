$(document).ready(function(){changeGridHeight();$(".sidebar .menu .menus").find(".icon").click(function(){$(this).parents(".menu").find(".menus").removeClass("open");$(this).parents(".menu").find(".menus").removeClass("opens");$(".menuList li").removeClass("current");var A=$(this).parent().children().length;if($(this).next().is(":hidden")||A==1){if(A==1){$(this).parent().addClass("opens")}else{$(this).parent().addClass("open")}$(this).next().slideDown();$(this).parent().siblings().find("ul").slideUp()}else{$(this).parent().addClass("active");$(this).next().slideUp()}});$(".menuList li").click(function(){$(".menuList li").removeClass("current");$(this).addClass("current")});$(".user").hover(function(){$(this).next().show();$(this).css("background-image","url(../images/index/open.png) no-repeat right")});$(".vipMessage").mouseleave(function(){$(this).find("ul").hide()});$("#menuList li").click(function(){var A=$(this).index();if(A!=0){$(".menubar").find(".current").removeClass("current");$(this).addClass("current");$(".sidebar").find(".current").removeClass("current");$(".sidebar").find("div").eq(A).addClass("current")}});$(".sidebar .menuList a").click(function(){$(".main .nav").find("span").text($(this).parent().parent().prev().text()+"/"+$(this).text())});$(".menu li.letter a").click(function(){$(".main .nav").find("span").text($(this).text())});$("#menu1").find("a").click(function(){$(".main .nav").find("span").text($(this).text())})});function changeGridHeight(){$("body").scrollTop(10);if($("body").scrollTop()>0){$(".content").css("width",$(window).width()+13);$(".main").css("width",$(window).width()-237)}else{$(".content").css("width",$(window).width());$(".main").css("width",$(window).width()-247)}$("body").scrollTop(0);$(".content").css("height","100%");$(".sidebar").css("height",$(window).height()-90);$(".main").css("height",$(window).height()-90);$(".menu-content").css("height",$(".main").height()-40);if($(window).width()<window.screen.width){$(".content").css("height",window.screen.height-150);$(".content").css("width",window.screen.width);$(".sidebar").css("height",window.screen.height-240);$(".main").css("width",window.screen.width-250);$(".main").css("height",window.screen.height-240);$(".menu-content").css("height",$(".main").height()-40)}}window.onresize=function(){changeGridHeight()};