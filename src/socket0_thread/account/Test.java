package socket0_thread.account;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Account account=new Account(100);
			AccountThread th1 =new AccountThread(account, "����");
			AccountThread th2 =new AccountThread(account, "����");
			th1.start();
			th2.start();
			
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("���"+account.getMoney());
	}

}
