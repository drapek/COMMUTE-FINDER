package Logic.GeoPointStructures;

import ProjectExceptions.CantBeNegativeException;

/**
 * Created by drapek on 27.12.15.
 */
public class ConnectionToNextPoint {
    private MapPoint connectionPoint;
    private double distnace;
    private double velocity;
    private double timeTravel;

    public ConnectionToNextPoint(MapPoint destPoint, double distnace, double velocity) throws CantBeNegativeException {
        if( destPoint == null )
            throw new NullPointerException();
        if( distnace < 0 )
            throw new CantBeNegativeException("Dystans nie może być ujemny!");
        if( velocity < 0 )
            throw new CantBeNegativeException("Prędkośc nie może być ujemna!");

        this.connectionPoint = destPoint;
        this.distnace = distnace;
        this.velocity = velocity;
        this.timeTravel = 0;
        /* fuse for dividing by 0 */
        if( velocity != 0)
            this.timeTravel = distnace / velocity;
    }



    public int getId() {
        return connectionPoint.getId();
    }

    public String getName() {
        return connectionPoint.getName();
    }

    public double getPauseTime() { return  connectionPoint.getPauseTime(); }

    public double getDistance() {
        return distnace;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getTimeTravel() { return timeTravel; }

    public MapPoint getPoint() { return connectionPoint; }

}
