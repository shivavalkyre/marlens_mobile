B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=11
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub


Sub SetBackgroundTintList(View As View,Active As Int, Enabled As Int)
	Dim States(2,1) As Int
	States(0,0) = 16842908     'Active
	States(1,0) = 16842910    'Enabled
	Dim Color(2) As Int = Array As Int(Active,Enabled)
	Dim CSL As JavaObject
	CSL.InitializeNewInstance("android.content.res.ColorStateList",Array As Object(States,Color))
	Dim jo As JavaObject
	jo.InitializeStatic("android.support.v4.view.ViewCompat")
	jo.RunMethod("setBackgroundTintList", Array(View, CSL))
End Sub

Sub EditTextUsername(EditTxt As EditText)
	'Dim AR As AndroidResources
	Dim ETxt As JavaObject = EditTxt
	ETxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array As Object(BmpToDrawable(TextToBitmap(Chr(0xF007),18)),Null,Null,Null))
End Sub

Sub EditTextEmail(EditTxt As EditText)
	'Dim AR As AndroidResources
	Dim ETxt As JavaObject = EditTxt
	ETxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array As Object(BmpToDrawable(TextToBitmap(Chr(0xF0E0),18)),Null,Null,Null))
End Sub

Sub EditTextPassword(EditTxt As EditText)
	
	Dim ETxt As JavaObject = EditTxt
	ETxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array As Object(BmpToDrawable(TextToBitmap(Chr(0xF023),22)),Null,BmpToDrawable(TextToBitmap(Chr(0xF070),18)),Null))
End Sub

Sub EditTextPassword1(EditTxt As EditText)
	
	Dim ETxt As JavaObject = EditTxt
	ETxt.RunMethod("setCompoundDrawablesWithIntrinsicBounds",Array As Object(BmpToDrawable(TextToBitmap(Chr(0xF023),22)),Null,BmpToDrawable(TextToBitmap(Chr(0xF06E),18)),Null))
End Sub

Sub TextToBitmap (s As String, FontSize As Float) As Bitmap
	Dim Bmp As Bitmap
	Bmp.InitializeMutable(28dip, 28dip)
	Dim cvs As Canvas
	cvs.Initialize2(Bmp)
	Dim h As Double = cvs.MeasureStringHeight(s, Typeface.FONTAWESOME, FontSize)
	cvs.DrawText(s, Bmp.Width / 2, Bmp.Height / 2 + h / 2, Typeface.FONTAWESOME, FontSize, Colors.Gray, "CENTER")
	Return Bmp
End Sub

Sub BmpToDrawable(bmp As Bitmap)As BitmapDrawable
	Dim bd As BitmapDrawable
	bd.Initialize(bmp)
	Return bd
End Sub

Private Sub ValidatePassword(pwd As String,minLength As Int,numUpper As Int,numLower As Int,numNumbers As Int,numSpecial As Int) As Boolean
	If pwd.Length < minLength Then Return False
	If CountMatches(pwd,"[A-Z]") < numUpper Then Return False
	If CountMatches(pwd,"[a-z]") < numLower Then Return False
	If CountMatches(pwd,"[0-9]") < numNumbers Then Return False
	If CountMatches(pwd,"[^a-zA-Z0-9]") < numSpecial Then Return False
	' Passed all checks.
	Return True
End Sub

Sub CountMatches(text As String,pattern As String) As Int
	Dim tmp_count As Int = 0
	Dim Matcher1 As Matcher
	Matcher1 = Regex.Matcher(pattern, text)
	Do While Matcher1.Find
		tmp_count = tmp_count +1
	Loop
	Return tmp_count
End Sub