package Logic.GeoPointStructures;

import ProjectExceptions.NameAlreadyExistException;
import ProjectExceptions.NotDefinedPointWithThisNameExistException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by drapek on 27.12.15.
 */
public class MapPointTest {

    @Test
    public void testMapPointOverall() throws Exception {
        MapPoint normalFullPoint = MapPoint.getMapPointObject(1, "Warschau", 5.2);

        MapPoint normalIDFullPoint = MapPoint.getMapPointObject(-1, "Wisnowice", 34);

        try {
            MapPoint wrongTimeFullPoint = MapPoint.getMapPointObject(-1, "Wisnowice", -34);
            Assert.fail("Prędkość nie może być zdefioniowana ujemnie! Powinien wyskoczyć wyjątek");
        } catch (Exception e) {
            //It's ok
        }

        MapPoint notDefinedPoint = MapPoint.getNotDefinedMapPoint(23, "Nono");
        Assert.assertEquals(-1.0, notDefinedPoint.getPauseTime());

        System.out.println(normalFullPoint.toString());
        System.out.println(notDefinedPoint);
        System.out.println(normalIDFullPoint);

        Assert.assertEquals(1, normalFullPoint.getId());
        Assert.assertEquals("Warschau", normalFullPoint.getName());
        Assert.assertEquals(5.2, normalFullPoint.getPauseTime());



    }

    @Test
    public void testEditinigNotDefinedPoints() throws Exception {
        MapPoint notDefined = MapPoint.getNotDefinedMapPoint(34, "Naboo");

        //powinno edytować
        MapPoint editorNotDefinedPoint = MapPoint.getMapPointObject(34, "NewName", 234);

        //orginal object must have this properties
        Assert.assertEquals( "NewName" , notDefined.getName());
        Assert.assertEquals( 234.0, notDefined.getPauseTime());

        //and his notdefined scrach must be this object now
        Assert.assertEquals( "NewName" , notDefined.getName());
        Assert.assertEquals( 234.0, notDefined.getPauseTime());

    }

    @Test
    public void testAutoIdAssigning() throws Exception {
        MapPoint myMapPoint = MapPoint.getMapPointObject(-1, "Andrzej", 234);
        Assert.assertTrue("Nie nastąpiło automatyczne przypisanie ID", myMapPoint.getId() > 0);
        System.out.println("automatycznie przypisane id: " + myMapPoint.getId());
    }

    @Test
    public void testcheckIfNameIsOccupied() throws Exception {
        MapPoint tmpPoint = MapPoint.getNotDefinedMapPoint(25, "Marroko");

        try {
            MapPoint tmp2Point = MapPoint.getNotDefinedMapPoint(32, "Marroko");
            Assert.fail("Nastąpiła próba utworzenia obiektu o tej samej nazwie! Powinno wyrzucić exception, ale tego nie zrobiło!");
        } catch (NameAlreadyExistException e) {
            //if exception occurs it's ok
        } catch (NotDefinedPointWithThisNameExistException e) {
            // it's ok because there can't be two notDefinedPoints which the same name!
        }

    }

    @Test
    public void testaddingConnections() throws Exception {
        MapPoint tmp1 = MapPoint.getMapPointObject(50, "Dunkan", 200);

        MapPoint dest1 = MapPoint.getMapPointObject(51, "Wadowice", 100);
        MapPoint dest2 = MapPoint.getMapPointObject(52, "Andrzejów", 200);
        MapPoint dest3 = MapPoint.getMapPointObject(53, "Gierlach", 300);

        System.out.println("##########Przed dodaniem punktów: \n" + tmp1);

        ConnectionToNextPoint tmpConnection = new ConnectionToNextPoint(dest1, 50, 80);
        tmp1.addConnection(tmpConnection);

        tmpConnection = new ConnectionToNextPoint(dest2, 100, 80);
        tmp1.addConnection(tmpConnection);

        tmpConnection = new ConnectionToNextPoint(dest3, 73, 120);
        tmp1.addConnection(tmpConnection);

        System.out.println("#########po dodaniu pkt: \n" + tmp1);

        Assert.assertEquals(3, tmp1.getConnections().getSize());

    }

}