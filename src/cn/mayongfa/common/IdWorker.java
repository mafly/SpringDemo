package cn.mayongfa.common;

import org.apache.log4j.Logger;

/**
 * 
 * 以后，根据数据id，即可计算出该数据，产自哪个数据中心的.
 * 
 * @author Administrator
 *
 */
public class IdWorker {
	/**
	 * 下面3个参数，不同部署的地方，系统，都要修改为不同的参数值.
	 */
	private long datacenterId = 1;
	private long workerId = 1;
	private static final String USERAGENT = "springdemo";
	private long sequence = 0L;
	private static final Logger logger = Logger.getLogger(IdWorker.class);

	/**
     *
     */
	private final long twepoch = 1288834974657L;
	// 机器标识位数
	private final long workerIdBits = 5L;
	// 数据中心标识位数
	private final long datacenterIdBits = 5L;
	// 机器ID最大值
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private final long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;

	/**
	 *
	 * @param workerId
	 * @param datacenterId
	 */
	public IdWorker(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"workerId can't be greater than %d or less than 0.",
					maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenterId can't be greater than %d or less than 0.",
					maxDatacenterId));
		}

		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public IdWorker() {

		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"workerId can't be greater than %d or less than 0.",
					maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenterId can't be greater than %d or less than 0.",
					maxDatacenterId));
		}
		//this.workerId = workerId;
		//this.datacenterId = datacenterId;
	}

	/**
	 *
	 * @return
	 */
	protected synchronized long nextId() {

		long id = 0L;

		long timestamp = timeGen();
		// 时间错误
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
					String.format(
							"clock moved backwards. refusing to generate id for %d milliseconds.",
							lastTimestamp - timestamp));
		}
		if (timestamp == lastTimestamp) {
			// 当前毫秒内，则+1
			sequence = (1 + sequence) & sequenceMask;
			if (0 == sequence) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		id = ((timestamp - twepoch) << timestampLeftShift)
				| (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;

		return id;
	}

	/**
	 * 等待下一个毫秒的到来
	 * 
	 * @param lastTimestamp
	 * @return
	 */
	protected long tilNextMillis(long lastTimestamp) {

		long timestamp = timeGen();

		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}

		return timestamp;
	}

	/**
	 *
	 * @return
	 */
	protected long timeGen() {

		return System.currentTimeMillis();
	}

	public long get_worker_id() throws Exception {
		return workerId;
	}

	public long get_timestamp() throws Exception {
		return System.currentTimeMillis();
	}

	public long get_id(String useragent) throws Exception {
		if (!USERAGENT.equals(useragent)) {
			throw new Exception("invalid useragent.");
		}
		long id = nextId();
		logger.info(String.format("id: %s  user_agent: %s  worker_id: %s  data_center_id: %s",id, useragent, workerId, datacenterId));
		return id;
	}

	public long get_datacenter_id() throws Exception {
		return datacenterId;
	}
}
