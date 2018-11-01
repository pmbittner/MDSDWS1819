Program Test
	Variables a, b, c.
	
	a := a + 3 * b.
	b := a * 3 + 4.
	
	c := (3).
	c := (3 + a) * b.
	
	a := 2 - 1.
	// Falsch geparst:
	//a := 2- 1 . //2- entfaellt
	//a := 2 -1 . //-1 entfaellt
	
	// Syntax Error
	//a := -1.
End.