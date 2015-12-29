package DrapekCollections;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by drapek on 26.12.15.
 */
public class UniversalStackTest {

    UniversalStack <Integer> testStack;

    @Before
    public void setUp() throws Exception {
        testStack = new UniversalStack<>();
    }

    @Test
    public void testPushAndPop() throws Exception {
        testStack.push(5);
        testStack.push(3);
        testStack.push(7);
        testStack.push(11);
        testStack.push(1);

        Assert.assertEquals(1, (int) testStack.pop());
        Assert.assertEquals(11, (int) testStack.pop());
        Assert.assertEquals(7, (int) testStack.pop());
        Assert.assertEquals(3, (int) testStack.pop());
        Assert.assertEquals(5, (int) testStack.pop());

        try {
            Assert.assertEquals(1, (int) testStack.pop());
            Assert.fail("Stack is empty, and because of this it throws exception! But in this case it won't happen");
        } catch (Exception e) {
            //it's ok
        }
    }

    @Test
    public void testcopyActualStackAsArrayList() throws Exception {
        UniversalStack <Integer> tempStack = new UniversalStack<>();

        tempStack.push(1);
        tempStack.push(6);
        tempStack.push(8);
        tempStack.push(12);
        tempStack.push(19);

        MyArrayList tempArray = tempStack.copyActualStackAsArrayList();

        Assert.assertEquals(5, tempArray.getSize());

        Assert.assertEquals(1 ,tempArray.get(0));
        Assert.assertEquals(6 ,tempArray.get(1));
        Assert.assertEquals(8 ,tempArray.get(2));
        Assert.assertEquals(12 ,tempArray.get(3));
        Assert.assertEquals(19 ,tempArray.get(4));

        Assert.assertNotSame(tempStack.getStackDataBase(), tempStack);


    }
}