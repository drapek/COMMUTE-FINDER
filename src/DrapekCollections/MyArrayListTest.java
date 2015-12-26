package DrapekCollections;

import junit.framework.Assert;

/**
 * Created by drapek on 26.12.15.
 */
public class MyArrayListTest {

    MyArrayList <Integer> arrayListTest;

    @org.junit.Before
    public void setUp() throws Exception {
        arrayListTest = new MyArrayList<>();
    }

    @org.junit.Test
    public void testArrayList() throws Exception {
        arrayListTest.add(3);
        arrayListTest.add(1);
        arrayListTest.add(-12);
        arrayListTest.add(43);
        arrayListTest.add(54);

        Assert.assertEquals(arrayListTest.getSize(), 5);

        org.junit.Assert.assertEquals(3, (int) arrayListTest.get(0));
        org.junit.Assert.assertEquals(1, (int) arrayListTest.get(1));
        org.junit.Assert.assertEquals(-12, (int) arrayListTest.get(2));
        org.junit.Assert.assertEquals(43, (int) arrayListTest.get(3));
        org.junit.Assert.assertEquals(54, (int) arrayListTest.get(4));

        arrayListTest.delete(0);
        Assert.assertEquals(4, arrayListTest.getSize());

        //stan po usunięciu
        org.junit.Assert.assertEquals(1, (int) arrayListTest.get(0));

        try {
            org.junit.Assert.assertEquals(54, (int) arrayListTest.get(4));
            Assert.fail("Powinnien wystąpić wyjątek do próby dostania się do elementu z poza zakresu!");
        } catch (Exception e ) {
            //if excepiton has happen it's ok!
        }

        arrayListTest.add(32);
        org.junit.Assert.assertEquals(32, (int) arrayListTest.get(4));







    }


}