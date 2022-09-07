public class SDArmijo extends SteepestDescent {
    // -----FIELDS-----
    private double maxStep ; // Armijo max step size
    private double beta ; // Armijo beta parameter
    private double tau ; // Armijo tau parameter
    private int K; // Armijo max number of iterations

    // -----CONSTRUCTORS-----

    //
    // Constructor initializes fields if no arguments given.
    //
    public SDArmijo() {
        this.maxStep = 1.0;
        this.beta = 0.0001;
        this.tau = 0.5;
        this.K = 10;
    }

    //
    // Constructor initializing fields if arguments filled out.
    //
    public SDArmijo (double maxStep , double beta , double tau , int K) {
        this.maxStep = maxStep;
        this.beta = beta;
        this.tau = tau;
        this.K = K;
    }

    // -----GETTERS----

    //
    // Methods to get fields in this class. All take no argument.
    //
    public double getMaxStep() { return this.maxStep; }
    public double getBeta() { return this.beta; }
    public double getTau() { return this.tau; }
    public int getK() { return this.K; }

    // -----SETTERS-----

    //
    // Methods that take input from users and set fields to the respective input.
    //
    public void setMaxStep (double a) { this.maxStep = a; }
    public void setBeta (double a) { this.beta = a; }
    public void setTau (double a) { this.tau = a; }
    public void setK (int a) { this.K = a; }

    // -----OTHER METHODS-----

    //
    // Function to run Armijo line search algorithm and the direction vector
    // Arguments: single polynomial object and the coordinates of the current point on the graph
    // Returns: step size
    //
    @Override
    public double lineSearch(Polynomial P, double[] x) {
        double currValue = P.f(x); // get value of polynomial at point x
        double stepSize = this.getMaxStep(); // starting step size = max step size
        double normSquared = Math.pow(P.gradientNorm(x), 2.0); // initialize normSquared to store the square of the initial norm
        double[] dir = direction(P, x); // get direction of next step to be taken

        double[] nextStep = new double[x.length]; // initialize newStep double to store the next step for a given step size
        getNextStep(nextStep, x, dir, stepSize); // get the potential next step

        int count = 0; // counting variable to control whether stepSize is multiplied

        while (P.f(nextStep) > (currValue - stepSize * this.beta * normSquared) && count < this.K) {
            stepSize *= this.tau; // reduce step size by a factor of tau
            getNextStep(nextStep, x, dir, stepSize); // get the potential next step
            count++; // increment count
        }

        if (count == this.K) { // check if Armijo line search didn't converge
            System.out.println("   Armijo line search did not converge!"); // print warning to user
            return -10; // return arbitrary number "illegal" step size to trigger stop condition in run()
        }

        return stepSize; // if suitable step size was found in time, return it, else return 1
    }


    //
    // get algorithm parameters from user
    // Arguments: None
    // Returns: true if parameters successfully set
    //
    @Override
    public boolean getParamsUser() {
        System.out.println("Set parameters for SD with an Armijo line search:");
        double maxStepSize = PolynomialMinimizer.getDouble("Enter Armijo max step size (0 to cancel): ", 0.0, Double.MAX_VALUE); // get epsilon from user
        if (maxStepSize == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        double beta = PolynomialMinimizer.getDouble("Enter Armijo beta (0 to cancel): ", 0.0, 1.0); // get max iterations from user
        if (beta == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        double tau = PolynomialMinimizer.getDouble("Enter Armijo tau (0 to cancel): ", 0.0, 1.0); // get step size from user
        if (tau == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        int kVal = PolynomialMinimizer.getInteger("Enter Armijo K (0 to cancel): ", 0, Integer.MAX_VALUE); // get starting point from user
        if (kVal == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        // call parent get parameters function to get the rest of the parameters
        if (super.getParamsUser()) { // if true is returned, set values as seen below
            this.setMaxStep(maxStepSize); // set parameters specific to this class
            this.setBeta(beta); // set parameters specific to this class
            this.setTau(tau); // set parameters specific to this class
            this.setK(kVal); // set parameters specific to this class
            setX0(super.getX0()); // set parameters in superclass
            setEps(super.getEps()); // set parameters in superclass
            setMaxIter(super.getMaxIter()); // set parameters in superclass
            System.out.println("\nAlgorithm parameters set!\n"); // print success message
        }
        return true; // return true as all parameters have been set successfully
    }

    //
    // Function to print parameters
    // Arguments: None
    // Returns: None
    //
    public void print() {
        System.out.println("\nSD with an Armijo line search:"); // display title
        super.print(); // print out parameters stored in superclass
        System.out.format("Armijo maximum step size: %s\n", this.maxStep); // print out parameters specific to this class
        System.out.format("Armijo beta: %s\n", this.beta); // print out parameters specific to this class
        System.out.format("Armijo tau: %s\n", this.tau); // print out parameters specific to this class
        System.out.format("Armijo maximum iterations: %s\n", this.K); // print out parameters specific to this class
    }
}
