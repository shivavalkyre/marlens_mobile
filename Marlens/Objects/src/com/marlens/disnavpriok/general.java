package com.marlens.disnavpriok;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class general {
private static general mostCurrent = new general();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public com.marlens.disnavpriok.main _main = null;
public com.marlens.disnavpriok.starter _starter = null;
public com.marlens.disnavpriok.home _home = null;
public com.marlens.disnavpriok.login _login = null;
public com.marlens.disnavpriok.httputils2service _httputils2service = null;
public static anywheresoftware.b4a.objects.drawable.BitmapDrawable  _bmptodrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd = null;
 //BA.debugLineNum = 56;BA.debugLine="Sub BmpToDrawable(bmp As Bitmap)As BitmapDrawable";
 //BA.debugLineNum = 57;BA.debugLine="Dim bd As BitmapDrawable";
_bd = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 58;BA.debugLine="bd.Initialize(bmp)";
_bd.Initialize((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 59;BA.debugLine="Return bd";
if (true) return _bd;
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return null;
}
public static int  _countmatches(anywheresoftware.b4a.BA _ba,String _text,String _pattern) throws Exception{
int _tmp_count = 0;
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matcher1 = null;
 //BA.debugLineNum = 72;BA.debugLine="Sub CountMatches(text As String,pattern As String)";
 //BA.debugLineNum = 73;BA.debugLine="Dim tmp_count As Int = 0";
_tmp_count = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="Dim Matcher1 As Matcher";
_matcher1 = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Matcher1 = Regex.Matcher(pattern, text)";
_matcher1 = anywheresoftware.b4a.keywords.Common.Regex.Matcher(_pattern,_text);
 //BA.debugLineNum = 76;BA.debugLine="Do While Matcher1.Find";
while (_matcher1.Find()) {
 //BA.debugLineNum = 77;BA.debugLine="tmp_count = tmp_count +1";
_tmp_count = (int) (_tmp_count+1);
 }
;
 //BA.debugLineNum = 79;BA.debugLine="Return tmp_count";
if (true) return _tmp_count;
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return 0;
}
public static String  _edittextemail(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.EditTextWrapper _edittxt) throws Exception{
anywheresoftware.b4j.object.JavaObject _etxt = null;
 //BA.debugLineNum = 28;BA.debugLine="Sub EditTextEmail(EditTxt As EditText)";
 //BA.debugLineNum = 30;BA.debugLine="Dim ETxt As JavaObject = EditTxt";
_etxt = new anywheresoftware.b4j.object.JavaObject();
_etxt = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_edittxt.getObject()));
 //BA.debugLineNum = 31;BA.debugLine="ETxt.RunMethod(\"setCompoundDrawablesWithIntrinsic";
_etxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf0e0))),(float) (18))).getObject()),anywheresoftware.b4a.keywords.Common.Null,anywheresoftware.b4a.keywords.Common.Null,anywheresoftware.b4a.keywords.Common.Null});
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static String  _edittextpassword(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.EditTextWrapper _edittxt) throws Exception{
anywheresoftware.b4j.object.JavaObject _etxt = null;
 //BA.debugLineNum = 34;BA.debugLine="Sub EditTextPassword(EditTxt As EditText)";
 //BA.debugLineNum = 36;BA.debugLine="Dim ETxt As JavaObject = EditTxt";
_etxt = new anywheresoftware.b4j.object.JavaObject();
_etxt = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_edittxt.getObject()));
 //BA.debugLineNum = 37;BA.debugLine="ETxt.RunMethod(\"setCompoundDrawablesWithIntrinsic";
_etxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf023))),(float) (22))).getObject()),anywheresoftware.b4a.keywords.Common.Null,(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf070))),(float) (18))).getObject()),anywheresoftware.b4a.keywords.Common.Null});
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _edittextpassword1(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.EditTextWrapper _edittxt) throws Exception{
anywheresoftware.b4j.object.JavaObject _etxt = null;
 //BA.debugLineNum = 40;BA.debugLine="Sub EditTextPassword1(EditTxt As EditText)";
 //BA.debugLineNum = 42;BA.debugLine="Dim ETxt As JavaObject = EditTxt";
_etxt = new anywheresoftware.b4j.object.JavaObject();
_etxt = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_edittxt.getObject()));
 //BA.debugLineNum = 43;BA.debugLine="ETxt.RunMethod(\"setCompoundDrawablesWithIntrinsic";
_etxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf023))),(float) (22))).getObject()),anywheresoftware.b4a.keywords.Common.Null,(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf06e))),(float) (18))).getObject()),anywheresoftware.b4a.keywords.Common.Null});
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _edittextusername(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.EditTextWrapper _edittxt) throws Exception{
anywheresoftware.b4j.object.JavaObject _etxt = null;
 //BA.debugLineNum = 22;BA.debugLine="Sub EditTextUsername(EditTxt As EditText)";
 //BA.debugLineNum = 24;BA.debugLine="Dim ETxt As JavaObject = EditTxt";
_etxt = new anywheresoftware.b4j.object.JavaObject();
_etxt = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_edittxt.getObject()));
 //BA.debugLineNum = 25;BA.debugLine="ETxt.RunMethod(\"setCompoundDrawablesWithIntrinsic";
_etxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",new Object[]{(Object)(_bmptodrawable(_ba,_texttobitmap(_ba,BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf007))),(float) (18))).getObject()),anywheresoftware.b4a.keywords.Common.Null,anywheresoftware.b4a.keywords.Common.Null,anywheresoftware.b4a.keywords.Common.Null});
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _setbackgroundtintlist(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _active,int _enabled) throws Exception{
int[][] _states = null;
int[] _color = null;
anywheresoftware.b4j.object.JavaObject _csl = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 10;BA.debugLine="Sub SetBackgroundTintList(View As View,Active As I";
 //BA.debugLineNum = 11;BA.debugLine="Dim States(2,1) As Int";
_states = new int[(int) (2)][];
{
int d0 = _states.length;
int d1 = (int) (1);
for (int i0 = 0;i0 < d0;i0++) {
_states[i0] = new int[d1];
}
}
;
 //BA.debugLineNum = 12;BA.debugLine="States(0,0) = 16842908     'Active";
_states[(int) (0)][(int) (0)] = (int) (16842908);
 //BA.debugLineNum = 13;BA.debugLine="States(1,0) = 16842910    'Enabled";
_states[(int) (1)][(int) (0)] = (int) (16842910);
 //BA.debugLineNum = 14;BA.debugLine="Dim Color(2) As Int = Array As Int(Active,Enabled";
_color = new int[]{_active,_enabled};
 //BA.debugLineNum = 15;BA.debugLine="Dim CSL As JavaObject";
_csl = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 16;BA.debugLine="CSL.InitializeNewInstance(\"android.content.res.Co";
_csl.InitializeNewInstance("android.content.res.ColorStateList",new Object[]{(Object)(_states),(Object)(_color)});
 //BA.debugLineNum = 17;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 18;BA.debugLine="jo.InitializeStatic(\"android.support.v4.view.View";
_jo.InitializeStatic("androidx.core.view.ViewCompat");
 //BA.debugLineNum = 19;BA.debugLine="jo.RunMethod(\"setBackgroundTintList\", Array(View,";
_jo.RunMethod("setBackgroundTintList",new Object[]{(Object)(_view.getObject()),(Object)(_csl.getObject())});
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _texttobitmap(anywheresoftware.b4a.BA _ba,String _s,float _fontsize) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
double _h = 0;
 //BA.debugLineNum = 46;BA.debugLine="Sub TextToBitmap (s As String, FontSize As Float)";
 //BA.debugLineNum = 47;BA.debugLine="Dim Bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Bmp.InitializeMutable(28dip, 28dip)";
_bmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (28)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (28)));
 //BA.debugLineNum = 49;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 50;BA.debugLine="cvs.Initialize2(Bmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 51;BA.debugLine="Dim h As Double = cvs.MeasureStringHeight(s, Type";
_h = _cvs.MeasureStringHeight(_s,anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME(),_fontsize);
 //BA.debugLineNum = 52;BA.debugLine="cvs.DrawText(s, Bmp.Width / 2, Bmp.Height / 2 + h";
_cvs.DrawText(_ba,_s,(float) (_bmp.getWidth()/(double)2),(float) (_bmp.getHeight()/(double)2+_h/(double)2),anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME(),_fontsize,anywheresoftware.b4a.keywords.Common.Colors.Gray,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 53;BA.debugLine="Return Bmp";
if (true) return _bmp;
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return null;
}
public static boolean  _validatepassword(anywheresoftware.b4a.BA _ba,String _pwd,int _minlength,int _numupper,int _numlower,int _numnumbers,int _numspecial) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Private Sub ValidatePassword(pwd As String,minLeng";
 //BA.debugLineNum = 63;BA.debugLine="If pwd.Length < minLength Then Return False";
if (_pwd.length()<_minlength) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 64;BA.debugLine="If CountMatches(pwd,\"[A-Z]\") < numUpper Then Retu";
if (_countmatches(_ba,_pwd,"[A-Z]")<_numupper) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 65;BA.debugLine="If CountMatches(pwd,\"[a-z]\") < numLower Then Retu";
if (_countmatches(_ba,_pwd,"[a-z]")<_numlower) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 66;BA.debugLine="If CountMatches(pwd,\"[0-9]\") < numNumbers Then Re";
if (_countmatches(_ba,_pwd,"[0-9]")<_numnumbers) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 67;BA.debugLine="If CountMatches(pwd,\"[^a-zA-Z0-9]\") < numSpecial";
if (_countmatches(_ba,_pwd,"[^a-zA-Z0-9]")<_numspecial) { 
if (true) return anywheresoftware.b4a.keywords.Common.False;};
 //BA.debugLineNum = 69;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return false;
}
}
