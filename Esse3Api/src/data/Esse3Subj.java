package data;

public class Esse3Subj {
	private String subjName;
	private Float subjHours;
	private String blockName;
	
	public Esse3Subj() {
	}
	
	public Esse3Subj(String subjName, Float subjHours, String blockName) {
		this.subjName = subjName;
		this.subjHours = subjHours;
		this.blockName = blockName;
	}
	
	public String getSubjName() {
		return subjName;
	}
	
	public void setSubjName(String subjName) {
		this.subjName = subjName;
	}
	
	public Float getSubjHours() {
		return subjHours;
	}
	
	public void setSubjHours(Float subjHours) {
		this.subjHours = subjHours;
	}
	
	public String getBlockName() {
		return blockName;
	}
	
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	
	

}
