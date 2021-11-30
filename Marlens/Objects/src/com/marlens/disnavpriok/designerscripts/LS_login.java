package com.marlens.disnavpriok.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("imageview1").vw.setTop((int)((10d / 100 * height)));
views.get("imageview1").vw.setLeft((int)((50d / 100 * width)-(views.get("imageview1").vw.getWidth())/2d));
views.get("panel1").vw.setTop((int)((40d / 100 * height)));
//BA.debugLineNum = 6;BA.debugLine="Panel2.Top=Panel1.Top+Panel1.Height+10dip"[Login/General script]
views.get("panel2").vw.setTop((int)((views.get("panel1").vw.getTop())+(views.get("panel1").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 7;BA.debugLine="CheckBox1.Top=Panel2.Top+Panel2.Height"[Login/General script]
views.get("checkbox1").vw.setTop((int)((views.get("panel2").vw.getTop())+(views.get("panel2").vw.getHeight())));
//BA.debugLineNum = 8;BA.debugLine="Label1.Top=CheckBox1.Top"[Login/General script]
views.get("label1").vw.setTop((int)((views.get("checkbox1").vw.getTop())));
//BA.debugLineNum = 9;BA.debugLine="Button1.Top= CheckBox1.Top+CheckBox1.Height+5dip"[Login/General script]
views.get("button1").vw.setTop((int)((views.get("checkbox1").vw.getTop())+(views.get("checkbox1").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 10;BA.debugLine="Label2.Top=Button1.Top+Button1.Height"[Login/General script]
views.get("label2").vw.setTop((int)((views.get("button1").vw.getTop())+(views.get("button1").vw.getHeight())));

}
}