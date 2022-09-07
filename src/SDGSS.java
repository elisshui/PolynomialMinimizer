public class SDGSS extends SteepestDescent {
    // -----FIELDS-----
    private final double _PHI_ = (1. + Math . sqrt (5) ) /2.;
    private double maxStep ; // Armijo max step size
    private double minStep ; // Armijo beta parameter
    private double delta ; // Armijo delta parameter

    // -----CONSTRUCTORS-----

    //
    // Constructor initializes fields if no arguments given.
    //
    public SDGSS() {
        super();
        this.maxStep = 1.0;
        this.minStep = 0.001;
        this.delta = 0.001;
    }

    //
    // Constructor initializing fields if arguments filled out.
    //
    public SDGSS ( double maxStep , double minStep , double delta) {
        super();
        this.maxStep = maxStep;
        this.minStep = minStep;
        this.delta = delta;
    }

    // -----GETTERS----

    //
    // Methods to get fields in this class. All take no argument.
    //
    public double getMaxStep() { return this.maxStep; }
    public double getMinStep() { return this.minStep; }
    public double getDelta() { return this.delta; }

    // -----SETTERS-----

    //
    // Methods that take input from users and set fields to the respective input.
    //
    public void setMaxStep(double a) { this.maxStep = a; }
    public void setMinStep(double a) { this.minStep = a; }
    public void setDelta(double a) { this.delta = a; }

    // -----OTHER METHODS-----

    //
    // Function to calculate step size from GSS
    // Arguments: single polynomial object, coordinates of current point in graph
    // Returns: new step size
    //
    @Override
    public double lineSearch (Polynomial P, double [] x) {
        double minStep = this.minStep; // store minimum step to calculate point C
        double maxStep = this.maxStep; // store maximum step to calculate point C
        double pointC = minStep + (maxStep - minStep)/this._PHI_; // finding interval point, c
        double[] dir = direction(P, x); // get direction to move in (needed for GSS method)
        return this.GSS(minStep, maxStep, pointC, x, dir, P); // call function to run GSS algorithm
    }

    //
    // Function to run the GSS algorithm
    // Arguments: golden ratio triplets (a, b, and c), coordinates of the current point, direction to move in, and the polynomial object
    // Returns: new step size to lineSearch method
    //
    private double GSS(double a, double b, double c, double[] x, double[] dir, Polynomial P) {
        while(true) { // keep looping until one of the return lines is 'hit'
            double[] leftSide = new double[x.length]; // store the coordinates of the left side of the interval
            double[] rightSide = new double[x.length]; // store the coordinates of the right side of the interval
            double[] pointC = new double[x.length]; // store the coordinates of point C in the interval

            getNextStep(leftSide, x, dir, a); // getting next step's coordinates for smallest step size
            getNextStep(rightSide, x, dir, b); // getting next step's coordinates for largest step size
            getNextStep(pointC, x, dir, c); // getting next step's coordinates for 'mid-point' (c)

            if (P.f(pointC) > P.f(leftSide) || P.f(pointC) > P.f(rightSide)) { // base case: check if point C is NOT in interval
                return (P.f(leftSide) >= P.f(rightSide)) ? b : a; // if true, return maximum step size, else return minimum step size
            }

            double[] newRightSide = new double[x.length]; // set up new double to store coordinates of the new right-most side of the interval
            if (b - a > this.delta) { // check if the interval is still larger than delta
                boolean leftLarger = (c - a > b - c); // boolean to check if left interval is larger than right interval
                double pointY = leftLarger ? (a + (c - a) / this._PHI_) : (b - (b - c) / this._PHI_); // initialize pointY (sample point in the larger interval)

                getNextStep(newRightSide, x, dir, pointY); // take a step and get the coordinates (changes the newRightSide)

                if (P.f(newRightSide) > P.f(pointC)) {  // check if the value at the new right side is larger than f(c)
                    if (leftLarger) { // check if left interval is larger than right interval
                        return this.GSS(pointY, b, c, x, dir, P); // recursion: search in left half of original interval (pointY = new 'a')
                    }

                    b = pointY; // make right end of the interval pointY
                    continue;
                }

                if (leftLarger) { // check if left interval is larger than right interval
                    return this.GSS(a, c, pointY, x, dir, P); // recursion: search in left half of original interval (pointY = new 'C')
                }

                double value = c; // save original value of c first
                c = pointY; // change value of c to pointY (the new 'point C')
                a = value; // change left interval to original 'point C' (essentially 'shifted' and 'shrank' interval)
                continue;
            }
            return (b + a) / 2.0; // return the mid-point of the step size interval (b/c now interval is smaller than delta)
        }
    }

    //
    // Function to get algorithm parameters from user
    // Arguments: None
    // Returns: None
    //
    public boolean getParamsUser() {
        System.out.println("Set parameters for SD with a golden section line search:");
        double maxStepSize = PolynomialMinimizer.getDouble("Enter GSS maximum step size (0 to cancel): ", 0.0, Double.MAX_VALUE); // get epsilon from user
        if (maxStepSize == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        double minStepSize = PolynomialMinimizer.getDouble("Enter GSS minimum step size (0 to cancel): ", 0.0, maxStepSize); // get max iterations from user
        if (minStepSize == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        double delta = PolynomialMinimizer.getDouble("Enter GSS delta (0 to cancel): ", 0.0, Double.MAX_VALUE); // get step size from user
        if (delta == 0) {
            System.out.println("\nProcess canceled. No changes made to algorithm parameters.\n"); // print cancellation message
            return false; // end if 0 is entered
        }

        if (super.getParamsUser()) {
            this.maxStep = maxStepSize;
            this.minStep = minStepSize;
            this.delta = delta;
            setX0(super.getX0()); // pass index and user input to setX0()
            setEps(super.getEps()); // no 0 entered so set tolerance
            setMaxIter(super.getMaxIter()); // no 0 entered so set maximum iterations
            System.out.println("\nAlgorithm parameters set!\n"); // display success message
        }
        return true;
    }

    //
    // Function to print parameters
    // Arguments: None
    // Returns: None
    //
    public void print () {
        System.out.println("\nSD with a golden section line search:"); // display title
        super.print(); // print out all the other parameters first
        System.out.format("GSS maximum step size: %s\n", this.maxStep); // print out parameters specific to this class
        System.out.format("GSS minimum step size: %s\n", this.minStep); // print out parameters specific to this class
        System.out.format("GSS delta: %s\n", this.delta); // print out parameters specific to this class
    }
}
