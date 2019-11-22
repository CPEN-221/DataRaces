package dataraces;

// This class has a race condition in it.
public class Computation {
    
    private boolean ready = false;
    private int answer = 0;
    
    // computeAnswer runs in one thread
    private void computeAnswer() {
        answer = 42;
        ready = true;
    }
    
    // useAnswer runs in a different thread
    private void useAnswer() {
        while (!ready) {
            Thread.yield();
        }
        if (answer == 0) throw new RuntimeException("answer wasn't ready!");
    }
    
    // number of trials to run the computation
    private static final int TRIES = 100;
    
    public static void main(String[] args) {
        for (int i = 0; i < TRIES; ++i) {
            final Computation e = new Computation();
            
            new Thread(new Runnable() {
                public void run() {
                    e.useAnswer();
                }
            }).start();
            
            new Thread(new Runnable() {
                public void run() {
                    Thread.yield();
                    e.computeAnswer();
                }
            }).start();
        }
    }
}
