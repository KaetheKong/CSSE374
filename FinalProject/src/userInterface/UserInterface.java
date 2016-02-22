package userInterface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import designPatterns.HashMapDetect;

public class UserInterface {
	public static void main(String s[]) {
		UserInterfaceLoader dil = new UserInterfaceLoader();
		dil.addDesignPatternData(new HashMapDetect(null));
		dil.addColorSet("HashMap", Color.CYAN);
		List<String> checkr = new ArrayList<String>();
		checkr.add("HashMap");
		dil.addDPchecker("HashMap", checkr);
		dil.addDpName("HashMap");
		dil.launcher();
	}
}
