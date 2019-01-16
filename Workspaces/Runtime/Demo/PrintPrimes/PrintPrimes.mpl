Function mod(x, m)
    While (x >= m) Do
    	x := x - m.
    End.
    
    Return x.
End.

Function isPrime(n)
    Variables i, true := 1, false := 0.
    
    If (n <= 1) Then
    	Return false.
    End.
    
    For i := 2 To n - 1 Do
        If (mod(n, i) = 0) Then
            Return false.
        End.
    End.
    
    Return true.
End.

Program PrintPrimes
	Variables false := 0, n := 1000, p.
	
	For p := 0 To n Do
		If (isPrime(p) <> false) Then
			Trace(p).
		End.
	End.
End.