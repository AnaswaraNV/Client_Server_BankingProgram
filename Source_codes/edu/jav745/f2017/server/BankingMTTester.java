/* This program serves as a tesing prgram for BankingServer class.
	This takes port number as a command line argument.
	Here an instance for BankingServer class is created.Using this instance, a method is invoked to start the whole banking server process.
	
	 Following links are used to implement some concepts in the program.

https://stackoverflow.com/questions/3868878/java-check-if-command-line-arguments-are-null
https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html
*/
/**
       This is a javadoc comment. It documents classes and methods.
		@param port number as a command line argument
       
	   @version 1.0
       @author  Anaswara Naderi Vadakkeperatta
       

 */
package edu.jav745.f2017.server;
import com.seneca.f2017.data.*;

public class BankingMTTester { 
	
	// A method to add 6 six accounts to the bankObject for further testing purpose 
	public static void addSixAccounts(Bank bankobj) { 
	
		// String array objects to enter bank account details
		String accountNames [] = {"John Doe", "John Doe" , "Mary Ryan", "Mary Ryan" , "Peter Liu", "Peter Liu" };
		String accountNumbers []= {"A1234", "Z1234", "B5678", "Y5678", "C9999", "X9999"};
		int accountBalances []= {1000, 9999, 3000, 1000, 9999, 1000};
		
		//adding to bank object
		for (int i = 0; i < 6; i++) {
			bankobj.addAccount( new Account(accountNames[i], accountNumbers[i], accountBalances[i]) );
		}
	}

	public static void main(String[] args) {
		
		int portNumber = 0;
			if(args.length == 0)
		{
			System.out.println("Proper Usage is: java program portnumber");
			System.exit(0);
		}
		if (args.length > 0) {
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Argument" + args[0] + " must be an integer.");
				System.exit(1);
			}
			catch (Exception e) {
				System.err.println("Please enter port number as command line argument");
				System.exit(1);
			}
		}
		
		System.out.println("\n \n----------Bank Server---------");
		Bank bankObj = new Bank("Anaswara Naderi Vadakkeperatta");
		
		// For testing purpose
		addSixAccounts(bankObj);
		
		
		BankingServer bankServerObj = new BankingServer(bankObj);
		bankServerObj.start( portNumber );
		
	}
}