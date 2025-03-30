package hk.edu.polyu.comp.comp2021.assignment1;

public class BalancedBrackets {

    public static boolean isBalanced(String str) {
        // Task 7: Return true if and only if 'str'
        // 1) is non-empty,
        // 2) contains only the six characters, and
        // 3) is balanced.

        int n = str.length();
        if(str == null||str.length() == 0){
            return false;
        }

        int countFirst = 0;
        int countSecond = 0;
        int countThrid = 0;

        for(int i = 0; i < n; i++){

            if (str.charAt(i) == '(') countFirst++;
            else if (str.charAt(i) == ')') countFirst--;
            else if (str.charAt(i) == '{') countSecond++;
            else if (str.charAt(i) == '}') countSecond--;
            else if (str.charAt(i) == '[') countThrid++;
            else if (str.charAt(i) == ']') countThrid--;
            else return false;

        }return countFirst == 0 && countSecond == 0 && countThrid == 0;

    }

}
