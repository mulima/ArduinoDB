/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinodb;

import java.sql.ResultSet;

/**
 *
 * @author 1304516
 */
public class ArduinoDB {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
		SerialReader main = new SerialReader();
		main.initialize();
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {
                                
                                
                                }
			}
		};
		t.start();
		System.out.println("Started");
	}
    
}
