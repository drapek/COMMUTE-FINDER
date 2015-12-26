package CliMenu;

import java.util.Scanner;

/**
 * Created by drapek on 26.12.15.
 */
public class MainMenu {
    //TODO add database handler

    public static void main(String [] args) {
        mainMenu();
    }



    private static class MAIN_MENU_OPTIONS {
        static final int INVALID_OPTION = 0,
                MANUAL_ADD_POINT = 1,
                READ_POINTS_FROM_FILE = 2,
                WRITE_POINTS_TO_FILE = 3,
                PRINT_POINTS = 4,
                FIND_THE_FASTEST_PATH = 5,
                EXIT_PROGRAM = 6;
    };

    static final String MENU_TEXT_OPTIONS = new StringBuilder()
                                                .append("========= COMMUTE-FINDER ========= \n")
                                                .append("Wybierz jedną z poniższych opcji:\n")
                                                .append("\t 1. Dodaj ręcznie punkt \n")
                                                .append("\t 2. Wczytaj punkty z pliku \n")
                                                .append("\t 3. Zapisz punkty do pliku \n")
                                                .append("\t 4. Wyświetl listę wczytanych punktów \n")
                                                .append("\t 5. Znajdź najszybszą ścieżkę pomiędzy punktami \n")
                                                .append("\t 6. Wyjdź z programu \n")
                                                .append("Twój wybór: \n")
                                                .toString();

    static final String ADD_POINTS_OPTIONS = new StringBuilder()
                                                .append("\t 1. Dodaj połączenie (drogę do innego pkt) \n")
                                                .append("\t 2. Zapisz punkt i wyjdź \n")
                                                .append("\t 3. Wyjdź bez zapisywania \n")
                                                .append("Twój wybór: \n")
                                                .toString();


    static final String BIG_SPACE = "\n \n \n \n \n \n \n \n \n \n \n \n";

    private static void mainMenu() {
        Scanner input = new Scanner(System.in);
        if( input == null ) {
            System.err.println("Nie mogłem wczytać consoli!");
            System.exit(1);
        }

        while ( true ) {
            //TODO print info about number of readed points
            System.out.println("Wczytano 0 punktów.");
            System.out.print(MENU_TEXT_OPTIONS);
            int anwser = input.nextInt();

            boolean exitProgram = false;
            switch (anwser) {
                case MAIN_MENU_OPTIONS.INVALID_OPTION:
                    System.out.println(BIG_SPACE);
                    break;
                case MAIN_MENU_OPTIONS.MANUAL_ADD_POINT:
                    manualAddPointMenu(input);
                    break;
                case MAIN_MENU_OPTIONS.READ_POINTS_FROM_FILE:
                    System.out.println(BIG_SPACE);
                    readFromFileMenu(input);
                    break;
                case MAIN_MENU_OPTIONS.WRITE_POINTS_TO_FILE:
                    System.out.println(BIG_SPACE);
                    writeToFileMenu(input);
                    break;
                case MAIN_MENU_OPTIONS.PRINT_POINTS:
                    System.out.println(BIG_SPACE);
                    printEveryPointMenu();
                    break;
                case MAIN_MENU_OPTIONS.FIND_THE_FASTEST_PATH:
                    System.out.println(BIG_SPACE);
                    findFastestPathMenu(input);
                    break;
                case MAIN_MENU_OPTIONS.EXIT_PROGRAM:
                    return;
                default:
                    break;
            }
        }

    }

    private static void readFromFileMenu(Scanner consoleHndl) {
        System.out.println("Podaj ścieżkę pliku w którym zapisane są punkty: ");
        String filePath = consoleHndl.next();

        //TODO parse filePath
        boolean parsingFilePathResult = false;

        while ( !parsingFilePathResult ) {
            System.out.println("Podany plik nie może zostać otworzony, spróbuj jeszcze raz lub wpisz '#stop' by wyjść \n Podaj ścieżkę pliku: ");
            filePath = consoleHndl.next();
            if ( filePath.equals("#stop") )
                return;

            //TODO attach parser
            parsingFilePathResult = true;
        }

        //TODO invoke function to read
    }

    private static void writeToFileMenu(Scanner consoleHndl) {
        System.out.println("Podaj ścieżkę pliku do którego chcesz zapisać punkty: ");
        String filePath = consoleHndl.next();

        //TODO parse filePath
        boolean parsingFilePathResult = false;

        while ( !parsingFilePathResult ) {
            System.out.println("Nie mogę zapisać pliku, zmień lokalizacje zapisu badź nazwę pliku! lub wpisz '#stop' by wyjść \n Podaj ścieżkę pliku: ");
            filePath = consoleHndl.next();
            if ( filePath.equals("#stop") )
                return;

            //TODO attach parser
            parsingFilePathResult = true;
        }

        //TODO invoke function to read
    }

    private static void printEveryPointMenu() {
        System.out.println("Lista wszystkich punktów:");
        System.out.println("-------------------------");

        //TODO invoke function which prints every point
    }

    private static void findFastestPathMenu(Scanner in) {
        System.out.println("Znajdywanie naszybszego połączenia");
        System.out.println("----------------------------------");
        System.out.println("Podaj ID lub nazwę punktu startowego: (wpisanie '#pokaz_punkty' wyswietli liste wszystkich punktow, '#stop' powrót do głównego menu )");

        //TODO switch with proper points types!!!
        Object startPoint = null;
        Object endPoint = null;

        if( !findAndAssignPoint(startPoint, in, "punktu startowego") ) {
            return; //because user writen #stop command
        }

        if( !findAndAssignPoint(endPoint, in, "punktu końcowego") ) {
            return; //because user writen #stop command
        }

        //TODO start algoirtm with this types and print result

    }

    private static boolean findAndAssignPoint(Object geoPoint, Scanner in, String pointName) {
        boolean pointIsFound = false;
        while( !pointIsFound ) {
            System.out.println( new StringBuilder().append("Id lub nazwa" ).append(pointName).append(": ").toString() );
            String ans = in.next();
            if( ans.equals("#stop") ) {
                return false;
            }
            if ( ans.equals("#pokaz_punkty") ) {
                printEveryPointMenu();
                System.out.println("\n \n \n");
                continue;
            }

            geoPoint = parsePoint(ans);
            if( geoPoint != null )
                pointIsFound = true;
        }
        return true;
    }

    //TODO change to proper type of point
    private static Object parsePoint(String pattern) {
        //TODO if only numbers serach by ids
        if( pattern.equals("object") ) //only for debbuging delete this later
            return new Object();
        //TODO search by names if first step can't found


        return null; //if any of method can't found
    }


    private static void manualAddPointMenu(Scanner in) {
        System.out.println("Dodawanie punktu: ");
        System.out.println("----------------- ");
        System.out.println("podaj ID (pozostawienie pustego autmoatycznie przypisze wolne ID,  wpisanie '#stop' spowoduje powrót do menu głównego) ");
        System.out.println("Id: ");
        String ans = in.next();

        boolean idIsGood = false;
        while( !idIsGood ) {
            if( ans.equals("#stop"))
                return;
            idIsGood = addingPointParseID();

            if ( !idIsGood) {
                //powtarza komunikat gdy wprowadzi się źle
                System.out.println("Podałeś złe id (mogą być to tylko liczby, lub te id jest już zajęte)");
                System.out.println("Inne id:");
                ans = in.next();
            }
        }

        //TODO make new object mapPoint and assign this value to ID
        String pointId = ans;


        System.out.println("podaj nazwę (można pozostawić puste,  wpisanie '#stop' spowoduje powrót do menu głównego) ");
        System.out.println("Nazwa: ");
        ans = in.next();

        idIsGood = false;
        while( !idIsGood ) {
            if( ans.equals("#stop"))
                return;
            idIsGood = addingPointParseName();

            if ( !idIsGood) {
                //powtarza komunikat gdy wprowadzi się źle
                System.out.println("Podałeś złą nazwę (musi być już zajęta)");
                System.out.println("Inna nazwa:");
                ans = in.next();
            }
        }
        //TODO assign this value to object
        String pointName = ans;

        System.out.println("podaj czas oczekiwania.");
        System.out.println("Czas: ");
        Double pointPasueTime = in.nextDouble();

        System.out.println("Możliwe opcje:");
        while ( true ) {
            printHeaderOfAddedPoint(new Object()/*TODO replece it with proper object*/);
            System.out.println(ADD_POINTS_OPTIONS);
            int optionAnswer = in.nextInt();

            switch (optionAnswer) {
                case 1:
                    addNewConnectionToPoint(new Object()/*TODO replece it with proper object*/, in);
                    break;
                case 2:
                    addThisPointToDataBase(new Object()/*TODO replece it with proper object*/);
                    return;
                case 3:
                    return;
                default:
                    break;
            }
        }

    }

    private static boolean addingPointParseID() {
        //TODO add parsing, it can be number (which must be compare to db if is not busy) or ==""
        return true;
    }

    private static boolean addingPointParseName() {
        //TODO check if this name is not occupied
        return true;
    }

    //TODO change object type to proper
    private static void printHeaderOfAddedPoint(Object geoPoint) {
        //TODO Print ID, name, and number of connections
    }

    //TODO change object type to proper
    private static void addThisPointToDataBase(Object geoPoint){
        //TODO don't forget that id can be empty, so the database must assign default ID!
    }

    //TODO change object type to proper
    private static void addNewConnectionToPoint(Object geoPoint, Scanner in) {
        //TODO make object of wrap for connection
        System.out.println("New conncetion adding:");
        System.out.println("----------------------");

        System.out.println("podaj id lub nazwę (wpisanie '#stop' spowoduje powrót do menu głównego, '#pokaz_punkty' wyswietli liste pkt) ");
        System.out.println(" Id lub Nazwa: ");
        String ans = in.next();

        Object connectionReference;

        boolean idIsGood = false;
        boolean hasShownPointList = false;
        while( !idIsGood ) {

            if( ans.equals("#stop"))
                return;

            if( ans.equals("#pokaz_punkty")) {
                printEveryPointMenu();
                hasShownPointList = true;
            }

            connectionReference = parsePoint( ans);
            if( connectionReference != null)
                break; //break becauce we found what we want in this loop

            if ( !idIsGood) {
                //powtarza komunikat gdy wprowadzi się źle
                if(!hasShownPointList) System.out.println("Prawdopodobnie złe id/nazwa");
                System.out.println(" Id lub nazwa:");
                ans = in.next();
            }

            hasShownPointList = false; //zerowanie wskaźnika mówiącego czy użytkownik wywołał listę
        }
        //TODO assign this connection Referetnce to wrap


        System.out.println("podaj dystans ( wpisanie '#stop' spowoduje powrót do menu głównego) ");
        System.out.println(" dystans: ");
        ans = in.next();

        idIsGood = false;
        while( !idIsGood ) {
            if( ans.equals("#stop"))
                return;
            idIsGood = parseDistanceAndVelocityValue(ans);

            if ( !idIsGood) {
                //powtarza komunikat gdy wprowadzi się źle
                System.out.println("Dystans nie może być ujemny!");
                System.out.println("Inna dystans:");
                ans = in.next();
            }
        }
        //TODO assign distance to wrap

        System.out.println("podaj prędkość");
        System.out.println(" prędkość: ");
        ans = in.next();

        idIsGood = false;
        while( !idIsGood ) {
            if( ans.equals("#stop"))
                return;
            idIsGood = parseDistanceAndVelocityValue(ans);

            if ( !idIsGood) {
                //powtarza komunikat gdy wprowadzi się źle
                System.out.println("Prędkość nie może być ujemna!");
                System.out.println("Inna prędkość:");
                ans = in.next();
            }
        }
        //TODO assign velocity to wrap

        while ( true ) {
            //TODO print this wrap obcjet
            System.out.println("1. Zapisz połączenie do punktu\n2. Wyjdź bez zapisu połączenia");
            int optionAnswer = in.nextInt();

            switch (optionAnswer) {
                case 1:
                    //TODO add this wrap to geoPoint
                    System.out.println("dodano!");
                    return;
                case 2:
                    return;
                default:
                    break;
            }
        }


    }

    private static boolean parseDistanceAndVelocityValue(String value) {
        //TODO convert to int
        //TODO can be double... but not negative
        return true;
    }





}

