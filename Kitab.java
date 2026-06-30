package pustakalaya;

public class Kitab {
    private String title;    
    private String author;   
    private boolean borrowed; 
    private String borrowerName; 

   
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

    
    public void borrow(String name) {
        borrowed = true;
        borrowerName = name;
    }

    
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
