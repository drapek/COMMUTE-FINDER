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
}