public class Polynomial {
    // -----FIELDS-----
    private int n; // no. of variables
    private int degree; // degree of polynomial
    private double [][] coefs; // coefficients

    // -----CONSTRUCTORS-----

    //
    // Constructor initializes fields if no arguments given.
    //
    public Polynomial () {
        this.n = 0; // initializing integer variable for storing number of variables
        this.degree = 0; // initializing integer variable for storing polynomial degree

        // Diagram depicting how polynomial data is stored
        // 2 vars, 2 degrees, 3 coefs
            // [0, 0, 0], [1, 1, 1]    <-- index (var - 1)
            // [2, 1, 0], [2, 1, 0]    <-- degree
            // [2, 3, 4], [5, 6, 7]    <---coef

        this.coefs = new double[1][1]; // initializing array for storing polynomial data
        this.coefs[0][0] = 0.0; // stores data in form: coefs[variable - 1][degree] = coef (See diagram: lines 11 to 15)
    }

    //
    // Constructor initializing fields if arguments filled out.
    //
    public Polynomial (int n, int degree , double[][] coefs) {
        this.n = n; // initializing integer variable for storing number of variables
        this.degree = degree; // initializing integer variable for storing polynomial degree
        this.coefs = coefs; // initializing array for storing polynomial data
    }

    // -----GETTERS----

    //
    // Methods to get fields in this class. All take no argument.
    //
    public int getN() { return this.n; }
    public int getDegree() { return this.degree; }
    public double [][] getCoefs() { return this.coefs; }

    // -----SETTERS-----

    //
    // Methods that take input from users and set fields to the respective input.
    //
    public void setN (int a) { this.n = a; } // setting number of variables
    public void setDegree (int a) { this.degree = a; } // setting polynomial degree
    public void setCoef (int j, int d, double a) { this.coefs[j][d] = a; } // setting coefficients in array

    // -----OTHER METHODS-----

    //
    // Method to initialize member arrays of coefficient array to the correct size
    // Arguments: None
    // Return: None
    //
    public void init() {
        this.coefs = new double[this.n][this.degree + 1]; // [#variables][degree + 1]
    }

    //
    // Method to calculate function value at point x. NEED TO ENSURE SIZE OF ARRAY = VARIABLE
    // Arguments: array of points per variable i.e. "coordinates" --> e.g. (a, b, c,...)
    // Return: value of f(x) at point (a, b, c,...)
    //
    public double f(double[] x) {
        double val = 0.0; // initialize double to 0.0, will store f(x)

        for (int v = 0; v < this.n; v++) { // for each variable
            for (int d = this.degree; d > 0; d--) { // for each degree starting from the highest degree to 1st degree
                val += this.coefs[v][d] * Math.pow(x[v], d);
            }
            val += this.coefs[v][0]; // add 0th degree
        }

        return val;
    }

    //
    // Method to calculate gradient at point x. NEED TO ENSURE SIZE OF ARRAY = VARIABLE
    // Arguments: array of points per variable i.e. "coordinates" --> e.g. (a, b, c)
    // Return: gradient per variable
    //
    public double[] gradient(double [] x) {
        double[] gradient = new double[this.n]; // initialize double[], will store array of f'(x): length = #variables

        for (int v = 0; v < this.n; v++) { // for each variable
            for (int d = this.degree; d > 0; d--) { // for each degree starting from highest degree to 1st degree
                gradient[v] += (this.coefs[v][d] * d) * Math.pow(x[v], (d - 1)); // differentiate and sum to gradient
            }
        }

        return gradient;
    }

    //
    // Method to calculate normal (magnitude) of gradient at point x
    // Argument: coordinates of point x in form double array
    // Returns: gradient of normal (double)
    //
    public double gradientNorm(double[] x) {
        double normGrad = 0.0; // initialize double to 0.0, will store gradient of normal at point x
        double[] tanGrad = this.gradient(x); // get gradient at point x

        for (double n : tanGrad) { // iterate over each tangent vector "number" at point x
            normGrad += Math.pow(n, 2.0); // sum up powers of tangent vector "numbers"
        }

        return Math.sqrt(normGrad); // return the square root of summed squares to get magnitude of tangent
    }

    //
    // Method to indicate whether polynomial is set
    // Arguments: None
    // Return: boolean --> true if polynomial is set
    //
    public boolean isSet() {
        return (this.n != 0 || this.degree != 0); // if n and degree entered, return true (coefs don't matter here)
    }

    //
    // Method to print out the polynomial
    // Arguments: None
    // Return: None
    //
    public void print() {
        if (!(this.isSet())) { // if polynomial not set
            System.out.println("\nERROR: Polynomial function has not been entered!\n"); // print error message
            return; // end print function
        }

        // polynomial exists
        System.out.print("f(x) = ( "); // starting of printed output (no new line)
        for (int v = 0; v < this.n; v++) { // for each variable
            for (int d = this.degree; d >= 0; d--) { // for each degree starting from highest degree
                if (d != 0) { // degree is not zero
                    System.out.format("%.2fx%d^%d + ", this.coefs[v][d], (v + 1), d); // print variable
                } else {
                    System.out.format("%.2f )", this.coefs[v][d]); // only print coefficient
                }
            }
            if ((v + 1) < this.n) { // if this v is not the last one: i.e. next v is not past num of vars
                System.out.print(" + ( "); // additional bracket
            }
        }

        System.out.print("\n"); // new lines for format
    }
}
