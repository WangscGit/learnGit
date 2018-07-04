/************************************* Achievo.Javascript Library ************************** 
* Using jQuery 1.7.1 
* Using cks.js 1.0.1 
* Name : ck.layer.js 
* Create by Angle.Yang on 2012/03/07 [V1.0.0] 
*******************************************************************************************/  
(function ($) {  
    $.fn.masklayer = function (settings) {  
        /// <summary>  
        /// ģ̬���ڣ��̳� easy-ui.window  
        /// </summary>  
        /// <param name="settings" type="object">��չ��{title:[div�е�����], action:[ִ�еĶ�����Ŀǰ֧��"close"], result:[���ؽ��]}</param>  
        /// <returns type="void" />  
  
        settings = $.extend(true, { title: '������...', action: "open" }, settings);  
  
  
        /// <summary>  
        /// ��ʼ������ cks ��ʽ�İ�ť��ҳ������ʱ���г�ʼ����  
        /// </summary>  
        /// <returns type="void" />  
  
        _init = function () {  
            if (settings.action == "open") {  
                if ($("#div_load").length == 0) {  
                    var boardDiv = "<div id='div_load'><\/div>";  
                    $(document.body).append(boardDiv);  
                }  
                if ($("#div_load").length > 0) {  
                    $("#div_load").fix_ie6Select();  
                    $("#div_load").css("display", "block");  
                    $("#div_load").css("height", document.body.offsetHeight);  
                    $("#div_load").html(settings.title);  
                }  
            }  
            else if (settings.action == "close") {  
                if ($("#div_load").length > 0) $("#div_load").css("display", "none");  
            }  
            else if (settings.action = "setTitle") {  
                if ($("#div_load").length > 0) $("#div_load").html(settings.title);  
                else {  
                    var boardDiv = "<div id='div_load'>" + settings.title + "<\/div>";  
                    $(document.body).append(boardDiv);  
                    $("#div_load").fix_ie6Select();  
                    $("#div_load").css("display", "block");  
                    $("#div_load").css("height", document.body.offsetHeight);  
                }  
            }  
        };  
  
  
        return (function () { _init() })();  
  
    };  
})(jQuery);  
new_element=document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","/cms_cloudy/scripts/ck.fixer.js");// ������������a.js
document.body.appendChild(new_element);