package cn.mayongfa.common;

public class IdWorkerManage {

	private static IdWorker IdWorkerInstance = null;

	static {

		IdWorkerManage.IdWorkerInstance = new IdWorker();
	}

	/**
	 * single 单例模式
	 * 
	 * @return
	 */
	private static IdWorker getIdWorkerInstance() {

		if (IdWorkerManage.IdWorkerInstance == null) {
			IdWorkerManage.IdWorkerInstance = new IdWorker();
		}
		return IdWorkerManage.IdWorkerInstance;
	}

	/**
	 * single 单例模式
	 * 
	 * @return
	 */
	public static long getId() throws Exception {
		return IdWorkerManage.getIdWorkerInstance().get_id("springdemo");
	}
	
	/*
	public static void main(String[] args) {
		for (int i = 0; i < 101; i++) {
			try {
				System.out.println(IdWorkerManage.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

}
