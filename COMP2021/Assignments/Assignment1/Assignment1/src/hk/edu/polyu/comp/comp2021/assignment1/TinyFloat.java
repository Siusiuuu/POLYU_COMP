package hk.edu.polyu.comp.comp2021.assignment1;

public class TinyFloat {

    public static final int TINY_FLOAT_SIZE = 8;
    public static final int SIGN_POS = 0;
    public static final int EXPONENT_POS = 1;
    public static final int MANTISSA_POS = 5;

    public static void main(String[] args){
        System.out.println(numberOfIntegers());
    }

    public static float fromString(String bitSequence){
        float result = 0;
        // Task 1: compute the tinyFloat value from "bitSequence";

    }

    public static int numberOfIntegers(){
        // Task 2: return the number of tinyFloat values that are integers

    }

    /**
     * Get all valid bit sequences for tinyFloat values.
     */
    private static String[] getValidTinyFloatBitSequences(){
        int nbrValues = (int)Math.pow(2, TINY_FLOAT_SIZE);

        String[] result = new String[nbrValues];
        for(int i = 0; i < nbrValues; i++){
            result[i] = String.format("%" + TINY_FLOAT_SIZE + "s", Integer.toBinaryString(i))
                    .replace(' ', '0');
        }
        return result;
    }


}
