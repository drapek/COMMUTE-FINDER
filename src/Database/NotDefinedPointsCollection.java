package Database;

import DrapekCollections.MyArrayList;
import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.CantFindElementToDeleteException;
import ProjectExceptions.NotDefinedPointWithThisIdExistException;
import ProjectExceptions.NotDefinedPointWithThisNameExistException;

/**
 * Created by drapek on 27.12.15.
 */
public class NotDefinedPointsCollection {
    private static MyArrayList <MapPoint> notDefinedPointsArrayList = new MyArrayList<>();

    public static void addNotDefinedPoint(MapPoint whatAdd) throws NotDefinedPointWithThisIdExistException, NotDefinedPointWithThisNameExistException {
        if( whatAdd == null)
            throw new NullPointerException("Nie wolno przekazywać pustych MapPointów!");

        if( searchById(whatAdd.getId()) != null)
            throw new NotDefinedPointWithThisIdExistException("Nie mogę włożyć obiektu bo isteje już w bazie obiekt o tym ID!");

        if( searchByName(whatAdd.getName()) != null)
            throw new NotDefinedPointWithThisNameExistException("Nie mogę włożyć obiektu bo isteje już w bazie obiekt o tej Nazwie!");

        notDefinedPointsArrayList.add(whatAdd);
    }

    public static void deleteNotDefinedPoint(MapPoint whichDelete) throws CantFindElementToDeleteException {
        int index = notDefinedPointsArrayList.getIndex(whichDelete);
        if( index < 0 )
            throw new CantFindElementToDeleteException("Nie mogę usunąć notDefinedPoint, bo nie mogę go odnaleźć w bazie niezdefionowanych punktów!");
        notDefinedPointsArrayList.delete(index);
    }

    /*
        @return null if object not found
     */
    public static MapPoint searchByName(String name) {
        if (name.equals(""))
                return null;

        for(int i = 0; i < notDefinedPointsArrayList.getSize(); i++ ) {
            MapPoint tmpPoint = notDefinedPointsArrayList.get(i);
            if( (tmpPoint.getName()).equals(name))
                return tmpPoint;
        }
        return null;
    }

    /*
        @return null if object not found
     */
    public static MapPoint searchById(int id){
        for(int i = 0; i < notDefinedPointsArrayList.getSize(); i++ ) {
            MapPoint tmpPoint = notDefinedPointsArrayList.get(i);
            if( tmpPoint.getId() == id)
                return tmpPoint;
        }
        return null;
    }

    public static boolean searchByExistion(MapPoint elem) {
        if( elem == null)
            throw new NullPointerException("NotDefinedDB - serach by existions dostało pusty element do wyszukania!");

        for( int i = 0; i < notDefinedPointsArrayList.getSize(); i++) {
            if(elem.equals(notDefinedPointsArrayList.get(i)))
                return true;
        }

        return false;
    }

    public static String DBtoString() {
        StringBuilder strBld = new StringBuilder();
        for(int i = 0; i < notDefinedPointsArrayList.getSize(); i++) {
            strBld.append("---NotDefined Point---\n")
                    .append("\t id: " ).append(notDefinedPointsArrayList.get(i).getId()).append("\n")
                    .append("\t name: ").append(notDefinedPointsArrayList.get(i).getId()).append("\n\n");
        }

        String result = strBld.toString();
        if ( result.equals(""))
            return "\t brak";
        else
            return result;
    }
}
