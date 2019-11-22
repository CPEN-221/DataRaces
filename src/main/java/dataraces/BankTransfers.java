package beforeclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// This class encounters deadlock.
public class BankTransfers {
    
    // lock for each account
    private static final Object[] locks = new Object[] { new Object(), new Object() };
    
    // amount of money in each account, in dollars
    private static final int[] balances = new int[] { 1000, 1000 };
    
    /**
     * Transfers 1 dollar from account # source to account # destination.
     */
    public static void transfer(int source, int destination) {
        synchronized(locks[source]) {
            synchronized(locks[destination]) {
                balances[source]--;
                balances[destination]++;
                System.out.println(Arrays.toString(balances));
            }
        }
    }
    
    // simulate concurrent automated transfers between accounts
    private static final int TRANSFERS = 10;
    
    // do a bunch of transfers from source to destination
    private static void automatedTransfers(int source, int destination) {
        for (int ii = 0; ii < TRANSFERS; ii++) {
            transfer(source, destination);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("starting balances in $: " + Arrays.toString(balances));
        
        System.out.println("... then we do "
                            + TRANSFERS
                            + " transfers from each account to the next...");
        
        // simulate automated transfers on several threads
        List<Thread> threads = new ArrayList<Thread>();
        for (int ii = 0; ii < balances.length; ii++) {
            final int source = ii;
            final int destination = (ii + 1) % balances.length;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    Thread.yield(); // give the other threads a chance to start, too
                    automatedTransfers(source, destination);
                }
            });
            t.start(); // don't forget to start the thread!
            threads.add(t);
        }
        
        // wait for all the threads to finish
        for (Thread t: threads) t.join();
        
        System.out.println("final balances in $: " + Arrays.toString(balances));
    }
}
