package com.uday.copilot;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

// inventory manager class that uses the Map and adds, removes, lists and updates products
public class InventoryManager {
    private final Map<Integer, Product> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public void addProduct(Product product) {
        inventory.put(product.getId(), product);
    }

    public void removeProduct(int id) {
        inventory.remove(id);
    }

    public Product getProduct(int id) {
        return inventory.get(id);
    }

    public List<Product> listProducts() {
        return new ArrayList<>(inventory.values());
    }
    
    // Update product price
    public void updateProductPrice(int id, double newPrice) {
        Product product = inventory.get(id);
        if (product != null) {
            // Create a new Product with the updated price and replace the old one
            Product updatedProduct = new Product(product.getId(), product.getName(), newPrice);
            inventory.put(id, updatedProduct);
        }
    }


    //main method to test the inventory manager 
    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        
        Product product1 = new Product(1, "Laptop", 999.99);
        Product product2 = new Product(2, "Smartphone", 499.99);
        
        manager.addProduct(product1);
        manager.addProduct(product2);
        
        System.out.println("Products in inventory:");
        for (Product p : manager.listProducts()) {
            System.out.println(p.getName() + " - $" + p.getPrice());
        }
        
        manager.updateProductPrice(1, 899.99);
        System.out.println("\nUpdated price for Laptop:");
        System.out.println(manager.getProduct(1).getName() + " - $" + manager.getProduct(1).getPrice());
        
        manager.removeProduct(2);
        System.out.println("\nProducts in inventory after removing Smartphone:");
        for (Product p : manager.listProducts()) {
            System.out.println(p.getName() + " - $" + p.getPrice());
        }
    }

}
