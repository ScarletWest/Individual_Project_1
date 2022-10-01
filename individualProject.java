import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

import static java.util.Arrays.deepToString;

public class individualProject {
    public static ArrayList<String[]>  routesData = new ArrayList<>();
    public static ArrayList<String[]>  inputData = new ArrayList<>();
    public static HashMap<String, String[]> airportData = new HashMap<>();
    public static HashMap<String, String[]> airlineData = new HashMap<>();
    public static List<String> getAirportIDByCityCountry (String city, String country){
        List<String> AirportID = new ArrayList<String>();
        for (String[] array: airportData.values()) {
            if (array[2].equalsIgnoreCase(city) && array[3].equalsIgnoreCase(country)){
                AirportID.add(array[0]);

            }
        }
        return AirportID;
    }


        public static void main (String[] args) throws IOException {
        String line = "";
        String splitBy = ",";
        BufferedReader input_file = null;
        BufferedReader airport = null;
        BufferedReader airline = null;
        BufferedReader route = null;
//        BufferedWriter output_file = null;

        try {
            route = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/routes.csv"));
            airline = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airlines.csv"));
            airport = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airports.csv"));
//            input_file = new BufferedReader(new FileReader("/Users/user/Desktop/Senior Year/Semester 1/ICP/Accra-New York.csv"));
            input_file = new BufferedReader(new FileReader("/Users/user/Desktop/Senior Year/Semester 1/ICP/Accra-Winnipeg.txt"));


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
        route.close();

        String[] airlines = new String[0];
        while ((line = airline.readLine()) != null) {
            airlines = line.split(splitBy);
            airlineData.put(airlines[0],airlines);
//            System.out.println("Airlines[Airline ID =" + airlines[0] + ", Name = " + airlines[1] + ", Alias =" + airlines[3] + ", IATA code = " + airlines[4] + ", Callsign =" + airlines[5] + ", Country = " + airlines[6] + ", Active =" + airlines[7] + "]");
        }
        airline.close();

//        for (String[] airlineArray: airlineData.values()) {
//            System.out.println(deepToString(airlineArray));
//        }

        String[] airports = new String[0];
        while ((line = airport.readLine()) != null) {
            airports = line.split(splitBy);
            airportData.put(airports[0],airports);
//            System.out.println("Airports[Airports ID =" + airports[0] + ", Name =" + airports[1] + ", City =" + airports[2] + ", Country =" + airports[3] + ",IATA code =" + airports[4] + ", ICAO code =" + airports[5] + ", Latitude =" + airports[6] + ", Longitude =" + airports[7] + ", Altitude =" + airports[8] + ", Timezone =" + airports[9] + ", DST = " + airports[10] + " , TZ database time zone =" + airports[11] + " , Type =" + airports[12] + " , Data source =" + airports[13] + "]");
        }
        airport.close();

        String[] input = new String[0];
        while ((line = input_file.readLine()) != null) {
            input = line.split(splitBy);
            inputData.add(input);
//            System.out.println("Input[Source City = " + input[0] + ", Source country = " + input[1]);
//            System.out.println(input[0]);
        }
        input_file.close();


        String[] sourceCityCountry = inputData.get(0);
        String sourceCity = sourceCityCountry[0].trim();
        String sourceCountry = sourceCityCountry[1].trim();
        List<String> sourceAirportIDs = getAirportIDByCityCountry(sourceCity,sourceCountry);
        System.out.println(sourceAirportIDs);
//            System.out.println(deepToString(sourceCityCountry).getClass().getSimpleName());
//        System.out.println(sourceCity);
//            System.out.println(sourceCountry);
//            System.out.println(getAirportIDByCityCountry("Accra","Ghana"));

// [246, 248, 300]
            // [230, 100]
            // 246, 230
            // 246, 100
            // 248. 230 ...


        String[] destinationCityCountry = inputData.get(1);
        String destinationCity = destinationCityCountry[0].trim();
        String destinationCountry = destinationCityCountry[1].trim();
        List<String> destinationAirportIDs = getAirportIDByCityCountry(destinationCity,destinationCountry);
        System.out.println(destinationAirportIDs);

//        if ((input[0] == airports[2]) && (input[1] == airports[3])) {
//            System.out.println(airports[2] + "," + airports[3]);
//
//        }

        ArrayList<Path> results = new ArrayList<>();
         for (String sourceAirportID: sourceAirportIDs) {
             for (String destinationAirportID: destinationAirportIDs) {
                 Path result = algo.UniformCostSearch(sourceAirportID, destinationAirportID, routesData);
                 if (result != null) {
                     results.add(result);
                 }
             }
         }
//         System.out.println(results);

        Collections.sort(results);

         Path optimalPath = results.get(0);
         ArrayList<String> actionSequence = optimalPath.getActionSequence();
         int cost = optimalPath.getPathCost();
         //System.out.println("Action sequence: " + actionSequence);
         //System.out.println("Path cost: " + cost);

         StringBuilder data = new StringBuilder();
         int iterations = 0;
         int stops = 0;

         for (String airportID: optimalPath.getActionSequence()) {
             for (String[] routeArray: routesData) {
                 ArrayList<String[]> possibleRoutes = new ArrayList<>();
                 if (routeArray[3].equals(airportID)) {
                     possibleRoutes.add(routeArray);
                 }
                 for (String[] possibleRoute: possibleRoutes) {
                     if (iterations < optimalPath.getActionSequence().size() - 1) {
                         if (possibleRoute[5].equals(optimalPath.getActionSequence().get(iterations + 1))) {
                             data.append((iterations + 1) + ". " +
                                     possibleRoute[0] + " from " +
                                     airportData.get(possibleRoute[3])[4] + " to " +
                                     airportData.get(possibleRoute[5])[4] + " " +
                                     possibleRoute[7] + " stops.\n"
                             );
                             iterations++;
                             stops += Integer.parseInt((possibleRoute[7]));
                         }
                     }
                 }
             }
         }
         data.append("\nTotal flights: ").append(optimalPath.getPathCost());
         data.append("\nTotal stops: ").append(stops);
         data.append("\nOptimality criteria: flights");

         try {
             BufferedWriter output_file = new BufferedWriter((new FileWriter("/Users/user/Desktop/Senior Year/Semester 1/ICP/Accra-Winnipeg-output.txt")));
             String Data = data.toString();
             String[] output = Data.split("\n");
             for (String info : output) {
                 output_file.write(info);
                 output_file.newLine();
             }
             System.out.println("\nFile output successfully created.");
             output_file.close();
         }
         catch (FileNotFoundException fe) {
             System.out.println(fe.getMessage());
         }


//         for (Object[] result: results) {
//             if (result != null) {
//                 ArrayList<String[]> actionSequence = (ArrayList<String[]>) result[0];
//                 Integer cost = (int) result[1];
//                 System.out.println("Action sequence: " + actionSequence);
//                 System.out.println("Path cost: " + cost);
//                 System.out.println();
//             }
//         }
    }
}



