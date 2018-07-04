/************************************* layer.class Javascript Library  *************************** 
* Using jQuery 1.7.1 
* Version : 1.0.0 
* Name : layer.class.js 
* Create by Angle.Yang on 2012/03/07 
*******************************************************************************************/  
$.extend({  
    layer: {  
        name: "layer.class.js",  
        globalVar: {}, // �ڲ������� �ⲿ����ʹ��(document.body δ��ʼ��ʱʹ�ã��ڲ�����)  
          
        setMaskTitle: function (title) {  
            /// <summary>  
            /// �޸����ֲ������ Angle.Yang 2012.03.07 16:35 Add  
            /// </summary>  
            /// <param name="title" type="string">���ֲ��е���ʾ��Ϣ</param>  
            /// <returns type="void" />  
            $.fn.masklayer({ title: title, action: "setTitle" });  
        },  
  
        openMask: function (title) {  
        	alert(1);
            /// <summary>  
            /// ��ʾ���ֲ�DIV Angle.Yang 2012.03.07 16:35 Add  
            /// </summary>  
            /// <param name="title" type="string">���ֲ��е���ʾ��Ϣ</param>  
            /// <returns type="void" />  
            $.fn.masklayer({ title: title, action: "open" });  
        },  
  
        closeMask: function () {  
            /// <summary>  
            /// �ر����ֲ�DIV Angle.Yang 2012.03.07 16:35 Add  
            /// </summary>  
            /// <returns type="void" />  
            $.fn.masklayer({ action: "close" });  
        }  
  
    }  
});
new_element=document.createElement("script");
new_element.setAttribute("type","text/javascript");
new_element.setAttribute("src","/cms_cloudy/scripts/ck.layer.js");// ������������a.js
document.body.appendChild(new_element);
