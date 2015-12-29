package Logic;

import Database.NotDefinedPointsCollection;
import DrapekCollections.MyArrayList;
import DrapekCollections.UniversalStack;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.CantBeNegativeException;

/**
 * Created by drapek on 27.12.15.
 */
public class MyShortestJourneyFinder {
    private UniversalStack <ConnectionToNextPoint> actualJourneyPath;
    private MyArrayList <MyArrayList> foundPathList;
    private int debugNmbOfInvokesFunction = 0;
    private static final boolean DEBUG = false;

    public MyShortestJourneyFinder() {
        actualJourneyPath = new UniversalStack<>();
        foundPathList = new MyArrayList<>();
    }

    public MyArrayList findShortestPath(MapPoint startPoint, MapPoint endPoint) {
        if( startPoint == null || endPoint == null) {
            throw new NullPointerException("Jako punkt startowy bądź docelowy podałeś wartość pustą!");
        }

        /* wraping startPoint into fiction connection so it can fits to stack */
        ConnectionToNextPoint startPointWrapper = null;
        try {
            startPointWrapper = new ConnectionToNextPoint(startPoint, 0, 0);
        } catch (CantBeNegativeException e) {
            System.err.println("Podano ujemną prędkość bądź dystans!");
            System.exit(1);
        }

        if( startPointWrapper == null) {
            System.err.println("Z magicznych przyczyn nie można było utworzyć wrapera dla startPoint!");
            System.exit(1);
        }

        recurseEndPointFinder(startPointWrapper, endPoint);

        return foundPathList;

        //TODO compute timing of paths
        //TODO sort by shortest time
        //TODO return arraylist with shortest conenction
        //return null;

    }

    private void recurseEndPointFinder(ConnectionToNextPoint actualSearchPoint, MapPoint endPoint) {
        if(DEBUG)
            System.out.println(" # recursieEndPointFinder Function " + debugNmbOfInvokesFunction++);

        if( (actualSearchPoint.getPoint()).equals(endPoint) ) {
            actualJourneyPath.push(actualSearchPoint);
            foundPathList.add( actualJourneyPath.copyActualStackAsArrayList() );
            actualJourneyPath.pop();
            return;
        }

        if(NotDefinedPointsCollection.searchByExistion(actualSearchPoint.getPoint())) {
            return; //because not defined points doesn't have connection, and if they are not end point they can't do nothing further
        }

        /* check if algorithm be in this point before */
        if( connectionWithThisPointExitInStack(actualSearchPoint) ) {
            return;
        }

        MyArrayList <ConnectionToNextPoint> availableConnections = actualSearchPoint.getPoint().getConnections();
        if( availableConnections.getSize() == 0) {
            return; //because there is not further connections
        }

        actualJourneyPath.push(actualSearchPoint);

        for( int j = 0; j < availableConnections.getSize(); j++) {
            recurseEndPointFinder(availableConnections.get(j) , endPoint);
        }

        actualJourneyPath.pop();
    }

    private boolean connectionWithThisPointExitInStack(ConnectionToNextPoint connect) {
        if( connect == null)
            throw new NullPointerException();

        MyArrayList <ConnectionToNextPoint> temp = actualJourneyPath.getStackDataBase();
        for( int i = 0; i < temp.getSize(); i++) {
            if ((temp.get(i).getPoint()).equals(connect.getPoint()))
                return true;
        }

        return false;
    }

    public String printAllFoundConnections() {
        StringBuilder strBld = new StringBuilder();

        for( int i = 0; i < foundPathList.getSize(); i++ ) {
            strBld.append("\t").append(i+1).append(". ");

            MyArrayList <ConnectionToNextPoint> path = foundPathList.get(i);
            for( int j = 0; j < path.getSize(); j++) {
                MapPoint unwrapPoint = path.get(j).getPoint();
                if( j != 0) {
                    /*strBld.append("  --[").append(path.get(j).getDistance()).append("/").append(path.get(j).getVelocity()).append("=")
                            .append(path.get(j).getTimeTravel()).append("(time)]-->  ");*/
                    strBld.append("  --[").append(String.format("%.3f", path.get(j).getTimeTravel())).append("]-->  ");
                }

                strBld.append(unwrapPoint.getId());
                if( !unwrapPoint.getName().equals(""))
                    strBld.append("(name: ").append(unwrapPoint.getName()).append(" )");
            }

            strBld.append("\n");
        }

        return strBld.toString();
    }
}
