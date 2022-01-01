/**
 * Higher Order Function interface for comparing characters.
 */
public interface CharacterComparator {

    /** Returns true if characters are equal by specified rules.
     * @param x char x
     * @param y chr y
     * @return boolean to see if x and y are equal */
    boolean equalChars(char x, char y);

}
