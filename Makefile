run: compile
	echo "Add command to run the program here"

compile:
	echo "Use this rule to compile all necessary java source files"


dataWrangler: CarData.class CarLoader.class

CarData.class: CarData.java
	javac CarData.java

CarLoader.class: CarLoader.java
	javac CarLoader.java

