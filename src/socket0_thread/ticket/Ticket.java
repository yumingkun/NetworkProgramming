package socket0_thread.ticket;

public class Ticket {
	private int ticket=20;

	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	public synchronized void sale(String name){
		if (ticket>0) {
			ticket--;
			System.out.println(name+"订票成功"+"：票号"+ticket);
		}else {
			System.out.println("票已售空");
		}


	}

}
