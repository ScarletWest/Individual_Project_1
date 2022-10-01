import java.io.*;
import java.util.*;

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

        try {
            route = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/routes.csv"));
            airline = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airlines.csv"));
            airport = new BufferedReader(new FileReader("/Users/user/Downloads/ICP_Individual_Project/airports.csv"));
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
        }
        route.close();

        String[] airlines = new String[0];
        while ((line = airline.readLine()) != null) {
            airlines = line.split(splitBy);
            airlineData.put(airlines[0],airlines);
        }
        airline.close();

        String[] airports = new String[0];
        while ((line = airport.readLine()) != null) {
            airports = line.split(splitBy);
            airportData.put(airports[0],airports);
        }
        airport.close();

        String[] input = new String[0];
        while ((line = input_file.readLine()) != null) {
            input = line.split(splitBy);
            inputData.add(input);
        }
        input_file.close();


        String[] sourceCityCountry = inputData.get(0);
        String sourceCity = sourceCityCountry[0].trim();
        String sourceCountry = sourceCityCountry[1].trim();
        List<String> sourceAirportIDs = getAirportIDByCityCountry(sourceCity,sourceCountry);
        System.out.println(sourceAirportIDs);


        String[] destinationCityCountry = inputData.get(1);
        String destinationCity = destinationCityCountry[0].trim();
        String destinationCountry = destinationCityCountry[1].trim();
        List<String> destinationAirportIDs = getAirportIDByCityCountry(destinationCity,destinationCountry);
        System.out.println(destinationAirportIDs);


        ArrayList<Path> results = new ArrayList<>();
         for (String sourceAirportID: sourceAirportIDs) {
             for (String destinationAirportID: destinationAirportIDs) {
                 Path result = algo.UniformCostSearch(sourceAirportID, destinationAirportID, routesData);
                 if (result != null) {
                     results.add(result);
                 }
             }
         }

        Collections.sort(results);

         Path optimalPath = results.get(0);
         ArrayList<String> actionSequence = optimalPath.getActionSequence();
         int cost = optimalPath.getPathCost();

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

    }
}



