package Logic.GeoPointStructures;

import DrapekCollections.MyArrayList;

/**
 * Created by drapek on 03.01.16.
 */
public class PathTimings implements Comparable <PathTimings> {

    private  double totalTime;
    private MyArrayList <ConnectionToNextPoint> pathReference;

    public PathTimings(double time, MyArrayList <ConnectionToNextPoint> path) {
        if( time < 0 || path == null)
            throw new NullPointerException();
        totalTime = time;
        pathReference = path;
    }

    public double getTotalTime() {
        return totalTime;
    }


    public MyArrayList getPathReference() {
        return pathReference;
    }


    @Override
    public int compareTo(PathTimings o) {
        if( o == null) return -1;
        if( this.totalTime == o.getTotalTime())
            return 0;
        if( this.totalTime > o.getTotalTime())
            return 1;
        else
            return -1;
    }
}
