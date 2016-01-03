package Logic;

import Database.GeneralPointsCollection;
import Database.NotDefinedPointsCollection;
import IOoperations.IOLinux;
import Logic.GeoPointStructures.MapPoint;
import junit.framework.Assert;
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

        Assert.assertEquals(" (total: 12,458) 1  --[1,125]-->  4 (pause: 11.0)  --[0,333]-->  3\n", result);

        startPoint = GeneralPointsCollection.searchById(2);
        endPoint = GeneralPointsCollection.searchById(4);
        result = newFinder.findShortestPath(startPoint, endPoint);
        Assert.assertEquals(" (total: 0,683) 2  --[0,683]-->  4\n", result);

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
        Assert.assertEquals(" (total: 0,000) 3\n", newFinder.findShortestPath(startPoint, endPoint));
    }
}