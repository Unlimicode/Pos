import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class MenuItem {
    private String product;
    private double price;
    private int quantity;

    public MenuItem(String product, double price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public MenuItem() {

    }

    public String getProduct(){
        return this.product;
    }
    public double getPrice(){
        return this.price;
    }
    public int getQuantity() {
        return this.quantity;
    }

}
class Inventory {
    private MenuItem[] Seeds;
    private MenuItem[] Fertilizers;
    private MenuItem[] Tools;
    public Inventory() {
    initialize_Inventory();
    }

    private void initialize_Inventory() {
        Seeds = new MenuItem[]{
                // Category 1: Seeds
                new MenuItem("Carrot Seeds", 5.99, 50),
                new MenuItem("Tomato Seeds", 4.49, 30),
                new MenuItem("Lemon Seeds", 3.99, 40),
                new MenuItem("Corn Seeds", 6.49, 20),
                new MenuItem("Bean Seeds", 4.99, 25)
        };
        Fertilizers = new MenuItem[]{
                // Category 2: Fertilizers
                new MenuItem("Organic Fertilizer", 8.99, 20),
                new MenuItem("Synthetic Fertilizer", 7.49, 15),
                new MenuItem("Liquid Fertilizer", 9.99, 25),
                new MenuItem("Granular Fertilizer", 6.99, 30),
                new MenuItem("Slow-Release Fertilizer", 10.49, 10)
        };
        Tools = new MenuItem[]{

                // Category 3: Tools
                new MenuItem("Shovel", 12.99, 15),
                new MenuItem("Hoe", 10.49, 12),
                new MenuItem("Rake", 8.99, 18),
                new MenuItem("Pruning Shears", 9.49, 20),
                new MenuItem("Watering Can", 7.99, 22)
        };

    }

    //Getter methods for items array
    public MenuItem[] getSeeds() {
        return this.Seeds;
    }

    public MenuItem[] getFertilizers() {
        return this.Fertilizers;
    }

    public MenuItem[] getTools() {
        return this.Tools;
    }


    public void displayMenu(MenuItem[] items) {
        System.out.println("Category: ");
        System.out.println("----------------------------");
        int i = 1;
        for (MenuItem item : items) {
            System.out.println(i + ". " + item.getProduct() + "\tPrice: $" + item.getPrice() + "\tQuantity: " + item.getQuantity());
            i++;
        }
        System.out.println();
    }

}

class Customer {
    private int categoryIndex;
    private int productIndex;
    private int amount;
    private Cart cart;

    public Customer() {
        this.cart = new Cart();
    }


    public int getCategoryIndex() {
        return this.categoryIndex;
    }

    public int getProductIndex() {
        return this.productIndex;
    }

    public int getAmount() {
        return this.amount;
    }

    public void Customer_Input(Inventory inventory, Cart cart) {
        Scanner scanner = new Scanner(System.in);
        //Cart cart = new Cart();

        boolean wantToAddItem = true;
        int categoryIndex = 0;
        int productIndex = 0;
        int amount = 0;
        do {
            // Display categories
            System.out.println("Select a category:");
            System.out.println("1. Seeds");
            System.out.println("2. Fertilizers");
            System.out.println("3. Tools");
            System.out.print("Enter the number corresponding to the category you want to browse: ");
            categoryIndex = scanner.nextInt();

            // Check if the selected category index is valid
            if (categoryIndex >= 1 && categoryIndex <= 3) {
                // Display products in the selected category
                MenuItem[] products = (categoryIndex == 1) ? inventory.getSeeds() :
                        (categoryIndex == 2) ? inventory.getFertilizers() :
                                inventory.getTools();
                System.out.println("Selected category: " + ((categoryIndex == 1) ? "Seeds" :
                        (categoryIndex == 2) ? "Fertilizers" :
                                "Tools"));
                inventory.displayMenu(products);
                // Get the selected product index
                System.out.print("Enter the number corresponding to the product you want to purchase: ");
                productIndex = scanner.nextInt();

                // Check if the selected product index is valid
                if (productIndex >= 1 && productIndex <= products.length) {
                    System.out.print("Enter the quantity you want to purchase: ");
                    amount = scanner.nextInt();
                    //Add the selected item to the transaction
                    cart.addItem(products[productIndex], amount);
                } else {
                    System.out.println("Invalid product choice.");
                }
                // Ask if the customer wants to add another item
                System.out.print("Do you want to add another item? (yes/no): ");
                String answer = scanner.next().toLowerCase();
                if (answer.equals("no")) {
                    wantToAddItem = false; // Set flag to false to exit the loop
                }

            }
        }while (wantToAddItem);
        System.out.println("Thank you for your order!");
        cart.displayOrder();
    }
}
class Transaction {
    private final int max_coupons = 15;
    private final int[] couponCodes = {
            123456, 234567, 345678, 456789, 567890,
            678901, 789012, 890123, 901234, 102345,
            987654, 876543, 765432, 654321, 543210
    };

    private boolean isValidCoupon(int Code) {
        // Check if the provided coupon code matches any of the predefined coupon codes
        for (int couponCode : couponCodes) {
            if (couponCode == Code) {
                return true;
            }
        }
        return false;
    }


    public double processOrder(Cart cart) {
        Scanner scanner = new Scanner(System.in);
        double total = cart.getTotal(); // Initialize total with the cart total
        String answer;
        System.out.println("Do you have a discount coupon? (yes/no)");
        answer = scanner.nextLine().toLowerCase();

        if (answer.equals("yes")) {
            System.out.print("Enter the coupon code: ");
            int couponCode = Integer.parseInt(scanner.nextLine());

            // Check if the entered coupon code is valid
            boolean isValidCoupon = isValidCoupon(couponCode);

            if (isValidCoupon) {
                if (cart.getItems().size() > 5) {
                    total *= 0.95; // Apply 5% discount for more than 5 items
                    System.out.println("5% discount applied for purchasing more than 5 items!");
                } else {
                    total *= 0.85; // Apply 15% discount for a single item
                    System.out.println("15% discount applied successfully!");
                }
            }
        }
        scanner.close();
        return total; // Return the total after applying discounts
    }
}

class Cart {
    private List<MenuItem> items;
    private static final int max_items = 7;
    public Cart() {
        items = new ArrayList<>();
    }
    public List<MenuItem> getItems() {
        return items;
    }
    public double getTotal() {
        double total = 0.0;
        for (MenuItem item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
    public void addItem(MenuItem item, int quantity) {
        if (item != null && quantity > 0 && items.size() <  max_items) {
            boolean itemExists = false;
            for (MenuItem existingItem : items) {
                if (existingItem.getProduct().equals(item.getProduct())) {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    itemExists = true;
                    break;
                }
            }
            if (!itemExists) {
                items.add(new MenuItem(item.getProduct(), item.getPrice(), quantity));
            }
            System.out.println("Item added to cart: " + item.getProduct() + " x" + quantity);
        } else {
            System.out.println("Invalid item or quantity provided.");
        }
    }
    public void displayOrder() {
        System.out.println();
        System.out.println("Your Receipt: \n---------------------------------");
        DecimalFormat df = new DecimalFormat("#.00"); // Create a new DecimalFormat object with two decimal places

        for (MenuItem item : items) {
            System.out.println(item.getProduct() + "\t$" + item.getPrice() + "\tQuantity: " + item.getQuantity());
        }
        System.out.println("Total: $" + df.format(getTotal()));
    }

}
