package Logic;

import Database.GeneralPointsCollection;
import DrapekCollections.MyArrayList;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.CantFindTheLowestTimeDijkstry;

/**
 * Created by drapek on 16.01.16.
 */
public class DijkstryJourneyFinder {

    MyArrayList <MapPoint> notProceedVertices;
    wrapDijkstryPoint [] distanceAndPredecessorTable;
    private double journeyTime = 0.0;

    public static final double INFINITY_TIME_TRAVEL = -1.0;
    public static final int RESULT_CANT_FIND_PATH = 1;
    public static final int RESULT_ALGORITHM_OK = 0;

    private void initializeAlgorithmCollections() {
        //copy all defined points from database to notProccedVertives
        notProceedVertices = new MyArrayList<>();
        MyArrayList <MapPoint> tempDbReference = GeneralPointsCollection.getDatabase();
        for( int i = 0; i < tempDbReference.getSize(); i++) {
            MapPoint tempPoint = tempDbReference.get(i);
            if(tempPoint.isDefined())
                notProceedVertices.add(tempPoint);
        }

        //make table of distances and predescessor table and init it
        distanceAndPredecessorTable = new wrapDijkstryPoint[notProceedVertices.getSize()];
        for( int i = 0; i < distanceAndPredecessorTable.length; i++) {
            distanceAndPredecessorTable[i] = new wrapDijkstryPoint(notProceedVertices.get(i), INFINITY_TIME_TRAVEL, null );

        }

    }

    public String findShortestPath(MapPoint startPoint, MapPoint endPoint) {
        initializeAlgorithmCollections();
        wrapDijkstryPoint startWrap = findWrapInCollection(startPoint);
        startWrap.travelAndStopTime = 0;

        int resultCode = dijkstryCoreAlgorithm();

        /* prepare answer */
        String result;
        if( resultCode == RESULT_ALGORITHM_OK || startPoint.equals(endPoint)) {
            MyArrayList <MapPoint> anwser = makePointsTraceFromAnwser(startPoint, endPoint);
            calculateJourneyTime(endPoint);

            result = formResultPath(anwser);
        }
        else
            result = "Brak połączenia pomiędzy tymi punktami\n";

        return result;
    }

    private int dijkstryCoreAlgorithm() {
        double theLastShortestTime  = -1.0;
        while( notProceedVertices.getSize() > 0 ) {
            try {
                wrapDijkstryPoint theShortestTimeWrapp = getWrapShortestTimeAtGivenTime();
                deleteWrapperFromNotProceedCollection(theShortestTimeWrapp.originPoint);
                theLastShortestTime = theShortestTimeWrapp.travelAndStopTime;

                MyArrayList <ConnectionToNextPoint> neighbors = theShortestTimeWrapp.originPoint.getConnections();
                for(int i = 0; i < neighbors.getSize(); i++) {
                    MapPoint theNeighbor = neighbors.get(i).getPoint();
                    if( !pointExistInNotProceed(theNeighbor) )
                        continue;
                    
                    wrapDijkstryPoint wrappedNeighbor = findWrapInCollection(theNeighbor);
                    double travelTimeWithStopping = neighbors.get(i).getTimeTravel() + theNeighbor.getPauseTime();
                    if( (wrappedNeighbor.travelAndStopTime == INFINITY_TIME_TRAVEL) ||
                        (wrappedNeighbor.travelAndStopTime > (theShortestTimeWrapp.travelAndStopTime + travelTimeWithStopping )) ) {
                        wrappedNeighbor.travelAndStopTime = theShortestTimeWrapp.travelAndStopTime + travelTimeWithStopping;
                        wrappedNeighbor.predecessor = theShortestTimeWrapp.originPoint;
                    }
                }

            } catch(CantFindTheLowestTimeDijkstry e) {
                return RESULT_CANT_FIND_PATH;
            }
            catch( Exception e ) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return RESULT_ALGORITHM_OK;
    }

    private MyArrayList <MapPoint> makePointsTraceFromAnwser(MapPoint startPoint, MapPoint endPoint) {
        MyArrayList <MapPoint> reverseAnwser = new MyArrayList<>();
        wrapDijkstryPoint tmpWrapPoint = findWrapInCollection(endPoint);
        reverseAnwser.add(endPoint);

        while( !tmpWrapPoint.originPoint.equals(startPoint) ) {
            reverseAnwser.add(tmpWrapPoint.predecessor);
            tmpWrapPoint = findWrapInCollection(tmpWrapPoint.predecessor);
        }

        //reverse the anwser
        MyArrayList <MapPoint> goodAnwser = new MyArrayList<>();
        for( int i = reverseAnwser.getSize() - 1; i >= 0; i -- ) {
            goodAnwser.add(reverseAnwser.get(i));
        }

        return goodAnwser;
    }

    private boolean pointExistInNotProceed(MapPoint vertex) {
        return notProceedVertices.getIndex(vertex) != -1;
    }

    private void deleteWrapperFromNotProceedCollection(MapPoint toDelete) {
        int elementId = notProceedVertices.getIndex(toDelete);
        if( elementId < 0 )
            throw new ArrayIndexOutOfBoundsException("Nastąpiła próba usunięcia elementu kóry nie istniał w kolecji Dijkstra!");
        notProceedVertices.delete(elementId);
    }

    /**
     *  it takes wrapper with lower travel time, but it can't be lower than @param bottomLimit
     **/
    private wrapDijkstryPoint getWrapShortestTimeAtGivenTime() throws CantFindTheLowestTimeDijkstry {
        wrapDijkstryPoint theLowest = new wrapDijkstryPoint(null, INFINITY_TIME_TRAVEL, null); //assign null wrap which must be overwritten by the lower

        for( int i = 0; i < notProceedVertices.getSize(); i ++) {
            wrapDijkstryPoint tempWrap = findWrapInCollection(notProceedVertices.get(i));
            //wrap from collection if is infinity it can't be the lower!
            if( tempWrap.travelAndStopTime == INFINITY_TIME_TRAVEL)
                continue;

            //if the theLowest is INFINITY it means that there weren't anny assigments to theLowest yet
            if( theLowest.travelAndStopTime == INFINITY_TIME_TRAVEL)
                theLowest = tempWrap;
            //and the proper finding the lowest time
            if( tempWrap.travelAndStopTime < theLowest.travelAndStopTime )
                theLowest = tempWrap;

        }

        //if it can't finds the lower time
        if( theLowest.travelAndStopTime == INFINITY_TIME_TRAVEL)
            throw new CantFindTheLowestTimeDijkstry("Wystąpił błąd, algorytm nie mógł odnaleźć kolejenej najmijeszej wartości przez co nie jest w stanie odnaleźć ściezki!");

        return theLowest;

    }


    /* finds wraps of given point */
    private wrapDijkstryPoint findWrapInCollection(MapPoint find) {
        for( wrapDijkstryPoint tempWrap : distanceAndPredecessorTable ) {
            if((tempWrap.originPoint).equals(find))
                return tempWrap;
        }

        return null;
    }

    private class wrapDijkstryPoint {
        wrapDijkstryPoint( MapPoint originPoint, double timeTravel, MapPoint predecessor ) {
            this.originPoint = originPoint;
            this.travelAndStopTime = timeTravel;
            this.predecessor = predecessor;
        }

        MapPoint originPoint;
        double travelAndStopTime; //jest to suma czasu podróży wraz z czasem postoju danym w punkcie docelowym
        MapPoint predecessor;
    }

    private String formResultPath(MyArrayList <MapPoint> anwser) {
        StringBuilder strBld = new StringBuilder();

        for( int i = 0; i < anwser.getSize(); i++) {
            strBld.append("[id: ").append(anwser.get(i).getId());
            strBld.append(" name: ").append(anwser.get(i).getName()).append("]");
            if(i != anwser.getSize() - 1)
                strBld.append(" -> ");
        }
        strBld.append("\nCzas podróży: ").append(String.format("%.3f", journeyTime)).append("\n");

        return strBld.toString();
    }

    private void calculateJourneyTime(MapPoint endPoint) {
        wrapDijkstryPoint wrapper = findWrapInCollection(endPoint);
        /* we different by endPoint pause time, because it doesn't count to journeyTime */
        journeyTime = wrapper.travelAndStopTime - endPoint.getPauseTime();
        if( journeyTime < 0 )
            journeyTime = 0.0;
    }
}
