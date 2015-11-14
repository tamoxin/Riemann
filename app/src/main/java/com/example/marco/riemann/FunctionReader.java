package com.example.marco.riemann;

/**
 * Created by Marco on 11/5/2015.
 */
public class FunctionReader {

    private String polynomial = "";
    private double[] coefficients;

    //Constructors
    public FunctionReader(String function) {
        setPolynomial(function);
        sortPolynomial();
    }

    //Methods start here


    /**
     * Gets the the value of every power of x in the original function.
     *
     * @return an array with the pows.
     */
    public int[] getPows() {
        int[] pows;
        String[] aux;

        aux = splitIntoTerms(this.polynomial);
        pows = new int[aux.length];
        for(int i = 0; i < aux.length; i++)
            pows[i] = getPow(aux[i]);
        return pows;
    }

    /**
     * Splits the polynomial expression passed into an array containing
     * its coefficients.
     *
     * @return an array with the coefficients of the function.
     */
    public double[] splitIntoCoefficients() {
        String[] aux;
        double[] coeff;

        aux = splitIntoTerms(this.polynomial);
        coeff= new double[aux.length];
        for(int i = 0; i < aux.length; i++)
            coeff[i] = Double.parseDouble(getCoefficient(aux[i]));
        setCoefficients(coeff);
        return coeff;
    }

    /**
     * Gets the coefficient of a algebraical term.
     *
     * @param term the algebraical term.
     * @return a String with the coefficient of the term.
     */
    public String getCoefficient(String term) {
        if(term.contains("-x"))
            return "-1";
        else if(term.charAt(0) != 'x')
            if(!term.contains("^") && term.contains("x"))
                return term.split("x")[0];
            else
                return term.split("x\\^\\d+\\+?")[0];
        return "1";
    }

    /**
     * Sorts the polynomial expression from the highest
     * to the lowest power. This method makes use of an insertion sort
     * as it is usual for people to use polynomials no longer 10 terms.
     * Feel free to use the sorting method of your preference.
     */
    public void sortPolynomial() {

        InsertionSort sorter = new InsertionSort();
        String pol = "";

        String[] parts = splitIntoTerms(this.polynomial);
        int[] pow = new int[parts.length];

        //Get the power of every term
        for(int i = 0; i < pow.length; i++){
            pow[i] = getPow(parts[i]);
        }
        pow = sorter.sort(pow, false);

        //Format the sorted expression
        for(int i = 0; i < pow.length; i++) {

            int j = 0;
            if(i != 0)
                pol += "+";
            while(pow[i] != getPow(parts[j]))
                j++;
            if(pol.length() > 0 &&
                    pol.charAt(pol.length()-1) == '+'&& parts[j].charAt(0) == '-')
                pol = pol.substring(0, pol.length() - 1);
            pol += parts[j];
        }
        setPolynomial(pol);
    }

    /**
     * Obtains the power to which a single term is raised.
     *
     * @param   terms
     *          The term raised to the Nth power.
     *
     * @return  The power to which the term is raised or 9000 if the power is not integer.
     */
    public int getPow(String terms) {
        if(terms.contains("x")) {
            if (terms.contains("^")) {
                if(terms.split("\\^")[1].contains("."))
                    //This error is handled by throwing a Toast saying powers can only be integers.
                    //Feel free to improve the way to handle the error,
                    // or even better, feel free to fix this problem.
                    return 9000;
                return Integer.parseInt(terms.split("\\^")[1]);
            }else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Splits the polynomial expression passed into an array containing
     * its separated terms.
     *
     * @param expression is the polynomial expression.
     * @return a new array with the expression split in terms.
     */
    public String[] splitIntoTerms(String expression) {
        String[] parts = expression.replace("-", "+-").split("\\+");
        //Removes the empty space in the first value of the array
        if (parts[0].equals("")) {
            String[] aux = new String[parts.length - 1];
            System.arraycopy(parts, 1, aux, 0, aux.length);
            parts = aux;
        }
        return parts;
    }

    public void setCoefficients(double[] values) {this.coefficients = values;}

    public void setPolynomial(String str) { this.polynomial = str;}

    //-----------------Just in case------------------//
    public String getPolynomial() {return this.polynomial;}

    public double[] getCoefficients() {return coefficients;}
    //----------------------------------------------//
}
