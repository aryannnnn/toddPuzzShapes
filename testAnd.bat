set M2=C:\apache-maven-3.0.3\bin
set M2_HOME=C:\apache-maven-3.0.3

call c:\apache-maven-3.0.3\bin\mvn clean install

call cd android

call c:\apache-maven-3.0.3\bin\mvn android:deploy

call cd ..