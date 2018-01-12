Account - Program containing all the account object concepts
compile : C:\>   javac -cp . C:\com\seneca\f2017\data\Account.java
************************************************************************************
Bank - A bank program accessing account class to deal with various actions on account 
compile : C:\>   javac -cp . C:\com\seneca\f2017\data\Bank.java
************************************************************************************
BankingClient - Client program 

(C:\ is the root directory ) 
compile : C:\> javac -cp . C:\edu\jav745\f2017\client\BankingClient.java
run : C:\>java -cp . edu.jav745.f2017.client.BankingClient
************************************************************************************
BankingServer - Server program 
C:\>javac -cp . C:\edu\jav745\f2017\server\BankingServer.java
************************************************************************************
BankingMTTester - A server (MT) tester program - This calles the BankingServer class

compile : C:\> javac -cp . C:\edu\jav745\f2017\server\BankingMTTester.java
run : ( !!Need to provide portnumber as a command line argument)
C:\>java -cp . edu.jav745.f2017.server.BankingMTTester 5678
*************************************************************************************

All the programs are stored in the package structure that mentioned in the question.
You must use the following package names to organize your Java classes:
    - package name: edu.jav745.f2017.client
      - Java classes: BankingClient (and additional classes used by your implementation)

    - package name: edu.jav745.f2017.server
     - Java classes: BankingServer (and additional classes used by your implementation)

    - package name: com.seneca.f2017.data
      - Java classes: Bank, Account (and additional classes used by your implementation)






