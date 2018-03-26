# DrawMeDaddyV2

For dev:
1. Download and install Eclipse from https://www.eclipse.org/downloads/
2. Clone repository
3. Open eclipse then import cloned repo as project
4. Create your own branch and name it as your surname

Conventions:
1. In committing with this format: git commit -m "task accomplished/Signed:Name" eg. git commit -m "Added erase button/Signed:Drew Guiao"
2. Try as much as possilble to Clean Code.
  2a. Variable names and methods should be descriptive. Eg. calculateDistance(Point a, Point b);
  2b. Non-final variables are always in camel case. Eg. int numOfStudents; 
  2c. Constants should always be declared as static final in the Constant class and in UPPER CASE. Use underscores as separators. Eg. static final PI_VALUE;
  
  
3. Always use the interface of the class in declaration. Eg. List<Student> students = new ArrayList<>();
4. Prefer for-each loops instead of traditional for loops. Eg. for(Student s: students) 
5. Always implement getters for each variable that you might need in each class. public int getNumOfStudents(){return numOfStudents;}
