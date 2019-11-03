/**
 * This class represents a patron, which has a first and last name, three weights different literary aspects and
 * enjoyment threshold.
 */
class Patron {

    /**
     * The first name of the patron
     */
    private final String firstName;

    /**
     * The last name of the patron
     */
    private final String lastName;

    /**
     * The weight comic value of the patron
     */
    private int comicValue;

    /**
     * The weight dramatic value of the patron
     */
    private int dramaticValue;

    /**
     * The weight education value of the patron
     */
    private int educationValue;

    /**
     * Grade which above it the Patron will enjoy the book
     */
    private int enjoymentThreshold;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new patron with the given characteristic.
     *
     * @param patronFirstName          The first name of the patron
     * @param patronLastName           The last name of the patron
     * @param comicTendency            The comic tendency of the patron.
     * @param dramaticTendency         The dramatic tendency of the patron.
     * @param educationalTendency      The educational tendency of the patron.
     * @param patronEnjoymentThreshold The enjoyment threshold of patron.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {

        this.firstName = patronFirstName;
        this.lastName = patronLastName;
        this.comicValue = comicTendency;
        this.dramaticValue = dramaticTendency;
        this.educationValue = educationalTendency;
        this.enjoymentThreshold = patronEnjoymentThreshold;

    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the book, which is a sequence
     * of the firstName and lastName, separated by blank.
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Get books enjoyment score by book values and patron tendencies
     * @param book Book to calculate score
     * @return int that represent book score
     */
    int getBookScore(Book book) {
        return (this.comicValue * book.comicValue) +
                (this.dramaticValue * book.dramaticValue) +
                (this.educationValue * book.educationalValue);
    }

    /**
     * Get true if book score is bigger or equal to patron enjoyment threshold, false otherwise
     * @param book Book to check if current patron will enjoy of
     * @return Boolean that represent if patron will enjoy given book
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) >= this.enjoymentThreshold;
    }
}
