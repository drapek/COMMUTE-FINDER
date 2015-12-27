package Database;

import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.CantFindElementToDeleteException;
import ProjectExceptions.NotDefinedPointWithThisIdExistException;
import ProjectExceptions.NotDefinedPointWithThisNameExistException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by drapek on 27.12.15.
 */
public class NotDefinedPointsCollectionTest {
    @Test
    public void testInserting() throws Exception {
        MapPoint tmpPoint = MapPoint.getNotDefinedMapPoint(23, "nono");

        NotDefinedPointsCollection.addNotDefinedPoint(tmpPoint);

        tmpPoint = MapPoint.getNotDefinedMapPoint(45, "Uhh");

        NotDefinedPointsCollection.addNotDefinedPoint(tmpPoint);

        Assert.assertEquals("Uhh", NotDefinedPointsCollection.searchByName("Uhh").getName());

        Assert.assertEquals("nono", NotDefinedPointsCollection.searchById(23).getName());

    }

    @Test
    public void testDeleting() throws Exception {
        MapPoint temp = MapPoint.getNotDefinedMapPoint(44, "dfdf");
        NotDefinedPointsCollection.addNotDefinedPoint(temp);

        MapPoint copyTemp = NotDefinedPointsCollection.searchByName("dfdf");

        Assert.assertEquals(temp, copyTemp);

        NotDefinedPointsCollection.deleteNotDefinedPoint(copyTemp);

        try {
            NotDefinedPointsCollection.deleteNotDefinedPoint(temp);
            Assert.fail("Próba usunięcia obiektu który już był usunięty! ");
        } catch (CantFindElementToDeleteException e) {
            //Wszystko ok!
        }

    }

    @Test
    public void testAddingWithWrongParameters() throws Exception {
        MapPoint temp1 = MapPoint.getNotDefinedMapPoint(22, "lila");
        MapPoint temp2 = MapPoint.getNotDefinedMapPoint(22, "dida");
        MapPoint temp3 = MapPoint.getNotDefinedMapPoint(33, "lila");

        NotDefinedPointsCollection.addNotDefinedPoint(temp1);

        try {
            NotDefinedPointsCollection.addNotDefinedPoint(temp2);
            Assert.fail("Obiekt o tym Id już istnieje, więc powinno wyrzucić wyjątek!");
        } catch (NotDefinedPointWithThisIdExistException e) {
            //It's ok!
        }

        try {
            NotDefinedPointsCollection.addNotDefinedPoint(temp3);
            Assert.fail("Obiekt o tej nazwie już istnieje, więc powinno dać wyjątek!");
        } catch (NotDefinedPointWithThisNameExistException e) {
            //It's ok
        }
    }

}