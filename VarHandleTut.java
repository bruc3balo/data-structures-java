import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleTut {
    private static long counter;  // The variable we'll access using VarHandle

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // Obtain a VarHandle for the 'counter' variable
        VarHandle counterHandle = MethodHandles.lookup().findStaticVarHandle(VarHandleTut.class, "counter", long.class);

        // Perform atomic updates on 'counter' using VarHandle
        System.out.println("Initial counter: " + counter);

        // Atomic add (similar to counter++)
        counterHandle.getAndAdd( 1);
        System.out.println("Counter after getAndAdd: " + counter);

        // Atomic compare and set (if counter == 1, set to 10)
        boolean success = counterHandle.compareAndSet( 1, 10);
        System.out.println("Compare and Set success: " + success);
        System.out.println("Counter after compareAndSet: " + counter);

        // Atomic set
        counterHandle.set( 100);
        System.out.println("Counter after set: " + counter);

        // Atomic get and set (returns old value and sets to new value)
        long oldValue = (long) counterHandle.getAndSet( 200);
        System.out.println("Old value: " + oldValue);
        System.out.println("Counter after getAndSet: " + counter);
    }


}
