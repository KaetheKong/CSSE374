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

For Milestone #4:

Design:
	we added an singleton detect class and a filed to ClassClass which can keep track of if the class is singleton.
	
Works:
	Kong implemented the ComputeSeqDiagram class inside of toUMLimplement.
	Wen changed some parts of the asm code to keep track of more information for each method.
	We both worked on the manual generation of UML and Sequence diagram.
	

For Milestone #5:

Design: 
	we added AdapterDetect, AdapterClass, DecoratorDetect and DecoratorClass for adapter pattern and decorator pattern.
	
Works:
	Kong implemented the DecoratorDetect class, which detect the decorator design pattern.
	Wen implemented the AdapterDetect class, which detect the adapter design pattern.
	We both worked on the manual generation of UML Diagram. We both worked on extending more classes for the UML string generating process such that each match up with their color and style.
	

For Milestone #6:

Design:
	we added CompositeDetect, CompositeClass, CompositeComponentClass, LeafClass to detect the Composite design pattern.
	
Works:
	We both worked on the detection class of the composite design pattern.
	Kong implemented majority of the logic of the detection.
	Wen implemented the tracing down levels of the subclasses for the detection.
	We both worked on the classes for the connections and generation of the UML textual code.
	
For Milestone #7:

Design:
	We changed the design a little bit more by pulling out some necessary data out from the system's code so that users can extend our code easily by calling the add methods for adding an extra piece of the data to the system. To implement the user interfaces, we implemented the interfaces into different phases, including loading phase, analyzing phase, and output phase. Then the output phase contain different parts containing the control (checkbox) part and picture part. A total number of classes were added for the interfaces.
	
Works:
	We both worked on the user interfaces.
	Wen worked on the basic structure of the UI and the calls of appropriate detection when necessary. And she also helped fix the small changes inside of the UML generator code such that the generator works better with the user interfaces.
	Kong worked on making the design of the user interfaces better so that each different phase were separated. Also, she worked on fixing the bug to enable better extensions. 
	We both worked on the manual generation of the UML diagram.

How to use our code?
	The main function was implemented in ToUML in the toUMLimplement package. Pass in a command follows a format as following as the argument of the ToUML class. 
	
	seq <classname> <methodname> <parameters (separate by commas)> <boolean value to include java classes or not>
	
	uml <package name (separate by commas)> <boolean value to show if the argment passed in is a classname>
	
How to use our UI?
	1. Create a config file similar to the config.txt file uploaded in the package
	2. Run the class UserInterface.java
	3. Click on load and find the file to load as config.txt
	4. And click on analyze, then it should generate what is contained inside the given classes/packages.
	..* | Config Fields                | Description   |
	    | ---------------------------- |:-------------:|
	    | Input-Folder                 | right-aligned |
	    | Input-Classes                | centered      |
	    | Output-Directory             | are neat      |
	    | Dot-Path                     |               |
	    | Phases	                   | 	           |
	    | Adapter-MethodDelegation     |               |
	    | Decorator-MethodDelegation   |               |
	    | Singleton-RequireGetInstance |               |
	    | Composite-MethodDelegation   |               |
	    | include-java                 |               |
	    | use-classes                  |               |
	
Example command passing in as argument:
	
	uml asm classes Data designPatterns implementation interfaces toUMLimplement false
	
	seq java/util/Collections shuffle List<?> list, Random r true
	
The textual code for GraphViz/SDEdit would print to the console. Copy the code over to GraphViz/SDEdit and it would generate the UML Diagram or the sequence diagram as requested.

