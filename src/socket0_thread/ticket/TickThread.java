package socket0_thread.ticket;

public class TickThread extends Thread {
	Ticket  ticket;
	public TickThread(Ticket ticket,String name) {
		super(name);
		this.ticket = ticket;
	}

	@Override
	public void run() {
		 ticket.sale(this.getName());
	}
}
