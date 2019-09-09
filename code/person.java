public class person extends zombie { 

	private boolean zombie; 
	private boolean infected; 
	private boolean isCpu; 

	private int alerted;  	
	private int infctTime; 
	
	public person(){
	}; 
	
	public person(,,,,){
	};
	
	public int getInfctTime(); 
	
	public void processMoves(); //virtual function
	private void infection(); 
}
