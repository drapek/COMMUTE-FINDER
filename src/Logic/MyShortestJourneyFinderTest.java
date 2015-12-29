package Logic;

import Database.GeneralPointsCollection;
import Database.NotDefinedPointsCollection;
import DrapekCollections.MyArrayList;
import IOoperations.IOLinux;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;
import org.junit.Test;

/**
 * Created by drapek on 29.12.15.
 */
public class MyShortestJourneyFinderTest {

    @Test
    public void testFindShortestPath() throws Exception {
        //need proper file in TestFiles folder!
        IOLinux pointReader = new IOLinux();
        pointReader.readXMLFile("/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/TestMysShortestJourneyFinder.xml");

        MapPoint startPoint = GeneralPointsCollection.searchById(1);
        MapPoint endPoint = GeneralPointsCollection.searchById(3);

        System.out.println("Lista niezdefiniowanych punktów: ");
        System.out.println(NotDefinedPointsCollection.DBtoString());

        MyShortestJourneyFinder newFinder = new MyShortestJourneyFinder();
        MyArrayList <ConnectionToNextPoint> result = newFinder.findShortestPath(startPoint, endPoint);

        System.out.println("Wszystkie znalezione ściezki:");
        System.out.println(newFinder.printAllFoundConnections());
    }
}