// learning objectives
//   - Java API
//   - Java class constructor

/* Note: a Java program is built on classes
*This program creates a class in which
*various types of constructors are defined
*Setters and getters are also used to get and set values to object variables
 */

/**
       This is a javadoc comment. It documents classes and methods.
       Below is the Account class defined with
			 @param fullname
			 @param account number and
			 @param Current Account Balance.
			 3 setters and getters are defined as follows
			 setFullName, setAccountNumber and setAccountBalance.
			 getFullName, getAccountNumber and getAccountBalance.
			 other methods are withdraw and deposit where account 
			 get updated with the amount value.


       @version 1.0
       @author  Anaswara Naderi Vadakkeperatta
       @see     "Sample program Employee for lab1"

 */
package com.seneca.f2017.data;
import java.util.*;
import java.util.ArrayList;
import java.io.*;

public class Account implements Serializable {

	//declaration of variables
	private String fullName;
	private String accountNumber;
	private int currentAccountBalance;

	//zero argument constructor
	public Account() {
		this(null, null, 0);
  }


	//Constructor defintion
	public Account(String name, String accNo, int balance) {
	
		setFullname(name);
		setAccountNumber(accNo);
		setAccountBalance(balance);
	}

	//Setters are defined below
	public void setFullname(String fullName) {
		if (fullName != null) {
			this.fullName = fullName;
		}
	}

	public void setAccountNumber(String accountNumber) {
		if (accountNumber != null) {
			this.accountNumber = accountNumber;
		}
	}

	public void setAccountBalance(int currentAccountBalance) {
		if (currentAccountBalance < 0 ) {
				this.currentAccountBalance = -1;
		} else {
			this.currentAccountBalance = currentAccountBalance;
		}
	}

	//getters are defined below
	public String getFullname() {
		return fullName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public int getAccountBalance() {
		return currentAccountBalance;
  }
  
   //These two methods update the balance of an account 
    boolean deposit( int amount) {
	
	boolean retValue = false; 
	 //updates currentbalance
		
	if ( amount < 0 ) {
		
		retValue = false;
    } else {
		currentAccountBalance = this.currentAccountBalance + amount ;
		retValue = true ; 
	}
	return retValue;
  }
  
   boolean withdraw( int amount) {
    
		boolean value = false; 
			
		if (currentAccountBalance < 0 || amount < 0 || (currentAccountBalance-amount < 0)) {
		
			value = false;
		} else {
			//updates currentbalance
			currentAccountBalance = this.currentAccountBalance - amount ;
			value = true ; 
		}
	return value;
   }
   
	//toString method 
	
	public String toString() {

		StringBuffer accInfo = new StringBuffer("*****************************************\n");
					accInfo.append("*           Account Information        *\n");
					accInfo.append("*****************************************\n");
					accInfo.append("name: ");
					accInfo.append(fullName);
					accInfo.append("\n");
					accInfo.append("Account number: ");
					accInfo.append(accountNumber);
					accInfo.append("\n");
					accInfo.append("current Account Balance: ");
					accInfo.append(currentAccountBalance);
					accInfo.append("\n");
					
        return (accInfo.toString());
    }

		//equals method
	public boolean equals(Object z) {

			boolean result = false;

			if (z instanceof Account) {
				Account z2 = (Account) z;

				if ((z2.fullName.equals(fullName)) &&
						(z2.accountNumber.equals(accountNumber)) &&
						(z2.currentAccountBalance == currentAccountBalance))
						result = true;
			}
			return result;
	}

	public static void main(String[] args) {
			
				Account obj1, obj2, obj3;
				boolean result;
						

				obj1 = new Account("Anaswara Naderi Vadakkeperatta", "A12345", 7890);
				obj2 = new Account("Anaswara Naderi Vadakkeperatta", "A12345", 7890);
				obj3 = new Account("Sreekumar Rajan", "A12346", 7891);
				System.out.println(obj1);
				System.out.println(obj2);
				System.out.println(obj3);

				if(obj1.equals(obj2)) {

					System.out.println("Two account objects obj1 and obj2  are similar");
				}else{

					System.out.println("Two account objects obj1 and obj2  are having different contents");
				}

				if(obj1.equals(obj3)) {

					System.out.println("Two account objects obj1 and obj3 are similar");
				}else{

					System.out.println("Two account objects obj1 and obj3 are having different contents");
				}
				
				//checking deposit function
				result=obj1.deposit(-7891);
				if ( result )
				{
					System.out.println("Account updation successful");
					System.out.println(obj1);
				}else {
					System.out.println("Account updation Not successful");
				}
				
				result=obj1.withdraw(560);
				if ( result )
				{
					System.out.println("Account updation successful");
					System.out.println(obj1);
				}else {
					System.out.println("Account updation Not successful");
				}
				result=obj1.deposit(400);
				if ( result )
				{
					System.out.println("Account updation successful");
					System.out.println(obj1);
				}else {
					System.out.println("Account updation Not successful");
				}
				
				result=obj2.withdraw(-1230);
				if ( result )
				{
					System.out.println("Account updation successful");
					System.out.println(obj1);
				}else {
					System.out.println("Account updation Not successful");
				}

	}

}
