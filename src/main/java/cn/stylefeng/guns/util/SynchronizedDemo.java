package cn.stylefeng.guns.util;

public class SynchronizedDemo implements Runnable {
	private static int count=0;
	public static void main(String[] arg){
		for(int i=0;i<10;i++){
			Thread thread = new Thread(new SynchronizedDemo());
			thread.start();
		}
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("result "+count);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (SynchronizedDemo.class) {
			for(int i=0;i<1000000;i++){
				count++;
			}
		}
	}

}
