/**
 * This class represents a library patron that has a name and
 * assigns values to different literary aspects of books.
 *
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
class Patron
{
    // instance variables
    /** The first name of the patron. */
    final String firstName;

    /** The last name of the patron. */
    final String lastName;

    /** The weight the patron assigns to the comic aspects of books. */
    final int myComicTendency;

    /** The weight the patron assigns to the dramatic aspects of books. */
    final int myDramaticTendency;

    /** The weight the patron assigns to the educational aspects of books. */
    final int myEducationalTendency;

    /** The minimal literary value a book must have for this patron to enjoy it. */
    final int enjoymentThreshold;

    /*----=  Constructors  =-----*/
    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName The first name of the patron.
     * @param patronLastName The last name of the patron.
     * @param comicTendency The weight the patron assigns to the
     * comic aspects of books.
     * @param dramaticTendency The weight the patron assigns to
     * the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns
     * to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value
     * a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency,
    int dramaticTendency, int educationalTendency, int patronEnjoymentThreshold)
    {
        firstName = patronFirstName;
        lastName = patronLastName;
        myComicTendency = comicTendency;
        myDramaticTendency = dramaticTendency;
        myEducationalTendency = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron, which is a
     * sequence of its first and last name, separated by a single white 
     * space. For example, if the patron's first name is "Ricky" and his
     * last name is "Bobby", this method will return the String "Ricky
     * Bobby".
     * @return the String representation of this patron.
     */
    String stringRepresentation()
    {
        final String WHITE_SPACE = " " ;
        return firstName + WHITE_SPACE + lastName;
    }

    /**
     * Returns the literary value this patron assigns to the given book.
     * @param book The book to asses.
     * @return the literary value this patron assigns to the given
     * book.
     */
    int getBookScore(Book book)
    {
        int litValue = book.dramaticValue * myDramaticTendency + book.comicValue * myComicTendency +
            book.educationalValue * myEducationalTendency;
        return litValue;
    }
    
    /**
     * Returns true of this patron will enjoy the given book, false
     * otherwise.
     * @param book The book to asses.
     * @return true of this patron will enjoy the given book, false 
     * otherwise.
     */
    boolean willEnjoyBook(Book book)
    {
        int litValue = getBookScore(book);
        boolean willEnjoy = litValue >= enjoymentThreshold;
        return willEnjoy;
    }
}
