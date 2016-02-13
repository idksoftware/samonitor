package samonitor;

import java.util.ArrayList;

public class SAMStations {
	private static SAMStationsList samStationsList = new SAMStationsList();
	static public SAMStationsList get() {
		return samStationsList;
	}
}

