$(document).ready(function(){var C=1;$.extend(jQuery.easing,{easeOutStrong:function(E,F,G,H,D){return -H*((F=F/D-1)*F*F*F-1)+G}});function B(){var D=4,F=0,E=0;E=$(".aItems li").length;F=Math.ceil(E/D);if(C<F){$(".aItems").animate({"marginLeft":-1100*C},800);C++}else{$(".aItems").animate({"marginLeft":0},500);C=1}}var A=setInterval(B,5000);$(".aItems").hover(function(){clearInterval(A)},function(){A=setInterval(B,5000)});$(".tabMenu span").click(function(){var D=$(this).index();$(this).siblings().removeClass("current");$(this).addClass("current");$(this).parents(".tabHead").next().find(".tabPane").hide();$(this).parents(".tabHead").next().find(".tabPane").eq(D).fadeIn()});$(".cItems li").hover(function(){$(this).find(".item-cname").stop().animate({"top":0},500);$(this).find(".sTip").fadeOut()},function(){$(this).find(".item-cname").stop().animate({"top":"-140px"},500);$(this).find(".sTip").fadeIn()})});