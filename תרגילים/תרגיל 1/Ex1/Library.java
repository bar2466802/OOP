/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books, if a
 * copy of the requested book is available.
 * @author Bar Melinarskiy
 * @version 16/8/20
 */
class Library
{
    //Constants 
    private static final int FALSE = -1;
    private static final int NOT_BORROWED = -1;
    // instance variables
    /** The maximal number of books this library can hold.
     */
    final int bookCapacity;

    /** The maximal number of books this
     * library allows a single patron to borrow at the same time. 
     */
    final int maxBooksSimultaneously;

    /** The maximal number of registered
     * patrons this library can handle 
     */
    final int patronCapacity;

    /** The maximal number of books this
     * library allows a single patron to borrow at the same time. 
     */
    int booksCounter = 0;

    /** The maximal number of registered
     * patrons this library can handle 
     */
    int patronsCounter = 0;

    /** array of the patrons registered to this library
     */
    Patron[] patrons;

    /** array to keeping tabs on how many books each patron is 
     * currently borrowing from the library
     */
    int[] borrowingCounters;

    /** array to keeping tabs on how many books each patron is 
     * currently borrowing from the library
     */
    int[][] patronsBorrowedBooks;

    /** array of the books this library holds
     */
    Book[] books;

    /*----=  Constructors  =-----*/
    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity The maximal number of books this
     * library can hold.
     * @param maxBorrowedBooks The maximal number of books this
     * library allows a single patron to borrow at the same 
     * time.
     * @maxPatronCapacity - The maximal number of registered
     * patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity)
    {
        bookCapacity = maxBookCapacity;
        maxBooksSimultaneously = maxBorrowedBooks;
        patronCapacity = maxPatronCapacity;
        books = new Book[bookCapacity];
        patrons = new Patron[maxPatronCapacity];
        borrowingCounters = new int[maxPatronCapacity];        
        patronsBorrowedBooks = new int[maxPatronCapacity][maxBorrowedBooks];
        initPatronsBorrowedBooks();
    }

    /*----=  Instance Methods  =-----*/

    /** Check if this library reached it's book
     * capacity limit
     * @return true of we indeed reached the capacity,
     * false otherwise.
     */
    boolean getIfReachedBookCapacity()
    {
        return bookCapacity <= booksCounter;
    }

    /** Check if this library reached it's patrons
     * capacity limit
     * @return true of we indeed reached the capacity,
     * false otherwise.
     */
    boolean getIfReachedPatronCapacity()
    {
        return patronCapacity <= patronsCounter;
    }

    /**
     * Adds the given book to this library, if there is place available, and
     * it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a
     * spot and the book was successfully added, or if the
     * book was already in the library; a negative number
     * otherwise.
     */
    int addBookToLibrary(Book book)
    {
        int bookId = getBookId(book);
        if(bookId != FALSE && books[bookId] == book)
        {
            return bookId;
        }

        if(getIfReachedBookCapacity())
        {
            return FALSE;
        }

        //if we reached this point then the given book don't exist - so lets add it
        bookId = booksCounter;
        books[bookId] = book;
        booksCounter++;     
        return bookId;
    }

    /**
     * Returns true if the given number is an id of some book in the
     * library, false otherwise.
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the
     * library, false otherwise.
     */
    boolean isBookIdValid(int bookId)
    {
        final int MIN_BOOK_ID = 0;
        return MIN_BOOK_ID <= bookId && bookId < booksCounter;
    }

    /**
     * Returns the non-negative id number of the given book if he is
     * owned by this library, -1 otherwise.
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is
     * owned by this library, -1 otherwise.
     */
    int getBookId(Book book)
    {
        for(int i = 0; i < booksCounter; i++)
        {

            boolean match = books[i] == book;
            if(match)
            {
                return i;
            }
        }
        //if we reached this point then the given book don't exist
        return FALSE; 
    }

    /**
     * Returns true if the book with the given id is available, false
     * otherwise.
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available,
     * false otherwise.
     */
    boolean isBookAvailable(int bookId)
    {
        boolean isAvailable = false;
        //Chech the given id is valid
        if(isBookIdValid(bookId) == false)
        {
            return false;
        }
        //Check if this book has a current borrower
        isAvailable = books[bookId].getCurrentBorrowerId() == NOT_BORROWED;
        return isAvailable;
    }

    /**
     * Registers the given Patron to this library, if there is a spot
     * available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was
     * a spot and the patron was successfully registered or
     * if the patron was already registered. a negative
     * number otherwise.
     */
    int registerPatronToLibrary(Patron patron)
    {
        int patronId = getPatronId(patron);
        //Check that the given patron isn't already registered
        if(patronId != FALSE && patrons[patronId] == patron)
        {
            return patronId;
        }

        if(getIfReachedPatronCapacity()) //check we didn'r reach the max capacity in this library
        {
            return FALSE;
        }

        //if we reached this point then the given patron don't exist - so lets add it
        patronId = patronsCounter;
        patrons[patronId] = patron;
        patronsCounter++;     
        return patronId;
    }

    /**
     * Returns the non-negative id number of the given patron if he is
     * registered to this library, -1 otherwise.
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is
     * registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron)
    {
        for(int i = 0; i < patronsCounter; i++) //loop through the parons array to find the id
        {
            boolean match = patrons[i] == patron;
            if(match)
            {
                return i;
            }
        }
        //if we reached this point then the given book don't exist
        return FALSE; 
    }

    /**
     * Returns true if the given number is an id of a patron in the
     * library, false otherwise.
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the
     * library, false otherwise.
     */
    boolean isPatronIdValid(int patronId)
    {
        final int MIN_PATRON_ID = 0;
        return MIN_PATRON_ID <= patronId && patronId < patronsCounter;
    }

    /**
     * Returns the number of books the given patron is currently borrowing from the library
     * @param patronId The id to check.
     * @return a non-negetive of the books the given patron
     * is currently borrowing from the library.
     */
    int getNumOfBorrowedBooks(int patronId)
    {
        return borrowingCounters[patronId];
    }

    /**
     * Sets the number of books the given patron is currently borrowing from the library
     * @param patronId The id to check.
     * @param counter the current number of books borrowed by this patron
     */
    void setNumOfBorrowedBooks(int patronId, int counter)
    {
        borrowingCounters[patronId] = counter;
    }

    /**
     * Marks the book with the given id number as borrowed by the 
     * patron with the given patron id, if this book is available, the 
     * given patron isn't already borrowing the maximal number of 
     * books allowed, and if the patron will enjoy this book.
     * @param bookId The id number of the book to bborrowBookorrow.
     * @param patronId The id number of the patron that will 
     * borrow the book.
     * @return true if the book was borrowed successfully, false
     * otherwise.
     */
    boolean borrowBook(int bookId, int patronId)
    {
        if(isPatronIdValid(patronId) && isBookIdValid(bookId)) //check the given ids are valid
        {
            Patron patron = patrons[patronId];
            Book book = books[bookId];
            int numberOfBorrowedBooks = getNumOfBorrowedBooks(patronId);
            if((book.getCurrentBorrowerId() == NOT_BORROWED) //check the current book is not borrowed
            &&(numberOfBorrowedBooks < maxBooksSimultaneously) //check the given patron can borrow more books
            &&(patron.willEnjoyBook(book))) //check the patron will indeed enjoy this book
            {
                //Update the book status and the patron's info
                books[bookId].setBorrowerId(patronId);
                updatePatronsBorrowedBooks(patronId, bookId, false);
                setNumOfBorrowedBooks(patronId, ++numberOfBorrowedBooks);
                return true;
            }
        }
        return false;
    }

    /**
     * Init the patronsBorrowedBooks array
     */
    void initPatronsBorrowedBooks()
    {
        for(int i = 0; i < patronCapacity; i++)
        {
            for(int j = 0; j < maxBooksSimultaneously; j++)
            {
                patronsBorrowedBooks[i][j] = NOT_BORROWED;
            }
        }
    }

    /**
     * Update the current borrowing info for the given patron
     * @param bookId The id number of the book to borrow/return.
     * @param patronId The id number of the patron that will 
     * borrow/return the book.
     * @param returning if the patrong is returning this book or borrwing it 
     */
    void updatePatronsBorrowedBooks(int patronId, int bookId, boolean returning)
    {
        if(isPatronIdValid(patronId) && isBookIdValid(bookId))
        {
            for(int j = 0; j < maxBooksSimultaneously; j++)
            {
                if(returning) //check if we want to return this book or borrow it
                {
                    if(patronsBorrowedBooks[patronId][j] == bookId) //check if this is the book we want ro return
                    {
                        patronsBorrowedBooks[patronId][j] = NOT_BORROWED;
                        return;
                    }

                }
                else
                {
                    if(patronsBorrowedBooks[patronId][j] == NOT_BORROWED) //check this index is available to save the new book
                    {
                        patronsBorrowedBooks[patronId][j] = bookId;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Check if the given book is a copy of a book the given patron is already reading
     * That way we won't recommend him a wrong book
     * @param currentBook The book to borrow.
     * @param patronId The id number of the patron that will 
     * borrow the book.
     * @return true if this book is a copy of a book this patron is already reading, false otherwise.
     */
    boolean checkNotACopyOfBorrwedBook(int patronId, Book currentBook)
    {
        for(int j = 0; j < maxBooksSimultaneously; j++)
        {
            int currentIndex = patronsBorrowedBooks[patronId][j];
            if(currentIndex != NOT_BORROWED)
            {
                Book bookToCheck = books[currentIndex];
                if(bookToCheck.stringRepresentation() == currentBook.stringRepresentation())
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return the given book.
     * @param bookId The id number of the book to return.
     * @return the given book.
     */
    void returnBook(int bookId)
    {
        if(isBookIdValid(bookId) == true)
        {
            int patronId = books[bookId].getCurrentBorrowerId();
            if(patronId != NOT_BORROWED)
            {
                //Update the book status and the patron's info
                int numberOfBorrowedBooks = getNumOfBorrowedBooks(patronId);
                updatePatronsBorrowedBooks(patronId, bookId, true);
                setNumOfBorrowedBooks(patronId, --numberOfBorrowedBooks);
                books[bookId].returnBook();
            }
        }
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the
     * most, out of all available books he will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the 
     * book to.
     * @return The available book the patron with the given ID will
     * enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId)
    {
        if(isPatronIdValid(patronId) == true)
        {
            int currentScore = 0;
            int maxScore = -1;
            Book bestBook = null;
            Patron patron = patrons[patronId];
            for(int i = 0; i < booksCounter; i++)
            {
                //Check the current book will be loved by the current patron and that it is available 
                if(books[i].currentBorrowerId == NOT_BORROWED && patron.willEnjoyBook(books[i]) && 
                   checkNotACopyOfBorrwedBook(patronId, books[i]))
                {
                    currentScore = patron.getBookScore(books[i]);
                    if(currentScore > maxScore)
                    {
                        maxScore = currentScore;
                        bestBook = books[i];
                    }
                }
            }

            return bestBook;
        }

        return null;
    }
}
