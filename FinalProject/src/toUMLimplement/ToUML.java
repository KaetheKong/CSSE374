package toUMLimplement;

import java.io.IOException;

public class ToUML {
	public static final String[] PRIMITIVE_TYPE = { "byte", "int", "short", "long", "double", "float", "char",
			"boolean" };

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		UMLGenerator umlgor = new UMLGenerator(args);
		umlgor.run();
	}
}
