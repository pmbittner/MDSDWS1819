Program Example
	Variables a := 4, b, c := 1.

	//bound1 has to be less than or equal to bound2 for "To" if given explicitly
	For b := 10 To 1 Do
		c := c + (b * 5).
	End.
	
	//bound2 has to be less than or equal to bound1 for "Down To" if given explicitly
	For b := 1 Down To 10 Do
		c := c + (b * 5).
	End.
End.