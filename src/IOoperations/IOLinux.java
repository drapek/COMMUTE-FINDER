package IOoperations;

import java.io.File;

/**
 * Created by drapek on 28.12.15.
 */
public class IOLinux {

    public void readXMLFile(String filePath) {
        try {
            File xmlFile = new File(filePath);
            PointsReader xmlPointsReader = new PointsReader();
            xmlPointsReader.readFile(xmlFile);
        } catch (Exception e ) {
            System.err.println("Nie odnaleziono pliku!");
        }

    }


}
