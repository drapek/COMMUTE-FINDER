package Database;

import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.*;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by drapek on 27.12.15.
 */
public class NotDefinedPointsCollectionTest {
    @Test
    public void testInserting() throws Exception {
        MapPoint tmpPoint = MapPoint.getNotDefinedMapPoint(23, "nono");

        tmpPoint = MapPoint.getNotDefinedMapPoint(45, "Uhh");


        Assert.assertEquals("Uhh", NotDefinedPointsCollection.searchByName("Uhh").getName());

        Assert.assertEquals("nono", NotDefinedPointsCollection.searchById(23).getName());

    }

    @Test
    public void testDeleting() throws Exception {
        MapPoint temp = MapPoint.getNotDefinedMapPoint(44, "dfdf");

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
    public void testAddingWithWrongParameters()  throws Exception {
        MapPoint temp1 = MapPoint.getNotDefinedMapPoint(22, "lila");

        try {
            MapPoint temp2 = MapPoint.getNotDefinedMapPoint(22, "dida");
            Assert.fail("Obiekt o tym Id już istnieje, więc powinno wyrzucić wyjątek!");
        } catch (NotDefinedPointWithThisIdExistException e) {
            //It's ok!
        } catch (NotDefinedPointWithThisNameExistException e) {
            e.printStackTrace();
        } catch (NameAlreadyExistException e) {
            e.printStackTrace();
        } catch (IdAlreadyExistException e) {
            //It's ok
        }

        try {
            MapPoint temp3 = MapPoint.getNotDefinedMapPoint(33, "lila");
            Assert.fail("Obiekt o tej nazwie już istnieje, więc powinno dać wyjątek!");
        } catch (NotDefinedPointWithThisNameExistException e) {
            //It's ok
        } catch (NameAlreadyExistException e) {
            //It's ok
        } catch (NotDefinedPointWithThisIdExistException e) {
            e.printStackTrace();
        } catch (IdAlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByExistions() throws Exception {
        MapPoint notDefined = MapPoint.getNotDefinedMapPoint(450, "Polskaaa");

        Assert.assertEquals(true, NotDefinedPointsCollection.searchByExistion(notDefined));

    }

}