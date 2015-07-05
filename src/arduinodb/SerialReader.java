/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduinodb;

/**
 * A lot of the code was obtained from an example on http://playground.arduino.cc/Interfacing/Java
 * @author Mulima Chibuye
 */
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.sql.Connection;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.StringTokenizer;


public class SerialReader implements SerialPortEventListener {
	SerialPort serialPort;
        PreparedStatement statement;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM21", // Windows
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
                // the next line is for Raspberry Pi and 
                // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
                //System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyUSB0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
                

                //prepare query that will read serial data and store in database
                String query = "INSERT into arduino_sensors(temp,humidity) VALUES(?,?)";
                try{
                Connection connection = new DatabaseConnection().getConnection();
                statement = connection.prepareStatement(query);
                              
                
                
                        
                        }catch(Exception e){
                            
                            e.printStackTrace();
                        }
		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
                                        System.out.println(portId.getName());
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		//System.out.println("Serial event successfully triggered");
            
            if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
                                String inputLine = null;
                                if(input.ready()){
				inputLine=input.readLine();
				System.out.println(inputLine);
                                StringTokenizer stringTokenizer = new StringTokenizer(inputLine,",");
                                
                                String humidity = stringTokenizer.nextToken();
                                System.out.println(humidity);
                                statement.setString(1,humidity);                                
                                String temperature = stringTokenizer.nextToken();
                                System.out.println(temperature);
                                statement.setString(2,temperature);
                                statement.executeUpdate();
                                
                                
                                
                                }
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	
}
