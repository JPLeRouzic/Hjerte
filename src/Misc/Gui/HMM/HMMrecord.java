package Misc.Gui.HMM;

    public final class HMMrecord{
    	// instance varables
    	private String author;
    	private String hmmName;
    	private String portFolio;
    	private String hmmUsage;
    	
    	//constructor
    	public HMMrecord(String authorName, String HMMname, String portFolio, String intendedUsage){
    	
    		setAuthor(authorName);
    		setHMM(HMMname);
    		setPortFolio(portFolio);
    		setHMMUsage(intendedUsage);
    	}
    	
    	//set author name
    	public void setAuthor(String authorName){
    		author = authorName;
    	}
    	
    	//get author name
    	public String getAuthor(){
    		return author;
    	}
    	
    	//set hmmName name
    	public void setHMM(String HMMname){
    		hmmName = HMMname;
    	}
    	//get hmmName name
    	public String getHMM(){
    		return hmmName;
    	}
    	
    	//set portFolio name
    	public void setPortFolio(String prtFolio){
    		portFolio = prtFolio;
    	}
    	
    	//get  name
    	public String getPortFolio(){
    		return portFolio;
    	}
    	
    	// set hmm length
    	public void setHMMUsage(String intendedUsage){
    		hmmUsage = intendedUsage;
    	}
    	
    	// get hmm length
    	public String getHMMUsage(){
    		return hmmUsage;  
    	}
    }
