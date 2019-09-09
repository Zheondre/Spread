public class zombie { 

	private int posX; 
	private int poxY; 
	private int poxZ; 
	private int infections; 
	private int attackPt;
	private int health;
	private byte weapon;
	private byte clsId; 
	
	//should we make this external to the system ?
	enum whoAreYou{
		Zombie, 
		Person, 
		Security, 
		Cop, 
		Army, 
		Emt, 
		Medic, 
		Hazmat,
		Master, 
		Debug,
	}
	private int *graphic;	

	public zombie(){
	}; 
	
	public zombie(,,,,){
	}; 

	public void processMoves(); //virtual function
	public void attack(); 	
	
	public int getHealth(); 
	public int getPosX();  
	public int getPosY();
	public int getPosZ();
	public byte getWeapon(); 
	public byte getID();

	public zombie* instance(); 

}
