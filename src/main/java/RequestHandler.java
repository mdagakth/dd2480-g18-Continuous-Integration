import java.lang.Thread;

public class RequestHandler implements Runnable{
	volatile String data;

	@Override
	public void run() {
		System.out.println(data);
		System.out.println("Hello from another thread");
		try {
			Thread.sleep(10000);
		}catch (Exception e){}
		System.out.println("wakey wakey");
	}
}
