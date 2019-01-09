Function Adventure(Finn, Jake)
	Variables spassFaktor.
			
	If (Finn = 0) Then
		If (Jake = 0) Then
			Return 0.
		End.
		
		Finn := Jake + 2.
		Jake := Jake - 1.
	Else 
		If (Jake = 0) Then
			Jake := Finn + 2.
			Finn := Finn - 1.
		End.
	End.
	
	spassFaktor := 2 * (Finn + Jake).
	
	Return spassFaktor.
End.

Program AdventureTime
	Variables Finn, Jake, abenteuerNummer.
	
	Finn := 1.
	Jake := 0.
	
	For abenteuerNummer := 1 To 3 Do
		Jake := Adventure(Finn, Jake).
		Trace(Jake).
	End.
End.