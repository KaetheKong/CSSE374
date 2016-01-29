# CSSE374
This is the final project for the junior design class CSSE374. 

For Milestone #1: 

Design:
	We are designing and building a simple tool to generate the most essential elements of the UML diagram for java code. We implemented this function by using the ASM, a Java parser, to parse the code. With the code parsed, all necessary information are saved under each different classes and all necessary information is saved in the visitors in a form of hashmaps. Different classes include MethodClass, FieldClass and ClassClass. MethodClass keeps track of each individual method in a general class including its access qualifier, parameter type, return types, etc.. FieldClass keeps track of each individual field in a general java class including its access qualifier, type, default value, etc.. ClassClass keeps track of each individual class including all fields and methods implemented inside the class. With all information collected, we created the connection class to generate textual code for GraphViz for each class to connect with each other. 
	
Works:
	Kong initialized all the classes and interfaces. Also she modified all the classes in classes package and interfaces package and CodeASM.
	Wen modified all the classes in asm package and toUMLimplement.
	
	We both devoted to the manual generation of UML diagram.
	
How to use our code?
	The main function was implemented in ToUML in the toUMLimplement package. Pass in the package name of the packages to generate the UML and run the code. The textual code for GraphViz would print to the console. Copy the code over to GraphViz and it would generate the UML Diagram.
