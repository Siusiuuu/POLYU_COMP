package hk.edu.polyu.comp.comp2021.assignment1;

public class TinyFloat {

    public static final int TINY_FLOAT_SIZE = 8;
    public static final int SIGN_POS = 0;
    public static final int EXPONENT_POS = 1;
    public static final int MANTISSA_POS = 5;

    public static void main(String[] args){
        System.out.println(numberOfIntegers());
    }

    // Task 1a: Complete the method binary2Integer
    // to convert the string value to integer value for the exponent.
    private static int binary2Integer(String exponentString){
        int n = exponentString.length();
        int expValue = 0;
        for(int i = 0; i < n; i++){
            if(exponentString.charAt(n - 1 - i) == '1'){
                expValue += 1<<i;
            }
        }return expValue;
    }

    // Task 1b: Complete the method binary2Decimal
    // to convert the string value to float value for the mantissa.
    private static float binary2Decimal(String mantissaString){
        int n = mantissaString.length();
        float mantisValue = 0;
        for(int i = 0; i < n; i++){
            if(mantissaString.charAt(i) == '1'){
                mantisValue += (float) (1.0 / (1<<(i + 1)));
            }
        }return mantisValue+1;
    }


    public static float fromString(String bitSequence){
        float result;

        if(bitSequence.length() != TINY_FLOAT_SIZE){
            throw new IllegalArgumentException("Input must be an 8-character binary string.");
        }

        String exponentBinary = bitSequence.substring(EXPONENT_POS, MANTISSA_POS);
        String mantissaBinary = bitSequence.substring(5);

        int exponent = binary2Integer(exponentBinary);
        float mantissa = binary2Decimal(mantissaBinary);
        int sign = (bitSequence.charAt(0) == '1') ? -1 : 1;

        result= sign * mantissa * (float) (1<<exponent);
        return result;
    }



    public static int numberOfIntegers(){
        // Task 2: return the number of TinyFloat object values that are integers
        String[] binary=getValidTinyFloatBitSequences();
        int counter=0;
        for(int i=0;i<(1<<8);i++){

            float value=fromString(binary[i]);
            int intValue = (int) value;
            if(value == intValue){
                System.out.println(binary[i] + " == " + ((int) value));
                counter++;
            }
        }return counter;

    }

    /**
     * Get all valid bit sequences for tinyFloat values.
     * Do not change the function.
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
