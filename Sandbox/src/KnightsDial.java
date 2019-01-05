import java.util.HashMap;

public class KnightsDial {

    private static HashMap<Integer, Integer[]> reachableNumbers;

    static {
        reachableNumbers = new HashMap<>();
        reachableNumbers.put(1, new Integer[]{6, 8});
        reachableNumbers.put(2, new Integer[]{7, 9});
        reachableNumbers.put(3, new Integer[]{4, 8});
        reachableNumbers.put(4, new Integer[]{3, 9, 0});
        reachableNumbers.put(5, new Integer[]{});
        reachableNumbers.put(6, new Integer[]{7, 9, 0});
        reachableNumbers.put(7, new Integer[]{6, 2});
        reachableNumbers.put(8, new Integer[]{1, 3});
        reachableNumbers.put(9, new Integer[]{2, 4});
        reachableNumbers.put(0, new Integer[]{6, 4});
    }

    public static int countDialsRecursively(int dialsLeft, int position) {
        if (dialsLeft-- == 0) {
            return 1;
        }
        int counter = 0;
        for (Integer i : reachableNumbers.get(position)) {
            counter += countDialsRecursively(dialsLeft, i);
        }
        return counter;
    }

    public static void main(String[] args) {
        long startTime1 = System.nanoTime();
        //System.out.println(countDialsRecursively(25, 6));
        long endTime1 = System.nanoTime();
        System.out.println("1st: " + (endTime1 - startTime1) / 1000000);
        long startTime2 = System.nanoTime();
        System.out.println(countDials(4, 6));
        long endTime2 = System.nanoTime();
        System.out.println("2nd: " + (endTime2 - startTime2) / 1000000);
        long startTime3 = System.nanoTime();
        System.out.println(countDialsDynamically(4, 6));
        long endTime3 = System.nanoTime();
        System.out.println("3rd: " + (endTime3 - startTime3) / 1000000);
    }

    public static int countDials(int N, int startingPosition) {
        int[][] cache = new int[N][10];
        return countDials(N, startingPosition, cache);
    }

    private static int countDials(int dialsLeft, int position, int[][] cache) {
        if (dialsLeft == 0) {
            return 1;
        }
        if (cache[dialsLeft - 1][position] > 0) {
            return cache[dialsLeft - 1][position];
        }
        int counter = 0;
        for (Integer i : reachableNumbers.get(position)) {
            counter += countDials(dialsLeft - 1, i, cache);
        }
        cache[dialsLeft - 1][position] = counter;
        return counter;
    }

    public static int countDialsDynamically(int N, int startingPosition) {
        int[] priorRun;
        int[] currentRun = new int[10];
        for (int i = 0; i < 10; i++) {
            currentRun[i] = 1;
        }
        for (int callsLeft = 1; callsLeft < N + 1; callsLeft++) {
            priorRun = currentRun;
            currentRun = new int[10];
            for (int position = 0; position < 10; position++) {
                int counter = 0;
                for (Integer i : reachableNumbers.get(position)) {
                    counter += priorRun[i];
                }
                currentRun[position] = counter;
            }
        }
        return currentRun[startingPosition];
    }


}
