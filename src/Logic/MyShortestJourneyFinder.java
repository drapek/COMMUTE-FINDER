package Logic;

import Database.NotDefinedPointsCollection;
import DrapekCollections.MyArrayList;
import DrapekCollections.UniversalStack;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;
import Logic.GeoPointStructures.PathTimings;
import ProjectExceptions.CantBeNegativeException;

import java.util.Arrays;

/**
 * Created by drapek on 27.12.15.
 */
public class MyShortestJourneyFinder {
    private UniversalStack <ConnectionToNextPoint> actualJourneyPath;
    private MyArrayList <MyArrayList <ConnectionToNextPoint>> foundPathList;
    private PathTimings [] timings;

    private int debugNmbOfInvokesFunction = 0;
    private static final boolean DEBUG = false;


    public String findShortestPath(MapPoint startPoint, MapPoint endPoint) {
        if( startPoint == null || endPoint == null) {
            throw new NullPointerException("Jako punkt startowy bądź docelowy podałeś wartość pustą!");
        }

        actualJourneyPath = new UniversalStack<>();
        foundPathList = new MyArrayList<>();

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

        /* searching paths algorithm */
        recurseEndPointFinder(startPointWrapper, endPoint);


        timings = new PathTimings[foundPathList.getSize()];
        calculateTimings(timings);

        Arrays.sort(timings);

        /* prepare answer */
        String result;
        if( timings.length != 0)
            result = printSimpleInfoAboutConnection(timings[0]);
        else
            result = "Brak połączenia pomiędzy tymi punktami";

        return result;

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

    private void calculateTimings(PathTimings [] whereToStore) {
        for(int i = 0; i < foundPathList.getSize(); i++) {
            MyArrayList <ConnectionToNextPoint> actualPath = foundPathList.get(i);
            double totalJourneyTime = addAllTimesInPath(actualPath);
            whereToStore[i] = new PathTimings(totalJourneyTime, actualPath);
        }
    }

    /* method to help counting time for method calculateTimings */
    private double addAllTimesInPath(MyArrayList <ConnectionToNextPoint> actual) {
        double result = 0;
        for( int j = 0; j < actual.getSize(); j++) {
            ConnectionToNextPoint temp = actual.get(j);
            result += temp.getTimeTravel();

            /* because the first and last points time pause doesn't count */
            if(j != 0 && j != actual.getSize() - 1)
                result += temp.getPauseTime();

        }
        return result;
    }

    public String printAllFoundConnections() {
        StringBuilder strBld = new StringBuilder();

        for( int i = 0; i < foundPathList.getSize(); i++ ) {
            strBld.append("\t").append(i+1).append(". ");

            MyArrayList <ConnectionToNextPoint> path = foundPathList.get(i);
            for( int j = 0; j < path.getSize(); j++) {
                MapPoint unwrapPoint = path.get(j).getPoint();
                if( j != 0) {
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

    public String printFoundConnectionsSortedByTime() {
        StringBuilder strBld = new StringBuilder();
        for( int i = 0; i < timings.length; i++ ) {
            PathTimings oneConnectin = timings[i];
            strBld.append("\t").append(i + 1).append(".");

            strBld.append(printAdvanceSpecifiedConnection(oneConnectin));
        }

        return strBld.toString();
    }

    private String printAdvanceSpecifiedConnection(PathTimings whichConnectionPrint) {
        StringBuilder strBld = new StringBuilder();

            strBld.append(" (total: ").append(String.format("%.3f", whichConnectionPrint.getTotalTime())).append(") ");

            MyArrayList <ConnectionToNextPoint> path = whichConnectionPrint.getPathReference();
            for( int j = 0; j < path.getSize(); j++) {
                MapPoint unwrapPoint = path.get(j).getPoint();
                if( j != 0) {
                    strBld.append("  --[").append(String.format("%.3f", path.get(j).getTimeTravel())).append("]-->  ");
                }

                strBld.append(unwrapPoint.getId());
                /* pause time for first and last point doesn't count*/
                if( j != 0 && j != path.getSize() - 1)
                    strBld.append(" (pause: ").append(unwrapPoint.getPauseTime()).append(")");

                if( !unwrapPoint.getName().equals(""))
                    strBld.append("(name: ").append(unwrapPoint.getName()).append(" )");
            }

            strBld.append("\n");

        return strBld.toString();
    }

    /* method which prints connection data in capability with dijkstrij algorithm printing result */
    private String printSimpleInfoAboutConnection(PathTimings shortestPath) {
        StringBuilder strBld = new StringBuilder();
        MyArrayList <ConnectionToNextPoint> foundPathList = shortestPath.getPathReference();
        for( int i = 0; i < foundPathList.getSize(); i++) {
            strBld.append("[id: ").append(foundPathList.get(i).getId());
            strBld.append(" name: ").append(foundPathList.get(i).getName()).append("]");
            if(i != foundPathList.getSize() - 1)
                strBld.append(" -> ");
        }
        strBld.append("\nCzas podróży: ").append(shortestPath.getTotalTime()).append("\n");

        return strBld.toString();
    }


}
