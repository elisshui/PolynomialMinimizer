import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PolynomialMinimizer {
    //
    // Setting up global BufferedReader object
    //
    public static BufferedReader br;
    static {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    //
    // Function to get integer from user
    // Arguments: get integer prompt, lower and upper bounds of integer to be entered
    // Returns: integer entered by user
    //
    public static int getInteger(String prompt, int LB, int UB) {
        boolean valid = true;
        int num = 0; // initialize integer that will store inputted number

        do {
            valid = true; // assume user input is valid first

            System.out.print(prompt); // prompt user

            try {
                num = Integer.parseInt(br.readLine()); // get user input
            }
            catch (IOException | NumberFormatException e) { // check for both exceptions (non-short-circuiting operator)
                valid = false;
                if (LB == Integer.MIN_VALUE && UB == Integer.MAX_VALUE) {
                    System.out.format("ERROR: Input must be an integer in [-infinity, infinity]!\n\n"); // print error message
                } else if (LB == Integer.MIN_VALUE) {
                    System.out.format("ERROR: Input must be an integer in [-infinity, %d]!\n\n", UB); // print error message
                } else if (UB == Integer.MAX_VALUE) {
                    System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB); // print error message
                }  else {
                    System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB); // print error message
                }
            }
            if (valid && (num < LB || num > UB)) { // check if input is between LB and UB inclusive
                valid = false;
                if (LB == Integer.MIN_VALUE) {
                    System.out.format("ERROR: Input must be an integer in [-infinity, %d]!\n\n", UB); // print error message
                } else if (UB == Integer.MAX_VALUE) {
                    System.out.format("ERROR: Input must be an integer in [%d, infinity]!\n\n", LB); // print error message
                }  else {
                    System.out.format("ERROR: Input must be an integer in [%d, %d]!\n\n", LB, UB); // print error message
                }
            }
        } while (!valid); // while input is not valid, loop

        return num; // return inputted number
    }

    //
    // Function to get double from user
    // Arguments: get double prompt, lower and upper bounds of double to be entered
    // Returns: double entered by user
    //
    public static double getDouble(String prompt, double LB, double UB) {
        boolean valid = true;
        double num = 0.0; // initialize double that will store inputted number

        do {
            valid = true; // assume user input is valid first

            System.out.print(prompt); // prompt user

            try {
                num = Double.parseDouble(br.readLine()); // get user input
            }
            catch (IOException | NumberFormatException e) { // check for both exceptions (non-short-circuiting operator)
                valid = false;
                if (LB == -Double.MAX_VALUE && UB == Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [-infinity, infinity]!\n\n"); // print error message
                } else if (LB == -Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n\n", UB); // print error message
                } else if (UB == Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB); // print error message
                }  else {
                    System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB); // print error message
                }
            }
            if (valid && (num < LB || num > UB)) { // check if input is between LB and UB inclusive
                valid = false;
                if (LB == -Double.MAX_VALUE && UB == Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [-infinity, infinity]!\n\n"); // print error message
                } else if (LB == -Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [-infinity, %.2f]!\n\n", UB); // print error message
                } else if (UB == Double.MAX_VALUE) {
                    System.out.format("ERROR: Input must be a real number in [%.2f, infinity]!\n\n", LB); // print error message
                }  else {
                    System.out.format("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB); // print error message
                }
            }
        } while (!valid); // while input is not valid, loop

        return num; // return inputted number
    }

    //
    // Display menu for user to see
    // Arguments: None
    // Returns: None
    //
    public static void displayMenu() {
        System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)"); // print program title
        System.out.println("L - Load polynomials from file"); // option L, load polynomials from file
        System.out.println("F - View polynomial functions"); // option F, View polynomial function
        System.out.println("C - Clear polynomial functions"); // option C, clear all polynomials
        System.out.println("S - Set steepest descent parameters"); // option S, Set steepest descent parameters
        System.out.println("P - View steepest descent parameters"); // option P, View steepest descent parameters
        System.out.println("R - Run steepest descent algorithms"); // option R, Run steepest descent algorithm
        System.out.println("D - Display algorithm performance"); // option D, Display algorithm performance
        System.out.println("X - Compare average algorithm performance"); // option X, Compare average algorithm performance
        System.out.println("Q - Quit"); // option 6, quit program
        System.out.print("\nEnter choice: "); // prompt user for selection
    }

    //
    // Function to get user Menu selection
    // Arguments: None
    // Returns: user's selection (integer)
    //
    public static String getMenuSelection() throws IOException, NumberFormatException{
        boolean valid = false; // initialize boolean variable to enable validity checks and looping
        String userSelection = "A"; // declare character that will store user menu selection
        String userSelUppCase; // declare character that will store uppercase version of user input

        // declaring new ArrayList that contains all menu options
        ArrayList<String> options = new ArrayList<String>(Arrays.asList("L", "F", "C", "S", "P", "R", "D", "X", "Q"));

        do {
            valid = true; // assume valid choice inputted first
            displayMenu(); // print menu for user to view

            try {
                userSelection = br.readLine(); // get user input
            }
            catch (IOException | NumberFormatException e) { // check for both exceptions (non-short-circuiting operator)
                valid = false;
                System.out.println("\nERROR: Invalid menu choice!\n"); // print error message
            }
            userSelUppCase = userSelection.toUpperCase(); // turn it into uppercase
            if (valid && (!options.contains(userSelUppCase))) { // check if input is a valid option
                valid = false;
                System.out.println("\nERROR: Invalid menu choice!\n"); // print error message
            }
        } while (!valid); // loop above while user selection is not valid
        return userSelUppCase; // return the user's choice
    }

    //
    // Function to print all the polynomial functions currently loaded
    // Arguments: ArrayList of polynomial objects (i.e. the polynomials loaded)
    // Returns: None
    //
    public static void printPolynomials(ArrayList<Polynomial> P) {
        if (P.isEmpty()) { // if there are no polynomials
            System.out.println("\nERROR: No polynomial functions are loaded!\n"); // print error message
            return; // end function
        }

        // printing header
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Poly No.  Degree   # vars   Function");
        System.out.println("---------------------------------------------------------");

        for (int i = 0; i < P.size(); i++) { // loop through ArrayList of polynomials
            System.out.format("%8d%8d%9d", (i + 1), P.get(i).getDegree(), P.get(i).getN());
            System.out.print("   "); // spaces for format
            P.get(i).print(); // print out the polynomial
        }
        System.out.println();
    }

    //
    // Function to read and save the polynomials
    // Arguments: ArrayList of polynomial objects, the name of the csv polynomial file
    // Returns: true if polynomial is loaded
    //
    public static boolean readPolynomials(ArrayList<Polynomial> P, String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName)); // BufferedReader object to read file
        ArrayList<String[]> polyData = new ArrayList<String[]>(); // new ArrayList to store individual polynomial data [poly number][poly data]

        int polysAttempted = 0; // num of polynomials attempted to be loaded (incl. unloaded, invalid polynomials)
        int polysLoaded = 0; // num of polynomials successfully loaded
        String row = ""; // String for reading each line ("row") in csv file

        do {
            if((row = br.readLine()) != null) {
                boolean makePoly = true; // boolean: true if data is a valid polynomial, false if data is an invalid polynomial
                boolean checked = false; // boolean: true if polynomial has never been marked as invalid
                polyData.clear(); // ensure ArrayList for storing polynomial data is empty before starting
                Polynomial newPoly = new Polynomial(); // initialize new polynomial

                while (row.charAt(0) != '*') { // while end of polynomial not reached, do below
                    String[] line = row.split(","); // initializing string to read each line of file

                    // check if polynomial is valid
                    polyData.add(line); // add line to ArrayList
                    if (!checked && polyData.get(0).length != line.length) { // check if polynomial is valid (if polynomial is already deemed invalid, don't need to check)
                        System.out.println("\nERROR: Inconsistent dimensions in polynomial " + (polysAttempted + 1) + "!"); // print error message
                        makePoly = false; // change boolean - don't make this polynomial object
                        checked = true; // prevents this if-block from being read again for this polynomial
                    }

                    if ((row = br.readLine()) == null) { // for last polynomial b/c it doesn't end with an '*'
                        break;
                    }
                }

                if (makePoly) { // if polynomial is valid, make the object
                    newPoly.setN(polyData.size()); // set number of variables: size of ArrayList = number of variables
                    newPoly.setDegree(polyData.get(0).length - 1); // set polynomial degree: length of one array in ArrayList - 1 = degree

                    newPoly.init(); // initialize coefficient double array of newPoly object to correct size
                    int cntDeg = -1;

                    for (int v = 0; v < newPoly.getN(); v++) { // loop over each variable
                        cntDeg = -1; // reset counter
                        for (int d = newPoly.getDegree(); d >= 0; d--) { // loop over degree + 1 times: d2 = c1, d1 = c2, d0 = c3
                            newPoly.setCoef(v, d, Double.parseDouble(polyData.get(v)[++cntDeg])); // set the coefficients of this polynomial object
                        }
                    }
                    polysLoaded++;
                    P.add(newPoly); // add the newly made polynomial object to the ArrayList of polynomials
                }
                polysAttempted++; // increment num of polys attempted to be loaded
            }
        } while (row != null); // while end of file not reached

        System.out.format("\n%d polynomials loaded!\n\n", polysLoaded); // print out number of polynomials loaded

        br.close(); // close buffered reader object
        return true; // all polynomials were entered so return true
    }

    //
    // Function to load the polynomial function details from a user-specified file. Function calls readPolynomials()
    // which reads and saves each individual polynomial into the ArrayList, P.
    // Arguments: ArrayList of polynomial objects
    // Returns: true if polynomial is loaded
    //
    public static boolean loadPolynomialFile(ArrayList<Polynomial> P) throws IOException {
        System.out.print("\nEnter file name (0 to cancel): "); // prompt user for file name
        String fileName = br.readLine(); // get file name from user

        if (Objects.equals(fileName, String.valueOf(0))) { // check if 0 was entered
            System.out.println("\nFile loading process canceled.\n"); // print cancellation message
            return false; // end function
        }

        File file = new File(fileName); // new object to check if file name exists
        if (!(file.exists())) { // check if file name exists
            System.out.println("\nERROR: File not found!\n"); // print error message
            return false; // end function
        }

        return readPolynomials(P, fileName); // call readPolynomials function to get the polynomials (note: always returns true)
    }

    //
    // Function to get algorithm parameters for EACH algorithm variation
    // Arguments: SDF object, SDA object, SDG object
    // Returns: boolean (true if algorithm parameters were entered)
    //
    public static void getAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
        System.out.println(); // print new line for formatting
        SDF.getParamsUser(); // get parameters for SDFixed
        SDA.getParamsUser(); // get parameters for SDArmijo
        SDG.getParamsUser(); // get parameters for SDGSS
    }

    //
    // Function to print the parameters for all the algorithm variations.
    // Arguments: SDF object, SDA object, SDG object
    // Returns: None
    //
    public static void printAllParams(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
        SDF.print(); // call print module from SDF class to print parameters
        SDA.print(); // call print module from SDA class to print parameters
        SDG.print(); // call print module from SDG class to print parameters
        System.out.println(); // new line for format
    }

    //
    // Function to run the ALL algorithms on ALL loaded polynomials
    // Arguments: SDFixed, SDArmijo, and SDGSS line search objects, and ArrayList of Polynomial objects
    // Returns: None
    //
    public static void runAll(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {
        if (P.size() == 0) { // if no polynomial exists, print error & exit function
            System.out.println("\nERROR: No polynomial functions are loaded!\n"); // printing error message
        } else {
            // Fixed Step Size
            System.out.println("\nRunning SD with a fixed line search:");
            SDF.init(P); // initialize member arrays in SDF object for ArrayList, polys, before running algorithm
            for (int i = 0; i < P.size(); i++) { // run algorithm for each polynomial in ArrayList, polys
                SDF.run(i, P.get(i)); // run algorithm for ith poly in ArrayList
            }

            // SDArmijo Step Size
            System.out.println("\nRunning SD with an Armijo line search:");
            SDA.init(P); // initialize member arrays in SDA object for ArrayList, polys, before running algorithm
            for (int i = 0; i < P.size(); i++) { // run algorithm for each polynomial in ArrayList, polys
                SDA.run(i, P.get(i)); // run algorithm for ith poly in ArrayList
            }

            // SDG Step Size
            System.out.println("\nRunning SD with a golden section line search:");
            SDG.init(P); // initialize member arrays in SDG object for ArrayList, polys, before running algorithm
            for (int i = 0; i < P.size(); i++) { // run algorithm for each polynomial in ArrayList, polys
                SDG.run(i, P.get(i)); // run algorithm for ith poly in ArrayList
            }

            System.out.println("\nAll polynomials done.\n"); // print final success message
        }
    }

    //
    // Function to print the detailed results and statistics summaries for ALL algorithm variations.
    // Arguments:
    // Returns: None.
    //
    public static void printAllResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {
        if (!SDF.hasResults() || !SDA.hasResults() || !SDG.hasResults()) {
            System.out.println("\nERROR: Results do not exist for all line searches!\n");
            return;
        }

        System.out.println("\nDetailed results for SD with a fixed line search:");
        for (int i = 0; i < P.size(); i++) {
            SDF.printSingleResult(i, i != 0); // call function to print results: if printing first polynomial, print header as well (rowOnly = false). Else, just print results with no header.
        }
        System.out.println(); // new line for formatting
        System.out.println("Statistical summary for SD with a fixed line search:");
        SDF.printStats();

        System.out.println("\nDetailed results for SD with an Armijo line search:");
        for (int i = 0; i < P.size(); i++) {
            SDA.printSingleResult(i, i != 0); // call function to print results: if printing first polynomial, print header as well (rowOnly = false). Else, just print results with no header.
        }
        System.out.println(); // new line for formatting
        System.out.println("Statistical summary for SD with an Armijo line search:");
        SDA.printStats();

        System.out.println("\nDetailed results for SD with a golden section line search:");
        for (int i = 0; i < P.size(); i++) {
            SDG.printSingleResult(i, i != 0); // call function to print results: if printing first polynomial, print header as well (rowOnly = false). Else, just print results with no header.
        }
        System.out.println(); // new line for formatting
        System.out.println("Statistical summary for SD with a golden section line search:");
        SDG.printStats();
    }

    //
    // Function to compare ALL algorithm performances and pick winners
    // Arguments:
    // Returns: None
    //
    public static void compare(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
        if (!SDF.hasResults() || !SDA.hasResults() || !SDG.hasResults()) {
            System.out.println("\nERROR: Results do not exist for all line searches!\n");
            return;
        }

        System.out.println("\n---------------------------------------------------");
        System.out.println("          norm(grad)       # iter    Comp time (ms)");
        System.out.println("---------------------------------------------------");

        // printing averages
        System.out.format("%-10s", "Fixed"); // printing table row label
        SDF.printAll();

        System.out.format("%-10s", "Armijo"); // printing table row label
        SDA.printAll();

        System.out.format("%-10s", "GSS"); // printing table row label
        SDG.printAll();

        // getting final results
        Double[] aveNormGrad = new Double[]{SDF.getAveNormGrad(), SDA.getAveNormGrad(), SDG.getAveNormGrad()};
        Double[] aveIters = new Double[]{SDF.getAveIters(), SDA.getAveIters(), SDG.getAveIters()};
        Double[] aveCompTime = new Double[]{SDF.getAveCompTime(), SDA.getAveCompTime(), SDG.getAveCompTime()};

        String[] winners = new String[]{getWinners(aveNormGrad), getWinners(aveIters), getWinners(aveCompTime)}; // average values to be printed
        String[] winnerSpacing = new String[]{"%10s", "%13s", "%18s"}; // create array to store spacing for each value in table

        boolean isSame = true;
        for(int i = 1; i < winners.length; i++) {
            if (!Objects.equals(winners[i], winners[0])) {
                isSame = false;
                break;
            }
        }
        String overallWinner = (isSame) ? winners[0] : "Unclear";

        // printing final results
        System.out.println("---------------------------------------------------");
        System.out.format("%-10s", "Winner"); // printing table row label
        for (int j = 0; j < 3; j++) { // loop over spacing[] and values[]
            System.out.format(winnerSpacing[j], winners[j]); // printing all values with format
        }
        System.out.println("\n---------------------------------------------------");
        System.out.format("Overall winner: %s\n\n", overallWinner);
    }

    public static String getWinners(Double[] array) {
        String[] types = new String[]{"Fixed", "Armijo", "GSS"};
        double lower = array[0];
        int lowestIndex = 0;

        for (int i = 0; i< array.length; i++) {
            if (array[i] < lower) {
                lower = array[i];
                lowestIndex = i;
            }
        }

        return types[lowestIndex];
    }
}
