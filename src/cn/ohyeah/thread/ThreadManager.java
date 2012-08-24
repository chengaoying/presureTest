package cn.ohyeah.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import cn.ohyeah.model.GameRecord;
import cn.ohyeah.service.RecordService;
import cn.ohyeah.util.RandomValue;
import cn.ohyeah.util.TimeUtils;

public class ThreadManager {
	
	//private static final String pattern = "/itvgame/protocolv2/processor";
	
	private String url;// = "http://192.168.16.66:8080/itvgame/protocolv2/processor";
	private int accountId;// = 10000;
	//private int[] accountIds = {10000,10001,10002,10003,10004,10005,10006,10007,10008};
	private int productId;// = 11;
	private GameRecord gameRecord;// = new GameRecord();
	private String ip;
	private int port;
	private int threadCount;
	
	private AtomicInteger successCount;
	private ExecutorService executor;
	
	public ThreadManager(int nThreads, String url, int productId/*, int accountId*/) {
		this.threadCount = nThreads;
		this.url = url;
		this.productId = productId;
		//this.accountId = accountId;
		executor = Executors.newCachedThreadPool();
		successCount = new AtomicInteger();
		gameRecord = new GameRecord();
		gameRecord.setData(new byte[1024*8]);
	}
	
	public int multiThreadTest() throws InterruptedException, ExecutionException{
		
		List<Future<?>> futures = new LinkedList<Future<?>>();
		int recordId = TimeUtils.getTime() + 10000;
		for(int i=0;i<threadCount;i++){
			//tasks.add(task);
			//final int idx = i%accountIds.length;
			final int _i = i + recordId;
			futures.add(executor.submit(new Runnable() {
				@Override
				public void run() {
					RecordService recordService = new RecordService(url);
					gameRecord.setRecordId(_i);
					accountId = RandomValue.getRandInt(1, 10000000);
					recordService.save(accountId, productId, gameRecord);
					if (recordService.getResult() == 0) {
						successCount.incrementAndGet();
					}
				}
			}));
		}
		
		//executor.shutdown();
		//List<Future<?>> futures = executor.invokeAll(tasks);
		//executor.awaitTermination(300, TimeUnit.SECONDS);
		
		for (int i = 0; i < futures.size(); ++i) {
			futures.get(i).get();
		}
		executor.shutdownNow();
		return successCount.get();
	}
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

}
