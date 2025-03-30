package hk.edu.polyu.comp.comp2021.assignment2.bankaccount;

/** A bank account object. */
public class BankAccount { 

    private int balance;

	/** Instantiate an account with 'initialBalance'. */
    public BankAccount(int initialBalance){
        if(initialBalance < 0)
            throw new IllegalArgumentException();

        balance = initialBalance;
    }

	/** Balance of the account. The balance should never be negative. */
    public int getBalance(){
        return balance;
    }

	/** Deposit 'amount' into this account. 'amount' should always be positive. */
    public synchronized void deposit(int amount){
        if(amount <= 0)
            throw new IllegalArgumentException();

        // Task 4.1: Add missing code here.
        balance+=amount;
        notifyAll();


    }

	/** Withdraw 'amount' from this account. 'amount' should always be positive. */
    public synchronized void withdraw(int amount){
        if(amount <= 0)
            throw new IllegalArgumentException();

        // Task 4.2: Add missing code here.
        while(balance<amount){

            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                throw new IllegalArgumentException();
            }

        }

        balance-=amount;

    }
}
