package Logic;

import Database.GeneralPointsCollection;
import Database.NotDefinedPointsCollection;
import IOoperations.IOLinux;
import Logic.GeoPointStructures.MapPoint;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by drapek on 16.01.16.
 */
public class DijkstryJourneyFinderTest {

    @Test
    public void testFindShortestPath() throws Exception {
        //need proper file in TestFiles folder!
        IOLinux pointReader = new IOLinux();
        pointReader.readXMLFile("/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/TestMysShortestJourneyFinder.xml");

        MapPoint startPoint = GeneralPointsCollection.searchById(1);
        MapPoint endPoint = GeneralPointsCollection.searchById(3);

        System.out.println("Lista niezdefiniowanych punktów: ");
        System.out.println(NotDefinedPointsCollection.DBtoString());

        DijkstryJourneyFinder newFinder = new DijkstryJourneyFinder();
        String result = newFinder.findShortestPath(startPoint, endPoint);

        System.out.println("Odnaleziony wynik dla podróży z 1 do 3: " + result);

        System.out.println("Nakrótsza ścieżka z 3 do 4 to:");
        startPoint = GeneralPointsCollection.searchById(3);
        endPoint = GeneralPointsCollection.searchById(4);
        System.out.println(newFinder.findShortestPath(startPoint, endPoint));

        System.out.println("Nakrótsza ścieżka z 3 do 3 to:");
        startPoint = GeneralPointsCollection.searchById(3);
        endPoint = GeneralPointsCollection.searchById(3);
        System.out.println(newFinder.findShortestPath(startPoint, endPoint));
        Assert.assertEquals("[id: 3 name: ]\nCzas podróży: 0,000\n", newFinder.findShortestPath(startPoint, endPoint));
    }
}