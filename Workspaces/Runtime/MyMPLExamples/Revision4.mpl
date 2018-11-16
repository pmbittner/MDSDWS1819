Function Add(a, b)
	Return a + b.
End.

Procedure doStuff() End.

Function f(n)
    If (n <= 1) Then
    	Return 1.
    End.

    Return f(n - 1) + f(n - 2).
End.

Function compare(a, b) 
	If (a < b) Then
		Return 1.
	Else
		If (a > b) Then
			Return - 1.
		Else // a = b
			Return 0.
		End.
	End.
End.

Function retTest2()
	If (1 < 2) Then
		While (1 < 2) Do
			Return 2.
		End.
	Else
		Return 2.
	End.
End.

Function retTest()
	While(1 < 2) Do
		Return 2.
	End.
End.

Program SomeName
	Variables n := 10.
	
	doStuff().
	
	SomeName().
	
	n + 3.
	
	(f(n) + 3) * 7.
	
	Trace(n).
End.