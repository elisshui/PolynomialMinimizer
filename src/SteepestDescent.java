import java.util.ArrayList;
import java.util.Arrays;

public class SteepestDescent {
    // -----FIELDS-----
    private double eps ; // tolerance
    private int maxIter; // maximum number of iterations
    private double x0; // starting point
    private ArrayList<double []> bestPoint; // best point found for all polynomials
    private double[] bestObjVal ; // best obj fn value found for all polynomials
    private double[] bestGradNorm ; // best gradient norm found for all polynomials
    private long[] compTime ; // computation time needed for all polynomials
    private int[] nIter ; // no. of iterations needed for all polynomials
    private boolean resultsExist ; // whether or not results exist
    private double aveNormGrad; // initialize double to hold average Norm gradient
    private double aveIters; // initialize double to hold average iterations
    private double aveCompTime; // initialize double to hold average computation time

    // -----CONSTRUCTORS-----

    //
    // Constructor initializes fields if no arguments given.
    //
    public SteepestDescent() {
        this.eps = 0.001; // initializing double variable, stores epsilon (tolerance)
        this.maxIter = 100; // initializing integer variable, stores max iterations
        this.x0 = 1.0; // initializing double, stores starting point

        // initialize the rest of the fields
        this.bestPoint = new ArrayList<double[]>(1);
        this.bestObjVal = new double[1];
        this.bestGradNorm = new double[1];
        this.compTime = new long[1];
        this.nIter = new int[1];
        this.resultsExist = false; // whether results exist

        // variables for calculating statistics
        this.aveNormGrad = 0.0; // initialize double to hold average Norm gradient
        this.aveIters = 0.0; // initialize double to hold average iterations
        this.aveCompTime = 0.0; // initialize double to hold average computation time
    }

    //
    // Constructor initializing fields if arguments filled out.
    //
    public SteepestDescent (double eps, int maxIter, double x0) {
        this.eps = eps; // initializing double variable, stores epsilon (tolerance)
        this.maxIter = maxIter; // initializing integer variable, stores max iterations
        this.x0 = x0; // initializing array, stores starting point

        // initialize the rest of the fields
        this.bestPoint = new ArrayList<double[]>(1);
        this.bestObjVal = new double[1];
        this.bestGradNorm = new double[1];
        this.compTime = new long[1];
        this.nIter = new int[1];
        this.resultsExist = false; // whether results exist

        // variables for calculating statistics
        this.aveNormGrad = 0.0; // initialize double to hold average Norm gradient
        this.aveIters = 0.0; // initialize double to hold average iterations
        this.aveCompTime = 0.0; // initialize double to hold average computation time

    }

    // -----GETTERS----

    //
    // Methods to get fields in this class. All take no argument.
    //
    public double getEps() { return this.eps; }
    public int getMaxIter() { return this.maxIter; }
    public double getX0() { return this.x0; }
    public double[] getBestObjVal () { return this.bestObjVal; }
    public double[] getBestGradNorm () { return this.bestGradNorm; }
    public double[] getBestPoint ( int i) { return this.bestPoint.get(i); }
    public int[] getNIter () { return this.nIter; }
    public long[] getCompTime () { return this.compTime; }
    public boolean hasResults() { return this.resultsExist; }
    public double getAveNormGrad() { return this.aveNormGrad; }
    public double getAveIters() { return this.aveIters; }
    public double getAveCompTime() { return this.aveCompTime; }

    // -----SETTERS-----

    //
    // Methods that take input from users and set fields to the respective input.
    //
    public void setEps (double a) { this.eps = a; }
    public void setMaxIter (int a) { this.maxIter = a; }
    public void setX0 (double a) { this.x0 = a; }
    public void setBestObjVal (int i, double a) { this.bestObjVal[i] = a; }
    public void setBestGradNorm (int i, double a) { this.bestGradNorm[i] = a; }
    public void setBestPoint (int i, double[] a) { this.bestPoint.add(i, a); }
    public void setCompTime (int i, long a) { this.compTime[i] = a; }
    public void setNIter (int i, int a) { this.nIter[i] = a; }
    public void setHasResults (boolean a) { this.resultsExist = a; }
    public void setAveNormGrad (double i) { this.aveNormGrad = i; }
    public void setAveIters (double i) { this.aveIters = i; }
    public void setAveCompTime (double i) { this.aveCompTime = i; }


    // -----OTHER METHODS-----

    //
    // Function to initialize member arrays to correct size
    // Arguments: ArrayList of Polynomial objects
    // Returns: None
    //
    public void init (ArrayList <Polynomial> P) {
        this.bestObjVal = new double[P.size()]; // need enough space for all polynomials
        this.compTime = new long[P.size()]; // need enough space for all polynomials
        this.bestGradNorm = new double[P.size()]; // need enough space for all polynomials
        this.nIter = new int[P.size()]; // need enough space for all polynomials

        for (int i = 0; i < P.size(); i++) { // loop over each poly in ArrayList, P
            this.bestPoint.add(i, new double[P.get(i).getN()]); // add double[] of size = #poly variables to bestPoint ArrayList
        }
    }

    //
    // Method to run the steepest descent algorithm
    // Argument: Single polynomial object and it's index in the ArrayList in main()
    // Returns: None
    //
    public void run(int i, Polynomial P) {
        double[] startCoords = new double[P.getN()]; // initializing new double to store starting coordinates
        Arrays.fill(startCoords, this.x0); // filling starting coordinates array with this.x0

        this.bestPoint.add(i, startCoords); // set bestPoint to start point first
        this.compTime[i] = 0; // set computation time for i to 0
        this.nIter[i] = 0; // set number of iterations for i to 0
        this.bestObjVal[i] = P.f(startCoords); // current (starting) f(x) for polynomial object, i
        this.bestGradNorm[i] = P.gradientNorm(startCoords); // current (starting) gradient norm
        boolean iterate = true;

        long startTime = System.currentTimeMillis(); // initialize and store current time

        while (iterate && this.nIter[i] < this.maxIter && this.bestGradNorm[i] > this.eps) { // while max iterations not reached OR norm at point <= tolerance
            double stepSize = this.lineSearch(P, this.bestPoint.get(i)); // initialize step size for this iteration using lineSearch method
            if (stepSize == -10) {
                iterate = false;
            } else {
                double[] dir = direction(P, this.bestPoint.get(i)); // get direction to move in

                for(int j = 0; j < (this.bestPoint.get(i)).length; ++j) { // get next step's point
                    this.bestPoint.get(i)[j] += stepSize * dir[j]; // next step's point = current point + (-gradient * step)
                }
            }

            this.nIter[i] += 1; // increment iterations completed counter
            this.bestObjVal[i] = P.f(this.bestPoint.get(i)); // get f(x) at new point
            this.bestGradNorm[i] = P.gradientNorm(this.bestPoint.get(i)); // get gradient norm at new point
        }

        this.compTime[i] = System.currentTimeMillis() - startTime; // update compTime needed

        System.out.println("Polynomial " + (i + 1) + " done in " + this.compTime[i] + "ms."); // print success message along with time taken
        this.resultsExist = true; // change results exist to true
    }

    //
    // Method to find the next step size
    // Arguments: single polynomial object and the current point??
    // Returns: step size (double)
    //
    public double lineSearch (Polynomial P, double[] x) {
        System.out.println("ERROR: Wrong line search template"); // error message (shouldn't ever be shown - just incl in code for checking)
        return 0.0; // essentially return nothing (will mess up algorithm but error message explains reason to user)
    }

    //
    // Method to find next direction to search in
    // Arguments: Polynomial object, current point (array of doubles)
    // Returns: direction vector in which to move in (array of doubles)
    //
    public double[] direction(Polynomial P, double[] x) {
        // direction = negative of gradient at point
        double[] dir = new double[P.getN()]; // initialize double array to hold direction vector

        for (int i = 0; i < x.length; i++) { // for loop to store the negative of each value in gradient[] in dir[]
            dir[i] = -((P.gradient(x))[i]); // storing the negative of the gradient values in dir[]
        }

        return dir; // return directions
    }

    //
    // Function to get next step (needed for Armijo and GSS lineSearch)
    // Arguments: Step array, current point the lineSearch is on, direction, stepSize
    // Returns: None (nextStep is updated b/c of pointers)
    //
    public void getNextStep(double[] nextStep, double[] currPoint, double[] dir, double stepSize) {
        for(int i = 0; i < currPoint.length; ++i) {
            nextStep[i] = currPoint[i] + stepSize * dir[i]; // get the next point for each 'variable'
        }
    }

    //
    // Method to set the rest of the algorithm parameters from user
    // Arguments: number of variables in polynomial (integer)
    // Returns: None
    //
    public boolean getParamsUser() {
        double eps = PolynomialMinimizer.getDouble("Enter tolerance epsilon (0 to cancel): ", 0.0, Double.MAX_VALUE); // get epsilon from user
        if (eps == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        int maxIter = PolynomialMinimizer.getInteger("Enter maximum number of iterations (0 to cancel): ", 0, 10000); // get max iterations from user
        if (maxIter == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        double startingPt = PolynomialMinimizer.getDouble("Enter value for starting point (0 to cancel): ", -Double.MAX_VALUE, Double.MAX_VALUE); // get starting point from user
        if (startingPt == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        setX0(startingPt); // pass index and user input to setX0()
        setEps(eps); // no 0 entered so set tolerance
        setMaxIter(maxIter); // no 0 entered so set maximum iterations

        return true;
    }

    //
    // Function to calculate average of numbers in an array
    // Arguments: average, array to calculate average for
    // Returns: average of array
    //
    public double calculateAverage(double[] variable) {
        double average = 0.0;
        for (double num : variable) { // loop over each number in the array, variable
            average += num; // sum up the numbers in the variable array
        }
        return average / variable.length; // return the average (the sum over the number of numbers in the array)
    }

    //
    // Function to calculate standard deviation of numbers in an array
    // Arguments: standard deviation, average of array values, and array to calculate standard deviation for
    // Returns: standard deviation of array
    //
    public double calculateStd(double std, double aveOfVar, double[] variable) {
        for (double num : variable) { // loop over each number in the array, variable
            std += Math.pow((num - aveOfVar), 2); // sum up the difference of each number and the array's average squared
        }
        return Math.sqrt(std/(variable.length - 1)); // return the square root of the sum/number of numbers in array - 1
    }

    //
    // Function to get minimum values from a given array
    // Arguments: array to search from minimum in
    // Returns: minimum of array
    //
    public double getMin(double[] variable) {
        double min = variable[0]; // initially, make minimum the first number in variable array

        for (double i : variable) { // loop over all numbers in variable
            if (i < min) { // check if smaller than min
                min = i; // update min value
            }
        }

        return min; // return min value
    }

    //
    // Function to get maximum values from a given array
    // Arguments: array to search from maximum in
    // Returns: maximum of array
    //
    public double getMax(double[] variable) {
        double max = variable[0]; // initially, make maximum the first number in variable array

        for (double i : variable) { // loop over all numbers in variable
            if (i > max) { // check if larger than max
                max = i; // update max value
            }
        }

        return max; // return max value
    }

    //
    // Function to print average values
    // Arguments: None
    // Returns: None
    //
    public void printAverages() {
        // calculating average
        this.aveNormGrad = calculateAverage(this.bestGradNorm); // calculating average Norm gradient
        this.aveIters = calculateAverage(Arrays.stream(this.nIter).asDoubleStream().toArray()); // calculating average iterations
        this.aveCompTime = calculateAverage(Arrays.stream(this.compTime).asDoubleStream().toArray()); // calculating average computational time

        // printing average
        String[] spacing = new String[]{"%10.3f", "%13.3f", "%18.3f"}; // create array to store spacing for each value in table
        Double[] values = new Double[]{this.aveNormGrad, this.aveIters, this.aveCompTime}; // average values to be printed

        System.out.format("%-10s", "Average"); // printing table row label
        for (int j = 0; j < values.length; j++) { // loop over spacing[] and values[]
            System.out.format(spacing[j], values[j]); // printing all values with format
        }
        System.out.println(); // new line (i.e. next row)
    }

    //
    // Function to print standard deviation values
    // Arguments: None
    // Returns: None
    //
    public void printStDev() {
        // calculating standard deviation
        double stdNormGrad = 0.0; // initialize double to hold standard deviation of Norm gradient
        double stdIters = 0.0; // initialize double to hold standard deviation of iterations
        double stdCompTime = 0.0; // initialize double to hold standard deviation of computation time

        stdNormGrad = calculateStd(stdNormGrad, this.aveNormGrad, this.bestGradNorm); // calculating std of Norm gradient
        stdIters = calculateStd(stdIters, this.aveIters, Arrays.stream(this.nIter).asDoubleStream().toArray()); // calculating std of iterations
        stdCompTime = calculateStd(stdCompTime, this.aveCompTime, Arrays.stream(this.compTime).asDoubleStream().toArray()); // calculating std of iterations

        // printing standard deviation
        String[] spacing = new String[]{"%10.3f", "%13.3f", "%18.3f"}; // create array to store spacing for each value in table
        Double[] values = new Double[]{stdNormGrad, stdIters, stdCompTime}; // standard deviations to be printed

        System.out.format("%-10s", "St Dev"); // printing table row label
        for (int j = 0; j < values.length; j++) { // loop over spacing[] and values[]
            System.out.format(spacing[j], values[j]); // printing all values with format
        }
        System.out.println(); // new line (i.e. next row)
    }

    //
    // Function to print minimum values
    // Arguments: None
    // Returns: None
    //
    public void printMin() {
        System.out.format("%-10s", "Min"); // printing table row label
        System.out.format("%10.3f%13d%18d", getMin(this.bestGradNorm),
                (int)getMin(Arrays.stream(this.nIter).asDoubleStream().toArray()),
                (int)getMin(Arrays.stream(this.compTime).asDoubleStream().toArray())); // print out values
        System.out.println(); // new line (i.e. next row)
    }

    //
    // Function to print maximum values
    // Arguments: None
    // Returns: None
    //
    public void printMax() {
        System.out.format("%-10s", "Max"); // printing table row label
        System.out.format("%10.3f%13d%18d", getMax(this.bestGradNorm),
                (int)getMax(Arrays.stream(this.nIter).asDoubleStream().toArray()),
                (int)getMax(Arrays.stream(this.compTime).asDoubleStream().toArray())); // print out values
        System.out.println("\n"); // new line for format
    }

    //
    // print statistical summary of results
    // Arguments: None
    // Returns: None
    //
    public void printStats() {
        // printing header
        System.out.println("---------------------------------------------------");
        System.out.println("          norm(grad)       # iter    Comp time (ms)");
        System.out.println("---------------------------------------------------");

        // reset values
        this.setAveNormGrad(0.0);
        this.setAveIters(0.0);
        this.setAveCompTime(0.0);

        // printing statistics
        this.printAverages(); // printing averages
        this.printStDev(); // printing standard deviations
        this.printMin(); // printing minimums
        this.printMax(); // printing maximums
    }

    //
    // Function to print final results for all polynomials
    // Arguments: None
    // Returns: None
    //
    public void printAll () {
        this.aveNormGrad = calculateAverage(this.bestGradNorm); // calculating average Norm gradient
        this.aveIters = calculateAverage(Arrays.stream(this.nIter).asDoubleStream().toArray()); // calculating average iterations
        this.aveCompTime = calculateAverage(Arrays.stream(this.compTime).asDoubleStream().toArray()); // calculating average computational time

        String[] spacing = new String[]{"%10.3f", "%13.3f", "%18.3f"}; // create array to store spacing for each value in table
        Double[] vals = new Double[]{getAveNormGrad(), getAveIters(), getAveCompTime()}; // average values to be printed
        for (int j = 0; j < vals.length; j++) { // loop over spacing[] and values[]
            System.out.format(spacing[j], vals[j]); // printing all values with format
        }
        System.out.println(); // new line (i.e. next row)
    }

    //
    // Function to print final result for one polynomial , column header optional
    // Arguments: index of polynomial, boolean --> tells whether to only print the row and no header
    // Returns: None
    //
    public void printSingleResult(int i, boolean rowOnly) {
        if (!rowOnly) {
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
            System.out.println("-------------------------------------------------------------------------");
        }

        // Printing results for single polynomial
        String[] spacing = new String[]{"%8.0f", "%13.6f", "%13.6f", "%9.0f", "%17.0f"}; // create array to store spacing for each value in table
        Double[] values = new Double[]{(double)(i + 1), this.bestObjVal[i], this.bestGradNorm[i], (double)this.nIter[i], (double)this.compTime[i]}; // values to be printed (not including best point)

        for (int j = 0; j < values.length; j++) { // loop over spacing[] and values[]
            System.out.format(spacing[j], values[j]); // printing all values with format, not including best point
        }

        System.out.print("   "); // 3 spaces for format

        for (int j = 0; j < this.bestPoint.get(i).length; j++) { // printing best point values
            System.out.format("%.4f", this.bestPoint.get(i)[j]); // print value in bestPoint[i] to 4dp

            if ((j + 1) != this.bestPoint.get(i).length) { // if this is not the last value, do below
                System.out.print(", "); // comma and space for format
            }
        }
        System.out.println(); // two new lines for format
    }

    //
    // Function to print algorithm parameters
    // Arguments: None
    // Returns: None
    //
    public void print() {
        System.out.format("Tolerance (epsilon): %s\n", this.eps); // printing tolerance
        System.out.format("Maximum iterations: %d\n", this.maxIter); // printing maximum iterations
        System.out.format("Starting point (x0): %s\n", this.x0); // printing starting point
    }
}
