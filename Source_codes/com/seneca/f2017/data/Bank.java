
/*
* This program creates a class in which
* a bank class is created to read the bankName
* User is able to add, remove, searchbyAccountName, number and balance.
* Methods are defined accordingly.
*/

/**
       This is a javadoc comment. It documents classes and methods.
       Below is the Bank class defined with
			@param bankName
			@param currentNumberOfAccounts
			@param accounts list 

    Methods that are defined are:
		public void setBankname(String bankName)
		public String getbankName()
		public int getcurrentNumberofAccounts()
		private boolean isExistingAccount(Account newAccount)
		public boolean addAccount(Account newAccount)
		public Account[] searchByAccountName( String accountName )
		public Account searchByAccountNumber( String accountNumber )
		public Account[] searchByAccountBalance( String accountBalance)
		public Account removeAccount( String accountNumber )
		public Account depositAccount(String accountNumber, int amount)
		public Account withdrawAccount(String accountNumber, int amount)
		
		
       @version 1.0
       @author  Anaswara Naderi Vadakkeperatta

 */
package com.seneca.f2017.data;
import java.util.*;
import java.util.ArrayList;
import java.io.*;

public class Bank {

  // bank class private variable declaration
  private String bankName;
  private int currentNumberOfAccounts;
  private ArrayList<Account> accounts;

  // default bank constructor
  public Bank() { this("Seneca@york"); }

  // bank constructor
  //public Bank(String bankName, int maxAccounts){
  public Bank(String bankName){

    this.bankName = bankName;
	accounts = new ArrayList<Account>();
    this.currentNumberOfAccounts = 0;
  }

  public void setBankname(String bankName) {

    if(bankName != null) {
      this.bankName = bankName;
    }
  }


  public String getbankName() {
		return bankName;
	}
	
	public int getcurrentNumberofAccounts() {
		return currentNumberOfAccounts;
	}
	
	public Account[] getAccounts() {
		return this.accounts.toArray(new Account[this.accounts.size()]);
	}
	
  // Defining a method to check whether an accountnumber is existing or not.
  // Bank with Null account is also handled
  public boolean isExistingAccount(Account newAccount) {
	  
    if (this.accounts.size() == 0) {   
	  return false;  
    } else {
		
      for (int i = 0; i < accounts.size(); i++) {
		  
        if (this.accounts.get(i).getAccountNumber().equals(newAccount.getAccountNumber())) {
			
		  return true; 
        
		}

      }
	  
      return false;
    }
  }

  // below method adds new accounts to the bank object
  public boolean addAccount(Account newAccount) {
	  
    if (newAccount == null) {
      return false;
    }

    if (!isExistingAccount(newAccount)) {
		
      this.accounts.add(newAccount);
      this.currentNumberOfAccounts++;
	  
    } else {
	
        return false;
	  
    }

    return true;
  }

//Method to search the account by AccountName
    public Account[] searchByAccountName( String accountName ) {
		
		ArrayList <Account> accountArray= new ArrayList <Account>();
		String currentName = " ";
		
		for(int i=0; i < this.accounts.size() ; i++){
			
			currentName = this.accounts.get(i).getFullname();
			if(currentName.toLowerCase().equals(accountName.toLowerCase()))
				
			{
				accountArray.add(this.accounts.get(i));
			} 
		}
	
		return accountArray.toArray(new Account[accountArray.size()]);
		
	}

	//Method to search an account in the bank object by account number
     public Account searchByAccountNumber( String accountNumber ){
		
			//ArrayList <Account> accountArray= new ArrayList <Account>();
			Account accountCopy = null;
			for(int i=0;i < this.accounts.size() ; i++){
				
				if((this.accounts.get(i).getAccountNumber()).equals(accountNumber))	
				{
					accountCopy= this.accounts.get(i);
					//System.out.println(this.accounts.get(i));
				}
			}
			return accountCopy;
			
	}

	//Account to search accounts by the value of balance 
    public Account [] searchByAccountBalance( int accountBalance ){

		
			ArrayList <Account> accountArray= new ArrayList <Account>();
			for(int i=0;i < this.accounts.size() ; i++){
				if((this.accounts.get(i).getAccountBalance())== accountBalance)
					
				{
					accountArray.add(this.accounts.get(i));
					
				}
			}
		
			return accountArray.toArray(new Account[accountArray.size()]);
	}

	//Method to implement removal of account by the user enterd Account number 
	public Account removeAccount( String accountNumber ){
		
			Account binCopy = null;
			
			for(int i=0;i < this.accounts.size() ; i++) {
				if((this.accounts.get(i).getAccountNumber()).equals(accountNumber))
					
				{
		
					binCopy=this.accounts.get(i);
					this.accounts.remove(i);
					break;
				}
			}
			return (binCopy);
	} 

	//deposit an amount to the account - account number and amount is asked from user
	public Account depositAccount(String accountNumber, int amount) {
	
			boolean updateStatus= false;
			Account accountCopy = null;
			
			for(int i=0;i < this.accounts.size() ; i++) {
				if((this.accounts.get(i).getAccountNumber()).equals(accountNumber))
				
				{
					updateStatus=this.accounts.get(i).deposit(amount);
					if(updateStatus) {
					
						accountCopy = this.accounts.get(i);
					} 
					
				}
			
			}
			System.out.println(accountCopy);
			return accountCopy;
	} 
	 
	  
	//deposit an amount to the account - account number and amount is asked from user
	  public Account withdrawAccount(String accountNumber, int amount) {
	
			boolean updateStatus= false;
			Account accountCopy = null;
			
			for(int i=0;i < this.accounts.size() ; i++) {
				if((this.accounts.get(i).getAccountNumber()).equals(accountNumber))
					
				{
					updateStatus=this.accounts.get(i).withdraw(amount);
					
					if(updateStatus) { 
					
						accountCopy = this.accounts.get(i);						
					}
					
				}
			}
			
			return accountCopy;
	} 
	  
  
	
  // Tostring method to display in specific format
  public String toString() {
	
    StringBuffer s1 = new StringBuffer("******************************************\n" +
									   "*** Welcome to the Bank of ");
				s1.append(bankName);
				s1.append(" *** \n");
                s1.append("It has ");
				s1.append(accounts.size());
				s1.append(" account(s).\n");
				s1.append("******************************************\n");

    if (accounts.size() > 0) {

      for(int i=0; i < accounts.size() ; i++) {

         s1.append((i+1) + ". number : ");
		 s1.append(accounts.get(i).getAccountNumber());
	     s1.append(", ");
		 s1.append("name :");
		 s1.append(accounts.get(i).getFullname());
		 s1.append(", ");
		 s1.append("balance : ");
		 s1.append("$"); 
		 s1.append(accounts.get(i).getAccountBalance());
		 s1.append("\n");
		
       }
    }
	
    return (s1.toString()) ;

  }
  
	//equals method to check whether two banks are equal by checking 
	//bankname,current number of accounts,and accounts arrayList(need to be identical)
		public boolean equals(Object z) {

			boolean result = false;

			if (z instanceof Bank) {
				Bank z2 = (Bank) z;

				if (z2.accounts.size()== accounts.size()) { //new
					for (int i = 0 ;  i < accounts.size() ; i++){
					
						if ((z2.accounts.equals(accounts)) &&
								(z2.bankName.equals(bankName)) &&
								(z2.currentNumberOfAccounts == currentNumberOfAccounts))
					
							result = true;
				    } 
				} 
			
			}
			return result;
		}


	//for testing
	public static void main(String[] args) {
		
		Bank bank1, bank2, bank3,bank4;
		boolean accountDone;
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		Account[] accountArray = null;
		Account accountCopy = null;
		Account bin = new Account();
		int maxAccountsInBank2 = 3;

		// String array objects
		String accountNames [] = {"John Doe", "John Doe", "Mary Ryan", "Peter Liu" };
		String accountNames2 [] = {"John Doe", "John", "Mary Ryan", "Liu" };
		String accountNumbers []= {"A1234", "A1234", "B5678", "C9999"};
		int accountBalances []= {1000, 2000, 3000, 5000};

		// Displaying values of empty bank object
		bank1 = new Bank();
		System.out.println(bank1);
		
		bank2 = new Bank("Naderi Vadakkeperatta, Anaswara");
		bank3 = new Bank("Naderi Vadakkeperatta, Anaswara"); //to check equality
		bank4 = new Bank("Naderi Vadakkeperatta, Anaswara"); //to check equality

		//adding account details to the bank2 object
		for (int i = 0; i < 4; i++) {
		  bank2.addAccount( new Account(accountNames[i], accountNumbers[i], accountBalances[i]) );
		}
		System.out.println(bank2);
		
		for (int i = 0; i < 4; i++) {
		  bank3.addAccount( new Account(accountNames2[i], accountNumbers[i], accountBalances[i]) );
		}
		for (int i = 0; i < 4; i++) {
		  bank4.addAccount( new Account(accountNames[i], accountNumbers[i], accountBalances[i]) );
		}
		System.out.println("displaying bank2 " + "/n" +bank2);
		System.out.println("displaying bank3 " + "/n" +bank3);
		System.out.println("displaying bank4 " + "/n" +bank4);
		
		//created bank2 and bank3 with similar contents
		//checking equals method for differnet bank objects
		if(bank2.equals(bank3)) {

					System.out.println("Two bank objects obj1 and obj2  are similar");
				}else{

					System.out.println("Two bank objects obj1 and obj2  are having different contents");
		}
		//checking equals method for simlar bank objects
		if(bank2.equals(bank4)) {

					System.out.println("Two bank objects obj1 and obj2  are similar");
				}else{

					System.out.println("Two bank objects obj1 and obj2  are having different contents");
		}
		
		
		
		bank2.addAccount(new Account("Haari","W3422",8799));
		System.out.println(bank2);
		bank2.addAccount(new Account("Haaritha","W3422",8790));
		System.out.println(bank2);
		bank2.addAccount(new Account("Haari","W3445",7000));
		System.out.println(bank2);
		
		//All account info are same.
		bank2.addAccount(new Account("Haari","W3422",8799));
		System.out.println(bank2);
		
		//deposit function
		System.out.println("bank2.depositAccount(W3422, -1000)");
		accountCopy=bank2.depositAccount("W3422", -1000);
		
		//deposit function
		System.out.println("bank2.depositAccount(W3422, 1000)");
		accountCopy=bank2.depositAccount("W3422", 1000);
		System.out.println("After deposit :");
		System.out.println(bank2);
		
		//Implementing deposit account
		System.out.println("depositaccount( 1000 )");
		accountCopy=bank2.depositAccount("W3422", 1000 );
		
		System.out.println("Bank info:");
		System.out.println(bank2);
		
		//Implementing withdraw account
		System.out.println("withdrawAccount( W3445, 1000 )");
		accountCopy=bank2.withdrawAccount("W3445", 1000 );
		/* System.out.println("updated accounts");
		for (int i=0;i< accountArray.length;i++) {
			System.out.println(accountArray[i]);
		} */
		
		System.out.println("Bank info:");
		System.out.println(bank2);
		
		//withdraw function
		System.out.println("bank2.withdrawAccount(W3445, -1000)");
		bank2.depositAccount("W3445", -1000);
		
		//Implementing searchbyaccountname 
		// ArrayList<Account> accountArray = Arrays.asList(bank2.searchByAccountName( "Hsjd" ));
		System.out.println("searchByAccountName( Hsjd)");
		accountArray=bank2.searchByAccountName( "Hsjd" );
		for (int i=0;i< accountArray.length;i++) {
			System.out.println(accountArray[i].toString());
		}
		System.out.println("searchByAccountName( John Doe)");
		accountArray=bank2.searchByAccountName("John Doe");
		for (int i=0;i< accountArray.length;i++) {
			System.out.println(accountArray[i].toString());
		}
		
		//Implementing searchbyaccountname 
		System.out.println("searchByAccountNumber( B5678)");
		accountCopy=bank2.searchByAccountNumber( "B5678" );
		/* for (int i=0;i< accountArray.length;i++) {
			System.out.println(accountArray[i]);
		} */
		System.out.println(accountCopy);
		System.out.println("searchByAccountNumber( B567)");
		accountCopy=bank2.searchByAccountNumber( "B567" );
		//for (int i=0;i< accountArray.length;i++) {
			System.out.println(accountCopy);
		//}
		
		//Implementing remove account
		System.out.println("removeAccount( null )");
		bin=bank2.removeAccount( null );
		System.out.println("removed account is\n" + bin);
		
		//Implementing remove account
		System.out.println("removeAccount( null )");
		bin=bank2.removeAccount("W3445" );
		System.out.println("removed account is\n" + bin);
		System.out.println("Bank info:");
		System.out.println(bank2);
		
		
		
		
	}
}