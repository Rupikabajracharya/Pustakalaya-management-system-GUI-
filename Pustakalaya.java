package pustakalaya;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Pustakalaya extends JFrame {
    private ArrayList<Kitab> kitabList = new ArrayList<>();

    private JTextField titleField, authorField;
    private JTextField borrowField, borrowerNameField, returnField, deleteField;
    private JTextArea displayArea;

    public Pustakalaya() {
        setTitle("📚 Pustakalaya (Library Management System)");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("➕ Add Kitab", createAddKitabPanel());
        tabs.addTab("📖 View Kitab", createViewKitabPanel());
        tabs.addTab("🔄 Borrow / Return", createBorrowReturnPanel());
        tabs.addTab("❌ Delete Kitab", createDeleteKitabPanel());

        add(tabs, BorderLayout.CENTER);
    }

    // ---------------- Add Kitab ----------------
    private JPanel createAddKitabPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Kitab Title:"), gbc);

        titleField = new JTextField(25);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Author:"), gbc);

        authorField = new JTextField(25);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        panel.add(authorField, gbc);

        JButton addBtn = new JButton("Add Kitab");
        addBtn.setBackground(new Color(0, 120, 215));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();

            if(title.isEmpty() || author.isEmpty()){
                JOptionPane.showMessageDialog(this, "⚠ Please enter both Title and Author!");
            } else {
                kitabList.add(new Kitab(title, author));
                titleField.setText(""); authorField.setText("");
                JOptionPane.showMessageDialog(this, "✅ Kitab added successfully!");
                refreshKitabList();
            }
        });

        return panel;
    }

    // ---------------- View Kitab ----------------
    private JPanel createViewKitabPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(255, 255, 240));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setBackground(new Color(245, 245, 245));
        displayArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton refreshBtn = new JButton("Refresh Kitab List");
        refreshBtn.setBackground(new Color(0, 120, 215));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> refreshKitabList());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshBtn, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------- Borrow / Return ----------------
    private JPanel createBorrowReturnPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(new Color(240, 240, 255));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Borrow Panel
        JPanel borrowPanel = new JPanel(new GridBagLayout());
        borrowPanel.setBackground(new Color(230, 240, 255));
        borrowPanel.setBorder(BorderFactory.createTitledBorder("Borrow Kitab"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kitab Title
        gbc.gridx = 0; gbc.gridy = 0;
        borrowPanel.add(new JLabel("Kitab Title:"), gbc);
        borrowField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        borrowPanel.add(borrowField, gbc);

        // Borrower Name
        gbc.gridx = 0; gbc.gridy = 1;
        borrowPanel.add(new JLabel("Borrower Name:"), gbc);
        borrowerNameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        borrowPanel.add(borrowerNameField, gbc);

        // Borrow Button beside
        JButton borrowBtn = new JButton("Borrow");
        borrowBtn.setBackground(new Color(204, 102, 0));
        borrowBtn.setForeground(Color.WHITE);
        borrowBtn.setFocusPainted(false);
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 2;
        borrowPanel.add(borrowBtn, gbc);

        borrowBtn.addActionListener(e -> {
            String title = borrowField.getText().trim();
            String name = borrowerNameField.getText().trim();

            if(title.isEmpty() || name.isEmpty()){
                JOptionPane.showMessageDialog(this, "⚠ Enter title and borrower name!");
                return;
            }

            for(Kitab k : kitabList){
                if(k.getTitle().equalsIgnoreCase(title)){
                    if(!k.isBorrowed()){
                        k.borrow(name);
                        JOptionPane.showMessageDialog(this, "📌 Borrowed: " + k.getTitle() + " by " + name);
                        borrowField.setText(""); borrowerNameField.setText("");
                        refreshKitabList();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, "⚠ Already borrowed by " + k.getBorrowerName());
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "⚠ Kitab not found!");
        });

        // Return Panel - Center aligned
        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBackground(new Color(230, 240, 255));
        returnPanel.setBorder(BorderFactory.createTitledBorder("Return Kitab"));

        GridBagConstraints gbcReturn = new GridBagConstraints();
        gbcReturn.insets = new Insets(10,10,10,10);
        gbcReturn.fill = GridBagConstraints.HORIZONTAL;

        gbcReturn.gridx = 0; gbcReturn.gridy = 0;
        returnPanel.add(new JLabel("Kitab Title:"), gbcReturn);

        returnField = new JTextField(15);
        gbcReturn.gridx = 1; gbcReturn.gridy = 0;
        returnPanel.add(returnField, gbcReturn);

        JButton returnBtn = new JButton("Return");
        returnBtn.setBackground(new Color(0, 102, 204));
        returnBtn.setForeground(Color.WHITE);
        returnBtn.setFocusPainted(false);
        gbcReturn.gridx = 2; gbcReturn.gridy = 0;
        returnPanel.add(returnBtn, gbcReturn);

        returnBtn.addActionListener(e -> {
            String title = returnField.getText().trim();
            if(title.isEmpty()){
                JOptionPane.showMessageDialog(this,"⚠ Enter Kitab title to return!");
                return;
            }

            for(Kitab k : kitabList){
                if(k.getTitle().equalsIgnoreCase(title)){
                    if(k.isBorrowed()){
                        k.returnBook();
                        JOptionPane.showMessageDialog(this,"✅ Returned: " + k.getTitle());
                        returnField.setText("");
                        refreshKitabList();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this,"⚠ Kitab was not borrowed!");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this,"⚠ Kitab not found!");
        });

        panel.add(borrowPanel);
        panel.add(returnPanel);
        return panel;
    }

    // ---------------- Delete ----------------
    private JPanel createDeleteKitabPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        panel.setBackground(new Color(255, 230, 230));
        panel.setBorder(new EmptyBorder(30, 20, 30, 20));

        panel.add(new JLabel("Kitab Title:"));
        deleteField = new JTextField(20);
        panel.add(deleteField);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(200, 0, 0));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        panel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            String title = deleteField.getText().trim();
            if(title.isEmpty()){
                JOptionPane.showMessageDialog(this,"⚠ Enter Kitab title to delete!");
                return;
            }

            for(Kitab k : kitabList){
                if(k.getTitle().equalsIgnoreCase(title)){
                    if(!k.isBorrowed()){
                        kitabList.remove(k);
                        JOptionPane.showMessageDialog(this,"🗑 Deleted: " + k.getTitle());
                        deleteField.setText("");
                        refreshKitabList();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this,"⚠ Cannot delete borrowed Kitab!");
                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this,"⚠ Kitab not found!");
        });

        return panel;
    }

    // ---------------- Refresh ----------------
    private void refreshKitabList(){
        if(displayArea != null){
            displayArea.setText("");
            int totalBooks = kitabList.size();
            int borrowedBooks = 0;

            for(Kitab k : kitabList){
                if(k.isBorrowed()) borrowedBooks++;
            }

            displayArea.append("📚 Total Books: " + totalBooks + "    🔖 Borrowed: " + borrowedBooks + "\n\n");

            if(kitabList.isEmpty()){
                displayArea.append("📂 No Kitab in Pustakalaya.\n");
            } else {
                int i = 1;
                for(Kitab k : kitabList){
                    displayArea.append(i + ". " + k + "\n");
                    i++;
                }
            }
        }
    }

    // ---------------- Main ----------------
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Pustakalaya pustakalaya = new Pustakalaya();
            pustakalaya.setVisible(true);
        });
    }
}
