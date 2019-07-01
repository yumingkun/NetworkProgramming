package socket0_thread.ticket;

public class TicketTest {

	public static void main(String[] args) {
		
		Ticket ticket=new Ticket();
		
 	
		TickThread[] thread=new TickThread[20];
		for (int i = 0; i <thread.length; i++) {
			thread[i]=new TickThread(ticket, "用户"+i);
			thread[i].start();
		}
		

	}

}
