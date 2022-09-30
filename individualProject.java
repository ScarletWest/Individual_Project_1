import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Arrays.deepToString;

public class individualProject {
    public static ArrayList<String[]>  routesData = new ArrayList<>();
    public static ArrayList<String[]>  inputData = new ArrayList<>();
    public static HashMap<String, String[]> airportData = new HashMap<>();
    public static HashMap<String, String[]> airlineData = new HashMap<>();
    public static void main (String[] args) throws IOException {
        String line = "";
        String splitBy = ",";
        BufferedReader input_file = null;
        BufferedReader airport = null;
        BufferedReader airline = null;
        BufferedReader route = null;

        try {
            route = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/routes.csv"));
            airline = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airlines.csv"));
            airport = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airports.csv"));
            input_file = new BufferedReader(new FileReader("/Users/user/Downloads/Cotonou-Ouagadougo.csv"));

        } catch (
                IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


        String[] routes = new String[0];
        while ((line = route.readLine()) != null) {
            routes = line.split(splitBy);
            routesData.add(routes);
//            System.out.println("Routes[Airline code =" + routes[0] + "Airline ID =" + routes[1] + "Source Airport code =" + routes[2] + "Source Airport ID =" + routes[3] + "Destination Airport ID =" + routes[4] + "Destination Airport Code =" + routes[5] + "Codeshare =" + routes[6] + "Stops =" + routes[7] + "Equipment =" + routes[8] + "]");

        }
        String[] airlines = new String[0];
        while ((line = airline.readLine()) != null) {
            airlines = line.split(splitBy);
            airlineData.put(airlines[0],airlines);
//            System.out.println("Airlines[Airline ID =" + airlines[0] + ", Name = " + airlines[1] + ", Alias =" + airlines[3] + ", IATA code = " + airlines[4] + ", Callsign =" + airlines[5] + ", Country = " + airlines[6] + ", Active =" + airlines[7] + "]");
        }
//        for (String[] airlineArray: airlineData.values()) {
//            System.out.println(deepToString(airlineArray));
//        }



        String[] airports = new String[0];
        while ((line = airport.readLine()) != null) {
            airports = line.split(splitBy);
            airportData.put(airports[0],airports);

//            System.out.println("Airports[Airports ID =" + airports[0] + ", Name =" + airports[1] + ", City =" + airports[2] + ", Country =" + airports[3] + ",IATA code =" + airports[4] + ", ICAO code =" + airports[5] + ", Latitude =" + airports[6] + ", Longitude =" + airports[7] + ", Altitude =" + airports[8] + ", Timezone =" + airports[9] + ", DST = " + airports[10] + " , TZ database time zone =" + airports[11] + " , Type =" + airports[12] + " , Data source =" + airports[13] + "]");
        }

        String[] input = new String[0];
        while ((line = input_file.readLine()) != null) {
            input = line.split(splitBy);
            inputData.add(input);
//            System.out.println("Input[Source City = " + input[0] + ", Source country = " + input[1] + ", Destination city = " + input[2] + ", Destination country =" + input[3] + "]");
        }

        String[] sourceCityCountry = inputData.get(0);
        String sourceCity = sourceCityCountry[0].trim();
        String sourceCountry = sourceCityCountry[1].trim();
        ArrayList<Integer> sourceAirportIDs = getAirportIDByCityCountry(sourceCity, sourceCountry);

        String[] destinationCityCountry = inputData.get(1);
        String destinationCity = destinationCityCountry[0].trim();
        String destinationCountry = destinationCityCountry[1].trim();
        ArrayList<Integer> destinationAirportIDs = getAirportIDByCityCountry(destinationCity, destinationCountry);
//
//        if ((input[0] == airports[2]) && (input[1] == airports[3])) {
//            System.out.println(airports[2] + "," + airports[3]);
//
//        }

    }
}



