/**
 * IE9及以下的输入框提示
 * Ver 0.1
 * Created by smt on 2017/2/18.
 */
(function ($) {
    $.fn.promptInput = function (prompt,fontColor) {
        var $this = $(this); //当前传入文本框
        prompt = prompt ? prompt : $this.attr("placeholder") || ""; //在输入框中显示的提示语
        fontColor = fontColor ? fontColor : '#999'; //提示语的颜色
        $this.preColor = $this.css("color");

        if($this.val()==""){
            $this.val(prompt);
            $this.css("color",fontColor);
        }

        $this.focusin(function(){
            if (prompt == $this.val()) {
                $this.val("");
                $this.css("color",$this.preColor);
            }
        });
        $this.focusout(function(){
            if ($this.val() == "") {
                $this.val(prompt);
                $this.css("color",fontColor);
            }
        });
    };
})(jQuery);
//找出所有拥有placeholder属性的input，并初始化
$(function () {
    $('input[placeholder]').each(function (index, element) {
        $(element).promptInput();
    });
});
