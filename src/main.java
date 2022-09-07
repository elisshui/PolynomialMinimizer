import java.io.IOException;
import java.util.ArrayList;

public class main {
    //
    // Main function
    //
    public static void main(String[] args) throws IOException, NumberFormatException {
        boolean quit = false; // initialize boolean quit to false
        ArrayList<Polynomial> polys = new ArrayList<Polynomial>(0); // initialize new ArrayList of type Polynomial (will hold polynomials)
        SteepestDescent SD = new SteepestDescent(); // initialize new Steepest Descent Algorithm object
        SDFixed SDF = new SDFixed(); // initialize new fixed step size object
        SDArmijo SDA = new SDArmijo(); // initialize new Armijo step size object
        SDGSS SDG = new SDGSS(); // initialize new GSS step size object

        do {
            quit = true; // assume user wants to quit first
            String selection = PolynomialMinimizer.getMenuSelection(); // call getMenuSelection() to get user selection
            if (!selection.equals("Q")) { // if user did not want to quit
                quit = false; // change quit to false
                if (selection.equals("L")) { // load polynomial
                    PolynomialMinimizer.loadPolynomialFile(polys); // call function to load polynomials
                }
                else if (selection.equals("F")) { // display polynomials
                    PolynomialMinimizer.printPolynomials(polys); // call function to print out the polynomials
                }
                else if (selection.equals("C")) { // clear polynomials
                    polys.clear(); // empty the ArrayList
                    SDF.setHasResults(false); // no more results exist
                    SDA.setHasResults(false); // no more results exist
                    SDG.setHasResults(false); // no more results exist
                    System.out.println("\nAll polynomials cleared.\n"); // print success message
                }
                else if (selection.equals("S")) { // set all algorithm parameters
                    PolynomialMinimizer.getAllParams(SDF, SDA, SDG); // call function to get algorithm details
                }
                else if (selection.equals("P")) { // view steepest descent algorithm parameters
                    PolynomialMinimizer.printAllParams(SDF, SDA, SDG); // call function to print all parameters
                }
                else if (selection.equals("R")) { // run steepest descent algorithm
                    PolynomialMinimizer.runAll(SDF, SDA, SDG, polys); // call function to run all steepest descent algorithms
                }
                else if (selection.equals("D")) { // display algorithm performance
                    PolynomialMinimizer.printAllResults(SDF, SDA, SDG, polys); // call function to display algorithm performance
                }
                else if (selection.equals("X")) { // compare average algorithm performance
                    PolynomialMinimizer.compare(SDF, SDA, SDG); // call function to compare the algorithms' performances
                }
            }
        } while (!quit); // while quit is false, keep looping

        System.out.println("\nArrivederci."); // print exit message
    }
}
