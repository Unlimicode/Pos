import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Customer customer = new Customer();
        Cart cart = new Cart();
        Transaction transaction = new Transaction();
        // Customer input
        customer.Customer_Input(inventory, cart);
    }
}
