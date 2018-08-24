$(function () {
    $("#edu_type").click(function () {
        $(".edu-internet-video").hide();
        $(".edu-internet-course").show();
    })
    $("#edu_video").click(function () {
        $(".edu-internet-video").show();
        $(".edu-internet-course").hide();
    })
    
})