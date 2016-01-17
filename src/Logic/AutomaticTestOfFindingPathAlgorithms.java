package Logic;

import Database.GeneralPointsCollection;
import DrapekCollections.MyArrayList;
import IOoperations.IOLinux;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;

import java.util.Random;

/**
 * Created by drapek on 16.01.16.
 */
public class AutomaticTestOfFindingPathAlgorithms {
    private final int TEST_NUMBER = 100;

    private double minPause;
    private double maxPause;

    private double minVelocity;
    private double maxVelocity;

    private double minDistance;
    private double maxDistance;

    private String [] filePathToStructures;

    private Random rand;

    public AutomaticTestOfFindingPathAlgorithms(double minPause, double maxPause,
                                                double minVelocity, double maxVelocity,
                                                double minDistance, double maxDistance) {

        if( minPause < 0 || maxPause < 0 || minVelocity <= 0 || maxVelocity < 0 ||
                minDistance < 0 || maxDistance < 0)
            throw new IllegalArgumentException("Podałeś ujemną wartość dla skłądowych które takie nie mogą być!");

        if( minPause > maxPause || minVelocity > maxVelocity || minDistance > maxDistance)
            throw new IllegalArgumentException("Podany zakres min, nie może przewyższać wartości max!");

        this.minPause = minPause;
        this.maxPause = maxPause;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.rand = new Random();


        filePathToStructures = new String[4];
        filePathToStructures[0] = "/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/AutoTest_1.xml";
        filePathToStructures[1] = "/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/AutoTest_2.xml";
        filePathToStructures[2] = "/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/AutoTest_3.xml";
        filePathToStructures[3] = "/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/AutoTest_4.xml";
        //filePathToStructures[4] = "/home/drapek/IdeaProjects/COMMUTE-FINDER/TestFiles/AutoTest_5.xml"; //zbyt długo musi się wykonywać nie warto


    }

    public void startTest() {
        //rand the structure
        int drawnStructure = rand.nextInt(filePathToStructures.length);

        //read structure to program db
        IOLinux pointReader = new IOLinux();
        //                                           v normaly here is: drawnStructure but you can change on specyfic path
        pointReader.readXMLFile(filePathToStructures[drawnStructure]);

        randValuesForStructureParameters();

        System.out.println("######### Struktura z wylosowanymi danymi: ###########");
        GeneralPointsCollection.printDatabase();

        MyShortestJourneyFinder myFinder = new MyShortestJourneyFinder();
        DijkstryJourneyFinder dijkstryFinder = new DijkstryJourneyFinder();

        int errCounter = 0;

        //the proper testing
        for( int i = 0; i < TEST_NUMBER; i ++ ) {
            MapPoint startPoint = randomPointFromCollection();
            MapPoint endPoint = randomPointFromCollection();

            long start = System.nanoTime();
            String myAlgResult = myFinder.findShortestPath(startPoint, endPoint);
            long proccesTimeMyAlgorithm = System.nanoTime() - start;


            start = System.nanoTime();
            String dijkstrAlgResult = dijkstryFinder.findShortestPath(startPoint, endPoint);
            long proccesTimeDijkstryAlgorithm = System.nanoTime() - start;

            String comparingResult = myAlgResult.equals(dijkstrAlgResult) ? "[OK]" : "[ERR]";
            if(comparingResult.equals("[ERR]"))
                errCounter++;

            //printing results
            StringBuilder strBld = new StringBuilder();

            strBld.append("Test ").append(i + 1);
            strBld.append(": z punktu (id: ").append(startPoint.getId()).append(" name: ").append(startPoint.getName()).append(")");
            strBld.append(" do punktu (id: ").append(endPoint.getId()).append(" name: ").append(endPoint.getName()).append(")\n");
            strBld.append("\tMój algorytm: ").append(myAlgResult);
            strBld.append("\tDikstrij algorytm: ").append(dijkstrAlgResult);
            strBld.append("Wynik: ").append(comparingResult).append("\n");
            strBld.append("\tCzas mojego: ").append(proccesTimeMyAlgorithm).append("\n");
            strBld.append("\tCzas dijkstra: ").append(proccesTimeDijkstryAlgorithm).append("\n\n");

            System.out.println(strBld.toString());

        }

        System.out.println("############ Podsumowanie ###########");
        System.out.println("znaleziono " + errCounter + " błędów. (" + ((double) errCounter / (double) TEST_NUMBER) * 100 + " %)");
    }

    private void randValuesForStructureParameters() {
        MyArrayList <MapPoint> database = GeneralPointsCollection.getDatabase();
        for(int i = 0; i < database.getSize(); i ++) {
            MapPoint tempPoint = database.get(i);
            tempPoint.testingSetPauseTime(randDouble(minPause, maxPause));

            MyArrayList <ConnectionToNextPoint> connectionsOfTempPoint = tempPoint.getConnections();
            if(tempPoint.isDefined()) {
                for( int j = 0 ; j < connectionsOfTempPoint.getSize() ; j ++) {
                    connectionsOfTempPoint.get(j).testingSetDistanceAndVelocity(randDouble(minDistance, maxDistance), randDouble(minVelocity, maxVelocity));
                }
            }
        }
    }

    private double randDouble(double min, double max ) {
        return  rand.nextDouble() * ( max - min ) + min;
    }

    private MapPoint randomPointFromCollection() {
        MyArrayList <MapPoint> database = GeneralPointsCollection.getDatabase();
        int randedPoint = rand.nextInt(database.getSize());
        return database.get(randedPoint);
    }

    public static void main(String [] args ) {
        AutomaticTestOfFindingPathAlgorithms newTest = new AutomaticTestOfFindingPathAlgorithms(0, 25, 1, 200, 0, 500);
        newTest.startTest();
    }
}
