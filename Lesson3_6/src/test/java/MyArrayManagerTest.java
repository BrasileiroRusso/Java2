import org.junit.jupiter.api.*;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;


public class MyArrayManagerTest {
    private static MyArrayManager manager;

    @BeforeAll
    public static void init(){
        manager = new MyArrayManager();
    }

    @Test
    public void getTailArrayTest(){
        int[] result;
        int[] ethalon;

        ethalon = new int[]{3, 6, 2, 9, 5};
        result = manager.getTailArray(new int[]{1, 5, 7, 4, 3, 6, 2, 9, 5});
        assertTrue(Arrays.equals(result, ethalon));

        ethalon = new int[]{1, 3, 2};
        result = manager.getTailArray(new int[]{1, 4, 7, 4, 3, 4, 1, 3, 2});
        assertTrue(Arrays.equals(result, ethalon));

        ethalon = new int[]{};
        result = manager.getTailArray(new int[]{6, 4, 7, 4, 9, 4});
        assertTrue(Arrays.equals(result, ethalon));

        ethalon = new int[]{1, 7, 9};
        result = manager.getTailArray(new int[]{4, 4, 1, 7, 9});
        assertTrue(Arrays.equals(result, ethalon));

        assertThrows(RuntimeException.class, () -> {
            manager.getTailArray(new int[]{3, 5, 1, 7, 9});
        });
    }

    @CsvSource({
            "{1 5 7 4 3 6 2 9 5}, {3 6 2 9 5}",
            "{4 1 5 7 2 3 9}, {1 5 7 2 3 9}",
            "{1 5 6 2 3 4}, {}",
            "{4 5 4 2 4 3 2 1}, {3 2 1}",
            "{4 4 2 4 4 4}, {}"
    })
    @ParameterizedTest
    public void getTailArrayTest2(@ConvertWith(IntArrayConverter.class) int[] testArr,
                                  @ConvertWith(IntArrayConverter.class) int[] ethalon) {
        assertTrue(Arrays.equals(manager.getTailArray(testArr), ethalon));
    }

    @Test
    public void isContainsSpecIntsTest(){
        assertTrue(manager.isContainsSpecInts(new int[]{1, 5, 7, 4, 3, 6, 2, 9, 5}));
        assertTrue(manager.isContainsSpecInts(new int[]{1, -9, 7, 3}));
        assertTrue(manager.isContainsSpecInts(new int[]{-3, 9, 4, 3}));
        assertTrue(manager.isContainsSpecInts(new int[]{7, 1, 1, -6, 8}));
        assertTrue(manager.isContainsSpecInts(new int[]{1, 1, 4, 4}));
        assertTrue(manager.isContainsSpecInts(new int[]{1}));
        assertTrue(manager.isContainsSpecInts(new int[]{4}));

        assertFalse(manager.isContainsSpecInts(new int[]{3}));
        assertFalse(manager.isContainsSpecInts(new int[]{}));
        assertFalse(manager.isContainsSpecInts(new int[]{5, 7, 3, 8, 9, 3, 0}));
        assertFalse(manager.isContainsSpecInts(new int[]{-3, -4, 0, -1, 8, 9}));
    }

    @CsvSource({
            "{1 5 4 6 12 9 5}, true",
            "{5 7 2 3 19}, false",
            "{17 1 6 1 1 9}, true",
            "{4 5 4 2 4}, true",
            "{-1 -4 8 3 17}, false",
            "{}, false"
    })
    @ParameterizedTest
    public void isContainsSpecIntsTest2(@ConvertWith(IntArrayConverter.class) int[] testArr,
                                  boolean expectedResult) {
        if(expectedResult)
            assertTrue(manager.isContainsSpecInts(testArr));
        else
            assertFalse(manager.isContainsSpecInts(testArr));
    }

    @AfterAll
    public static void destroy() {
        manager = null;
    }
}
