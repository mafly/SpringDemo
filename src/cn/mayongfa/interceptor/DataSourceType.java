package cn.mayongfa.interceptor;

public enum DataSourceType {

	// 主库
	Master("master"),

	// 从库
	Slave("slave"),

	// 金币主库
	GoldMaster("master"),

	// 金币从库
	GoldSlave("slave");

	private DataSourceType(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
