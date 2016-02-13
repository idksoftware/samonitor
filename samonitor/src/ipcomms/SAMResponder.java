package ipcomms;

import java.io.IOException;

public class SAMResponder {
	static SAMResponderThread samResponderThread = null;
	public static void Start() throws IOException {
		samResponderThread = new SAMResponderThread();
		samResponderThread.start();
    }
}
