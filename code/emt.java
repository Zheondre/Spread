public class emt extends person { 


	public emt(){
	}; 
	
	public emt(,,,,){
	};

	public void transport(); 
	public void heal(); 
	public void treatInfection(); 
	public void provessMoves(); 
	public void callForHelp(); 
}

public class medic extends emt { 


	public emt(){
	}; 
	
	public emt(,,,,){
	};
	
	void shoot(); 

}

public class hazmat extends medic { 


	public hazmat(){
	}; 
	
	public hazmat(,,,,){
	};


}