package socket0_thread.account;

public class Account {
	private double money;

	public Account(double money) {
		this.money = money;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}



	public synchronized void  withdraw(String name){
//		synchronized (this) {
		if (money>=100) {
			try {
				Thread.sleep(300);
				money-=100;
				System.out.println(name+"取出100元");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else {
			System.out.println(name+"取钱失败");
		}
//		}
	}

	
}
