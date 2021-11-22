import java.util.Scanner;

interface SearchFrontEndInterface {
    public void run(SearchBackEndInterface searchEngine);
}

/** 
 * This class contains the run method which runs the Car Catalog Application
 * @author Ojas Sethi
 */

public class SearchFrontEnd implements SearchFrontEndInterface {

    @Override
    public void run(SearchBackEndInterface searchEngine) {
        System.out.println("Welcome to Car Catalog! You can do do a lot of stuff with this application.");
        System.out.println("Here are your options: ");
        System.out.println("1. Insert a car into the catalog.");
        System.out.println("2. Lookup a car in the catalog by price.");
        System.out.println("3. Find number of cars in the catalog.");
        System.out.println("4. Quit the application.");
        System.out.println("Please type the number corresponding to the action you want to take: ");
        Scanner scnr = new Scanner(System.in);
        mainLoop: while (scnr.hasNext()) {
        System.out.println("Here are your options: ");
        System.out.println("1. Insert a car into the catalog.");
        System.out.println("2. Lookup a car in the catalog by price.");
        System.out.println("3. Find number of cars in the catalog.");
        System.out.println("4. Quit the application.");
        System.out.println("Please type the number corresponding to the action you want to take: ");
            if (scnr.hasNextInt()) {
                int userOption = scnr.nextInt();
                if (userOption <= 0 || userOption > 4) {
                    System.out.println("Invalid command. Please type in a number from the list below.");
                    continue;
                } else {
                    // Call functions 
                    switch(userOption) {
                        case 1: // Insert a car into the catalog
                            System.out.println("Enter the make of the car: ");
                            String carMake = scnr.next();
                            System.out.println("Enter the model of the car: ");
                            String carModel = scnr.next();
                            System.out.println("Enter the price of the car (exclude the $ symbol): ");
                            double carPrice = scnr.nextInt();
                            // Code for inserting a node into the RBTree
                            Node<Integer> newCarNode = new Node<Integer>(1, carModel, carMake, carPrice);
                            try {
                                searchEngine.addData(newCarNode);
                                System.out.println("Car added to the catalog successfully.");
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: Could not add car to the catalog.");
                                System.out.println(e.getMessage());
                                break mainLoop;
                            }
                        
                        case 2: // Looking up a car by its price
                            System.out.println("Enter the price of the car: ");
                            carPrice = (double)scnr.nextInt();
                            while (carPrice < 0) {
                                System.out.println("Please enter a valid price: ");
                                carPrice = scnr.nextInt();
                            }
                            // Code for looking up a car in the RBTree
                            try {
                                String carName = searchEngine.findPrice(carPrice);
                                if (carName != null && carName != "") {
                                    System.out.println("Match found!");
                                    System.out.println("The car is: " + carName);
                                    break;
                                } else {
                                    System.out.println("Sorry. The car with that price could not be found.");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Error: the application encountered a problem. Could not perform the search operation.");
                                System.out.println(e.getMessage());
                                break mainLoop;
                            }
                            break;
                        
                        case 3:
                            System.out.println("Finding number of cars in the catalog...");
                            // Code for printing out the catalog
                            try {
                                int numCars = searchEngine.findNumberOfCars();
                                System.out.println("The number of cars in the catalog are: " + numCars);
                                break;
                            } catch (Exception e) {
                                System.out.println("Error: the application encountered a problem. Could not perform the operation.");
                                System.out.println(e.getMessage());
                                break mainLoop;
                            }
                            break;
                        
                        case 4:
                            System.out.println("Thank you for using the car catalog. Exiting now...");
                            break mainLoop;

                    }
                }
            } else {
                System.out.println("Please enter a valid number. Enter 4 to quit.");
                continue;
            }
        }

        scnr.close();
    }

}