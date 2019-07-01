package socket0_thread.cave;

public class CaveTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 Cave cave=new  Cave();




		CaveThread [] caveThreads=new CaveThread[10];
		for (int i = 0; i < caveThreads.length; i++) {
			caveThreads[i]=new CaveThread(cave,"游客"+i);
			caveThreads[i].start();
		}

	}

}
