/*A banking server is a Java object that provides banking services to multiple clients over the Internet. When a banking server is started to run at port 5678, it creates a Bank object and loads pre-defined Account objects (i.e. account information) into the Bank object. It then listens for client connections. Whenever the server knows that a new client wants to establish a network connection with it, it uses a Java thread to process all the service requests from the particular client. 
 
 Following links are used to implement some concepts in the program.
https://docs.oracle.com/javase/tutorial/java/package/namingpkgs.html

*/


package edu.jav745.f2017.server;
import com.seneca.f2017.data.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class BankingServer {
	
	private Bank bankObj;
	
	//Banking Server Constructor
	public BankingServer(Bank bankObject) {
		
		bankObj = bankObject;
	}
	
	public void start(int portNumber) {
		
		ServerSocket serverSocket;		// TCP socket used for listening
		
		
		try {
			   /* step 1: create a server socket
							port number:    5678
				*/

			   serverSocket = new ServerSocket( portNumber );         // create a server socket
																// with port number

			  /* setp 2: a loop that listens for connections and
                                    creates THREADS with sockets
              */
			  
				int count=1;
				while (true) { 
				
					System.out.println( "listening for a connection..." );

					Socket socketConnection
							 = serverSocket.accept();  // listen and wait
													   // socketConnection: a TCP socket
													  // that is connected to the client
					System.out.println("Start a thread for a client " + count );
					System.out.println("\n Hostname : " + 
										socketConnection.getInetAddress().getHostName() + 
										"\n Client IP address : " + 
									    socketConnection.getInetAddress().getHostAddress());
										
					//passing socket connection details to each thread.
					//We create a class to 	Execute the thread.
					Thread t = new ServiceThread( socketConnection, bankObj );
					t.start();

					count++;
				}
			}	
		     catch(IOException e ) {    
										// I/O error in socket connection
										System.out.println( "I/O error in socket connection" );
										e.printStackTrace();     
									}

			 System.out.println( "*** the server is going to stop running! ***" );
		}
}
			 
class ServiceThread extends Thread { 
			
	private Socket connection;
	private Bank bankObj;
	//to stop thread execution
	private volatile boolean running = true;
				
	//constructor to initialize  socket connection.
	//ie whenever an object to ServiceThread is created it passes
	// the socket connecttion parameters to each thread.
	public ServiceThread(Socket sock, Bank bank) {
		connection = sock; 
		bankObj = bank;
	}		
	
				
	//implementing run method - This is the general tasks to be execute by each thread.
	public void run() {
		
		try {
				Account account = null;
				boolean running = true;
							
							/* step 3: connect input and output streams to the socket
						  */
						DataOutputStream sendToClient = new DataOutputStream( 
															connection.getOutputStream() );

						DataInputStream  receiveFromClient = new DataInputStream(
															  connection.getInputStream() );
						ObjectOutputStream objectToClient = new ObjectOutputStream( 
															 connection.getOutputStream() );

						ObjectInputStream  objectFromClient = new ObjectInputStream( 
															   connection.getInputStream() );

						System.out.println( "I/O streams connected to the socket" );
					 
					 
					 /* step 4: exchange data with THE client
						  */
						  
							try {
								 while(running) {  // keep on getting data from the client
												
												int option = receiveFromClient.readInt();
												System.out.println("***Received option " +
															option + " from the client***	");
															
												String response = "Got Your option!!";
												// send the data to THE client
												sendToClient.writeUTF(response);
												sendToClient.flush(); 
										
												running=receivedOptionMenu(option,receiveFromClient,
																	objectFromClient,
																	sendToClient,objectToClient);
												
												
										 }
										 //display the result to the screen of the server program
												System.out.println( "\t*** transaction complete "); 
							} 	catch( EOFException eof ) {  // loss of connection
									System.out.println( "*** THE client has terminated connection ***" );
								}
								

								catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
							  /* step 5: close the connection to the client
							  */
							sendToClient.close();
							receiveFromClient.close();
							connection.close();	
						
			} 	catch( IOException e) {    
				// I/O error in socket connection
				System.out.println( "I/O error in socket connection" );
				e.printStackTrace();     
			}
	} //end run
	public boolean receivedOptionMenu(int option, DataInputStream receiveFromClient,
											ObjectInputStream objectFromClient,
										DataOutputStream sendToClient,ObjectOutputStream objectToClient) {
		boolean running = true;
		try { 
			switch(option) 
			{
				case 1: addAccountChoice(receiveFromClient,
										 objectFromClient, 
										 sendToClient, 
										 objectToClient); 
						break;
				case 2: removeAccountChoice(receiveFromClient,
											objectFromClient, 
											sendToClient, 
											objectToClient);
						break;
				case 3: updateAccountChoice(receiveFromClient,
											objectFromClient, 
											sendToClient, 
											objectToClient);
						break;		
				case 4:	searchByAccountNameChoice(receiveFromClient,
										objectFromClient, sendToClient, objectToClient);
						break;
				case 5: searchByAccountNumberChoice(receiveFromClient,
											objectFromClient, sendToClient, objectToClient);
						break;
				case 6: searchByAccountBalance(receiveFromClient,
								objectFromClient, sendToClient, objectToClient);
						break;
				case 7: displayAccounts(receiveFromClient,
									objectFromClient, sendToClient, objectToClient);
						break;
				case 8: running = false;
						break;
				default: running = false;
						break;
			}
		
	} catch(Exception e) { 
				
			System.out.println("Oops!!! Something wrong happened in Server!!!");
			System.out.println("More info : ");
			System.out.println(e.getMessage());
			System.out.println("Please run the app again!");
		
		}
		return running;
	}

	
	//Method to display all the accounts in the bank object
	public void displayAccounts(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
			try { 
				
				Account[] accountCopy = this.bankObj.getAccounts();
				
				sendToClient.writeInt(accountCopy.length);
				sendToClient.flush();
				
					if(accountCopy.length > 0)
					{
						System.out.println("\n-------------------------------------------");
						
						for(int i = 0; i < accountCopy.length; i++) {
							
							if(accountCopy[i] != null){
								
								// send an object to the server
								objectToClient.writeObject( accountCopy[i] ); 
								objectToClient.flush();
					 
								String output = StringManipulation(accountCopy[i].getAccountNumber(), 
												accountCopy[i].getFullname(),
												accountCopy[i].getAccountBalance());
					
								sendToClient.writeUTF(output);
								sendToClient.flush();
							}
							
						}
					} else {  
							
							sendToClient.writeUTF( " Bank object is empty!! " ); 
							sendToClient.flush();
					}
						  
				}catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}

								catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
							
	
	 }
	 
	 //Method to add an account to the bank object by the user enterd details.
	public boolean addAccountChoice(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {

		Account account =null; 
		boolean accountDone=false;
		int balance = 0;
		try { 
		
		// read an object from THE client
		account = (Account) objectFromClient.readObject();// casting!
		System.out.println( "\n*** received an object" +
											"from the CLIENT:\n " + account );
		
	//Calling the addAccount method in bank class with passing proper elements from the list 		
		accountDone=this.bankObj.addAccount(account);
		
		
		 if(accountDone) {
			
			sendToClient.writeUTF("\nAccount added: \n ");
			sendToClient.flush();
			String output=StringManipulation(account.getAccountNumber(), 
										account.getFullname(),
										account.getAccountBalance());
			
			sendToClient.writeUTF(output); 
			sendToClient.flush();
			
		} else {	
			sendToClient.writeUTF("Account : " + account.getAccountNumber()); 
			sendToClient.flush();
			sendToClient.writeUTF("Cannot be added !!\n");
			sendToClient.flush();
		}  
		
		} 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
								catch( ClassNotFoundException cnf) { 
								cnf.printStackTrace(); 
								}

								catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
								}
		return accountDone;
	}		
	
	
	
	//remove an account
	public void removeAccountChoice(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
		
		Account binCopy = null;
		String accountNumber = "";
		try {
       
			accountNumber=receiveFromClient.readUTF();
			binCopy = this.bankObj.removeAccount(accountNumber);
			
			if (binCopy != null) {
			
				sendToClient.writeUTF("\nAccount removal successful ");	
				sendToClient.flush();
				String output=StringManipulation(binCopy.getAccountNumber(), 
										binCopy.getFullname(),
										binCopy.getAccountBalance());
				
				sendToClient.writeUTF(output);
				sendToClient.flush();
			} else {
					
				sendToClient.writeUTF("Account " + accountNumber); 
				sendToClient.flush();
				sendToClient.writeUTF("Cannot be removed !!\n");
				sendToClient.flush();
			}	
			
		} 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
						

			catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
						}
			
	}	
	
	//Update account - deposit function is implemented
   
	public void updateAccountChoice(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
		try { 
				int amount = 0, option = 0;
				Account accountCopy = null;
				boolean updateStatus = false;
				String accountNumber = "";
				
				option = receiveFromClient.readInt();
				amount=  receiveFromClient.readInt();
				accountNumber = receiveFromClient.readUTF();
				System.out.println("option is " + option);
				System.out.println("amount is " + amount);
				System.out.println("account number is " + accountNumber);
				switch (option) {
					case 1 : 	System.out.println(" here -- deposit");
								accountCopy = this.bankObj.depositAccount(accountNumber,amount);
								break;
					case 2 : 	accountCopy = this.bankObj.withdrawAccount(accountNumber,amount);
								break;
				} 
				
					if(accountCopy != null )
					{
						updateStatus = true;
			
					}
					else { 
							System.out.println("boolean status " + updateStatus);
							sendToClient.writeUTF("\n Updation not successful!!..");
							sendToClient.flush();
							sendToClient.writeUTF("\n Please start from top!!..");
							sendToClient.flush();
					}
					
					if(updateStatus) {
							sendToClient.writeUTF("\nAccount updated: \n ");	
							sendToClient.flush();
						
							String output = StringManipulation( accountCopy.getAccountNumber(),  
											accountCopy.getFullname(),
											accountCopy.getAccountBalance());
							sendToClient.writeUTF(output);	
							sendToClient.flush();				
						
					}
			} 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
						

			catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
						}
			
	}
		    

   
   //Search by AccoutName
	public void searchByAccountNameChoice(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
		
		
			Account[] accountCopy = null;
			try { 
				
				String accountName = receiveFromClient.readUTF();
				accountCopy = this.bankObj.searchByAccountName(accountName);
				
				sendToClient.writeInt(accountCopy.length);
				sendToClient.flush();
				
				if(accountCopy.length > 0)
				{
					System.out.println("\n-------------------------------------------");
					
					for(int i = 0; i < accountCopy.length; i++) {
						
						if(accountCopy[i] != null){
							
							// send an object to the server
							objectToClient.writeObject( accountCopy[i] ); 
							objectToClient.flush();
				 
							String output = StringManipulation(accountCopy[i].getAccountNumber(), 
											accountCopy[i].getFullname(),
											accountCopy[i].getAccountBalance());
				
							sendToClient.writeUTF(output);
							sendToClient.flush();
						}	
					}	
				} else {
					sendToClient.writeUTF("Sorry! The account with the entered name not found!");
					sendToClient.flush();
				}
			} 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
						

			catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
						}
			
			    
	}	
   
     //Search by AccoutNumber
	public void searchByAccountNumberChoice(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
		
		
			Account accountCopy = null;
			try { 
				
				String accountNumber = receiveFromClient.readUTF();
				accountCopy = this.bankObj.searchByAccountNumber(accountNumber);
						
						if(accountCopy != null){
							
							String output = StringManipulation(accountCopy.getAccountNumber(), 
											accountCopy.getFullname(),
											accountCopy.getAccountBalance());
				
							sendToClient.writeUTF(output);
							sendToClient.flush();
					
						} else {
							sendToClient.writeUTF("Sorry! The account with the entered number not found!");
							sendToClient.flush();
						}
			} 	catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
						
				catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
						}			    
	}


	//Search by Account Balance
	public void searchByAccountBalance(DataInputStream receiveFromClient,
								ObjectInputStream objectFromClient,DataOutputStream sendToClient,
								ObjectOutputStream objectToClient) {
		
			Account[] accountCopy = null;
			try{ 
				int accountBalance = receiveFromClient.readInt();
				
				accountCopy= this.bankObj.searchByAccountBalance(accountBalance);
				
				sendToClient.writeInt(accountCopy.length);
				sendToClient.flush();
				
				if(accountCopy.length > 0)
				{
					/*System.out.println("Please find below account(s) details matching your input: \n");*/
					
					for(int i = 0; i < accountCopy.length; i++) {
						if(accountCopy[i] != null){
							
							// send an object to the server
								objectToClient.writeObject( accountCopy[i] ); 
								objectToClient.flush();
								
							String output = StringManipulation(accountCopy[i].getAccountNumber(), 
											accountCopy[i].getFullname(),
											accountCopy[i].getAccountBalance());
				
							sendToClient.writeUTF(output);
							sendToClient.flush();
						}
					}
					
					
				} else {
						sendToClient.writeUTF("Sorry! The account with the entered balance not found!");
						sendToClient.flush();
				}
			}catch( EOFException eof ) {  // loss of connection
								System.out.println( "*** THE client has terminated connection ***" );
								}
						

			catch(IOException e ) {  // I/O error in data exchange
									System.out.println( "I/O error in data exchange" );
									e.printStackTrace();	
						}
	}
		
      // A string manipulation function
	 // that will read name, number and balance using Stringbuffer concept
	 public static String StringManipulation(String number, String name, int balance) {
		 
			StringBuffer detailsOfAccount = new StringBuffer( "Account Number : ");
						 detailsOfAccount.append( number );
						 detailsOfAccount.append( " Account Name : ");
						 detailsOfAccount.append( name );
						 detailsOfAccount.append( " Current Balance : ");
						 detailsOfAccount.append("$");
						 detailsOfAccount.append( balance );
						 detailsOfAccount.append("\n");
			
			return (detailsOfAccount.toString());
		 
	 }
} //end ServiceThread
				