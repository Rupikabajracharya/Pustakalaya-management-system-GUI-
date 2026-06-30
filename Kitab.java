package pustakalaya;

// Kitab.java - Represents a book in the library
public class Kitab {
    private String title;    
    private String author;   
    private boolean borrowed; 
    private String borrowerName; // store borrower's name

    // Constructor
    public Kitab(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowed = false;  
        this.borrowerName = "";
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    // Borrow book with borrower name
    public void borrow(String name) {
        borrowed = true;
        borrowerName = name;
    }

    // Return book
    public void returnBook() {
        borrowed = false;
        borrowerName = "";
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    @Override
    public String toString() {
        if(borrowed)
            return title + " by " + author + " (Borrowed by " + borrowerName + ")";
        else
            return title + " by " + author + " (Available)";
    }
}
