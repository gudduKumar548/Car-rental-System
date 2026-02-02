import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Car class
class Car {
    private int carId;
    private String modelName;
    private double pricePerDay;
    private boolean available;

    public Car(int carId, String modelName, double pricePerDay) {
        this.carId = carId;
        this.modelName = modelName;
        this.pricePerDay = pricePerDay;
        this.available = true;
    }

    public int getCarId() {
        return carId;
    }

    public String getModelName() {
        return modelName;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void rentCar() {
        this.available = false;
    }

    public void returnCar() {
        this.available = true;
    }

    @Override
    public String toString() {
        return String.format("%-5d %-15s %-10.2f %-10s",
                carId, modelName, pricePerDay, available ? "Available" : "Rented");
    }
}

// Car Rental System
class CarRentalSystem {
    private List<Car> carList = new ArrayList<>();

    public void addCar(Car car) {
        for (Car c : carList) {
            if (c.getCarId() == car.getCarId()) {
                System.out.println("❌ Car with this ID already exists!");
                return;
            }
        }
        carList.add(car);
        System.out.println("✅ Car added successfully.");
    }

    public void removeCar(int id) {
        Car toRemove = findCarById(id);
        if (toRemove != null) {
            carList.remove(toRemove);
            System.out.println("✅ Car removed successfully.");
        } else {
            System.out.println("❌ Car not found.");
        }
    }

    public Car findCarById(int id) {
        for (Car c : carList) {
            if (c.getCarId() == id) return c;
        }
        return null;
    }

    public List<Car> findCarByModel(String model) {
        List<Car> found = new ArrayList<>();
        for (Car c : carList) {
            if (c.getModelName().equalsIgnoreCase(model)) {
                found.add(c);
            }
        }
        return found;
    }

    // Rent car and generate rental slip
    public void rentCar(int id, String customerName, int days) {
        Car c = findCarById(id);
        if (c != null) {
            if (c.isAvailable()) {
                c.rentCar();
                double totalCost = c.getPricePerDay() * days;
                generateRentalSlip(customerName, c, days, totalCost);
            } else {
                System.out.println("❌ Car is already rented.");
            }
        } else {
            System.out.println("❌ Car not found.");
        }
    }

    // Return car
    public void returnCar(int id) {
        Car c = findCarById(id);
        if (c != null) {
            if (!c.isAvailable()) {
                c.returnCar();
                System.out.println("✅ Car returned successfully.");
            } else {
                System.out.println("❌ Car is already available.");
            }
        } else {
            System.out.println("❌ Car not found.");
        }
    }

    // Update price
    public void updateCarPrice(int id, double newPrice) {
        Car c = findCarById(id);
        if (c != null) {
            c.setPricePerDay(newPrice);
            System.out.println("✅ Car price updated successfully.");
        } else {
            System.out.println("❌ Car not found.");
        }
    }

    // Display cars
    public void displayCars() {
        if (carList.isEmpty()) {
            System.out.println("📂 No cars available in the system.");
            return;
        }
        System.out.printf("%-5s %-15s %-10s %-10s%n", "ID", "Model", "Price/Day", "Status");
        System.out.println("------------------------------------------------");
        for (Car c : carList) {
            System.out.println(c);
        }
        System.out.println("------------------------------------------------");
    }

    // Generate rental slip
    private void generateRentalSlip(String customerName, Car car, int days, double totalCost) {
        System.out.println("\n===== RENTAL SLIP =====");
        System.out.println("Customer Name: " + customerName);
        System.out.println("Car ID: " + car.getCarId());
        System.out.println("Car Model: " + car.getModelName());
        System.out.println("Price per Day: " + car.getPricePerDay());
        System.out.println("Rental Duration: " + days + " days");
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Status: Rented");
        System.out.println("=======================\n");
    }
}

// Main Menu
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CarRentalSystem rental = new CarRentalSystem();
        int choice;

        do {
            System.out.println("\n==== Car Rental System Menu ====");
            System.out.println("1. Add Car");
            System.out.println("2. Remove Car");
            System.out.println("3. View All Cars");
            System.out.println("4. Search Car by ID");
            System.out.println("5. Search Car by Model");
            System.out.println("6. Rent a Car");
            System.out.println("7. Return a Car");
            System.out.println("8. Update Car Price");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Car ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Model Name: ");
                    String model = sc.nextLine();
                    System.out.print("Enter Price per Day: ");
                    double price = sc.nextDouble();
                    rental.addCar(new Car(id, model, price));
                }
                case 2 -> {
                    System.out.print("Enter Car ID to remove: ");
                    int id = sc.nextInt();
                    rental.removeCar(id);
                }
                case 3 -> rental.displayCars();
                case 4 -> {
                    System.out.print("Enter Car ID to search: ");
                    int id = sc.nextInt();
                    Car c = rental.findCarById(id);
                    if (c != null) System.out.println(c);
                    else System.out.println("❌ Car not found.");
                }
                case 5 -> {
                    System.out.print("Enter Model Name to search: ");
                    String model = sc.nextLine();
                    List<Car> found = rental.findCarByModel(model);
                    if (!found.isEmpty()) found.forEach(System.out::println);
                    else System.out.println("❌ No cars found with that model.");
                }
                case 6 -> {
                    System.out.print("Enter Car ID to rent: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customer = sc.nextLine();
                    System.out.print("Enter Rental Duration (days): ");
                    int days = sc.nextInt();
                    rental.rentCar(id, customer, days);
                }
                case 7 -> {
                    System.out.print("Enter Car ID to return: ");
                    int id = sc.nextInt();
                    rental.returnCar(id);
                }
                case 8 -> {
                    System.out.print("Enter Car ID to update price: ");
                    int id = sc.nextInt();
                    System.out.print("Enter new Price per Day: ");
                    double price = sc.nextDouble();
                    rental.updateCarPrice(id, price);
                }
                case 0 -> System.out.println("👋 Exiting Car Rental System...");
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 0);

        sc.close();
    }
}
