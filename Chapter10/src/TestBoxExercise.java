public class TestBoxExercise {
    private Integer i = new Integer(5);
    private int j = 7;

    public static void main(String[] args) {
        TestBoxExercise t = new TestBoxExercise();
        t.go();
    }

    public void go() {
        j = i;
        System.out.println(j);
        System.out.println(i);
        // 5 printed out 2 times
    }
}