public class TestAdi {
    TestAdi() {
        test3();
    }

    public void test3() {
        // make libraries and patrons:
        Library library1 = new Library(3, 2, 3);
        Library library2 = new Library(1, 1, 1);
        Book book1 = new Book("Harry Potter 1", "J.K. Rowling", 1996,
                              3, 1, 3);
        Book book2 = new Book("Mien Kampf", "A. H.", 1996,
                              3, 2, 1);
        Book book3 = new Book("Hello", "Josef Stalin", 1996,
                              2, 3, 1);
        Book book4 = new Book("Hello 2", "Josef Stalin", 1996,
                              1, 1, 1);
        Patron patron1 = new Patron("Adi", "Shimoni",
                                    3, 1, 1, 7);
        Patron patron2 = new Patron("Adi2", "Shimoni",
                                    1313, 5, 2, 7);
        Patron patron3 = new Patron("Adi3", "Shimoni",
                                    2, 100, 1, 2);
        Patron patron4 = new Patron("Adi4", "Shimoni",
                                    0, 0, 0, 0);

        // register patrons:
        int b = library1.registerPatronToLibrary(patron1);
        boolean s = library1.isPatronIdValid(1);
        System.out.println("patron id is valid? - " + Boolean.toString(!s));
        int k = library1.registerPatronToLibrary(patron2);
        int l = library1.registerPatronToLibrary(patron3);
        int ll = library1.registerPatronToLibrary(patron3);
        System.out.println(ll == 2);
        boolean m = library1.isPatronIdValid(0);
        System.out.println("patron id is valid? - " + Boolean.toString(m));
        library1.getPatronId(patron1);

        int jjj = library1.registerPatronToLibrary(patron3);
        int kkk = library1.registerPatronToLibrary(patron2);
        int mmm = library1.registerPatronToLibrary(patron1);
        System.out.println(jjj + kkk + mmm == 3);

        // add books library 1:
        int a = library1.addBookToLibrary(book1);
        System.out.println(a == 0);
        library1.addBookToLibrary(book2);
        library1.addBookToLibrary(book3);
        int mm = library1.addBookToLibrary(book3);
        System.out.println(mm == 2);
        boolean c = library1.borrowBook(2, 0);
        System.out.println(c);
        library1.returnBook(2);
        boolean d = library1.borrowBook(2, 0);
        System.out.println(d);
        library1.returnBook(2);
        boolean r = library1.borrowBook(2, 0);
        System.out.println(r);
        Book whichBook = library1.suggestBookToPatron(0);
        Book whichBook2 = library1.suggestBookToPatron(1);
        Book whichBook3 = library1.suggestBookToPatron(2);
        System.out.println(whichBook.stringRepresentation().equals("[Harry Potter 1,J.K. Rowling,1996,7]"));
        System.out.println(whichBook2.stringRepresentation().equals("[Mien Kampf,A. H.,1996,6]"));
        System.out.println(whichBook3.stringRepresentation().equals("[Mien Kampf,A. H.,1996,6]"));


        int aaaa = library2.addBookToLibrary(book1);
        System.out.println(aaaa == 0);
        library2.addBookToLibrary(book2);
        library2.addBookToLibrary(book3);
        int mmmm = library2.addBookToLibrary(book3);
        System.out.println(mmmm == -1);
        boolean cc = library2.borrowBook(2, 0);
        System.out.println(!cc);
        library2.returnBook(2);
        boolean dd = library2.borrowBook(2, 0);
        System.out.println(!dd);
        library2.returnBook(2);
        boolean rr = library2.borrowBook(2, 0);
        System.out.println(!rr);
        Book whichBook111 = library2.suggestBookToPatron(0);
        Book whichBook222 = library2.suggestBookToPatron(1);
        Book whichBook333 = library2.suggestBookToPatron(2);
        System.out.println(whichBook111 == null);
        System.out.println(whichBook222 == null);
        System.out.println(whichBook333 == null);
    }
}
