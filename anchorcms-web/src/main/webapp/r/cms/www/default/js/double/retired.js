$(document).ready(function() {
	$("#guide").click(function() {
		
		$("#guide_content").show();		
		$("#recommend_content").hide();
		$("#words_content").hide();
	});
	$("#recommend").click(function() {
		
		$("#guide_content").hide();		
		$("#recommend_content").show();
		$("#words_content").hide();
	});
	$("#noviciate").click(function() {
		
		$("#guide_content").hide();		
		$("#recommend_content").show();
		$("#words_content").hide();
	});
	$("#words").click(function() {
		
		$("#guide_content").hide();		
		$("#recommend_content").hide();
		$("#words_content").show();
	});
$("#integrated_service").click(function() {
	
		$("#tree").toggle();
	});
	
});