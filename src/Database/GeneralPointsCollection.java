package Database;

import DrapekCollections.MyArrayList;
import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.CantFindElementToDeleteException;
import ProjectExceptions.IdAlreadyExistException;
import ProjectExceptions.NameAlreadyExistException;

/**
 * Created by drapek on 27.12.15.
 */
public class GeneralPointsCollection {
    private static MyArrayList<MapPoint> generalCollection = new MyArrayList<>();
    private static int nextFreeID = 1;

    public static MapPoint searchById(int id) {
        if( id < 0 )
            return null; //because ids must be unsigned!

        for( int i = 0; i < generalCollection.getSize(); i++) {
            MapPoint temp = generalCollection.get(i);
            if( temp.getId() == id )
                return temp;
        }

        return null;
    }

    public static MapPoint searchByName(String name) {
        if( name.equals(""))
            return null; //because it's ok to doesn't have name, but then this can't be indentification

        for( int i = 0; i < generalCollection.getSize(); i++) {
            MapPoint temp = generalCollection.get(i);
            if( temp.getName().equals(name) )
                return temp;
        }

        return null;
    }

    public static void addPoint(MapPoint point) throws IdAlreadyExistException, NameAlreadyExistException {
        if( searchById(point.getId() ) != null)
            throw new IdAlreadyExistException("Nie mogę dodać punktu, bo punkt o tym ID już istnieje w bazie!");

        if( searchByName(point.getName()) != null)
            throw new NameAlreadyExistException("Nie mogę dodać puktu, bo punkt o tej nazwie już istnieje w bazie!");

        generalCollection.add(point);

        if( point.getId() >= nextFreeID ) {
            nextFreeID = point.getId() + 1;
        }
    }

    public static int nextFreeId() {
        int result = nextFreeID;
        nextFreeID++;
        return result;
    }

    public static void deletePoint(MapPoint whatDelete) throws CantFindElementToDeleteException {
        int index = generalCollection.getIndex(whatDelete);

        if( index < 0 )
            throw new CantFindElementToDeleteException("Nie mogę usunąć punktu z głownej bazy, bo go tam nie ma :D");

        generalCollection.delete(index);

    }

    public static void  printDatabase() {
        for( int i = 0; i < generalCollection.getSize(); i++) {
            System.out.println(generalCollection.get(i));
        }
    }

}
