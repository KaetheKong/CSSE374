The design of the tools: ASM, Graphviz

Kathy initialized all the classes and interfaces.And also she modified all the classes in classes package and interfaces package and CodeASM.
Sophia modified all the classes in asm package and toUMLimplement.
Both of us worked together to generate the UML diagram.

Design:
we decided to make classes for each component in the java code. Each java code contains fields, methods, extends superclass and implements interfaces. Each method has its specific parameters' return type. Each file has it's own type, default value and access level.we wrap up java method as a object in method class.then you can retrieve the method by calling getter method.


How to use:
since we have a package called toUMLimplementation, and just need to pass in the package 
name to ToUML class. then copy what it returned into a dot file. 
then using Graphviz to open the dot file. it will generate the UML diagram automatically. 