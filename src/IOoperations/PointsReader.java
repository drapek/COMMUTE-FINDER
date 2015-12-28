package IOoperations;

import Database.GeneralPointsCollection;
import Logic.GeoPointStructures.ConnectionToNextPoint;
import Logic.GeoPointStructures.MapPoint;
import ProjectExceptions.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * Created by drapek on 28.12.15.
 */
public class PointsReader {
    DocumentBuilder dBuilder;
    NodeList xmlPointsList;

    public PointsReader()  {
        /*init XML parser*/
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            printError(e);
        }
    }

    public void readFile(File input) {
        try {
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            xmlPointsList = doc.getElementsByTagName("mapPoint");
            readPointData();
        } catch (Exception e) {
            printError(e);
        }
    }

    private void readPointData() {
        for(int i =0; i < xmlPointsList.getLength(); i++) {
            Node onePoint = xmlPointsList.item(i);
            if (onePoint.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) onePoint;
                String id;
                String name;
                String pauseTime;

                /* wyłapywanie wyjątków ponieważ w naszym przypadku gdy pola nie istnieją to mamy zdefioniwane akcje autmatyczne */
                if( eElement.hasAttribute("id") ) {
                    id = eElement.getAttribute("id");
                } else {
                    id = "";
                }

                try {
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                } catch (NullPointerException e) {
                    name = "";
                }

                try {
                    pauseTime = eElement.getElementsByTagName("pauseTime").item(0).getTextContent();
                } catch (Exception e) {
                    pauseTime = "";
                }

                int convertedId = convertId(id);
                double convertedTime = convertPauseTime(pauseTime);


                MapPoint newMapPoint = null;
                try {
                    newMapPoint = MapPoint.getMapPointObject(convertedId, name, convertedTime);
                } catch (NameAlreadyExistException e) {
                    System.err.println("Obiekt " + convertedId + " (name: " + name + ") już istnieje o tej nazwie!");
                    e.printStackTrace();
                    System.exit(1);
                } catch (CantBeNegativeException e) {
                    System.err.println("Obiekt " + convertedId + " (name: " + name + ") ma ujemny czas postoju!");
                    e.printStackTrace();
                    System.exit(1);
                } catch (IdAlreadyExistException e) {
                    System.err.println("Obiekt " + convertedId + " (name: " + name + ") już istnieje o tym id!");
                    e.printStackTrace();
                    System.exit(1);
                }

                if( newMapPoint == null) {
                    System.err.println("Z jakiś powodów obiekt newMapPoint nie istnieje i nie można do niego przypisać połaczeń!");
                    System.exit(1);
                }

                //connections
                NodeList connectionsList = eElement.getElementsByTagName("connection");
                for(int j = 0; j < connectionsList.getLength(); j++) {
                    Node oneConnection = connectionsList.item(j);
                    if (onePoint.getNodeType() == Node.ELEMENT_NODE) {
                        Element eConnection = (Element) oneConnection;
                        String destId;
                        String destName;
                        try {
                            destId = eConnection.getElementsByTagName("destId").item(0).getTextContent();
                        } catch (NullPointerException e ){
                            destId = "";
                        }
                        int convertedDestId = convertId(destId);

                        try {
                            destName = eConnection.getElementsByTagName("destName").item(0).getTextContent();
                        } catch (NullPointerException e ){
                            destName = "";
                        }

                        String distance = eConnection.getElementsByTagName("distance").item(0).getTextContent();
                        double convertedDistance = Double.parseDouble(distance);
                        String velocity = eConnection.getElementsByTagName("velocity").item(0).getTextContent();
                        double convertedVelocity = Double.parseDouble(velocity);

                        MapPoint destMapPoint = getProperMapPoint(convertedDestId, destName);
                        try {
                            ConnectionToNextPoint tempConnection = new ConnectionToNextPoint(destMapPoint, convertedDistance, convertedVelocity);
                            newMapPoint.addConnection(tempConnection);
                        } catch (CantBeNegativeException e) {
                            System.err.println("Prędkość oraz dystans nie mogą być ujemne!");
                            e.printStackTrace();
                            System.exit(1);
                        }

                    }
                }


            }
        }

        System.out.println("#############Print wczytanych danych##########");
        GeneralPointsCollection.printDatabase();
    }

    private int convertId(String nmb) {
        if (nmb.equals(""))
                return -1;
        return Integer.parseInt(nmb);
    }

    private double convertPauseTime(String time) {
        if( time.equals(""))
            return 0.0;
        return Double.parseDouble(time);
    }

    /*
        This method search matches MapPoint in DB, first by ID then if not found by Name and then if not found it will create
          notDefined point.
     */
    private MapPoint getProperMapPoint(int id, String name) {
        MapPoint result = null;
        if( id > 0 )
            result = GeneralPointsCollection.searchById(id);
        if( result == null)
            result = GeneralPointsCollection.searchByName(name);
        if( result == null)
            try {
                result = MapPoint.getNotDefinedMapPoint(id, name);
            } catch (NotDefinedPointWithThisNameExistException e) {
                System.err.println("Już zdefniowano punkt o tej nazwie, a chciałem go jeszcze raz utowrzyć! Miałem pewnie problem z wyszukiwaniem! Pamiętaj że każdy punkt musi mieć własną nazwę");
                e.printStackTrace();
                System.exit(1);
            } catch (NameAlreadyExistException e) {
                System.err.println("Punkt o tej nazwie został już defionowny, błąd logiczny programu nie mógł wyszukać tego punktu");
                e.printStackTrace();
                System.exit(1);
            } catch (NotDefinedPointWithThisIdExistException e) {
                System.err.println("Punkt ten insieje już w niezdefionowanych! Problem z tym samym ID");
                e.printStackTrace();
                System.exit(1);
            } catch (IdAlreadyExistException e) {
                System.err.println("Punkt o tym id już istnieje w bazie głównej! Problem wewnętrzny z wyszukiwaniem");
                e.printStackTrace();
                System.exit(1);
            }

        return result;
    }

    private void printError(Exception e) {
        System.err.println("Nastąpił błąd przy wczytywaniu pliku!");
        e.printStackTrace();
        System.exit(1);
    }



}
