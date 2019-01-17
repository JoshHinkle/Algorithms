import org.junit.Assert;
import org.junit.Test;

public class HashMapTest {

    @Test
    public void hashTest() {
        HashMap<Integer, Integer> hashMap = new HashMap<>(2, .75);

        hashMap.put(1, 1);
        hashMap.put(2, 3);
        hashMap.put(3, 4);
        Assert.assertEquals(new Integer(1), hashMap.get(1));
        Assert.assertEquals(new Integer(3), hashMap.get(2));
        Assert.assertEquals(new Integer(4), hashMap.get(3));

        hashMap.put(4, 6);
        Assert.assertEquals(4, hashMap.size());

        hashMap.remove(1);
        Assert.assertNull(hashMap.get(1));
        Assert.assertEquals(3, hashMap.size());

    }

}