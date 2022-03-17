package view;

import controller.ClientController;
import controller.DestinationController;
import controller.VacantionController;
import controller.Validator;
import model.Client;
import model.Destination;
import model.Vacantion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class MainView extends JFrame {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("assignment1.SD");
    private static final ClientController clientController = new ClientController();
    private static final VacantionController vacantionController = new VacantionController();
    private static final DestinationController destinationController = new DestinationController();

    private TableManager vacantionTableManager;
    private Validator validator;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createUserButton;
    private JButton agencyButton;
    private JTextField nameField;
    private JButton createUser2Button;
    private JButton cancelButton;
    private JButton logOutButton;

    private JTable dbTable;
    private JTable dbTable2;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JTextField minDateField;
    private JTextField maxDateField;
    private JButton filterButton;
    private JButton detailsButton;
    private JButton backButton;
    private JButton bookButton;

    private JButton addDestinationButton;
    private JTextField destinationNameField;
    private JTextField destinationCountryField;
    private JTextField destinationDetailsField;
    private JButton addPackageButton;
    private JTextField packageNameField;
    private JTextField packagePriceField;
    private JTextField packageStartDateField;
    private JTextField packageEndDateField;
    private JTextField packageDetailsField;
    private JTextField packageSpotsField;
    private JComboBox<String> destinationList;
    private JButton updatePackageButton;
    private JButton deletePackageButton;
    private JButton deleteDestinationButton;

    /**
     * Method for frame initialization
     * */
    public void initialize(){

        this.setTitle("Travelling Agency");
        this.setSize(700, 800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        vacantionTableManager = new TableManager();
        validator = new Validator();

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        initializeLogin(panel1);
        initializeLoginListeners();

        this.setContentPane(panel1);
        this.setVisible(true);
    }

    /**
     * Method that adds the necessary elements to the login panel
     * @param panel - the login panel
     * */
    private void initializeLogin(JPanel panel) {

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBounds(270, 110, 200, 30);

        Font labelFont = loginLabel.getFont();
        String labelText = loginLabel.getText();

        int stringWidth = loginLabel.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = loginLabel.getWidth();

        double widthRatio = (double)componentWidth / (double)stringWidth;

        int newFontSize = (int)(labelFont.getSize() * widthRatio);
        int componentHeight = loginLabel.getHeight() - 5;

        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        loginLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));

        JLabel userLabel = new JLabel("Username ");
        userLabel.setBounds(170, 200, 200, 30);

        usernameField = new JTextField();
        usernameField.setBounds(240, 200, 200, 30);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(170, 240, 200, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(240, 240, 200, 30);

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(170, 310, 100, 30);

        createUserButton = new JButton("CREATE NEW USER");
        createUserButton.setBounds(290, 310, 150, 30);

        agencyButton = new JButton("AGENCY ADMINISTRATION");
        agencyButton.setBounds(170, 350, 270, 30);

        panel.add(loginLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(createUserButton);
        panel.add(agencyButton);
    }

    private void initializeLoginListeners() {

        loginButton.addActionListener(e -> {
            Client currentClient = clientController.getByName(usernameField.getText());
            if (currentClient != null) {
                if (currentClient.getPassword().equals(passwordField.getText())) {
                    JPanel panel2 = new JPanel();
                    panel2.setLayout(null);

                    List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                    initializeClient(panel2, vacantions, currentClient.getUsername());
                    initializeClientListeners(currentClient.getUsername());

                    this.setContentPane(panel2);
                    this.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong password!",
                            "Invalid password", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username doesn't exist!",
                        "Invalid username", JOptionPane.ERROR_MESSAGE);
            }
        });

        createUserButton.addActionListener(e -> {

            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            initializeCreateUser(panel21);
            initializeCreateUserListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });

        agencyButton.addActionListener(e -> {

            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeAgency(panel21, vacantions);
            initializeAgencyListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });
    }


    //Client registration page

    private void initializeCreateUser(JPanel panel) {

        JLabel titleLabel = new JLabel("Create User");
        titleLabel.setBounds(270, 65, 200, 30);

        JLabel userLabel = new JLabel("Username ");
        userLabel.setBounds(140, 200, 200, 30);

        usernameField = new JTextField();
        usernameField.setBounds(250, 200, 200, 30);

        JLabel passwordLabel = new JLabel("Password ");
        passwordLabel.setBounds(140, 240, 200, 30);

        passwordField = new JPasswordField();
        passwordField.setBounds(250, 240, 200, 30);

        JLabel nameLabel = new JLabel("Name ");
        nameLabel.setBounds(140, 280, 200, 30);

        nameField = new JTextField();
        nameField.setBounds(250, 280, 200, 30);

        createUser2Button = new JButton("CREATE");
        createUser2Button.setBounds(140, 460, 100, 30);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(300, 460, 150, 30);

        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(createUser2Button);
        panel.add(cancelButton);
    }

    /**
     * Method that contains listeners for the user create panel's buttons
     * */
    private void initializeCreateUserListeners() {

        cancelButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        createUser2Button.addActionListener(e -> {

            if(clientController.getByName(usernameField.getText()) == null) {
                Client client = new Client(usernameField.getText(), passwordField.getText(), nameField.getText());
                try {
                    validator.validateClient(client);
                    clientController.createClient(usernameField.getText(), passwordField.getText(), nameField.getText());
                    JPanel panel1 = new JPanel();
                    panel1.setLayout(null);

                    initializeLogin(panel1);
                    initializeLoginListeners();

                    this.setContentPane(panel1);
                    this.setVisible(true);
                } catch (IllegalArgumentException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(),
                            "Invalid user", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username already exists!",
                        "Invalid username", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //Client page


    private void initializeClient(JPanel panel, List<Vacantion> vacantionList, String clientUsername) {

        Vacantion toRemove = new Vacantion();
        int minPrice = 1000, maxPrice = -3;
        LocalDate maxDate = LocalDate.now();

        Client currentClient = clientController.getByName(clientUsername);
        List<Vacantion> clientVacantions = currentClient.getVacantions();

        for (Vacantion clientVacantion : clientVacantions) {
            for (Vacantion vacantion : vacantionList) {
                if (vacantion.getName().equals(clientVacantion.getName())) {
                    toRemove = vacantion;
                }
            }
            vacantionList.remove(toRemove);
        }

        Collections.sort(vacantionList, Comparator.comparing(Vacantion::getName));
        for (Vacantion vacantion1 : vacantionList) {
            if(vacantion1.getAvailable_spots() <= vacantion1.getClients().size()) {
                toRemove = vacantion1;
            }
        }
        vacantionList.remove(toRemove);

        List<Vacantion> valueList = vacantionController.getAll();
        for (Vacantion vacantion : valueList) {
            if(vacantion.getPrice() < minPrice) {
                minPrice = vacantion.getPrice();
            }
            if(vacantion.getPrice() > maxPrice) {
                maxPrice = vacantion.getPrice();
            }
            if (vacantion.getStartDate().isAfter(maxDate)) {
                maxDate = vacantion.getStartDate();
            }
        }

        String[] destinations = new String[destinationController.getAll().size() + 1];
        int i = 0;
        destinations[i] = "Select";
        i++;
        for (Destination destination : destinationController.getAll()) {
            destinations[i] = destination.getName();
            i++;
        }

        JLabel titleLabel = new JLabel("Client page");
        titleLabel.setBounds(270, 35, 200, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        logOutButton = new JButton("LOG OUT");
        logOutButton.setBounds(30, 35, 100, 30);

        JLabel packagesLabel = new JLabel("Vacantion packages");
        packagesLabel.setBounds(30, 90, 200, 30);
        packagesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        dbTable = vacantionTableManager.fillVacantionTable(vacantionList);
        dbTable.setVisible(true);
        dbTable.setEnabled(true);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 140, 450, 250);
        scrollPane.setViewportView(dbTable);

        JLabel filtersLabel = new JLabel("Filters");
        filtersLabel.setBounds(500, 140, 150, 30);
        filtersLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel destinationsLabel = new JLabel("Destination");
        destinationsLabel.setBounds(500, 180, 150, 30);

        destinationList = new JComboBox<>(destinations);
        destinationList.setBounds(500, 220, 150, 30);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBounds(500, 260, 150, 30);

        minPriceField = new JTextField(String.valueOf(minPrice));
        minPriceField.setBounds(500, 300, 70, 30);

        maxPriceField = new JTextField(String.valueOf(maxPrice));
        maxPriceField.setBounds(580, 300, 70, 30);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(500, 340, 150, 30);

        minDateField = new JTextField(String.valueOf(LocalDate.now()));
        minDateField.setBounds(500, 380, 70, 30);

        maxDateField = new JTextField(String.valueOf(maxDate));
        maxDateField.setBounds(580, 380, 70, 30);

        filterButton = new JButton("FILTER");
        filterButton.setBounds(500, 440, 150, 30);

        detailsButton = new JButton("PACKAGE DETAILS");
        detailsButton.setBounds(500, 480, 150, 30);

        Client client = clientController.getByName(clientUsername);

        JLabel bookedPackagesLabel = new JLabel("Booked vacantions");
        bookedPackagesLabel.setBounds(30, 410, 200, 30);
        bookedPackagesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        dbTable2 = vacantionTableManager.fillVacantionTable(client.getVacantions());
        dbTable2.setVisible(true);
        dbTable2.setEnabled(true);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(30, 460, 450, 250);
        scrollPane2.setViewportView(dbTable2);

        panel.add(titleLabel);
        panel.add(logOutButton);
        panel.add(packagesLabel);
        panel.add(scrollPane);
        panel.add(filtersLabel);
        panel.add(destinationsLabel);
        panel.add(destinationList);
        panel.add(priceLabel);
        panel.add(minPriceField);
        panel.add(maxPriceField);
        panel.add(dateLabel);
        panel.add(minDateField);
        panel.add(maxDateField);
        panel.add(filterButton);
        panel.add(detailsButton);
        panel.add(bookedPackagesLabel);
        panel.add(scrollPane2);
    }

    /**
     * Method that contains listeners for the client panel's buttons
     * */
    private void initializeClientListeners(String clientUsername) {

        logOutButton.addActionListener(e -> {
            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        filterButton.addActionListener(e -> {

            try {
                validator.validateDate(minDateField.getText());
                validator.validateDate(maxDateField.getText());
                validator.validatePrice(minPriceField.getText());
                validator.validatePrice(maxPriceField.getText());

                JPanel panel2 = new JPanel();
                panel2.setLayout(null);

                int minPrice = Integer.parseInt(minPriceField.getText());
                int maxPrice = Integer.parseInt(maxPriceField.getText());
                LocalDate minDate = LocalDate.parse(minDateField.getText());
                LocalDate maxDate = LocalDate.parse(maxDateField.getText());

                List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                List<Vacantion> filteredVacantions = new ArrayList<Vacantion>();
                for (Vacantion vacantion : vacantions) {
                    if (vacantion.getPrice() >= minPrice && vacantion.getPrice() <= maxPrice) {
                        if (vacantion.getStartDate().isAfter(minDate) && vacantion.getEndDate().isBefore(maxDate)) {
                            if (destinationList.getSelectedItem().equals("Select")) {
                                filteredVacantions.add(vacantion);
                            } else {
                                if (vacantion.getDestination().getName().equals(destinationList.getItemAt(destinationList.getSelectedIndex()))) {
                                    filteredVacantions.add(vacantion);
                                }
                            }
                        }
                    }
                }
                initializeClient(panel2, filteredVacantions, clientUsername);
                initializeClientListeners(clientUsername);

                this.setContentPane(panel2);
                this.setVisible(true);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Invalid filter", JOptionPane.ERROR_MESSAGE);
            }
        });

        detailsButton.addActionListener(e -> {
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            initializeVacantion(panel2, dbTable.getModel().getValueAt(dbTable.getSelectedRow(), 0).toString(), clientUsername);
            initializeVacantionListeners(dbTable.getModel().getValueAt(dbTable.getSelectedRow(), 0).toString(), clientUsername);

            this.setContentPane(panel2);
            this.setVisible(true);
        });
    }

    //Vacantion page

    private void initializeVacantion(JPanel panel, String vacantionName, String clientUsername) {

        Vacantion vacantion = vacantionController.getByName(vacantionName);

        JLabel titleLabel = new JLabel("Vacantion page");
        titleLabel.setBounds(270, 35, 200, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        backButton = new JButton("BACK");
        backButton.setBounds(30, 35, 100, 30);

        JLabel nameLabel = new JLabel(vacantion.getName());
        nameLabel.setBounds(30, 100, 200, 30);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

        JLabel destinationLabel = new JLabel("Destination:");
        destinationLabel.setBounds(30, 140, 150, 30);
        destinationLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel destinationVacantionLabel = new JLabel(vacantion.getDestination().getName());
        destinationVacantionLabel.setBounds(180, 140, 300, 30);
        destinationVacantionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 180, 150, 30);
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel priceVacantionLabel = new JLabel(String.valueOf(vacantion.getPrice()));
        priceVacantionLabel.setBounds(180, 180, 300, 30);
        priceVacantionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JLabel periodLabel = new JLabel("Period:");
        periodLabel.setBounds(30, 220, 150, 30);
        periodLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel periodVacantionLabel = new JLabel(String.valueOf(vacantion.getStartDate()) + " - " + String.valueOf(vacantion.getEndDate()));
        periodVacantionLabel.setBounds(180, 220, 300, 30);
        periodVacantionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JLabel detailsLabel = new JLabel("Details:");
        detailsLabel.setBounds(30, 260, 150, 30);
        detailsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JLabel detailsVacantionLabel = new JLabel(String.valueOf(vacantion.getDetails()));
        detailsVacantionLabel.setBounds(180, 260, 300, 30);
        detailsVacantionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        bookButton = new JButton("BOOK");
        bookButton.setBounds(30, 320, 300, 30);

        panel.add(backButton);
        panel.add(titleLabel);
        panel.add(nameLabel);
        panel.add(destinationLabel);
        panel.add(priceLabel);
        panel.add(periodLabel);
        panel.add(detailsLabel);
        panel.add(destinationVacantionLabel);
        panel.add(priceVacantionLabel);
        panel.add(periodVacantionLabel);
        panel.add(detailsVacantionLabel);
        panel.add(bookButton);
    }

    private void initializeVacantionListeners(String vacantionName, String clientUsername) {

        backButton.addActionListener(e -> {
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeClient(panel2, vacantions, clientUsername);
            initializeClientListeners(clientUsername);

            this.setContentPane(panel2);
            this.setVisible(true);
        });

        bookButton.addActionListener(e -> {
            JPanel panel2 = new JPanel();
            panel2.setLayout(null);

            Vacantion vacantion = vacantionController.getByName(vacantionName);
            Client client = clientController.getByName(clientUsername);

            addVacantionToClient(client, vacantion);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeClient(panel2, vacantions, clientUsername);
            initializeClientListeners(clientUsername);

            this.setContentPane(panel2);
            this.setVisible(true);
        });
    }

    public void addVacantionToClient(Client client, Vacantion vacantion) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Vacantion> clientVacantions = client.getVacantions();
        clientVacantions.add(vacantion);
        client.setVacantions(clientVacantions);

        List<Client> vacantionClients = vacantion.getClients();
        vacantionClients.add(client);
        vacantion.setClients(vacantionClients);

        entityManager.merge(vacantion);
        entityManager.merge(client);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    //Agency

    public void initializeAgency(JPanel panel, List<Vacantion> vacantionList) {

        JLabel titleLabel = new JLabel("Agency page");
        titleLabel.setBounds(270, 35, 200, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        backButton = new JButton("BACK");
        backButton.setBounds(30, 35, 100, 30);

        JLabel destinationsLabel = new JLabel("Vacantion destinations");
        destinationsLabel.setBounds(30, 90, 200, 30);
        destinationsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        List<Destination> destinations = destinationController.getAll();

        dbTable2 = vacantionTableManager.fillDestinationTable(destinations);
        dbTable2.setVisible(true);
        dbTable2.setEnabled(true);

        scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(30, 140, 450, 250);
        scrollPane2.setViewportView(dbTable2);

        JLabel packagesLabel = new JLabel("Vacantion packages");
        packagesLabel.setBounds(30, 410, 200, 30);
        packagesLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        dbTable = vacantionTableManager.fillAgencyVacantionTable(vacantionList);
        dbTable.setVisible(true);
        dbTable.setEnabled(true);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 460, 450, 250);
        scrollPane.setViewportView(dbTable);

        addDestinationButton = new JButton("ADD DESTINATION");
        addDestinationButton.setBounds(500, 140, 150, 30);

        addPackageButton = new JButton("ADD PACKAGE");
        addPackageButton.setBounds(500, 180, 150, 30);

        updatePackageButton = new JButton("UPDATE PACKAGE");
        updatePackageButton.setBounds(500, 220, 150, 30);

        deletePackageButton = new JButton("DELETE PACKAGE");
        deletePackageButton.setBounds(500, 260, 150, 30);

        deleteDestinationButton = new JButton("DELETE DESTINATION");
        deleteDestinationButton.setBounds(500, 300, 150, 30);

        panel.add(titleLabel);
        panel.add(backButton);
        panel.add(destinationsLabel);
        panel.add(scrollPane2);
        panel.add(packagesLabel);
        panel.add(scrollPane);
        panel.add(addDestinationButton);
        panel.add(addPackageButton);
        panel.add(updatePackageButton);
        panel.add(deletePackageButton);
        panel.add(deleteDestinationButton);
    }

    public void initializeAgencyListeners() {

        backButton.addActionListener(e -> {

            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeLogin(panel1);
            initializeLoginListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        addDestinationButton.addActionListener(e -> {

            JPanel panel1 = new JPanel();
            panel1.setLayout(null);

            initializeAddDestination(panel1);
            initializeAddDestinationListeners();

            this.setContentPane(panel1);
            this.setVisible(true);
        });

        addPackageButton.addActionListener(e -> {

            if (dbTable2.getSelectedRow() >= 0) {
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);

                initializeAddPackage(panel1);
                initializeAddPackageListeners();

                this.setContentPane(panel1);
                this.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please select destination!",
                        "Invalid destination", JOptionPane.ERROR_MESSAGE);
            }
        });

        updatePackageButton.addActionListener(e -> {

            if (dbTable.getSelectedRow() >= 0) {
                Vacantion vacantion = vacantionController.getByName(dbTable.getModel().getValueAt(dbTable.getSelectedRow(), 0).toString());

                JPanel panel1 = new JPanel();
                panel1.setLayout(null);

                initializeUpdatePackage(panel1, vacantion);
                initializeUpdatePackageListeners(vacantion);

                this.setContentPane(panel1);
                this.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please select package!",
                        "Invalid package", JOptionPane.ERROR_MESSAGE);
            }
        });

        deletePackageButton.addActionListener(e -> {

            if (dbTable.getSelectedRow() >= 0) {
                vacantionController.deleteVacantion(dbTable.getModel().getValueAt(dbTable.getSelectedRow(), 0).toString());

                JPanel panel21 = new JPanel();
                panel21.setLayout(null);

                List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                initializeAgency(panel21, vacantions);
                initializeAgencyListeners();

                this.setContentPane(panel21);
                this.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please select package!",
                        "Invalid package", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteDestinationButton.addActionListener(e -> {

            if (dbTable2.getSelectedRow() >= 0) {
                destinationController.deleteDestination(dbTable2.getModel().getValueAt(dbTable2.getSelectedRow(), 0).toString());

                JPanel panel21 = new JPanel();
                panel21.setLayout(null);

                List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                initializeAgency(panel21, vacantions);
                initializeAgencyListeners();

                this.setContentPane(panel21);
                this.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Please select destination!",
                        "Invalid destination", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //Add destination

    public void initializeAddDestination(JPanel panel) {

        JLabel titleLabel = new JLabel("Add destination");
        titleLabel.setBounds(270, 35, 200, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        JLabel destinationNameLabel = new JLabel("Name");
        destinationNameLabel.setBounds(140, 200, 200, 30);

        destinationNameField = new JTextField();
        destinationNameField.setBounds(250, 200, 200, 30);

        JLabel destinationCountryLabel = new JLabel("Country");
        destinationCountryLabel.setBounds(140, 240, 200, 30);

        destinationCountryField = new JTextField();
        destinationCountryField.setBounds(250, 240, 200, 30);

        JLabel destinationDetailsLabel = new JLabel("Details");
        destinationDetailsLabel.setBounds(140, 280, 200, 30);

        destinationDetailsField = new JTextField();
        destinationDetailsField.setBounds(250, 280, 200, 60);

        createUser2Button = new JButton("ADD");
        createUser2Button.setBounds(140, 460, 100, 30);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(300, 460, 150, 30);

        panel.add(titleLabel);
        panel.add(destinationNameLabel);
        panel.add(destinationNameField);
        panel.add(destinationCountryLabel);
        panel.add(destinationCountryField);
        panel.add(destinationDetailsLabel);
        panel.add(destinationDetailsField);
        panel.add(createUser2Button);
        panel.add(cancelButton);
    }

    private void initializeAddDestinationListeners() {

        cancelButton.addActionListener(e -> {
            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeAgency(panel21, vacantions);
            initializeAgencyListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });

        createUser2Button.addActionListener(e -> {

            try {
                validator.validateDestination(destinationNameField.getText(), destinationCountryField.getText());

                destinationController.createDestination(destinationNameField.getText(), destinationCountryField.getText(), destinationDetailsField.getText());

                JPanel panel21 = new JPanel();
                panel21.setLayout(null);

                List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                initializeAgency(panel21, vacantions);
                initializeAgencyListeners();

                this.setContentPane(panel21);
                this.setVisible(true);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Invalid destination", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //Add package

    public void initializeAddPackage(JPanel panel) {

        JLabel titleLabel = new JLabel("Add vacantion package");
        titleLabel.setBounds(200, 35, 300, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        JLabel packageDestinationLabel = new JLabel("Destination:    " + dbTable2.getModel().getValueAt(dbTable2.getSelectedRow(), 0).toString());
        packageDestinationLabel.setBounds(140, 160, 200, 30);

        JLabel packageNameLabel = new JLabel("Name");
        packageNameLabel.setBounds(140, 200, 200, 30);

        packageNameField = new JTextField();
        packageNameField.setBounds(250, 200, 200, 30);

        JLabel packagePriceLabel = new JLabel("Price");
        packagePriceLabel.setBounds(140, 240, 200, 30);

        packagePriceField = new JTextField();
        packagePriceField.setBounds(250, 240, 200, 30);

        JLabel packageStartDateLabel = new JLabel("Start Date");
        packageStartDateLabel.setBounds(140, 280, 200, 30);

        packageStartDateField = new JTextField();
        packageStartDateField.setBounds(250, 280, 200, 30);

        JLabel packageEndDateLabel = new JLabel("End Date");
        packageEndDateLabel.setBounds(140, 320, 200, 30);

        packageEndDateField = new JTextField();
        packageEndDateField.setBounds(250, 320, 200, 30);

        JLabel packageDetailsLabel = new JLabel("Details");
        packageDetailsLabel.setBounds(140, 360, 200, 30);

        packageDetailsField = new JTextField();
        packageDetailsField.setBounds(250, 360, 200, 60);

        JLabel packageSpotsLabel = new JLabel("Available spots");
        packageSpotsLabel.setBounds(140, 440, 200, 30);

        packageSpotsField = new JTextField();
        packageSpotsField.setBounds(250, 440, 200, 30);

        createUser2Button = new JButton("ADD");
        createUser2Button.setBounds(140, 490, 100, 30);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(300, 490, 150, 30);

        panel.add(titleLabel);
        panel.add(packageDestinationLabel);
        panel.add(packageNameLabel);
        panel.add(packageNameField);
        panel.add(packagePriceLabel);
        panel.add(packagePriceField);
        panel.add(packageStartDateLabel);
        panel.add(packageStartDateField);
        panel.add(packageEndDateLabel);
        panel.add(packageEndDateField);
        panel.add(packageDetailsLabel);
        panel.add(packageDetailsField);
        panel.add(packageSpotsLabel);
        panel.add(packageSpotsField);
        panel.add(createUser2Button);
        panel.add(cancelButton);
    }

    private void initializeAddPackageListeners() {

        cancelButton.addActionListener(e -> {
            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeAgency(panel21, vacantions);
            initializeAgencyListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });

        createUser2Button.addActionListener(e -> {

            try {
                validator.validateVacantion(packagePriceField.getText(), packageStartDateField.getText(), packageEndDateField.getText(), packageSpotsField.getText());

                Destination destination = destinationController.getByName(dbTable2.getModel().getValueAt(dbTable2.getSelectedRow(), 0).toString());
                vacantionController.createVacantion(packageNameField.getText(), Integer.parseInt(packagePriceField.getText()), LocalDate.parse(packageStartDateField.getText()), LocalDate.parse(packageEndDateField.getText()), packageDetailsField.getText(), Integer.parseInt(packageSpotsField.getText()), destination);

                JPanel panel21 = new JPanel();
                panel21.setLayout(null);

                List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
                initializeAgency(panel21, vacantions);
                initializeAgencyListeners();

                this.setContentPane(panel21);
                this.setVisible(true);
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Invalid package", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //Update package

    public void initializeUpdatePackage(JPanel panel, Vacantion vacantion) {

        JLabel titleLabel = new JLabel("Update vacantion package");
        titleLabel.setBounds(270, 35, 200, 30);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

        JLabel packageNameLabel = new JLabel("Name");
        packageNameLabel.setBounds(140, 200, 200, 30);

        packageNameField = new JTextField(vacantion.getName());
        packageNameField.setBounds(250, 200, 200, 30);

        JLabel packagePriceLabel = new JLabel("Price");
        packagePriceLabel.setBounds(140, 240, 200, 30);

        packagePriceField = new JTextField(String.valueOf(vacantion.getPrice()));
        packagePriceField.setBounds(250, 240, 200, 30);

        JLabel packageStartDateLabel = new JLabel("Start Date");
        packageStartDateLabel.setBounds(140, 280, 200, 30);

        packageStartDateField = new JTextField(String.valueOf(vacantion.getStartDate()));
        packageStartDateField.setBounds(250, 280, 200, 30);

        JLabel packageEndDateLabel = new JLabel("End Date");
        packageEndDateLabel.setBounds(140, 320, 200, 30);

        packageEndDateField = new JTextField(String.valueOf(vacantion.getEndDate()));
        packageEndDateField.setBounds(250, 320, 200, 30);

        JLabel packageDetailsLabel = new JLabel("Details");
        packageDetailsLabel.setBounds(140, 360, 200, 30);

        packageDetailsField = new JTextField(String.valueOf(vacantion.getDetails()));
        packageDetailsField.setBounds(250, 360, 200, 60);

        JLabel packageSpotsLabel = new JLabel("Available spots");
        packageSpotsLabel.setBounds(140, 440, 200, 30);

        packageSpotsField = new JTextField(String.valueOf(vacantion.getAvailable_spots()));
        packageSpotsField.setBounds(250, 440, 200, 30);

        createUser2Button = new JButton("UPDATE");
        createUser2Button.setBounds(140, 490, 100, 30);

        cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(300, 490, 150, 30);

        panel.add(titleLabel);
        panel.add(packageNameLabel);
        panel.add(packageNameField);
        panel.add(packagePriceLabel);
        panel.add(packagePriceField);
        panel.add(packageStartDateLabel);
        panel.add(packageStartDateField);
        panel.add(packageEndDateLabel);
        panel.add(packageEndDateField);
        panel.add(packageDetailsLabel);
        panel.add(packageDetailsField);
        panel.add(packageSpotsLabel);
        panel.add(packageSpotsField);
        panel.add(createUser2Button);
        panel.add(cancelButton);
    }

    private void initializeUpdatePackageListeners(Vacantion vacantion) {

        cancelButton.addActionListener(e -> {
            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeAgency(panel21, vacantions);
            initializeAgencyListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });

        createUser2Button.addActionListener(e -> {

            Vacantion newVacantion = new Vacantion(packageNameField.getText(), Integer.parseInt(packagePriceField.getText()), LocalDate.parse(packageStartDateField.getText()), LocalDate.parse(packageEndDateField.getText()), packageDetailsField.getText(), Integer.parseInt(packageSpotsField.getText()), vacantion.getDestination());
            vacantionController.updateVacantion(vacantion.getName(), newVacantion);

            JPanel panel21 = new JPanel();
            panel21.setLayout(null);

            List<Vacantion> vacantions = new ArrayList<Vacantion>(vacantionController.getAll());
            initializeAgency(panel21, vacantions);
            initializeAgencyListeners();

            this.setContentPane(panel21);
            this.setVisible(true);
        });
    }
}
