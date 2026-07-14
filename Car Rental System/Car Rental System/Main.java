package Car_rental_system;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private int carId;
    private String modelName;
    private String brand;
    private double pricePerDay;
    private boolean available;

    // Constructor
    public Car(int carId, String brand, String modelName, double pricePerDay) {
        this.carId       = carId;
        this.brand       = brand;
        this.modelName   = modelName;
        this.pricePerDay = pricePerDay;
        this.available   = true; // default available
    }

    // Getters
    public int getCarId()       { return carId; }
    public String getModelName()   { return modelName; }
    public String getBrand()       { return brand; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable()   { return available; }

    // Setters
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    // Rent and Return car
    public void rentCar()   { this.available = false; }
    public void returnCar() { this.available = true; }

    @Override
    public String toString() {
        String status = available ? "Available" : "Rented";
        return String.format("  %-5d %-12s %-15s Rs.%-10.0f %s",
                carId, brand, modelName, pricePerDay, status);
    }
}


// ── RENTAL RECORD CLASS ───────────────────────────────
// Stores info about who rented which car
class RentalRecord {

    private String customerName;
    private int carId;
    private String carModel;
    private int days;
    private double totalCost;

    public RentalRecord(String customerName, int carId, String carModel, int days, double totalCost) {
        this.customerName = customerName;
        this.carId = carId;
        this.carModel = carModel;
        this.days = days;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("  %-20s %-5d %-15s %-8d Rs.%.0f",
                customerName, carId, carModel, days, totalCost);
    }
}


// ── CAR RENTAL SYSTEM CLASS (Main Logic) ─────────────
class CarRentalSystem {

    private List<Car> carList = new ArrayList<>();
    private List<RentalRecord> rentalHistory = new ArrayList<>(); // stores all past rentals

    // ── Add Car ───────────────────────────────────────
    public void addCar(Car car) {
        // Check if car ID already exists
        for (Car c : carList) {
            if (c.getCarId() == car.getCarId()) {
                System.out.println("\n  [ERROR] Car with ID " + car.getCarId() + " already exists!");
                return;
            }
        }
        carList.add(car);
        System.out.println("\n  [SUCCESS] Car added — " + car.getBrand() + " " + car.getModelName());
    }

    // ── Remove Car ────────────────────────────────────
    public void removeCar(int id) {
        Car car = findCarById(id);
        if (car == null) {
            System.out.println("\n  [ERROR] Car not found with ID: " + id);
            return;
        }
        if (!car.isAvailable()) {
            System.out.println("\n  [ERROR] Cannot remove — car is currently rented out!");
            return;
        }
        carList.remove(car);
        System.out.println("\n  [SUCCESS] Car removed successfully.");
    }

    // ── Rent Car ──────────────────────────────────────
    public void rentCar(int id, String customerName, int days) {
        Car car = findCarById(id);

        if (car == null) {
            System.out.println("\n  [ERROR] Car not found with ID: " + id);
            return;
        }
        if (!car.isAvailable()) {
            System.out.println("\n  [ERROR] Sorry! This car is already rented.");
            return;
        }
        if (days <= 0) {
            System.out.println("\n  [ERROR] Rental days must be at least 1.");
            return;
        }

        // Process rental
        car.rentCar();
        double totalCost = car.getPricePerDay() * days;

        // Save to rental history
        rentalHistory.add(new RentalRecord(customerName, car.getCarId(), car.getModelName(), days, totalCost));

        // Print slip
        printRentalSlip(customerName, car, days, totalCost);
    }

    // ── Return Car ────────────────────────────────────
    public void returnCar(int id) {
        Car car = findCarById(id);

        if (car == null) {
            System.out.println("\n  [ERROR] Car not found with ID: " + id);
            return;
        }
        if (car.isAvailable()) {
            System.out.println("\n  [ERROR] This car was not rented.");
            return;
        }

        car.returnCar();
        System.out.println("\n  [SUCCESS] Car returned successfully!");
        System.out.println("  " + car.getBrand() + " " + car.getModelName() + " is now available.");
    }

    // ── Update Price ──────────────────────────────────
    public void updatePrice(int id, double newPrice) {
        Car car = findCarById(id);
        if (car == null) {
            System.out.println("\n  [ERROR] Car not found.");
            return;
        }
        if (newPrice <= 0) {
            System.out.println("\n  [ERROR] Price must be greater than 0.");
            return;
        }
        car.setPricePerDay(newPrice);
        System.out.println("\n  [SUCCESS] Price updated to Rs." + newPrice + " per day.");
    }

    // ── Display All Cars ──────────────────────────────
    public void displayAllCars() {
        if (carList.isEmpty()) {
            System.out.println("\n  No cars in the system.");
            return;
        }

        System.out.println("\n  " + "─".repeat(60));
        System.out.printf("  %-5s %-12s %-15s %-12s %s%n", "ID", "Brand", "Model", "Price/Day", "Status");
        System.out.println("  " + "─".repeat(60));
        for (Car c : carList) {
            System.out.println(c);
        }
        System.out.println("  " + "─".repeat(60));
    }

    // ── Display Available Cars Only ───────────────────
    public void displayAvailableCars() {
        System.out.println("\n  --- Available Cars ---");
        boolean found = false;
        for (Car c : carList) {
            if (c.isAvailable()) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("  No cars available right now.");
    }

    // ── Rental History ────────────────────────────────
    public void displayRentalHistory() {
        if (rentalHistory.isEmpty()) {
            System.out.println("\n  No rental history yet.");
            return;
        }
        System.out.println("\n  " + "─".repeat(65));
        System.out.printf("  %-20s %-5s %-15s %-8s %s%n", "Customer", "CarID", "Model", "Days", "Total Cost");
        System.out.println("  " + "─".repeat(65));
        for (RentalRecord r : rentalHistory) {
            System.out.println(r);
        }
        System.out.println("  " + "─".repeat(65));
    }

    // ── Search by ID ──────────────────────────────────
    public void searchById(int id) {
        Car car = findCarById(id);
        if (car != null) {
            System.out.println("\n  Car found:");
            System.out.println(car);
        } else {
            System.out.println("\n  [ERROR] No car found with ID: " + id);
        }
    }

    // ── Search by Model ───────────────────────────────
    public void searchByModel(String model) {
        List<Car> found = new ArrayList<>();
        for (Car c : carList) {
            if (c.getModelName().toLowerCase().contains(model.toLowerCase())) {
                found.add(c);
            }
        }
        if (found.isEmpty()) {
            System.out.println("\n  [ERROR] No cars found for: " + model);
        } else {
            System.out.println("\n  Cars found:");
            for (Car c : found) System.out.println(c);
        }
    }

    // ── Helper: Find car by ID ────────────────────────
    private Car findCarById(int id) {
        for (Car c : carList) {
            if (c.getCarId() == id) return c;
        }
        return null;
    }

    // ── Rental Slip ───────────────────────────────────
    private void printRentalSlip(String customer, Car car, int days, double total) {
        System.out.println("\n  ╔══════════════════════════════╗");
        System.out.println("  ║         RENTAL SLIP          ║");
        System.out.println("  ╠══════════════════════════════╣");
        System.out.printf ("  ║  Customer : %-17s║%n", customer);
        System.out.printf ("  ║  Car ID   : %-17d║%n", car.getCarId());
        System.out.printf ("  ║  Car      : %-17s║%n", car.getBrand() + " " + car.getModelName());
        System.out.printf ("  ║  Days     : %-17d║%n", days);
        System.out.printf ("  ║  Rate     : Rs.%-14.0f║%n", car.getPricePerDay());
        System.out.println("  ╠══════════════════════════════╣");
        System.out.printf ("  ║  TOTAL    : Rs.%-14.0f║%n", total);
        System.out.println("  ╚══════════════════════════════╝");
    }
}


// ── MAIN CLASS ────────────────────────────────────────
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        CarRentalSystem system = new CarRentalSystem();

        // Sample data
        system.addCar(new Car(101, "Maruti", "Swift",    1500));
        system.addCar(new Car(102, "Hyundai","Creta",    2500));
        system.addCar(new Car(103, "Tata",   "Nexon",    2000));
        system.addCar(new Car(104, "Honda",  "City",     2200));
        system.addCar(new Car(105, "Toyota", "Fortuner", 5000));

        System.out.println("\n  ================================");
        System.out.println("      CAR RENTAL SYSTEM v1.0     ");
        System.out.println("  ================================");

        int choice;

        do {
            printMenu();

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                // Exception handling - agar user number nahi daalta
                System.out.println("\n  [ERROR] Please enter a valid number!");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1 -> addCarMenu(system);
                case 2 -> removeCarMenu(system);
                case 3 -> system.displayAllCars();
                case 4 -> system.displayAvailableCars();
                case 5 -> searchMenu(system);
                case 6 -> rentCarMenu(system);
                case 7 -> returnCarMenu(system);
                case 8 -> updatePriceMenu(system);
                case 9 -> system.displayRentalHistory();
                case 0 -> System.out.println("\n  Thank you! Visit again. Goodbye!\n");
                default -> System.out.println("\n  [ERROR] Invalid choice. Enter 0-9.");
            }

        } while (choice != 0);

        sc.close();
    }

    // ── Menu ──────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n  ┌─────────────────────────────┐");
        System.out.println("  │         MAIN MENU           │");
        System.out.println("  ├─────────────────────────────┤");
        System.out.println("  │  1. Add Car                 │");
        System.out.println("  │  2. Remove Car              │");
        System.out.println("  │  3. View All Cars           │");
        System.out.println("  │  4. View Available Cars     │");
        System.out.println("  │  5. Search Car              │");
        System.out.println("  │  6. Rent a Car              │");
        System.out.println("  │  7. Return a Car            │");
        System.out.println("  │  8. Update Car Price        │");
        System.out.println("  │  9. Rental History          │");
        System.out.println("  │  0. Exit                    │");
        System.out.println("  └─────────────────────────────┘");
        System.out.print("  Enter choice: ");
    }

    // ── Input Handlers ────────────────────────────────
    static void addCarMenu(CarRentalSystem system) {
        try {
            System.out.print("\n  Enter Car ID     : ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("  Enter Brand      : ");
            String brand = sc.nextLine().trim();

            System.out.print("  Enter Model Name : ");
            String model = sc.nextLine().trim();

            System.out.print("  Enter Price/Day  : Rs.");
            double price = Double.parseDouble(sc.nextLine().trim());

            system.addCar(new Car(id, brand, model, price));

        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid input! ID and Price must be numbers.");
        }
    }

    static void removeCarMenu(CarRentalSystem system) {
        try {
            System.out.print("\n  Enter Car ID to remove: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            system.removeCar(id);
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid ID!");
        }
    }

    static void searchMenu(CarRentalSystem system) {
        System.out.println("\n  Search by: 1. Car ID   2. Model Name");
        System.out.print("  Enter choice: ");
        String opt = sc.nextLine().trim();

        if (opt.equals("1")) {
            try {
                System.out.print("  Enter Car ID: ");
                int id = Integer.parseInt(sc.nextLine().trim());
                system.searchById(id);
            } catch (NumberFormatException e) {
                System.out.println("\n  [ERROR] Invalid ID!");
            }
        } else if (opt.equals("2")) {
            System.out.print("  Enter Model Name: ");
            String model = sc.nextLine().trim();
            system.searchByModel(model);
        } else {
            System.out.println("\n  [ERROR] Invalid option.");
        }
    }

    static void rentCarMenu(CarRentalSystem system) {
        try {
            system.displayAvailableCars();
            System.out.print("\n  Enter Car ID      : ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("  Enter Your Name   : ");
            String name = sc.nextLine().trim();

            System.out.print("  Enter Rental Days : ");
            int days = Integer.parseInt(sc.nextLine().trim());

            system.rentCar(id, name, days);

        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid input!");
        }
    }

    static void returnCarMenu(CarRentalSystem system) {
        try {
            System.out.print("\n  Enter Car ID to return: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            system.returnCar(id);
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid ID!");
        }
    }

    static void updatePriceMenu(CarRentalSystem system) {
        try {
            System.out.print("\n  Enter Car ID    : ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("  Enter New Price : Rs.");
            double price = Double.parseDouble(sc.nextLine().trim());
            system.updatePrice(id, price);
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid input!");
        }
    }
}
