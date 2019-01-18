import java.util.Arrays;
import java.util.Random;

public class Knapsack {

    // Given weights and values of N items, put these in a knapsack of max capacity W to get the max value
    // split items


    public static int recursiveKnapsack2(int[] weights, int[] values, int capacity) {
        return recursiveKnapsackHelper2(weights, values, capacity, 0);
    }

    public static int recursiveKnapsackHelper2(int[] weights, int[] values, int capacity, int index) {
        if (index >= weights.length) return 0;
        if (weights[index] > capacity) return recursiveKnapsackHelper2(weights, values, capacity, index + 1);
        int withThis = recursiveKnapsackHelper2(weights, values, capacity - weights[index], index + 1) + values[index];
        int withoutThis = recursiveKnapsackHelper2(weights, values, capacity, index + 1);
        return Math.max(withoutThis, withThis);
    }


    public static int recursiveKnapsack(int[] weights, int[] values, int capacity) {
        if (weights.length == 0 || weights.length != values.length) throw new IllegalArgumentException();
        int maxWeight = 0;
        for (int i = 0; i < weights.length; i++) {
            int maxStartingFromThisItem = recursiveKnapsackHelper(weights, values, capacity, 0, i);
            maxWeight = Math.max(maxStartingFromThisItem, maxWeight);
        }
        return maxWeight;
    }

    public static int dynamicKnapsack(int[] weights, int[] values, int capacity) {
        int[][] data = new int[weights.length + 1][capacity + 1];
        for (int item = 0; item < data.length; item++) {
            for (int cap = 0; cap < data[0].length; cap++) {
                if (item == 0 || cap == 0) data[item][cap] = 0;
                else if (weights[item] > capacity) data[item][cap] = data[item - 1][cap];
                else {
                    int maxWithout = data[item - 1][cap];
                    int maxWith = data[item - 1][cap - weights[item]];
                    data[item][cap] = Math.max(maxWith, maxWithout);
                }
            }
        }
        return data[weights.length][capacity];

    }

    public static int recursiveKnapsackHelper(int[] weights, int[] values, int capacity, int currentWeight, int currentItem) {
        int currentWeightWithThisItem = currentWeight + weights[currentItem];
        if (currentWeightWithThisItem > capacity || currentItem >= weights.length) {
            return 0;
        }
        int maxWeight = 0;
        for (int i = currentItem + 1; i < weights.length; i++) {
            maxWeight = Math.max(recursiveKnapsackHelper(weights, values, capacity, currentWeightWithThisItem, i), maxWeight);
        }
        return maxWeight + values[currentItem];
    }

    public static int memoKnapsack(int[] weights, int[] values, int capacity) {
        int[][] data = new int[weights.length][capacity + 1];
        for (int[] row : data) {
            Arrays.fill(row, -1);
        }
        int maxValue = 0;
        for (int i = 0; i < weights.length; i++) {
            maxValue = Math.max(memoKnapsackHelper(weights, values, capacity, 0, i, data), maxValue);
        }
        return maxValue;
    }

    public static int memoKnapsackHelper(int[] weights, int[] values, int capacity, int currentWeight, int currentItem, int[][] data) {
        int currentWeightWithThisItem = currentWeight + weights[currentItem];
        if (currentWeightWithThisItem > capacity || currentItem >= weights.length) {
            return 0;
        }
        if (data[currentItem][currentWeight] > -1) return data[currentItem][currentWeight];
        int maxValue = 0;
        for (int i = currentItem + 1; i < weights.length; i++) {
            maxValue = Math.max(recursiveKnapsackHelper(weights, values, capacity, currentWeightWithThisItem, i), maxValue);
        }
        int updatedValue = maxValue + values[currentItem];
        data[currentItem][currentWeight] = updatedValue;
        return updatedValue;
    }


    public static void main(String[] args) {
        int[] weights = new int[40];
        int[] values = new int[weights.length];
        Random r = new Random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = r.nextInt(100);
            values[i] = r.nextInt(100);
        }

        int capacity = 500;
        System.out.println("Capacity: " + capacity);
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Values: " + Arrays.toString(values));

        long time1start = System.nanoTime();
        int trial1 = recursiveKnapsack(weights, values, capacity);
        long time1end = System.nanoTime();
        System.out.println(String.format("Recursive -> Answer: %d , Time: %d", trial1, (time1end - time1start) / 1000000));

        long time2start = System.nanoTime();
        int trial2 = memoKnapsack(weights, values, capacity);
        long time2end = System.nanoTime();
        System.out.println(String.format("Recursive w/ Memo -> Answer: %d , Time: %d", trial2, (time2end - time2start) / 1000000));

        long time3start = System.nanoTime();
        int trial3 = recursiveKnapsack2(weights, values, capacity);
        long time3end = System.nanoTime();
        System.out.println(String.format("Recursive2 -> Answer: %d , Time: %d", trial3, (time3end - time3start) / 1000000));

        long time4start = System.nanoTime();
        int trial4 = recursiveKnapsack2(weights, values, capacity);
        long time4end = System.nanoTime();
        System.out.println(String.format("dynamic -> Answer: %d , Time: %d", trial4, (time4end - time4start) / 1000000));

    }
}
