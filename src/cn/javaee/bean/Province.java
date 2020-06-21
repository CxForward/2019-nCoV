package cn.javaee.bean;

public class Province {
	private String provinceName;
	private String provinceShortName;
	private int currentConfirmedCount;
	private int confirmedCount;
	private int suspectedCount;// 疑似病例
	private int curedCount;
	private int deadCount;
	private String comment;// 备注
	

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceShortName() {
		return provinceShortName;
	}

	public void setProvinceShortName(String provinceShortName) {
		this.provinceShortName = provinceShortName;
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

	public int getSuspectedCount() {
		return suspectedCount;
	}

	public void setSuspectedCount(int suspectedCount) {
		this.suspectedCount = suspectedCount;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Province [provinceName=" + provinceName
				+ ", provinceShortName=" + provinceShortName
				+ ", currentConfirmedCount=" + currentConfirmedCount
				+ ", confirmedCount=" + confirmedCount + ", suspectedCount="
				+ suspectedCount + ", curedCount=" + curedCount
				+ ", deadCount=" + deadCount + ", comment=" + comment + "]";
	}
	
}
