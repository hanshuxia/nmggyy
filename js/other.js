// JavaScript Document

$(function(){
  
   $('.input_text').click(function(){
			
	   $('.input_cli').css('display','block');
		
	});
	
	$('.input_cli').mouseover(function(){
		
	   $(this).css('display','block');
	
	});
	
	$('.input_cli').mouseout(function(){
		
	   $(this).css('display','none');
	
	});
	
	
	
	var window_height = $(window).height();	
	$('.fixed').css('height',window_height);
		
    
	//回到顶部及侧栏目的效果
	
	$(".top").click(function(){
        $('body,html').animate({scrollTop:0},400);
        return false;
    });
	
	$('.ce div').click(function(){
			var ind = $('.ce div').index(this)+1;			 
			var topVal = $('.floor_' +ind).offset().top;
			$('body,html').animate({scrollTop:topVal},1000)
		})
	
	$(window).scroll(scrolls)
	scrolls()
	function scrolls()
	{
	
   	  //各个楼层距离顶部的大小
	  var f1,f2,f3,f4,f5;
	  fl = $('.floor_first').offset().top;
      f2 = $('.floor_second').offset().top;
      f3 = $('.floor_third').offset().top;
      f4 = $('.floor_fourth').offset().top;
      f5 = $('.floor_fifth').offset().top;    
	
	  //浏览器的高度
	  var sTop = $(window).scrollTop();
	  var width = $(window).width();
	  
	  var ce = $('.ce');
	  var div = $('.ce div');
	   /*&& width > 1200*/
	  if( sTop >= fl && width >= 1300)
 	  {
		  ce.fadeIn(500);
	  }
	  else
	  {
		  ce.fadeOut(500);  		  
	  }
	  
	 /* if(sTop >= 600)
	  {

		  $('.fixed_logo').fadeIn(500);
		  
	  }
	  else
	  {
		  $('.fixed_logo').fadeOut(500);   
	  }*/
	    
	  if(sTop>=fl)
	  {
		
		div.eq(0).addClass('cur').siblings().removeClass('cur');
	  }
		   
	  if(sTop >= f2 - 100)
	  {
		
		div.eq(1).addClass('cur').siblings().removeClass('cur');
			 
 	  }
		   
	  if(sTop >= f3 - 100)
	  {
		
		div.eq(2).addClass('cur').siblings().removeClass('cur');
		
	  }
	
	  if(sTop >= f4 - 100)
	  {
		
		div.eq(3).addClass('cur').siblings().removeClass('cur');
	  
	  }
		   
	  if(sTop>=f5-100)
	  {
		
		div.eq(4).addClass('cur').siblings().removeClass('cur');

	  }
		  
	  
	}
	
	
	//上边的导航
	
})