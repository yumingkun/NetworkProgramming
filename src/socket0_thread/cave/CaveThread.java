package  socket0_thread.cave;

import socket0_thread.cave.Cave;

public class CaveThread extends Thread {

	Cave cave;


	public CaveThread(Cave cave,String name) {
		super(name);
		this.cave = cave;
	}


	@Override
	public void run() {
		cave.passCase(this.getName());
	}
}
