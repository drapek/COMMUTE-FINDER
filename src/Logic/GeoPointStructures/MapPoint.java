package Logic.GeoPointStructures;

import Database.NotDefinedPointsCollection;
import DrapekCollections.MyArrayList;
import ProjectExceptions.CantBeNegativeException;
import ProjectExceptions.NameAlreadyExistException;
import ProjectExceptions.NotDefinedPointWithThisIdExistException;
import ProjectExceptions.NotDefinedPointWithThisNameExistException;

/**
 * Created by drapek on 27.12.15.
 */
public class MapPoint {
    private int id;
    private String name;
    private double pauseTime;
    private MyArrayList<ConnectionToNextPoint> connections;
    private boolean isDefined; /*parameter which is false, when this point has been genereted automative, and pauseTime and connecitons wasn,t defined*/

    private MapPoint(int id, String name, double pauseTime ) throws CantBeNegativeException, NameAlreadyExistException {
        if( id <= 0 )
            ; //TODO find next default id from DB database.getNextFreeId();
        else {
            //TODO sprawdź czy nie zajęty, jeśli tak to przypisz automatycznie
            this.id = id;
        }


        if(checkIfNameIsOccupied(name))
            throw new NameAlreadyExistException("Dana nazwa jest już zajęta!");

        this.name = name;

        this.pauseTime = pauseTime;

        isDefined = true;
        connections = new MyArrayList<>();
    }

    private MapPoint(int id, String name) throws CantBeNegativeException, NameAlreadyExistException {
        if( id <= 0 )
            ; //TODO find next default id from DB database.getNextFreeId();
        else {
            //TODO sprawdź czy nie zajęty, jeśli tak to przypisz automatycznie
            this.id = id;
        }

        if(checkIfNameIsOccupied(name))
            throw new NameAlreadyExistException("Dana nazwa jest już zajęta!");
        this.name = name;

        this.pauseTime = -1;//what mean that this object is not defined!

        isDefined = false;

        connections = new MyArrayList<>();
    }

    private boolean checkIfNameIsOccupied(String name) {
        if( name.equals(""))
            return false; //because is allowed to don't have name
        //TODO serach if (databse.searchName(name) != null)
        //return ture;
        return false;
    }

    //for normal object creating
    public static MapPoint getMapPointObject(int id, String name, double pauseTime) throws CantBeNegativeException, NameAlreadyExistException {
        //parsing
        if( pauseTime < 0 )
            throw new CantBeNegativeException("pauseTime must be positive because Time can't be negative");

        MapPoint notDefinedPointReference = checkIfPointNotExistAsNotDefined(id, name);
        if (notDefinedPointReference == null)
            return new MapPoint(id, name, pauseTime);
        else {
            editNotDefinedObject( notDefinedPointReference, id, name, pauseTime );
            try {
                NotDefinedPointsCollection.deleteNotDefinedPoint(notDefinedPointReference);
            } catch (Exception e) {
                System.err.println("Próbowano usunąć nieistniejący obiekt z bazy niezdefioniowanych punktów!");
                System.exit(1);
            }

        }

        return  notDefinedPointReference;
    }

    //needed when we must create connection, but given object not yet exist
    public static MapPoint getNotDefinedMapPoint(int id, String name) throws CantBeNegativeException, NameAlreadyExistException, NotDefinedPointWithThisIdExistException, NotDefinedPointWithThisNameExistException {
        MapPoint notDefined = new MapPoint(id, name);
        NotDefinedPointsCollection.addNotDefinedPoint(notDefined);
        return notDefined;
    }

    /*
        @return null if can't find
     */
    private static MapPoint checkIfPointNotExistAsNotDefined(int id, String name) {
        MapPoint result = NotDefinedPointsCollection.searchById(id);
        if( result != null )
            return result;
        result = NotDefinedPointsCollection.searchByName(name);
        if( result != null)
            return result;

        return null;
    }

    private static void editNotDefinedObject(MapPoint whatEdit, int id, String name, double pauseTime) {
        whatEdit.id = id;
        whatEdit.name = name;
        whatEdit.pauseTime = pauseTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPauseTime() {
        return pauseTime;
    }

    public MyArrayList<ConnectionToNextPoint> getConnections() {
        return connections;
    }

    public void addConnection(ConnectionToNextPoint destConnection) {
        if( this.equals(destConnection) )
            return; //do not assign this object as loop! because we have already pause_time!
        connections.add(destConnection);
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("----MapPoint----\n")
                .append("   ID: ").append(id).append("\n")
                .append("   Nazwa: ").append(name).append("\n")
                .append("   Czas postoju: ").append(pauseTime).append("\n")
                .append("   Połączenia: \n");
        if( !this.isDefined )
            strBuild.append("\t\tObiekt nie zdefiniowany!");
        else
            for(int i = 0; i < connections.getSize(); i++) {
                ConnectionToNextPoint connect = connections.get(i);
                strBuild.append("\t\t* Do id:").append(connect.getId()).append(" (name:").append(connect.getName())
                        .append("), Dystans: ").append(connect.getDistance()).append(", Prędkość: ").append(connect.getVelocity());
            }

        return strBuild.toString();
    }

}
