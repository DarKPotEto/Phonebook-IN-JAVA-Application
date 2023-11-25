import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PhonebookGUI extends JFrame {
    private ArrayList<Contact> contacts;
    private JTextArea contactDisplay;

    public PhonebookGUI() {
        contacts = new ArrayList<>();
        loadContacts(); // Load existing contacts

        setTitle("Phonebook Application");
        setSize(1050, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); // Set background color to black

        displayMainMenu();

        contactDisplay = new JTextArea(30, 35);
        contactDisplay.setEditable(false);
        JScrollPane contactScrollPane = new JScrollPane(contactDisplay);
        contactDisplay.setFont(new Font("Serif", Font.BOLD, 16)); // Set text color to black and bold
        contactDisplay.setForeground(Color.RED); // Set text color to black
        contactScrollPane.setBackground(Color.BLACK); // Set background color to black
        add(contactScrollPane, BorderLayout.EAST);

        setVisible(true);
    }

    private void displayMainMenu() {
        JPanel mainMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Increase the font size and make it bold for the welcome text
        JLabel welcomeLabel = new JLabel("* Welcome to PhoneBook Application *");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 37));
        welcomeLabel.setForeground(Color.RED); // Set text color to black
        mainMenuPanel.add(welcomeLabel, gbc);

        // Create a red color for buttons
        Color redColor = new Color(255, 0, 0);

        JButton addContactButton = new JButton("[1] Add a new Contact");
        addContactButton.setFont(new Font("Serif", Font.BOLD, 18));
        addContactButton.setBackground(redColor); // Set the button color to red
        addContactButton.setForeground(Color.BLACK); // Set text color to black
        addContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAddContactForm();
            }
        });

        JButton listContactsButton = new JButton("[2] List all Contacts");
        listContactsButton.setFont(new Font("Serif", Font.BOLD, 18));
        listContactsButton.setBackground(redColor); // Set the button color to red
        listContactsButton.setForeground(Color.BLACK); // Set text color to black
        listContactsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listAllContacts();
            }
        });

        JButton searchContactButton = new JButton("[3] Search for contact");
        searchContactButton.setFont(new Font("Serif", Font.BOLD, 18));
        searchContactButton.setBackground(redColor); // Set the button color to red
        searchContactButton.setForeground(Color.BLACK); // Set text color to black
        searchContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySearchContactForm();
            }
        });

        JButton editContactButton = new JButton("[4] Edit a Contact");
        editContactButton.setFont(new Font("Serif", Font.BOLD, 18));
        editContactButton.setBackground(redColor); // Set the button color to red
        editContactButton.setForeground(Color.BLACK); // Set text color to black
        editContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayEditContactForm();
            }
        });

        JButton deleteContactButton = new JButton("[5] Delete a Contact");
        deleteContactButton.setFont(new Font("Serif", Font.BOLD, 18));
        deleteContactButton.setBackground(redColor); // Set the button color to red
        deleteContactButton.setForeground(Color.BLACK); // Set text color to black
        deleteContactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDeleteContactForm();
            }
        });

        JButton exitButton = new JButton("[0] Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 18));
        exitButton.setBackground(redColor); // Set the button color to red
        exitButton.setForeground(Color.BLACK); // Set text color to black
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveContacts();
                System.exit(0);
            }
        });

        gbc.gridy = 1;
        mainMenuPanel.add(addContactButton, gbc);
        gbc.gridy = 2;
        mainMenuPanel.add(listContactsButton, gbc);
        gbc.gridy = 3;
        mainMenuPanel.add(searchContactButton, gbc);
        gbc.gridy = 4;
        mainMenuPanel.add(editContactButton, gbc);
        gbc.gridy = 5;
        mainMenuPanel.add(deleteContactButton, gbc);
        gbc.gridy = 6;
        mainMenuPanel.add(exitButton, gbc);

        mainMenuPanel.setBackground(Color.BLACK); // Set the background of the panel to black
        add(mainMenuPanel, BorderLayout.WEST);
    }

    private void displayAddContactForm() {
        JPanel addContactPanel = new JPanel(new GridLayout(6, 2));

        addContactPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(20);
        addContactPanel.add(nameField);

        addContactPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField(20);
        addContactPanel.add(phoneField);

        addContactPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(20);
        addContactPanel.add(addressField);

        addContactPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        addContactPanel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, addContactPanel, "Add a New Contact", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            long phone = Long.parseLong(phoneField.getText());
            String address = addressField.getText();
            String email = emailField.getText();

            Contact contact = new Contact(name, phone, address, email);
            contacts.add(contact);
            saveContacts();
            listAllContacts(); // Update the displayed contacts
        }
    }
    private void listAllContacts() {
        contactDisplay.setText(""); // Clear the contact display area
    
        for (Contact contact : contacts) {
            String formattedContact = String.format("Name: %s\nPhone: %s\nAddress: %s\nEmail: %s\n\n",
                    contact.getName(), contact.getPhone(), contact.getAddress(), contact.getEmail());
            contactDisplay.append(formattedContact);
        }
    }
    private void displaySearchContactForm() {
        String query = JOptionPane.showInputDialog("Enter the name of the contact to search:");

        if (query != null) {
            query = query.trim();

            ArrayList<Contact> searchResults = new ArrayList<>();

            for (Contact contact : contacts) {
                if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                    searchResults.add(contact);
                }
            }

            JTextArea displayArea = new JTextArea();
            displayArea.setEditable(false);

            if (searchResults.isEmpty()) {
                displayArea.append("No matching contacts found.");
            } else {
                for (Contact contact : searchResults) {
                    displayArea.append(contact.toString() + "\n");
                }
            }

            JScrollPane scrollPane = new JScrollPane(displayArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void displayEditContactForm() {
        String query = JOptionPane.showInputDialog("Enter the name of the contact to edit:");

        if (query != null) {
            query = query.trim();

            Iterator<Contact> iterator = contacts.iterator();
            boolean found = false;

            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(query)) {
                    displayEditContactDialog(contact);
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "Contact not found.", "Edit Contact", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void displayEditContactDialog(Contact contact) {
        JPanel editContactPanel = new JPanel(new GridLayout(6, 2));

        editContactPanel.add(new JLabel("Name:"));
        JTextField nameField = new JTextField(contact.getName());
        editContactPanel.add(nameField);

        editContactPanel.add(new JLabel("Phone:"));
        JTextField phoneField = new JTextField(String.valueOf(contact.getPhone()));
        editContactPanel.add(phoneField);

        editContactPanel.add(new JLabel("Address:"));
        JTextField addressField = new JTextField(contact.getAddress());
        editContactPanel.add(addressField);

        editContactPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(contact.getEmail());
        editContactPanel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, editContactPanel, "Edit Contact", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            contact.setName(nameField.getText());
            contact.setPhone(Long.parseLong(phoneField.getText()));
            contact.setAddress(addressField.getText());
            contact.setEmail(emailField.getText());
            saveContacts();
            listAllContacts(); // Update the displayed contacts
        }
    }

    private void displayDeleteContactForm() {
        String query = JOptionPane.showInputDialog("Enter the name of the contact to delete:");

        if (query != null) {
            query = query.trim();

            Iterator<Contact> iterator = contacts.iterator();
            boolean found = false;

            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if (contact.getName().equalsIgnoreCase(query)) {
                    iterator.remove();
                    saveContacts();
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(null, "Contact not found.", "Delete Contact", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void loadContacts() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("contacts.dat"));
            contacts = (ArrayList<Contact>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveContacts() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("contacts.dat"));
            oos.writeObject(contacts);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PhonebookGUI());
    }
}

class Contact implements Serializable {
    private String name;
    private long phone;
    private String address;
    private String email;

    public Contact(String name, long phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nPhone: " + phone + "\nAddress: " + address + "\nEmail: " + email;
    }
}
