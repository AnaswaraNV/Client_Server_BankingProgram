/* 
This program is the banking client program that is used A Java-based client application uses the menu system (from Assignment #1 - which have add, update, deposit, search by balance, search by account number etc options) to interact with a customer and requests services from the server. It uses a Java socket to communicate with the banking server over the Internet. It displays the result (as in Assignment #1) after the server has provided the requested service (e.g. deposit money into an account).
*/


/**
       This is a javadoc comment. It documents classes and methods.
       @version 1.0
       @author  Anaswara Naderi Vadakkeperatta

 */
package edu.jav745.f2017.client; // Specifying package name
import com.seneca.f2017.data.*; // bank, account package name
import java.net.*;
import java.io.*;
import java.util.*;

public class BankingClient {
	
	
    public static int displayMenu() {
		
        int choice = 7;
        java.util.Scanner keyboard = new java.util.Scanner(System.in);
		Account account = null;

		try { 
					System.out.println("#######################################################");
					System.out.println("#######################################################");
					System.out.println("#1. Add an account.\n");
					System.out.println("#2. Remove an account.\n");
					System.out.println("#3. update an account.\n");
					System.out.println("#4. Search by account name.\n");
					System.out.println("#5. Search by account number.\n");
					System.out.println("#6. Search by account balance.\n");
					System.out.println("#7. Show all accounts.\n");
					System.out.println("#8. Exit.\n");
					System.out.println("#######################################################");
					System.out.println("#Please enter a choice : (Your options are from 1-8)");
					choice= keyboard.nextInt();
					System.out.println("option:" + choice);
				
		//In case of any input errors or Exception will notify the below message to user and exit the program		
		} catch(Exception e) { 
				
			System.out.println("Oops!!! Something wrong happened!!!");
			System.out.println("More info : ");
			System.out.println(e.getMessage());
			System.out.println("Please run the app again!");
		
		}
		
		return choice;
           
    }
	
	public static void sendOption(int option, DataOutputStream sendPrimitiveToServer,
								  ObjectOutputStream objectToServer,
								  DataInputStream receivePrimitiveFromServer,
								  ObjectInputStream objectFromServer) {
		try { 
			switch(option) 
			{
				case 1: addAccountChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer); 
						break;
				case 2: removeAccountChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer); 
						break;
				case 3: updateAccountChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer); 
						break;		
				case 4:	searchByAccountNameChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer);
						break;
				case 5: searchByAccountNumberChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer);
						break;
				case 6: searchByAccountBalanceChoice(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer);
						break;
				case 7: displayAccounts(sendPrimitiveToServer,objectToServer,
										receivePrimitiveFromServer,objectFromServer);
						break;
				case 8: System.out.println("Thank you for using our Banking App..!");
						System.exit(0);
						break;
				default: System.out.println("Please run the App again with valid option!");
						System.out.println("Thank you for using our Banking App..!");
						System.exit(0);
						break;
			}
	} catch(Exception e) { 
				
			System.out.println("Oops!!! Something wrong happened!!!");
			System.out.println("More info : ");
			System.out.println(e.getMessage());
			System.out.println("Please run the app again!");
			System.exit(0); //exiting
		
		}
	}
	 
	//If option is 1, account details are retrieved the user  
	public static void addAccountChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		
		int balance = 0;
		ArrayList<String> input = new ArrayList<String>();
		
		try { 
		
		java.util.Scanner keyboard = new java.util.Scanner(System.in);
		
		System.out.println("Please enter Account information (e.g. account name, account number, balance): ");
		System.out.println("**********************************************");
		System.out.println("Account name and Account number - Can have alphanumeric values");
		System.out.println("Account balance - Integer number");
		System.out.println("Sample format : A1234, John Doe, 1099");
		System.out.println("**********************************************");

		if (keyboard.hasNextLine()) {
					String string= keyboard.nextLine();
					String arr[] = string.split(", ");
					
					for (String item : arr) {
						
						input.add(item);
					}
		}
		

		balance = Integer.parseInt(input.get(2).toString());
		System.out.println("balance:" + balance);
		
		Account account = new Account(input.get(1).toString(),input.get(0).toString(), balance);
		
		//sending account object to server
		objectToServer.writeObject( account ); 
		objectToServer.flush();
		System.out.println( "\n### send this account to the server for registration:\n" +	 account );
		 
		 String response = receivePrimitiveFromServer.readUTF();
		 System.out.println("response is **** \n"+ response);
		 
		 String response2 = receivePrimitiveFromServer.readUTF();
		 System.out.println(response2);
		 
		 } 	catch( EOFException eof ) {  // loss of connection
				System.out.println( "*** THE client has terminated connection ***" );
			}
				
			catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
			}							
						
	}
	
	//remove an account
	public static void removeAccountChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		Account binCopy = null;
		String accountNumber = "";
		try { 
			java.util.Scanner keyboard = new java.util.Scanner(System.in);
			
			System.out.println("Please enter the account number for the account to be deleted:");
			System.out.println("Account number can have alphanumeric values ");
			accountNumber = keyboard.nextLine(); 
			
			sendPrimitiveToServer.writeUTF(accountNumber);
			sendPrimitiveToServer.flush();
			String response = receivePrimitiveFromServer.readUTF();
			System.out.println("response is **** \n"+ response);
			 
			 String response2 = receivePrimitiveFromServer.readUTF();
			 System.out.println(response2);
		  } 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}				
								
		
	}
	
	public static void updateAccountChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		
		try { 
				char option = ' ';
				int amount = 0; 
				String accountNumber = "";
				Account accountCopy = null;
				boolean updateStatus = false;
				java.util.Scanner keyboard = new java.util.Scanner(System.in);
				
				System.out.println("Please enter d/D or w/W for deposit/Withdraw option: ");
				option= keyboard.next().charAt(0); 
				
				option = Character.toLowerCase(option);
				
			// If option is withdraw send 1 to server , if withdraw 2 is sent to the server
			do {
				switch (option) {
				case 'd' : 	sendPrimitiveToServer.writeInt(1);
							sendPrimitiveToServer.flush();
							break;
				case 'w' : 	sendPrimitiveToServer.writeInt(2);
							sendPrimitiveToServer.flush();
							break;
				default  :  System.out.println("You have to enter d/D or w/W! Try the menu again!");
							break;
				}
			} while (option != 'd' && option != 'w') ;
			
				System.out.println("Please enter the amount to deposit : ");
				System.out.println("********Format is :1000 ***************");
				amount= keyboard.nextInt();
				sendPrimitiveToServer.writeInt(amount);
				sendPrimitiveToServer.flush();
				System.out.println("Please enter the account number for the account to be updated:");
				System.out.println("Account number can have alphanumeric values ");
				accountNumber = keyboard.next(); 
				sendPrimitiveToServer.writeUTF(accountNumber);
				sendPrimitiveToServer.flush();			
							
				String response = receivePrimitiveFromServer.readUTF();
				System.out.println("response is **** \n"+ response);
			 
				String response2 = receivePrimitiveFromServer.readUTF();
				System.out.println(response2);
		 
			
		} 
		catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
		catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}							

	}
	
	//searchByAccountNameChoice
	public static void searchByAccountNameChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		
		try { 									
			
			Account accountCopy = null;
			String accountName = "";
			
			java.util.Scanner keyboard = new java.util.Scanner(System.in);
			
			System.out.println("Please enter the account Name ");
			System.out.println("Account name can have alphanumeric values ");
			
			accountName = keyboard.nextLine(); 
			
			sendPrimitiveToServer.writeUTF(accountName);
			sendPrimitiveToServer.flush();
			
			int accountLength = receivePrimitiveFromServer.readInt();
			System.out.println(accountLength);
			 
				 if(accountLength > 0) {
					 for(int i=0; i< accountLength ; i++) {
						 
						// read an object from THE client
						accountCopy = (Account) objectFromServer.readObject();         // casting!
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					}
				 }
				  else { 
						
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					 }
					  
			 
		  } 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
				catch( ClassNotFoundException cnf ) { cnf.printStackTrace(); }
                     // thrown by readObject()										
								
	}
	
	//searchByAccountNumberChoice
	public static void searchByAccountNumberChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		
		try { 									
			
			Account accountCopy = null;
			String accountNumber = "";
			
			java.util.Scanner keyboard = new java.util.Scanner(System.in);
			
			System.out.println("Please enter the account Number ");
			System.out.println("Account number can have alphanumeric values ");
			
			accountNumber = keyboard.next(); 
			
			sendPrimitiveToServer.writeUTF(accountNumber);
			sendPrimitiveToServer.flush();
			
			String response = receivePrimitiveFromServer.readUTF();
			System.out.println(response);
		  
			 
		  } 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}									
								
	}
	
	//searchByAccountBalance choice
	public static void searchByAccountBalanceChoice(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		
		try { 									
			
			int accountBalance = 0;
			Account accountCopy = null;
			
			java.util.Scanner keyboard = new java.util.Scanner(System.in);
			
			System.out.println("\n-------------------------------------------");
			System.out.println("Please enter the account balance you wish to search ");
			System.out.println("********Format is :1000 ***************");
				
			accountBalance = keyboard.nextInt();
			
			sendPrimitiveToServer.writeInt(accountBalance);
			sendPrimitiveToServer.flush();
		
			int accountLength = receivePrimitiveFromServer.readInt();
			System.out.println(accountLength);
			 
				 if(accountLength > 0) {
					 for(int i=0; i< accountLength ; i++) {
						// read an object from THE client
						accountCopy = (Account) objectFromServer.readObject();         // casting!
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					}
				 }
				  else { 
						
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					 }
					  
			 
		  } 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
				catch( ClassNotFoundException cnf ) { cnf.printStackTrace(); }
                     // thrown by readObject()
														
	}
	
	public static void displayAccounts(DataOutputStream sendPrimitiveToServer,ObjectOutputStream objectToServer,
										DataInputStream receivePrimitiveFromServer,
										ObjectInputStream objectFromServer) {
		try { 
			
			Account accountCopy = null;
			
			//received accountlength from server
			int accountLength = receivePrimitiveFromServer.readInt();
			System.out.println(accountLength);
			
			
			if(accountLength > 0) {
				
					 for(int i=0; i < accountLength ; i++) {
						 
						// read an object from THE client
						accountCopy = (Account) objectFromServer.readObject();         // casting!
						
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					}
			}
				  else { 
						
						String response = receivePrimitiveFromServer.readUTF();
						System.out.println(response);
					 }
			
		}catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
				catch( ClassNotFoundException cnf ) { cnf.printStackTrace(); }
                     // thrown by readObject()
	}
	

				    
   /**
            The main method will be called by the JVM.

        	@param args the command-line arguments
          */

   public static void main(String[] args) {
	 
		
		Socket clientSocket;
		
		
		try {
				/* step 1: connect to the server
                        IP address/server name: "localhost"
                        port number:            5678
              */
				clientSocket = new Socket( InetAddress.getByName( "localhost" ),
                                        5678 );
				System.out.println( "Connected to " +
		                 clientSocket.getInetAddress().getHostName() );
						 
				/* step 2: connect input and output streams to the socket
                */

				DataOutputStream sendPrimitiveToServer = new DataOutputStream(clientSocket.getOutputStream());
		
				DataInputStream  receivePrimitiveFromServer= new DataInputStream( clientSocket.getInputStream() );
			    
			
				ObjectInputStream  objectFromServer= new ObjectInputStream( 
                                                   clientSocket.getInputStream() );

				ObjectOutputStream objectToServer = new ObjectOutputStream( 
                                                  clientSocket.getOutputStream() );
									

				 System.out.println( "I/O streams connected to the socket" );
				
				try {
				
						int option;
						do { 
						/* step3: Exchange data with server
						*/
						option=displayMenu();
						
						//sending option to server - whether to add, remove update etc.
						sendPrimitiveToServer.writeInt(option);
						sendPrimitiveToServer.flush();
					
						System.out.println("going to wait for response");
						
						String response = " ";
						
						//Receive confirmation status from server
						response = receivePrimitiveFromServer.readUTF();
						System.out.println("response from server is "+ response);
						
						//method to read from user and send info to server
						sendOption(option,sendPrimitiveToServer,objectToServer,
								  receivePrimitiveFromServer, objectFromServer);
						
						} while(option != 8);
						
				
		            } catch( EOFException eof ) { // loss of connection

												System.out.println( "The server has terminated connection!" ); }

					 catch(IOException e ) { 
											System.out.println( "I/O errors in data exchange" );
                                            e.printStackTrace(); 								
										}
				System.out.println( "Client: data exchange completed" );
			  
			     /* step 4: close the connection to the server
                */
				System.out.println( "Client: closing the connection..." );

				 sendPrimitiveToServer.close();
				 receivePrimitiveFromServer.close();
				 clientSocket.close();
	        }
				catch( IOException ioe ) { 
											System.out.println( "I/O errors in socket connection" );
											ioe.printStackTrace(); 
										}

			System.out.println( "... the client is going to stop running..." );
	
   
   }
   
}
