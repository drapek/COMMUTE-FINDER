package Logic;

import Database.GeneralPointsCollection;
import Database.NotDefinedPointsCollection;
import IOoperations.IOLinux;
import Logic.GeoPointStructures.MapPoint;
import org.junit.Assert;
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
        String result = newFinder.findShortestPath(startPoint, endPoint);

        System.out.println("Wszystkie znalezione ściezki z 1 do 3 posortowane po czasie:");
        System.out.println(newFinder.printFoundConnectionsSortedByTime());

        System.out.println("Najszybsza ściezka z 1 do 3 to: ");
        System.out.println(result);

        Assert.assertEquals("[id: 1 name: ] -> [id: 4 name: ] -> [id: 3 name: ]\nCzas podróży: 12.458333333333334\n", result);

        startPoint = GeneralPointsCollection.searchById(2);
        endPoint = GeneralPointsCollection.searchById(4);
        result = newFinder.findShortestPath(startPoint, endPoint);
        Assert.assertEquals("[id: 2 name: ] -> [id: 4 name: ]\nCzas podróży: 0.6833333333333333\n", result);

        System.out.println("Nakrótsza ścieżka z 2 do 4 to:");
        System.out.println(result);

        System.out.println("Nakrótsza ścieżka z 3 do 4 to:");
        startPoint = GeneralPointsCollection.searchById(3);
        endPoint = GeneralPointsCollection.searchById(4);
        System.out.println(newFinder.findShortestPath(startPoint, endPoint));

        System.out.println("Nakrótsza ścieżka z 3 do 3 to:");
        startPoint = GeneralPointsCollection.searchById(3);
        endPoint = GeneralPointsCollection.searchById(3);
        System.out.println(newFinder.findShortestPath(startPoint, endPoint));
        Assert.assertEquals("[id: 3 name: ]\nCzas podróży: 0.0\n", newFinder.findShortestPath(startPoint, endPoint));
    }
}