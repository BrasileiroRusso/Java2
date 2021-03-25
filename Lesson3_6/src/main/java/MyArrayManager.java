import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyArrayManager {
    private static final int REQUIRED_ELEM = 4;
    private static final int CONTROL_ELEM = 1;

    private final int requiredInt;
    private final int controlInt;

    public MyArrayManager(int requiredInt, int controlInt){
        this.requiredInt = requiredInt;
        this.controlInt = controlInt;
    }

    public MyArrayManager(){
        requiredInt = REQUIRED_ELEM;
        controlInt = CONTROL_ELEM;
    }

    public int[] getTailArray(int[] array){
        int[] tailArray;
        int pos = -1;

        for(int i = array.length - 1; i >=0; i--)
            if(array[i] == requiredInt){
                pos = i;
                break;
            }
        if(pos < 0)
            throw new RuntimeException();

        tailArray = new int[array.length - pos - 1];
        if(tailArray.length > 0)
            System.arraycopy(array, pos + 1, tailArray, 0, tailArray.length);

        return tailArray;
    }

    public boolean isContainsSpecInts(int[] array){
        int pos = -1;

        for(int i = array.length - 1; i >=0; i--)
            if(array[i] == requiredInt || array[i] == controlInt){
                pos = i;
                break;
            }

        return pos >= 0;
    }

}
