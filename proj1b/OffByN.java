/**
 * @author Helen Rhee on 9/16/2020
 */
public class OffByN implements CharacterComparator {
    /** Specifies distance between the 2 character. */
    private int distance;

    /** Constructor that takes in specified distance.
     * @param x int for distance
     */
    public OffByN(int x) {
        this.distance = x;
    }

    /**
     * Checks if characters are a specified distance apart.
     * @param x character x
     * @param y character y
     * @return boolean value if they are the right distance apart.
     */
    public boolean equalChars(char x, char y) {
        return Math.pow(x - y, 2) == Math.pow(distance, 2);
    }
}
