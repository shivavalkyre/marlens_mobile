B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=11
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private txtUsername As EditText
	Private txtPassword As EditText
	Private ClickStatus As Int = 0
	Private Label2 As Label
	Private Button1 As Button
	Private ph As Phone
	Private url_login As String ="http://insaf.disnavpriok.id:3000/api/users/insaf/login"
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("Login")
	ph.SetScreenOrientation(1)
	general.SetBackgroundTintList(txtUsername, Colors.White,Colors.White)
	general.SetBackgroundTintList(txtPassword, Colors.White,Colors.White)
	general.EditTextUsername(txtUsername)
	general.EditTextPassword(txtPassword)
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Private Sub txtPassword_Click
	If ClickStatus=0 Then
		general.EditTextPassword1(txtPassword)
		ClickStatus=1
		'Log("1")
		txtPassword.PasswordMode=False
	Else
		general.EditTextPassword(txtPassword)
		ClickStatus=0
		txtPassword.PasswordMode=True
		'Log("0")
	End If
End Sub

Private Sub Label2_Click
	'StartActivity(Register)
End Sub

Private Sub Button1_Click
	If txtUsername.Text.Length>0 And txtPassword.Text.Length>0 Then
		Dim j As HttpJob
		Dim parameter As String ="username="&txtUsername.Text&"&password=" & txtPassword.Text
		j.Initialize("j",Me)
		j.PostString (url_login,parameter)
		Wait For (j) JobDone(j As HttpJob)
		If j.Success Then
			'Log(j.GetString)
			Dim result = j.GetString
			Dim parser As JSONParser
			parser.Initialize(result)
			Dim root As Map = parser.NextObject
			Dim level As Int = root.Get("level")
			Dim id As String = root.Get("id")
			Dim token As String = root.Get("token")
			Dim username As String = root.Get("username")
			StartActivity(Home)
			Activity.Finish
		End If
		j.Release
	End If
End Sub
