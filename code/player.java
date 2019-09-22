public class player extends person { 
// for now just set the person to be a zombie
// in multiplayer u can chooser other wise
	private int points; 
	private zombie* curZombie;  
	
	public player(){
	}; 
	
	public player(,,,,){
	};
  
	public void processMoves(); //virtual function
	
	public void switchZombie();
	
	// need to define which button does what ect
	private void controls(); 

}
