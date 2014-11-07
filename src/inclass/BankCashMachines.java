package inclass;

import java.util.ArrayList;
import java.util.List;

// This class has a race condition in it.
public class BankCashMachines {

    // the amount of money in the account, in dollars
    private static int balance = 0;

    /**
     * Modifies this account by depositing 1 dollar.
     */
    public static void deposit() {
        balance++;
    }

    /**
     * Modifies this account by withdrawing 1 dollar.
     */
    public static void withdraw() {
        balance--;
    }
    
    // simulate a network of cash machines, handling customer transactions concurrently
    private static final int NUMBER_OF_CASH_MACHINES = 20;
    private static final int TRANSACTIONS_PER_MACHINE = 1000;
    
    // each ATM does a bunch of transactions that should
    // leave the account balance unchanged
    private static void cashMachine() {
        for (int ii = 0; ii < TRANSACTIONS_PER_MACHINE; ++ii) {
            deposit(); // put a dollar in
            withdraw(); // take it back out
            //System.out.println(balance);  // uncomment this to make the bug magically disappear! (it's not really gone though)
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting balance is $" + balance);

        System.out.println("... then " 
                            + NUMBER_OF_CASH_MACHINES 
                            + " cash machines do "
                            + TRANSACTIONS_PER_MACHINE
                            + " $1-deposit/$1-withdrawal transactions each...");

        // simulate each cash machine with a thread
        List<Thread> threads = new ArrayList<Thread>();
        for (int ii = 0; ii < NUMBER_OF_CASH_MACHINES; ++ii) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    Thread.yield();  // give the other threads a chance to start too, so it's a fair race
                    cashMachine();   // do the transactions for this cash machine
                }
            });
            t.start(); // don't forget to start the thread!
            threads.add(t);
        }
        
        // wait for all the threads to finish
        for (Thread t: threads) t.join(); 
        
        System.out.println("final account balance: $" + balance); 
    }
}
