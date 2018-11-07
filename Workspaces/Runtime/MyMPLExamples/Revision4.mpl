Function Add(a, b)
	Return a + b.
End.

Procedure SoFarProceduresAreUseless()
	Return.
	Return 1.
End.

Function f(n)
    If (n <= 1) Then
    	Return 1.
    End.
    
    Return f(n - 1) + f(n - 2).
    Return.
End.

Program someName
	Variables n := 10.
	
	f(n).
	
	Trace(n).
End.

Procedure underground(Filth, coming, out, to, play)
	Chop().
	Chop().
	Cut(em(), up()).
	Return Lethal(DNA()).
End.