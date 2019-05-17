package cn.stylefeng.guns.util;

public class ThreadSafeCache {

	    int result;
		public synchronized int getResult(){
			return result;
		}
		public  void setResult(int result){
			this.result=result;
		}
		public static void main(String[] arg){
			ThreadSafeCache threadSafeCache = new ThreadSafeCache();
			ThreadSafeCache threadSafeCache2 = new ThreadSafeCache();
			/*new Thread(() ->{
				threadSafeCache2.setResult(200);
			}).start();*/
			for(int i=0;i<1;i++){
				new Thread(() ->{
					int x=0;
					while(threadSafeCache.getResult()<100){
						x++;
					}
					System.out.println(x);
				}).start();
			}
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			threadSafeCache.setResult(200);
		}

}
