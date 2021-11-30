package com.marlens.disnavpriok;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class home extends Activity implements B4AActivity{
	public static home mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.marlens.disnavpriok", "com.marlens.disnavpriok.home");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (home).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.marlens.disnavpriok", "com.marlens.disnavpriok.home");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.marlens.disnavpriok.home", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (home) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (home) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return home.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (home) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (home) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            home mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (home) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static anywheresoftware.b4a.phone.Phone _ph = null;
public static anywheresoftware.b4a.objects.Timer _timerrefresh = null;
public static anywheresoftware.b4a.objects.Timer _timerenable = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper _gmap = null;
public uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras _gmap_extra = null;
public anywheresoftware.b4a.objects.MapFragmentWrapper _mapfragment1 = null;
public static String _url_aton = "";
public static String _url_ships = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public static int _showmenustatus = 0;
public static int _showfilterstatus = 0;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public static int _refreshenable = 0;
public com.marlens.disnavpriok.main _main = null;
public com.marlens.disnavpriok.starter _starter = null;
public com.marlens.disnavpriok.login _login = null;
public com.marlens.disnavpriok.general _general = null;
public com.marlens.disnavpriok.httputils2service _httputils2service = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(com.marlens.disnavpriok.home parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
com.marlens.disnavpriok.home parent;
boolean _firsttime;
String _permission = "";
boolean _result = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 43;BA.debugLine="Activity.LoadLayout(\"LayoutHome\")";
parent.mostCurrent._activity.LoadLayout("LayoutHome",mostCurrent.activityBA);
 //BA.debugLineNum = 44;BA.debugLine="ph.SetScreenOrientation(0)";
parent._ph.SetScreenOrientation(processBA,(int) (0));
 //BA.debugLineNum = 45;BA.debugLine="Panel1.Height=315dip";
parent.mostCurrent._panel1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (315)));
 //BA.debugLineNum = 46;BA.debugLine="Panel2.Height=60dip";
parent.mostCurrent._panel2.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 47;BA.debugLine="TimerRefresh.Initialize(\"TimerRefresh\",10000)";
parent._timerrefresh.Initialize(processBA,"TimerRefresh",(long) (10000));
 //BA.debugLineNum = 48;BA.debugLine="TimerEnable.Initialize(\"TimerEnable\",3000)";
parent._timerenable.Initialize(processBA,"TimerEnable",(long) (3000));
 //BA.debugLineNum = 49;BA.debugLine="TimerRefresh.Enabled=False";
parent._timerrefresh.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 50;BA.debugLine="TimerEnable.Enabled=False";
parent._timerenable.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 52;BA.debugLine="Wait For MapFragment1_Ready";
anywheresoftware.b4a.keywords.Common.WaitFor("mapfragment1_ready", processBA, this, null);
this.state = 7;
return;
case 7:
//C
this.state = 1;
;
 //BA.debugLineNum = 53;BA.debugLine="gmap = MapFragment1.GetMap";
parent.mostCurrent._gmap = parent.mostCurrent._mapfragment1.GetMap();
 //BA.debugLineNum = 54;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOCA";
parent._rp.CheckAndRequest(processBA,parent._rp.PERMISSION_ACCESS_FINE_LOCATION);
 //BA.debugLineNum = 55;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 8;
return;
case 8:
//C
this.state = 1;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 56;BA.debugLine="If Result Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_result) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 57;BA.debugLine="gmap.MyLocationEnabled = False";
parent.mostCurrent._gmap.setMyLocationEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 58;BA.debugLine="gmap_position";
_gmap_position();
 //BA.debugLineNum = 59;BA.debugLine="get_aton";
_get_aton();
 //BA.debugLineNum = 60;BA.debugLine="get_ships";
_get_ships();
 //BA.debugLineNum = 61;BA.debugLine="TimerRefresh.Enabled=True";
parent._timerrefresh.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 63;BA.debugLine="Log(\"No permission!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("5917526","No permission!",0);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _mapfragment1_ready() throws Exception{
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 70;BA.debugLine="If gmap.IsInitialized Then";
if (mostCurrent._gmap.IsInitialized()) { 
 //BA.debugLineNum = 71;BA.debugLine="gmap_position";
_gmap_position();
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Private Sub Button1_Click";
 //BA.debugLineNum = 226;BA.debugLine="TimerRefresh.Enabled=False";
_timerrefresh.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 227;BA.debugLine="TimerEnable.Enabled=False";
_timerenable.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 228;BA.debugLine="gmap.Clear";
mostCurrent._gmap.Clear();
 //BA.debugLineNum = 229;BA.debugLine="gmap_position";
_gmap_position();
 //BA.debugLineNum = 230;BA.debugLine="get_ships";
_get_ships();
 //BA.debugLineNum = 231;BA.debugLine="get_aton";
_get_aton();
 //BA.debugLineNum = 232;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Private Sub Button2_Click";
 //BA.debugLineNum = 221;BA.debugLine="TimerEnable.Enabled=True";
_timerenable.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 222;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static void  _get_aton() throws Exception{
ResumableSub_get_aton rsub = new ResumableSub_get_aton(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_get_aton extends BA.ResumableSub {
public ResumableSub_get_aton(com.marlens.disnavpriok.home parent) {
this.parent = parent;
}
com.marlens.disnavpriok.home parent;
com.marlens.disnavpriok.httpjob _j = null;
String _result = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.objects.collections.List _data = null;
int _total = 0;
anywheresoftware.b4a.objects.collections.List _rows = null;
anywheresoftware.b4a.objects.collections.Map _coldata = null;
String _aton_name = "";
double _degree1 = 0;
double _minute1 = 0;
double _second1 = 0;
double _degree2 = 0;
double _minute2 = 0;
double _second2 = 0;
int _radius = 0;
double _lat = 0;
double _lon = 0;
uk.co.martinpearman.b4a.com.google.android.gms.maps.model.CircleOptions _co = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
String _success = "";
anywheresoftware.b4a.BA.IterableList group14;
int index14;
int groupLen14;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 93;BA.debugLine="Dim j As HttpJob";
_j = new com.marlens.disnavpriok.httpjob();
 //BA.debugLineNum = 95;BA.debugLine="j.Initialize(\"j\",Me)";
_j._initialize /*String*/ (processBA,"j",home.getObject());
 //BA.debugLineNum = 96;BA.debugLine="j.Download(url_aton)";
_j._download /*String*/ (parent.mostCurrent._url_aton);
 //BA.debugLineNum = 97;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 9;
return;
case 9:
//C
this.state = 1;
_j = (com.marlens.disnavpriok.httpjob) result[0];
;
 //BA.debugLineNum = 98;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 100;BA.debugLine="Dim result As String = j.GetString";
_result = _j._getstring /*String*/ ();
 //BA.debugLineNum = 101;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 102;BA.debugLine="parser.Initialize(result)";
_parser.Initialize(_result);
 //BA.debugLineNum = 103;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 104;BA.debugLine="Dim data As List = root.Get(\"data\")";
_data = new anywheresoftware.b4a.objects.collections.List();
_data = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_root.Get((Object)("data"))));
 //BA.debugLineNum = 105;BA.debugLine="Dim total As Int = data.Get(0).As(Map).Get(\"tota";
_total = (int)(BA.ObjectToNumber(((anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_data.Get((int) (0))))).Get((Object)("total"))));
 //BA.debugLineNum = 106;BA.debugLine="Log(total)";
anywheresoftware.b4a.keywords.Common.LogImpl("54915214",BA.NumberToString(_total),0);
 //BA.debugLineNum = 107;BA.debugLine="Dim rows As List = data.Get(1).As(List).Get(0).A";
_rows = new anywheresoftware.b4a.objects.collections.List();
_rows = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(((anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_data.Get((int) (1))))).Get((int) (0))))).Get((Object)("rows"))));
 //BA.debugLineNum = 108;BA.debugLine="for  each coldata As Map In rows";
if (true) break;

case 4:
//for
this.state = 7;
_coldata = new anywheresoftware.b4a.objects.collections.Map();
group14 = _rows;
index14 = 0;
groupLen14 = group14.getSize();
this.state = 10;
if (true) break;

case 10:
//C
this.state = 7;
if (index14 < groupLen14) {
this.state = 6;
_coldata = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group14.Get(index14)));}
if (true) break;

case 11:
//C
this.state = 10;
index14++;
if (true) break;

case 6:
//C
this.state = 11;
 //BA.debugLineNum = 110;BA.debugLine="Dim aton_name As String = coldata.Get(\"aton_nam";
_aton_name = BA.ObjectToString(_coldata.Get((Object)("aton_name")));
 //BA.debugLineNum = 111;BA.debugLine="Dim degree1 As Double =coldata.Get(\"degree1\")";
_degree1 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("degree1"))));
 //BA.debugLineNum = 112;BA.debugLine="Dim minute1 As Double = coldata.Get(\"minute1\")";
_minute1 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("minute1"))));
 //BA.debugLineNum = 113;BA.debugLine="Dim second1 As Double = coldata.Get(\"second1\")";
_second1 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("second1"))));
 //BA.debugLineNum = 114;BA.debugLine="Dim degree2 As Double =coldata.Get(\"degree2\")";
_degree2 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("degree2"))));
 //BA.debugLineNum = 115;BA.debugLine="Dim minute2 As Double = coldata.Get(\"minute2\")";
_minute2 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("minute2"))));
 //BA.debugLineNum = 116;BA.debugLine="Dim second2 As Double = coldata.Get(\"second2\")";
_second2 = (double)(BA.ObjectToNumber(_coldata.Get((Object)("second2"))));
 //BA.debugLineNum = 117;BA.debugLine="Dim radius As Int = coldata.Get(\"radius\")";
_radius = (int)(BA.ObjectToNumber(_coldata.Get((Object)("radius"))));
 //BA.debugLineNum = 119;BA.debugLine="Dim lat As Double= degree1+(minute1/60)+(second";
_lat = _degree1+(_minute1/(double)60)+(_second1/(double)3600);
 //BA.debugLineNum = 120;BA.debugLine="Dim lon As Double= degree2+(minute2/60)+(second";
_lon = _degree2+(_minute2/(double)60)+(_second2/(double)3600);
 //BA.debugLineNum = 122;BA.debugLine="Dim co As CircleOptions";
_co = new uk.co.martinpearman.b4a.com.google.android.gms.maps.model.CircleOptions();
 //BA.debugLineNum = 123;BA.debugLine="co.Initialize";
_co.Initialize();
 //BA.debugLineNum = 124;BA.debugLine="co.Center2(lat,lon).Radius(radius).FillColor(Co";
_co.Center2(_lat,_lon).Radius(_radius).FillColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (0),(int) (0),(int) (255))).StrokeColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 126;BA.debugLine="Dim Marker1 As Marker";
_marker1 = new anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper();
 //BA.debugLineNum = 127;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 128;BA.debugLine="bmp.Initialize(File.DirAssets,\"aton_marker.png\"";
_bmp.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"aton_marker.png");
 //BA.debugLineNum = 129;BA.debugLine="Marker1 = gmap.AddMarker3(lat,lon,aton_name,bmp";
_marker1 = parent.mostCurrent._gmap.AddMarker3(_lat,_lon,_aton_name,(android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="gmap_extra.AddCircle(gmap,co)";
parent.mostCurrent._gmap_extra.AddCircle((com.google.android.gms.maps.GoogleMap)(parent.mostCurrent._gmap.getObject()),(com.google.android.gms.maps.model.CircleOptions)(_co.getObject()));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 133;BA.debugLine="Dim success As String = root.Get(\"success\")";
_success = BA.ObjectToString(_root.Get((Object)("success")));
 if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 136;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(com.marlens.disnavpriok.httpjob _j) throws Exception{
}
public static void  _get_ships() throws Exception{
ResumableSub_get_ships rsub = new ResumableSub_get_ships(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_get_ships extends BA.ResumableSub {
public ResumableSub_get_ships(com.marlens.disnavpriok.home parent) {
this.parent = parent;
}
com.marlens.disnavpriok.home parent;
com.marlens.disnavpriok.httpjob _j = null;
String _result = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.objects.collections.List _data = null;
int _total = 0;
anywheresoftware.b4a.objects.collections.List _rows = null;
anywheresoftware.b4a.objects.collections.Map _coldata = null;
double _lat = 0;
double _lon = 0;
String _shipname = "";
int _heading = 0;
String _tipe_kapal = "";
String _kategori_kapal = "";
String _mmsi = "";
String _callsign = "";
String _imo = "";
anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper _marker1 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
uk.co.martinpearman.b4a.googlemapsextras.MarkerExtras _markerextras1 = null;
anywheresoftware.b4a.BA.IterableList group14;
int index14;
int groupLen14;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 140;BA.debugLine="Dim j As HttpJob";
_j = new com.marlens.disnavpriok.httpjob();
 //BA.debugLineNum = 142;BA.debugLine="j.Initialize(\"j\",Me)";
_j._initialize /*String*/ (processBA,"j",home.getObject());
 //BA.debugLineNum = 143;BA.debugLine="j.PostString(url_ships,\"\")";
_j._poststring /*String*/ (parent.mostCurrent._url_ships,"");
 //BA.debugLineNum = 144;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 9;
return;
case 9:
//C
this.state = 1;
_j = (com.marlens.disnavpriok.httpjob) result[0];
;
 //BA.debugLineNum = 145;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 8;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 147;BA.debugLine="Dim result As String = j.GetString";
_result = _j._getstring /*String*/ ();
 //BA.debugLineNum = 148;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 149;BA.debugLine="parser.Initialize(result)";
_parser.Initialize(_result);
 //BA.debugLineNum = 150;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 151;BA.debugLine="Dim data As List = root.Get(\"data\")";
_data = new anywheresoftware.b4a.objects.collections.List();
_data = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_root.Get((Object)("data"))));
 //BA.debugLineNum = 152;BA.debugLine="Dim total As Int = data.Get(0).As(Map).Get(\"total";
_total = (int)(BA.ObjectToNumber(((anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_data.Get((int) (0))))).Get((Object)("total"))));
 //BA.debugLineNum = 153;BA.debugLine="Log(total)";
anywheresoftware.b4a.keywords.Common.LogImpl("55767182",BA.NumberToString(_total),0);
 //BA.debugLineNum = 154;BA.debugLine="Dim rows As List = data.Get(1).As(List).Get(0).A";
_rows = new anywheresoftware.b4a.objects.collections.List();
_rows = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(((anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_data.Get((int) (1))))).Get((int) (0))))).Get((Object)("rows"))));
 //BA.debugLineNum = 155;BA.debugLine="for  each coldata As Map In rows";
if (true) break;

case 4:
//for
this.state = 7;
_coldata = new anywheresoftware.b4a.objects.collections.Map();
group14 = _rows;
index14 = 0;
groupLen14 = group14.getSize();
this.state = 10;
if (true) break;

case 10:
//C
this.state = 7;
if (index14 < groupLen14) {
this.state = 6;
_coldata = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group14.Get(index14)));}
if (true) break;

case 11:
//C
this.state = 10;
index14++;
if (true) break;

case 6:
//C
this.state = 11;
 //BA.debugLineNum = 156;BA.debugLine="Dim lat As Double = coldata.Get(\"lat\")";
_lat = (double)(BA.ObjectToNumber(_coldata.Get((Object)("lat"))));
 //BA.debugLineNum = 157;BA.debugLine="Dim lon As Double = coldata.Get(\"lon\")";
_lon = (double)(BA.ObjectToNumber(_coldata.Get((Object)("lon"))));
 //BA.debugLineNum = 158;BA.debugLine="Dim shipname As String = coldata.Get(\"shipname\"";
_shipname = BA.ObjectToString(_coldata.Get((Object)("shipname")));
 //BA.debugLineNum = 159;BA.debugLine="Dim heading As Int = coldata.Get(\"heading\")";
_heading = (int)(BA.ObjectToNumber(_coldata.Get((Object)("heading"))));
 //BA.debugLineNum = 160;BA.debugLine="Dim tipe_kapal As String = coldata.Get(\"tipe_ka";
_tipe_kapal = BA.ObjectToString(_coldata.Get((Object)("tipe_kapal")));
 //BA.debugLineNum = 161;BA.debugLine="Dim kategori_kapal As String = coldata.Get(\"kat";
_kategori_kapal = BA.ObjectToString(_coldata.Get((Object)("kategori_kapal")));
 //BA.debugLineNum = 162;BA.debugLine="Dim mmsi As String = coldata.Get(\"mmsi\")";
_mmsi = BA.ObjectToString(_coldata.Get((Object)("mmsi")));
 //BA.debugLineNum = 163;BA.debugLine="Dim callsign As String= coldata.Get(\"callsign\")";
_callsign = BA.ObjectToString(_coldata.Get((Object)("callsign")));
 //BA.debugLineNum = 164;BA.debugLine="Dim imo As String = coldata.Get(\"imo\")";
_imo = BA.ObjectToString(_coldata.Get((Object)("imo")));
 //BA.debugLineNum = 167;BA.debugLine="Dim Marker1 As Marker";
_marker1 = new anywheresoftware.b4a.objects.MapFragmentWrapper.MarkerWrapper();
 //BA.debugLineNum = 168;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 169;BA.debugLine="bmp.Initialize(File.DirAssets,\"ship.png\")";
_bmp.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ship.png");
 //BA.debugLineNum = 170;BA.debugLine="Marker1 = gmap.AddMarker3(lat,lon,shipname,bmp)";
_marker1 = parent.mostCurrent._gmap.AddMarker3(_lat,_lon,_shipname,(android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 171;BA.debugLine="Dim MarkerExtras1 As MarkerExtras";
_markerextras1 = new uk.co.martinpearman.b4a.googlemapsextras.MarkerExtras();
 //BA.debugLineNum = 172;BA.debugLine="MarkerExtras1.SetRotation(Marker1,heading)";
_markerextras1.SetRotation((com.google.android.gms.maps.model.Marker)(_marker1.getObject()),(float) (_heading));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 177;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private gmap As GoogleMap";
mostCurrent._gmap = new anywheresoftware.b4a.objects.MapFragmentWrapper.GoogleMapWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private gmap_extra As GoogleMapsExtras";
mostCurrent._gmap_extra = new uk.co.martinpearman.b4a.googlemapsextras.GoogleMapsExtras();
 //BA.debugLineNum = 20;BA.debugLine="Private MapFragment1 As MapFragment";
mostCurrent._mapfragment1 = new anywheresoftware.b4a.objects.MapFragmentWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private url_aton As String =\"http://marlens.disna";
mostCurrent._url_aton = "http://marlens.disnavpriok.id:3002/api/aton/masdex/read";
 //BA.debugLineNum = 22;BA.debugLine="Private url_ships As String =\"http://marlens.disn";
mostCurrent._url_ships = "http://marlens.disnavpriok.id:3002/api/ais/marlens/read";
 //BA.debugLineNum = 23;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private ShowMenuStatus As Int=1";
_showmenustatus = (int) (1);
 //BA.debugLineNum = 30;BA.debugLine="Private ShowFilterStatus As Int";
_showfilterstatus = 0;
 //BA.debugLineNum = 31;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private RefreshEnable As Int";
_refreshenable = 0;
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _gmap_position() throws Exception{
anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper _latlng1 = null;
anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper _cameraposition1 = null;
 //BA.debugLineNum = 80;BA.debugLine="Sub gmap_position";
 //BA.debugLineNum = 82;BA.debugLine="Dim LatLng1 As LatLng";
_latlng1 = new anywheresoftware.b4a.objects.MapFragmentWrapper.LatLngWrapper();
 //BA.debugLineNum = 83;BA.debugLine="LatLng1.Initialize(-6.132055,106.871483)";
_latlng1.Initialize(-6.132055,106.871483);
 //BA.debugLineNum = 84;BA.debugLine="Dim CameraPosition1 As CameraPosition";
_cameraposition1 = new anywheresoftware.b4a.objects.MapFragmentWrapper.CameraPositionWrapper();
 //BA.debugLineNum = 85;BA.debugLine="CameraPosition1.Initialize(LatLng1.Latitude, LatL";
_cameraposition1.Initialize(_latlng1.getLatitude(),_latlng1.getLongitude(),(float) (8));
 //BA.debugLineNum = 86;BA.debugLine="gmap.GetUiSettings";
mostCurrent._gmap.GetUiSettings();
 //BA.debugLineNum = 87;BA.debugLine="gmap.MoveCamera(CameraPosition1)";
mostCurrent._gmap.MoveCamera((com.google.android.gms.maps.model.CameraPosition)(_cameraposition1.getObject()));
 //BA.debugLineNum = 89;BA.debugLine="gmap.GetUiSettings.ZoomControlsEnabled=False";
mostCurrent._gmap.GetUiSettings().setZoomControlsEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_click() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Private Sub ImageView1_Click";
 //BA.debugLineNum = 182;BA.debugLine="If ShowMenuStatus = 0 Then";
if (_showmenustatus==0) { 
 //BA.debugLineNum = 183;BA.debugLine="Panel1.Height=315dip";
mostCurrent._panel1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (315)));
 //BA.debugLineNum = 184;BA.debugLine="ShowMenuStatus=1";
_showmenustatus = (int) (1);
 }else {
 //BA.debugLineNum = 186;BA.debugLine="Panel1.Height=60dip";
mostCurrent._panel1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 187;BA.debugLine="ShowMenuStatus=0";
_showmenustatus = (int) (0);
 };
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _imageview2_click() throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Private Sub ImageView2_Click";
 //BA.debugLineNum = 217;BA.debugLine="Panel3.Visible=True";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _label1_click() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Private Sub Label1_Click";
 //BA.debugLineNum = 192;BA.debugLine="If ShowFilterStatus = 0 Then";
if (_showfilterstatus==0) { 
 //BA.debugLineNum = 193;BA.debugLine="Panel2.Height=315dip";
mostCurrent._panel2.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (315)));
 //BA.debugLineNum = 194;BA.debugLine="ShowFilterStatus=1";
_showfilterstatus = (int) (1);
 }else {
 //BA.debugLineNum = 196;BA.debugLine="Panel2.Height=60dip";
mostCurrent._panel2.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 197;BA.debugLine="ShowFilterStatus=0";
_showfilterstatus = (int) (0);
 };
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 10;BA.debugLine="Private ph As Phone";
_ph = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 11;BA.debugLine="Private TimerRefresh As Timer";
_timerrefresh = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="Private TimerEnable As Timer";
_timerenable = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _timerenable_tick() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub TimerEnable_tick";
 //BA.debugLineNum = 210;BA.debugLine="get_ships";
_get_ships();
 //BA.debugLineNum = 211;BA.debugLine="get_aton";
_get_aton();
 //BA.debugLineNum = 212;BA.debugLine="TimerRefresh.Enabled=True";
_timerrefresh.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 213;BA.debugLine="TimerEnable.Enabled=False";
_timerenable.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _timerrefresh_tick() throws Exception{
 //BA.debugLineNum = 201;BA.debugLine="Sub TimerRefresh_tick";
 //BA.debugLineNum = 202;BA.debugLine="Log(\"tick\")";
anywheresoftware.b4a.keywords.Common.LogImpl("57667713","tick",0);
 //BA.debugLineNum = 203;BA.debugLine="gmap.Clear";
mostCurrent._gmap.Clear();
 //BA.debugLineNum = 204;BA.debugLine="gmap_position";
_gmap_position();
 //BA.debugLineNum = 205;BA.debugLine="TimerRefresh.Enabled=False";
_timerrefresh.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 206;BA.debugLine="TimerEnable.Enabled=True";
_timerenable.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
}
