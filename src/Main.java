import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static UserManager userManager;
    private static PropertyManager propertyManager;
    private static ReservationManager reservationManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        initialize();
        showMainMenu();
    }

    private static void initialize() {
        userManager = new UserManager();
        propertyManager = new PropertyManager();
        reservationManager = new ReservationManager();
        scanner = new Scanner(System.in);
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== Real Estate Property Listings Portal ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Thank you for using our system!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userManager.authenticate(username, password);
        if (user != null) {
            System.out.println("Welcome, " + user.getName() + "!");
            switch (user.getRole()) {
                case "ADMIN":
                    showAdminMenu(user);
                    break;
                case "SELLER":
                    showSellerMenu((Seller) user);
                    break;
                case "BUYER":
                    showBuyerMenu((Buyer) user);
                    break;
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void register() {
        System.out.println("\n=== Registration ===");
        System.out.println("Select role:");
        System.out.println("1. Seller");
        System.out.println("2. Buyer");
        System.out.print("Choose role: ");

        int roleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        if (userManager.usernameExists(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        // Generate a new user ID
        int userId = userManager.getAllUsers().size() + 1;

        User newUser;
        if (roleChoice == 1) {
            newUser = new Seller(userId, username, password, name, email, phone);
        } else {
            newUser = new Buyer(userId, username, password, name, email, phone);
        }

        userManager.addUser(newUser);
        System.out.println("Registration successful! Please login.");
    }

    private static void showSellerMenu(Seller seller) {
        while (true) {
            System.out.println("\n=== Seller Menu ===");
            System.out.println("1. Add Property");
            System.out.println("2. View My Properties");
            System.out.println("3. Update Property");
            System.out.println("4. Delete Property");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProperty(seller);
                    break;
                case 2:
                    viewSellerProperties(seller);
                    break;
                case 3:
                    updateProperty(seller);
                    break;
                case 4:
                    deleteProperty(seller);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showBuyerMenu(Buyer buyer) {
        while (true) {
            System.out.println("\n=== Buyer Menu ===");
            System.out.println("1. View All Properties");
            System.out.println("2. Make Reservation");
            System.out.println("3. View My Reservations");
            System.out.println("4. Add Preference");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllProperties();
                    break;
                case 2:
                    makeReservation(buyer);
                    break;
                case 3:
                    viewBuyerReservations(buyer);
                    break;
                case 4:
                    addPreference(buyer);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showAdminMenu(User admin) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View All Users");
            System.out.println("2. View All Properties");
            System.out.println("3. View All Reservations");
            System.out.println("4. Delete User");
            System.out.println("5. Delete Property");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n=== All Users ===");
                    for (User user : userManager.getAllUsers()) {
                        System.out.println(user);
                    }
                    break;
                case 2:
                    viewAllProperties();
                    break;
                case 3:
                    System.out.println("\n=== All Reservations ===");
                    for (Reservation reservation : reservationManager.getAllReservations()) {
                        System.out.println(reservation);
                    }
                    break;
                case 4:
                    System.out.print("Enter user ID to delete: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    userManager.deleteUser(userId);
                    System.out.println("User deleted successfully!");
                    break;
                case 5:
                    System.out.print("Enter property ID to delete: ");
                    int propertyId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    propertyManager.deleteProperty(propertyId);
                    System.out.println("Property deleted successfully!");
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper methods for property management
    private static void addProperty(Seller seller) {
        System.out.println("\n=== Add New Property ===");
        System.out.print("Enter property name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter location: ");
        String location = scanner.nextLine();
        System.out.print("Enter type: ");
        String type = scanner.nextLine();

        int propertyId = propertyManager.getProperties().size() + 1;
        Property property = new Property(propertyId, name, price, location, type, seller.getUserId());
        propertyManager.addProperty(property);
        seller.addProperty(propertyId);

        System.out.println("Property added successfully!");
    }

    private static void viewAllProperties() {
        System.out.println("\n=== All Properties ===");
        propertyManager.sortPropertiesByPrice(); // Sort properties by price
        for (Property property : propertyManager.getProperties()) {
            System.out.println(property);
        }
    }

    private static void viewSellerProperties(Seller seller) {
        System.out.println("\n=== My Properties ===");
        for (int propertyId : seller.getPropertyIds()) {
            Property property = propertyManager.getProperty(propertyId);
            if (property != null) {
                System.out.println(property);
            }
        }
    }

    private static void makeReservation(Buyer buyer) {
        viewAllProperties();
        System.out.print("\nEnter property ID to reserve: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Property property = propertyManager.getProperty(propertyId);
        if (property != null && property.getStatus().equals("Available")) {
            int reservationId = reservationManager.getAllReservations().size() + 1;
            Reservation reservation = new Reservation(reservationId, propertyId, buyer.getUserId(), LocalDateTime.now());
            reservationManager.addReservation(reservation);
            buyer.addReservation(reservationId);
            property.setStatus("Reserved");
            propertyManager.updateProperty(property);
            System.out.println("Reservation made successfully!");
        } else {
            System.out.println("Property not available for reservation.");
        }
    }

    private static void viewBuyerReservations(Buyer buyer) {
        System.out.println("\n=== My Reservations ===");
        for (int reservationId : buyer.getReservationIds()) {
            Reservation reservation = reservationManager.getReservation(reservationId);
            if (reservation != null) {
                Property property = propertyManager.getProperty(reservation.getPropertyId());
                System.out.println(reservation);
                if (property != null) {
                    System.out.println("Property: " + property);
                }
                System.out.println();
            }
        }
    }

    private static void addPreference(Buyer buyer) {
        System.out.print("Enter property preference (type/location/price range): ");
        String preference = scanner.nextLine();
        buyer.addPreference(preference);
        System.out.println("Preference added successfully!");
    }

    private static void updateProperty(Seller seller) {
        viewSellerProperties(seller);
        System.out.print("\nEnter property ID to update: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Property property = propertyManager.getProperty(propertyId);
        if (property != null && property.getSellerId() == seller.getUserId()) {
            System.out.print("Enter new price (current: " + property.getPrice() + "): ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            property.setPrice(newPrice);
            propertyManager.updateProperty(property);
            System.out.println("Property updated successfully!");
        } else {
            System.out.println("Invalid property ID or you don't own this property.");
        }
    }

    private static void deleteProperty(Seller seller) {
        viewSellerProperties(seller);
        System.out.print("\nEnter property ID to delete: ");
        int propertyId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Property property = propertyManager.getProperty(propertyId);
        if (property != null && property.getSellerId() == seller.getUserId()) {
            propertyManager.deleteProperty(propertyId);
            seller.removeProperty(propertyId);
            System.out.println("Property deleted successfully!");
        } else {
            System.out.println("Invalid property ID or you don't own this property.");
        }
    }
} 