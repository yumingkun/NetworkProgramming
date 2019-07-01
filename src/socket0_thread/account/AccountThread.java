package socket0_thread.account;

public class AccountThread extends Thread{
	Account account;

	public AccountThread(Account account,String name) {
		super(name);
		this.account = account;
	}
	
	@Override
	public void run() {
		account.withdraw(this.getName());
	}
	
}
