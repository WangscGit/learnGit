(function ($) {  
    $.fn.fix_ie6Select = function () {  
        /// <summary>  
        /// 兼容弹出层在 IE6 下不能掩盖 Select  
        /// </summary>  
        /// <returns type="void" />  
        return this.each(function (index) {  
            var frm = $(this).find('iframe[tag*="ie6Selector"]');  
            if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {  
                var w = $(this).width();  
                var h = $(this).height();  
                if (frm.length == 0) {  
                    $(this).prepend('<iframe tag="ie6Selector" src="" frameborder="no" marginwidth="0" marginheight="0" style="border:none;position:absolute;visibility:inherit;top:0px;left:0px;width:' + w + 'px;height:' + h + 'px;z-index:-1;"></iframe>');  
                }  
                else {  
                    frm.css("width", w);  
                    frm.css("height", h);  
                }  
            }  
        });  
    };  
})(jQuery); 