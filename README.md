# CSSE374
This is the final project for the junior design class CSSE374. 

For Milestone #1: 

Design:
	We are designing and building a simple tool to generate the most essential elements of the UML diagram for java code. We implemented this function by using the ASM, a Java parser, to parse the code. With the code parsed, all necessary information are saved under each different classes and all necessary information is saved in the visitors in a form of hashmaps. Different classes include MethodClass, FieldClass and ClassClass. MethodClass keeps track of each individual method in a general class including its access qualifier, parameter type, return types, etc.. FieldClass keeps track of each individual field in a general java class including its access qualifier, type, default value, etc.. ClassClass keeps track of each individual class including all fields and methods implemented inside the class. With all information collected, we created the connection class to generate textual code for GraphViz for each class to connect with each other. 
	
Works:
	Kong initialized all the classes and interfaces. Also she modified all the classes in classes package and interfaces package and CodeASM.
	Wen modified all the classes in asm package and toUMLimplement.
	We both devoted to the manual generation of UML diagram.
	
For Milestone #2:

Design: 
	We added two more classes: UsesClass and AssociationClass that have specific edge description.we enhance the decoration design pattern so that every additional edges can be added simply.
	
Works: 
	Kong implemented the UsesClass and tests and modified interfaces package.
	Wen implemented the AssociationClass and also modified toUMLimplementation package.
	We modified the design to enhance the design principles.
	Both of us worked together to generate the UML diagram.

For Milestone #3:

Design:
	We added an extra class inside of toUMLimplement that compute the sequence diagram connection for us. To find the order of the execution of methods, we added extra fields into the visitors class so that we would be able to know the owner of each inner method and parameter that are called by the method to generate diagram for. Also, we added a parsing identifier into toUMLimplement package such that it can identify if it wants to generate a sequence diagram based on the classname and method name given or if it wants to generate a UML diagram based on package name/class name.
	
Works:
	Kong implemented the ComputeSeqDiagram class inside of toUMLimplement.
	Wen changed some parts of the asm code to keep track of more information for each method.
	We both worked on the manual generation of UML and Sequence diagram.

How to use our code?
	The main function was implemented in ToUML in the toUMLimplement package. Pass in a command follows a format as following as the argument of the ToUML class. 
	
	seq <classname> <methodname> <parameters (separate by commas)> <boolean value to include java classes or not>
	
	uml <package name (separate by commas)> <boolean value to show if the argment passed in is a classname>
	
Example command passing in as argument:
	
	uml asm classes Data designPatterns implementation interfaces toUMLimplement false
	
	seq java/util/Collections shuffle List<?> list, Random r true
	
The textual code for GraphViz/SDEdit would print to the console. Copy the code over to GraphViz/SDEdit and it would generate the UML Diagram or the sequence diagram as requested.
