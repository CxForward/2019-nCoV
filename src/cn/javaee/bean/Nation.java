package cn.javaee.bean;

public class Nation {
	private String provinceName;
	private int currentConfirmedCount;
	private int confirmedCount;
	private int curedCount;
	private int deadCount;
	private String continents;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public int getCurrentConfirmedCount() {
		return currentConfirmedCount;
	}
	public void setCurrentConfirmedCount(int currentConfirmedCount) {
		this.currentConfirmedCount = currentConfirmedCount;
	}
	public int getConfirmedCount() {
		return confirmedCount;
	}
	public void setConfirmedCount(int confirmedCount) {
		this.confirmedCount = confirmedCount;
	}
	public int getCuredCount() {
		return curedCount;
	}
	public void setCuredCount(int curedCount) {
		this.curedCount = curedCount;
	}
	public int getDeadCount() {
		return deadCount;
	}
	public void setDeadCount(int deadCount) {
		this.deadCount = deadCount;
	}
	public String getContinents() {
		return continents;
	}
	public void setContinents(String continents) {
		this.continents = continents;
	}
	@Override
	public String toString() {
		return "Nation [provinceName=" + provinceName
				+ ", currentConfirmedCount=" + currentConfirmedCount
				+ ", confirmedCount=" + confirmedCount + ", curedCount="
				+ curedCount + ", deadCount=" + deadCount + ", continents="
				+ continents + "]";
	}
}
