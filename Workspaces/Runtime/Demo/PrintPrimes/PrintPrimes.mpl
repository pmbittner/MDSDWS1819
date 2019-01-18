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
	Variables n := 1000, foundPrimes, i, true := 1.
	
	foundPrimes := 0.
	i := 2.
	
	While (foundPrimes < n) Do
		If (isPrime(i) = true) Then
			Trace(i).
			foundPrimes := foundPrimes + 1.
		End.
		
		i := i + 1.
	End.
End.