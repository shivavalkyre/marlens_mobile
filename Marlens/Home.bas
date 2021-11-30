B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Private rp As RuntimePermissions
	Private ph As Phone
	Private TimerRefresh As Timer
	Private TimerEnable As Timer
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private gmap As GoogleMap
	Private gmap_extra As GoogleMapsExtras
	Private MapFragment1 As MapFragment
	Private url_aton As String ="http://marlens.disnavpriok.id:3002/api/aton/masdex/read"
	Private url_ships As String ="http://marlens.disnavpriok.id:3002/api/ais/marlens/read"
	Private Label1 As Label
	Private Label2 As Label
	Private Label3 As Label
	Private Label4 As Label
	Private Label5 As Label
	Private ImageView1 As ImageView
	Private ShowMenuStatus As Int=1
	Private ShowFilterStatus As Int
	Private Panel1 As Panel
	Private Panel2 As Panel

	Private ImageView2 As ImageView
	Private Panel3 As Panel
	Private Button2 As Button
	Private Button1 As Button
	Private RefreshEnable As Int
	'Private MarkerOverlay1 as markerov
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("LayoutHome")
	ph.SetScreenOrientation(0)
	Panel1.Height=315dip
	Panel2.Height=60dip
	TimerRefresh.Initialize("TimerRefresh",10000)
	TimerEnable.Initialize("TimerEnable",3000)
	TimerRefresh.Enabled=False
	TimerEnable.Enabled=False
	
	Wait For MapFragment1_Ready
	gmap = MapFragment1.GetMap
	rp.CheckAndRequest(rp.PERMISSION_ACCESS_FINE_LOCATION)
	Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
	If Result Then
		gmap.MyLocationEnabled = False
		gmap_position
		get_aton
		get_ships
		TimerRefresh.Enabled=True
	Else
		Log("No permission!")
	End If
	

End Sub

Sub Activity_Resume
	If gmap.IsInitialized Then
		gmap_position
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub gmap_position

	Dim LatLng1 As LatLng
	LatLng1.Initialize(-6.132055,106.871483)
	Dim CameraPosition1 As CameraPosition
	CameraPosition1.Initialize(LatLng1.Latitude, LatLng1.Longitude, 8)
	gmap.GetUiSettings
	gmap.MoveCamera(CameraPosition1)
	
	gmap.GetUiSettings.ZoomControlsEnabled=False
End Sub

Sub get_aton
	Dim j As HttpJob
	'Dim parameter As String ="username="&txtUsername.Text&"&password=" & txtPassword.Text
	j.Initialize("j",Me)
	j.Download(url_aton)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		'Log(j.GetString)
		Dim result As String = j.GetString
		Dim parser As JSONParser
		parser.Initialize(result)
		Dim root As Map = parser.NextObject
		Dim data As List = root.Get("data")
		Dim total As Int = data.Get(0).As(Map).Get("total")
		Log(total)
		Dim rows As List = data.Get(1).As(List).Get(0).As(Map).Get("rows")
		for  each coldata As Map In rows
			'Dim id As String = coldata.Get("id")
			Dim aton_name As String = coldata.Get("aton_name")
			Dim degree1 As Double =coldata.Get("degree1")
			Dim minute1 As Double = coldata.Get("minute1")
			Dim second1 As Double = coldata.Get("second1")
			Dim degree2 As Double =coldata.Get("degree2")
			Dim minute2 As Double = coldata.Get("minute2")
			Dim second2 As Double = coldata.Get("second2")
			Dim radius As Int = coldata.Get("radius")
			
			Dim lat As Double= degree1+(minute1/60)+(second1/3600)
			Dim lon As Double= degree2+(minute2/60)+(second2/3600)
			
			Dim co As CircleOptions
			co.Initialize
			co.Center2(lat,lon).Radius(radius).FillColor(Colors.ARGB(100,0,0,255)).StrokeColor(Colors.Blue)
			
			Dim Marker1 As Marker
			Dim bmp As Bitmap
			bmp.Initialize(File.DirAssets,"aton_marker.png")
			Marker1 = gmap.AddMarker3(lat,lon,aton_name,bmp)
		
			gmap_extra.AddCircle(gmap,co)
			
		Next
		Dim success As String = root.Get("success")
		
	End If
	j.Release
End Sub

Sub get_ships
	Dim j As HttpJob
	'Dim parameter As String ="username="&txtUsername.Text&"&password=" & txtPassword.Text
	j.Initialize("j",Me)
	j.PostString(url_ships,"")
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
	'Log(j.GetString)
	Dim result As String = j.GetString
	Dim parser As JSONParser
	parser.Initialize(result)
	Dim root As Map = parser.NextObject
	Dim data As List = root.Get("data")
	Dim total As Int = data.Get(0).As(Map).Get("total")
	Log(total)
		Dim rows As List = data.Get(1).As(List).Get(0).As(Map).Get("rows")
		for  each coldata As Map In rows
			Dim lat As Double = coldata.Get("lat")
			Dim lon As Double = coldata.Get("lon")
			Dim shipname As String = coldata.Get("shipname")
			Dim heading As Int = coldata.Get("heading")
			Dim tipe_kapal As String = coldata.Get("tipe_kapal")
			Dim kategori_kapal As String = coldata.Get("kategori_kapal")
			Dim mmsi As String = coldata.Get("mmsi")
			Dim callsign As String= coldata.Get("callsign")
			Dim imo As String = coldata.Get("imo")
			
			'' Marker
			Dim Marker1 As Marker
			Dim bmp As Bitmap
			bmp.Initialize(File.DirAssets,"ship.png")
			Marker1 = gmap.AddMarker3(lat,lon,shipname,bmp)
			Dim MarkerExtras1 As MarkerExtras
			MarkerExtras1.SetRotation(Marker1,heading)

		Next
		'TimerRefresh.Enabled=True
	End If
	j.Release
End Sub


Private Sub ImageView1_Click
	If ShowMenuStatus = 0 Then
		Panel1.Height=315dip
		ShowMenuStatus=1
	Else
		Panel1.Height=60dip
		ShowMenuStatus=0
	End If
End Sub

Private Sub Label1_Click
	If ShowFilterStatus = 0 Then
		Panel2.Height=315dip
		ShowFilterStatus=1
	Else
		Panel2.Height=60dip
		ShowFilterStatus=0
	End If
End Sub

Sub TimerRefresh_tick
	Log("tick")
	gmap.Clear
	gmap_position
	TimerRefresh.Enabled=False
	TimerEnable.Enabled=True
End Sub

Sub TimerEnable_tick
	get_ships
	get_aton
	TimerRefresh.Enabled=True
	TimerEnable.Enabled=False
End Sub

Private Sub ImageView2_Click
	Panel3.Visible=True
End Sub

Private Sub Button2_Click
	TimerEnable.Enabled=True
	Panel3.Visible=False
End Sub

Private Sub Button1_Click
	TimerRefresh.Enabled=False
	TimerEnable.Enabled=False
	gmap.Clear
	gmap_position
	get_ships
	get_aton
	Panel3.Visible=False
End Sub