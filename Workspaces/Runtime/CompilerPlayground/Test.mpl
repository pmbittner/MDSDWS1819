Program someName
	Variables peter, pan, hook.
	
	3 + Betrag(2).
	
	For peter := 0 To 5 Do
		Trace(peter).
	End.
	
	hook := Betrag(- peter).
	Trace(hook).
	traceAsSteve(hook).
End.

Function Betrag(n)
	If (n >= 0) Then
		Return n.
	Else
		Return - n.
	End.
End.

Procedure traceAsSteve(peter)
	Variables Steve := peter.
	Trace(Steve).
End.