public class WrapUnwrapTester {
    public static void main(String[] args) {
        // Wrap a primitive!
        int i = 288;
        Integer iWrap = new Integer(i);

        //Unwrap a primitive
        int unWrapped = iWrap.intValue();
    }
}