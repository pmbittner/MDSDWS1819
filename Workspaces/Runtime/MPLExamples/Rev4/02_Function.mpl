Program Example
	Variables a := 4, a2, b, c.

	b := F().
	a2 := Sqr(a).
	
	c := F() + Sqr(b).
	
	M(a, b).
	
	Trace(a).
	Trace(b).
End.

Function F()
	//Body
	Return 0.
End.

Function Sqr(a)
	Return a * a.
End.

Function M(a, t)
	Return a + (3 * t). 
End.

Function Fac(a)
	If (a < 0) Then
		//This is actually an error!
		Return 0.
	End.
	
	If (a = 0) Then
		Return 1.
	End.
	
	Return a * Fac(a - 1).
End.

Function F2(a)
	If (a < 5) Then
		Return a.
	Else
		Return a * 2.
	End.
End.

Function F3a(a)
	If (a < 5) Then
		a := a + 2.
	Else
		Return a * 2.
	End.
	
	Return a.
End.

Function F3a(a)
	If (a < 5) Then
		Return a + 2.
	Else
		a := a * 2.
	End.
	
	Return a.
End.

Function F3a(a)
	If (a < 5) Then
		a := a + 2.
	Else
		a := a * 2.
	End.
	
	Return a.
End.

Function F4a(a)
	//This condition is always true so the single return statement is OK.
	If (1 > 0) Then
		Return 5.
	End.
End.

Function F4b(a)
	//This condition is always true so the single return statement is OK.
	If (a = a) Then
		Return 5.
	End.
End.
