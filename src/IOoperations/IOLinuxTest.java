package IOoperations;

import org.junit.Test;

/**
 * Created by drapek on 28.12.15.
 */
public class IOLinuxTest {

    @Test
    public void testReadXMLFile() throws Exception {
        IOLinux temp = new IOLinux();
        temp.readXMLFile("/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/Europa.xml");
    }

}