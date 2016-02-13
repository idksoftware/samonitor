package samonitor;

import telnet.TelnetServer;

public class TelnetServerManager {

	static TelnetServerManager thisTelnetServerManager = null;
	static Thread telnetServerThread  = null;
	
	static public TelnetServerManager create()
	{
		if (thisTelnetServerManager == null) {
			thisTelnetServerManager = new TelnetServerManager();
			
			try {
				TelnetServerManager.Start("SAM Telnet Server");
				//telnetServer = new TelnetServer();
     	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return thisTelnetServerManager;
	}
	
	
	
	
    
	private static class TelnetServerThread implements Runnable {
        public void run() {
           
            try {
            	TelnetServer.Run();       
            } catch (InterruptedException e) {
                System.out.println("Telnet server stopped");
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
    
    public static void Start(String arg) throws InterruptedException {

        System.out.println("Starting Telnet server thread");
        long startTime = System.currentTimeMillis();
        
        telnetServerThread = new Thread(new TelnetServerThread());
        telnetServerThread.start();

        System.out.println("Telnet server Started");
    }
    
    public static void Stop()
    {
        //loop until MessageLoop thread exits
        if (telnetServerThread.isAlive()) {
            //threadMessage("Still waiting...");
            //Wait maximum of 1 second for MessageLoop thread to
            //finish.
        	try {
        		telnetServerThread.join(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //if (((System.currentTimeMillis() - startTime) > patience) &&
            //		webServerThread.isAlive()) {
                //threadMessage("Tired of waiting!");
				TelnetServer.shutdown();
				telnetServerThread.interrupt();
                //Shouldn't be long now -- wait indefinitely
            	try {
            		telnetServerThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            //}

        }
    	System.out.println("Finally!");
    }
    
    public static boolean isStopping() {
    	return TelnetServer.isStopping();
    }
   
}
