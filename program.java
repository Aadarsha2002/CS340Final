public class program {
    public static void main(String[] args) {
        // Consider an array of integers and a non-zero positive starting value x. A
        // running sum is calculated by adding each element of the array to x
        // consecutively. Determine the minimum value of x such that the running sum is
        // at least 1 after every iteration.

    }

    public static long minStart(List<Integer> arr) {
        long sum = 0;
        long min = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
            if (sum < min) {
                min = sum;
            }
        }
        return 1 - min;
    }
}