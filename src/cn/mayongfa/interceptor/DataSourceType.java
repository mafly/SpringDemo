package cn.mayongfa.interceptor;

public enum DataSourceType {

	//主库
	Master("master"),
	
	//从库
	Slave("slave");
	
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


