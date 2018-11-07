Program Example
	Variables a := 4, b, c := 1.

	For b := 1 To 10 Do
		c := c + (b * 5).
	End.
	
	For b := 10 Down To 1 Do
		c := c + (b * 5).
	End.
	
	For b := a To 10 Do
		c := c + (b * 5).
	End.
	
	For b := 4 To (a * 10) - 3 Do
		c := c + (b * 5).
	End.
	
	For b := (a * 5) - 2 Down To c * 2 Do
		c := c + (b * 5).
	End.
End.