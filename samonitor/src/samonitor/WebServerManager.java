package samonitor;

import WebServer.WebServer;

public class WebServerManager {

	static Thread webServerThread = null;
    

    private static class WebServerThread implements Runnable {
        public void run() {
           
            try {
            	WebServer.Run("8090");
                
            } catch (InterruptedException e) {
                System.out.println("Web server stopped");
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public static void Start(int port) throws InterruptedException {

        System.out.println("Starting Web server thread");
        long startTime = System.currentTimeMillis();
        webServerThread = new Thread(new WebServerThread());
        webServerThread.start();

        System.out.println("Web server Started");
    }
    
    public static void Stop()
    {
        //loop until MessageLoop thread exits
        if (webServerThread.isAlive()) {
            //threadMessage("Still waiting...");
            //Wait maximum of 1 second for MessageLoop thread to
            //finish.
        	try {
				webServerThread.join(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //if (((System.currentTimeMillis() - startTime) > patience) &&
            //		webServerThread.isAlive()) {
                //threadMessage("Tired of waiting!");
				WebServer.Quit();
            	webServerThread.interrupt();
                //Shouldn't be long now -- wait indefinitely
            	try {
					webServerThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            //}

        }
    	System.out.println("Finally!");
    }
}
