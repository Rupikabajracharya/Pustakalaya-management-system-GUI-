package pustakalaya;

import java.util.ArrayList;

public class Libraries {
    private ArrayList<Kitab> books = new ArrayList<>();

    public Libraries() {
      
        books.add(new Kitab("Harry Potter", "J.K. Rowling"));
        books.add(new Kitab("The Hobbit", "J.R.R. Tolkien"));
        books.add(new Kitab("1984", "George Orwell"));
    }

    public void addBook(String title, String author) {
        books.add(new Kitab(title, author));
    }

    public boolean deleteBook(String title) {
        return books.removeIf(b -> b.getTitle().equalsIgnoreCase(title));
    }

    public Kitab findBook(String title) {
        for (Kitab b : books) if (b.getTitle().equalsIgnoreCase(title)) return b;
        return null;
    }

    public ArrayList<Kitab> getAllBooks() { return books; }

    public ArrayList<Kitab> getAvailableBooks() {
        ArrayList<Kitab> list = new ArrayList<>();
        for (Kitab b : books) if (!b.isBorrowed()) list.add(b);
        return list;
    }

    public ArrayList<Kitab> getBorrowedBooks() {
        ArrayList<Kitab> list = new ArrayList<>();
        for (Kitab b : books) if (b.isBorrowed()) list.add(b);
        return list;
    }
}
