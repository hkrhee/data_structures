/**
 * @author Helen Rhee on 9/16/2020
 * Checks if characters are off by one.
 */
public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        return Math.pow(x - y, 2) == 1;
    }

}
