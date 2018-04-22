# DrawMeDaddyV2

For dev (in IDE):
1. Download and install Eclipse from https://www.eclipse.org/downloads/
2. Clone repository
3. Open eclipse then import cloned repo as project
4. Create your own branch and name it as your surname


For Compiling and Running:
1. Open Terminal (Linux) or CMD (Windows)
2. Go to the the repository's directory (cd DrawMeDaddyV2\DrawMeDaddy\src)
3. Compile everything via : javac * .java //remove space after asterisk
4. Run server via: java ServerRunner
5. Run client via: java ClientRunner


Conventions:
1. In committing, use this format: git commit -m "task accomplished/Signed:Name" eg. git commit -m "Added erase button/Signed:Drew Guiao"
2. Try as much as possilble to Clean Code.<br>
  2a. Variable names and methods should be descriptive. Eg. calculateDistance(Point a, Point b);<br>
  2b. Non-final variables and methods are always in camel case. Eg. int numOfStudents; <br>
  2c. Constants should always be declared as static final in the Constant class and in UPPER CASE. Use underscores as separators. Eg. public static final double PI_VALUE = 3.14;<br>
  
  
3. Always use the interface of the class in declaration. Eg. List&lt;Student&gt; students = new ArrayList<>();
4. Prefer for-each loops instead of traditional for loops. Eg. for(Student s: students) 
5. Always implement getters for each variable that you might need in each class. public int getNumOfStudents(){return numOfStudents;}
6. Make use of the Constant class. All constants must be declared there. To use, just implement the Constants class
7. Prefer Collections(ArrayLists, HashMaps, LinkedLists) over primitive grouping(int[], double[], float[]). Lists have built-in methods like get(int index), clear(), contains(Object o), isEmpty(), etc.; 
8. For 1 line for loops, remove brackets and use this format: for(Student s: students) s.performTask();
  
Notes:
* Lost? Go to javadocs for finding classes that you may need.
