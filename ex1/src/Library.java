class Library {

    /**
     * Library books array
     */
    Book[] books;

    /**
     * Library patrons array
     */
    Patron[] patrons;

    /**
     * Max books for patron to borrow
     */
    int maxBorrowedBooks;

    /*----=  Constructors  =-----*/

    /**
     * @param maxBookCapacity   Books array length
     * @param maxBorrowedBooks  Max borrowed books in library
     * @param maxPatronCapacity Patron array length
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.books = new Book[maxBookCapacity];
        this.patrons = new Patron[maxPatronCapacity];
        this.maxBorrowedBooks = maxBorrowedBooks;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Add bool to library, return book Id if succeed, -1 otherwise
     *
     * @param book Book to add to library
     * @return New book Id
     */
    int addBookToLibrary(Book book) {
        int firstBlank = -1;
        for (int i = 0; i < this.books.length; i++) {
            if (this.books[i] == null) {
                if (firstBlank == -1) {
                    firstBlank = i;
                }
            } else if (this.books[i].stringRepresentation().equals(book.stringRepresentation())) {
                return i;
            }
        }
        if (firstBlank != -1) {
            this.books[firstBlank] = book;
        }
        return firstBlank;
    }

    /**
     * Check if book Id is valid
     *
     * @param bookId Book Id
     * @return Boolean represent if book Id is valid
     */
    boolean isBookIdValid(int bookId) {
        return this.books[bookId] != null;
    }

    /**
     * Get book Id by book object
     *
     * @param book book object
     * @return book Id
     */
    int getBookId(Book book) {
        for (int i = 0; i < this.books.length; i++) {
            if (this.books[i] != null &&
                    this.books[i].stringRepresentation().equals(book.stringRepresentation())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Return true if book is available, false otherwise
     *
     * @param bookId book Id
     * @return Boolean represent if book is available
     */
    boolean isBookAvailable(int bookId) {
        return (this.isBookIdValid(bookId) &&
                this.books[bookId].getCurrentBorrowerId() == -1);
    }

    /**
     * Register new patron to the library, return his Id, if fail return -1
     *
     * @param patron New patron to add
     * @return patron Id
     */
    int registerPatronToLibrary(Patron patron) {
        int firstBlank = -1;
        for (int i = 0; i < this.patrons.length; i++) {
            if (this.patrons[i] == null) {
                if (firstBlank == -1) {
                    firstBlank = i;
                }
            } else if (this.patrons[i].stringRepresentation().equals(patron.stringRepresentation())) {
                return i;
            }
        }
        if (firstBlank != -1) {
            this.patrons[firstBlank] = patron;
        }
        return firstBlank;
    }

    /**
     * Check if patron Id is valid
     *
     * @param patronId Patron Id to check if valid
     * @return Boolean represent if patron Id is valid
     */
    boolean isPatronIdValid(int patronId) {
        return this.patrons[patronId] != null;
    }

    /**
     * Get patron Id from patron object
     *
     * @param patron patron object
     * @return Id of given patron object
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < this.patrons.length; i++) {
            if (this.patrons[i] != null &&
                    this.patrons[i].stringRepresentation().equals(patron.stringRepresentation())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get borrowed books count of given patron
     *
     * @param patronId Patron Id
     * @return Count of patron borrowed books
     */
    private int getPatronBookCount(int patronId) {
        int count = 0;
        for (Book book : this.books) {
            if (book != null && book.getCurrentBorrowerId() == patronId) {
                count++;
            }
        }
        return count;
    }

    /**
     * Borrow book to patron
     *
     * @param bookId   Book Id to borrow
     * @param patronId Borrower patron Id
     * @return Boolean represent if book got borrowed to patron
     */
    boolean borrowBook(int bookId, int patronId) {
        if (this.isBookIdValid(bookId) && this.isPatronIdValid(patronId) &&
                this.isBookAvailable(bookId) &&
                this.getPatronBookCount(patronId) != this.maxBorrowedBooks &&
                this.patrons[patronId].willEnjoyBook(this.books[bookId])) {
            this.books[bookId].setBorrowerId(patronId);
            return true;
        }
        return false;
    }

    /**
     * Return book to library
     *
     * @param bookId Book Id to return
     */
    void returnBook(int bookId) {
        if (this.isBookIdValid(bookId)) {
            this.books[bookId].returnBook();
        }
    }

    /**
     * Suggest the best book for given patron
     *
     * @param patronId Patron Id to suggest to
     * @return Book object to suggest patron, if not exist return null
     */
    Book suggestBookToPatron(int patronId) {
        Book bestBookId = null;
        int bestBookScore = -1;
        for (Book book : this.books) {
            if (book != null &&
                    book.getCurrentBorrowerId() == -1 &&
                    this.patrons[patronId] != null &&
                    this.patrons[patronId].willEnjoyBook(book) &&
                    this.patrons[patronId].getBookScore(book) > bestBookScore) {
                bestBookId = book;
                bestBookScore = this.patrons[patronId].getBookScore(book);
            }
        }
        return bestBookId;
    }

}
