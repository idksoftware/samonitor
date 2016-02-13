package telnet;

import samonitor.Samonitor;

public class TelnetCmds {
	protected static String[] removeFirstArg(String[] args) {
		String[] argsll = new String[args.length-1];
		for (int i = 1; i < args.length; i++)
		{
			argsll[i-1] = args[i];
		}
		return argsll;
	}
	
	static void pauseProcessing() {
		Samonitor.setPauseOn(true);
		while (Samonitor.isPaused() == false) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static void unpauseProcessing() {
		Samonitor.setPauseOn(false);
	}
}
