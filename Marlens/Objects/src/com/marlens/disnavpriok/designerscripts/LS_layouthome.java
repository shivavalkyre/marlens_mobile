package com.marlens.disnavpriok.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layouthome{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 5;BA.debugLine="Panel2.Left=100%x-Panel2.Width-10dip"[LayoutHome/General script]
views.get("panel2").vw.setLeft((int)((100d / 100 * width)-(views.get("panel2").vw.getWidth())-(10d * scale)));
//BA.debugLineNum = 7;BA.debugLine="Panel3.Left=50%x-Panel3.Width/2"[LayoutHome/General script]
views.get("panel3").vw.setLeft((int)((50d / 100 * width)-(views.get("panel3").vw.getWidth())/2d));
//BA.debugLineNum = 8;BA.debugLine="Panel3.top = 20%y"[LayoutHome/General script]
views.get("panel3").vw.setTop((int)((20d / 100 * height)));

}
}