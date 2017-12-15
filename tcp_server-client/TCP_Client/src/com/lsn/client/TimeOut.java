package com.lsn.client;

public class TimeOut implements Runnable{
	private int num;
	public TimeOut(int num){
		this.num = num;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis()-startTime)<=5000){
			
		}
		if(Client.list.containsKey(num)){
			System.out.println(Client.list.get(num)+" send fail");
			Client.list.remove(num);
		}
	}
	
}
