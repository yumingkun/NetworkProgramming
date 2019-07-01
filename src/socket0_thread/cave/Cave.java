package socket0_thread.cave;

public class Cave {

	public synchronized void passCase(String name){
		try {
			Thread.sleep(5000);//过山洞5秒
			System.out.println(name+"通过山洞");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
