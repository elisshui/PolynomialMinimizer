public class SDFixed extends SteepestDescent {
    // -----FIELDS-----
    private double alpha ; // fixed step size

    // -----CONSTRUCTORS-----

    //
    // Constructor initializes fields if no arguments given.
    //
    public SDFixed() {
        super(); // calls superclass constructor
        this.alpha = 0.01; // initialize step size to 0.05
    }

    //
    // Constructor initializing fields if arguments filled out.
    //
    public SDFixed (double alpha) {
        super(); // calls superclass constructor
        this.alpha = alpha; // initialize step size to user input
    }

    // -----GETTERS----

    //
    // Methods to get fields in this class. All take no argument.
    //
    public double getAlpha() { return this.alpha; }

    // -----SETTERS-----

    //
    // Methods that take input from users and set fields to the respective input.
    //
    public void setAlpha(double a) {
        this.alpha = a;
    }

    // -----OTHER METHODS-----

    @Override
    public double lineSearch (Polynomial P, double []x) {
        return this.alpha;
    } // fixed step size

    @Override
    public boolean getParamsUser() {
        System.out.println("Set parameters for SD with a fixed line search:"); // print title

        double stepSize = PolynomialMinimizer.getDouble("Enter fixed step size (0 to cancel): ", 0.0, Double.MAX_VALUE); // get epsilon from user
        if (stepSize == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        if (super.getParamsUser()) { // check if 0 was entered (i.e. if parameter entering process was cancelled)
            this.alpha = stepSize; // set alpha to the step size inputted
            setX0(super.getX0()); // pass index and user input to setX0()
            setEps(super.getEps()); // no 0 entered so set tolerance
            setMaxIter(super.getMaxIter()); // no 0 entered so set maximum iterations
            System.out.println("\nAlgorithm parameters set!\n"); // print success message
        }
        return true;
    } // get algorithm parameters from user

    //
    // Function to print parameters
    // Arguments: None
    // Returns: None
    //
    @Override
    public void print() {
        System.out.println("\nSD with a fixed line search:"); // printing title
        super.print(); // print out parameters in superclass
        System.out.format("Fixed step size (alpha): %s\n", this.alpha); // print out parameters in this class (step size)
    }
}
