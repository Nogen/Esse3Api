package data;

public class Esse3Block {
	private String blockName;
	private Float totalHours;
	
	
	public Esse3Block() {
	}
	
	public Esse3Block(String blockName, Float totalHours) {
		this.setBlockName(blockName);
		this.setTotalHours(totalHours);
	}
	
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public Float getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Float totalHours) {
		this.totalHours = totalHours;
	}
	
	@Override
	public String toString() {
		return "Group name: " +  this.blockName +
				"\n" +
				"Group total hours: " + this.totalHours +
				"\n";
	}

}
