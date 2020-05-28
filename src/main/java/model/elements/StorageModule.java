package model.elements;


public class StorageModule {

	// TODO: This whole class is currently more or less useless. 
	// It could in the future be used to delay getting of the workpiece and for getting different workpiece colors (randomly or in a row)
	
	private boolean gettingWorkpiece = false; 
	
	public boolean isGettingWorkpiece() {
		// return gettingWorkpiece and reset it
		boolean gettingWorkpiece = this.gettingWorkpiece;
		this.gettingWorkpiece = false;
		return gettingWorkpiece;
	}
	
	public void getWorkpiece() {
		this.gettingWorkpiece = true;
	}
	
}
