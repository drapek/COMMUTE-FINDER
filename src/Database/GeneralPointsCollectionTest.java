package Database;

import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.IdAlreadyExistException;
import ProjectExceptions.NameAlreadyExistException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by drapek on 28.12.15.
 */
public class GeneralPointsCollectionTest {
    
    @Test
    public void testInserting() throws Exception {
        MapPoint defined1 =  MapPoint.getMapPointObject(1, "Abec", 0);
        MapPoint defined2 =  MapPoint.getMapPointObject(2, "Babec", 0);
        MapPoint defined3 =  MapPoint.getMapPointObject(18, "Cabec", 0);
        MapPoint defined4 =  MapPoint.getMapPointObject(19, "Caryca", 0);


        MapPoint notDefined1 = MapPoint.getNotDefinedMapPoint(21, "Ryga");
        MapPoint notDefined2 = MapPoint.getNotDefinedMapPoint(22, "Wilno");
        MapPoint notDefined3 = MapPoint.getNotDefinedMapPoint(23, "Kiev");


        GeneralPointsCollection.addPoint(defined1);
        GeneralPointsCollection.addPoint(defined2);
        GeneralPointsCollection.addPoint(defined3);
        GeneralPointsCollection.addPoint(defined4);

        Assert.assertEquals(20, GeneralPointsCollection.nextFreeId());


        GeneralPointsCollection.addPoint(notDefined1);
        GeneralPointsCollection.addPoint(notDefined2);
        GeneralPointsCollection.addPoint(notDefined3);

        Assert.assertEquals(24, GeneralPointsCollection.nextFreeId());

        try{
            MapPoint defined5 =  MapPoint.getMapPointObject(2, "Caryca", 0);
            GeneralPointsCollection.addPoint(defined5);
            Assert.fail("Powinno wyrzucić wyjątek zajętego ID! (w głównej bazie punktów)");
        } catch (IdAlreadyExistException e) {
            //It's ok
        }

        try{
            MapPoint defined6 =  MapPoint.getMapPointObject(3, "Caryca", 0);
            GeneralPointsCollection.addPoint(defined6);
            Assert.fail("Powinno wyrzucić wyjątek zajętego NAME! (w głównej bazie punktów)");
        } catch (NameAlreadyExistException e) {
            //It's ok
        }



    }

    @Test
    public void testSearchByName() throws Exception {
        MapPoint defined1 =  MapPoint.getMapPointObject(31, "", 0);
        MapPoint defined2 =  MapPoint.getMapPointObject(32, "", 0);
        MapPoint defined3 =  MapPoint.getMapPointObject(33, "Lalka", 0);

        GeneralPointsCollection.addPoint(defined1);
        GeneralPointsCollection.addPoint(defined2);
        GeneralPointsCollection.addPoint(defined3);

        Assert.assertEquals(null, GeneralPointsCollection.searchByName("")); //powinno dać null, a nie jakiś obiekt
        Assert.assertTrue(GeneralPointsCollection.searchById(31) != null); //ale po id powinno już znaleźć


    }
}