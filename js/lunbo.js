// JavaScript Document



var i=0;
var timer;
$(function(){
	
  $('.ig').eq(0).show().siblings().hide();
  $('.lunbo .ig').eq(0).siblings().hide();
  showtime();
  
  //触摸事件
  
  $('.xyq').hover(function(){
	 i = $(this).index();
     show();
	 clearInterval(timer);
	 
  },function(){
	  
	showtime();  
	 
  });
  
  
  // 点击事件
  
  $('.btn1').click(function(){
	
	 clearInterval(timer);
	 
	 if(i == 0)
	 {
		 i = 9; 
	 } 
	 i--;
	 
	 show();
	 showtime();
  });
  
  $('.btn2').click(function(){
	
	 clearInterval(timer);

	 if(i == 8)
	 {
		 i = -1; 
	 } 
	 i++;
	 
	 show();
	 showtime();
  });
	
});

function show()
{
	 $('.ig').eq(i).fadeIn().siblings().fadeOut();
	 $('.xyq').eq(i).addClass('xyq_ys').siblings().removeClass('xyq_ys');
	 $('.ig').eq(i).fadeIn().siblings().fadeOut();
	 $('.xyq').eq(i).addClass('xyq_ys').siblings().removeClass('xyq_ys');
}

function showtime()
{
	timer = setInterval(function(){
	  i++;
	  if(i == 10)
	  {
		 i = 0;  
	  }
	  show();
  },5000);
}


