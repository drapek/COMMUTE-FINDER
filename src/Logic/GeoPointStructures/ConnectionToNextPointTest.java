package Logic.GeoPointStructures;

import ProjectExceptions.CantBeNegativeException;
import junit.framework.Assert;
import org.junit.Test;


/**
 * Created by drapek on 27.12.15.
 */
public class ConnectionToNextPointTest {

    @Test
    public void testGetters() throws Exception {
        MapPoint guestPoint = MapPoint.getMapPointObject(23, "GOSC", 100);
        ConnectionToNextPoint testConnection = new ConnectionToNextPoint(guestPoint, 20, 10);

        Assert.assertEquals(23, testConnection.getId());
        Assert.assertEquals("GOSC", testConnection.getName());
        Assert.assertEquals(100.0, testConnection.getPauseTime());
        Assert.assertEquals(20.0, testConnection.getDistance());
        Assert.assertEquals(10.0, testConnection.getVelocity());
        Assert.assertEquals(200.0, testConnection.getTimeTravel());

    }

    @Test
    public void testIncorrectParameters() throws Exception {
        MapPoint tmp1 = MapPoint.getMapPointObject(54, "dfaf", 0);

        try {
            ConnectionToNextPoint con1 = new ConnectionToNextPoint(null, 23, 23);
            Assert.fail("Podano pusty wskaźnik na punkt! powinno wyrzucic wyjatek NullPointerException!");
        } catch (NullPointerException e) {
            //It's ok!
        } catch ( Exception e ) {
            Assert.fail("Powinno wyrzucyć konkretnie NullPointerException!");
        }

        try {
            ConnectionToNextPoint con1 = new ConnectionToNextPoint(tmp1, -23, 23);
            Assert.fail("Podano ujemny dystans! powinno wyrzucic wyjatek CantBeNegativeException!");
        } catch (CantBeNegativeException e) {
            //It's ok!
        } catch ( Exception e ) {
            Assert.fail("Powinno wyrzucyć konkretnie CantBeNegativeException!");
        }

        try {
            ConnectionToNextPoint con1 = new ConnectionToNextPoint(tmp1, 23, -23);
            Assert.fail("Podano ujemną prędkość! powinno wyrzucic wyjatek CantBeNegativeException!");
        } catch (CantBeNegativeException e) {
            //It's ok!
        } catch ( Exception e ) {
            Assert.fail("Powinno wyrzucyć konkretnie CantBeNegativeException!");
        }

    }
}