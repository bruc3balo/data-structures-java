import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandle;

public class MethodHandleTut {

    public static void main(String[] args) throws Throwable {
        // Create a MethodHandles.Lookup object for current class
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // Find the virtual method "length" in the String class
        MethodHandle mh = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));

        // Use the method handle to invoke the method on a String object
        int length = (int) mh.invoke("Hello, World!");
        System.out.println("Length of the string: " + length);  // Output: 13

        // Bind a specific value to the "substring" method
        MethodHandle substringMH = lookup.findVirtual(String.class, "substring", MethodType.methodType(String.class, int.class));
        MethodHandle boundMH = substringMH.bindTo("Hello, World!");
        System.out.println((String) boundMH.invoke(7));  // Output: World!
    }


}
