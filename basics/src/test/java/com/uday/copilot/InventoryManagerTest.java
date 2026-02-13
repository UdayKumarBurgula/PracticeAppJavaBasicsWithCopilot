package com.uday.copilot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class InventoryManagerTest {

    private InventoryManager manager;

    @Before
    public void setUp() {
        manager = new InventoryManager();
    }

    @Test
    public void testAddAndGetProduct() {
        Product p1 = new Product(1, "Laptop", 999.99);
        manager.addProduct(p1);

        Product fetched = manager.getProduct(1);
        assertNotNull(fetched);
        assertEquals(1, fetched.getId());
        assertEquals("Laptop", fetched.getName());
        assertEquals(999.99, fetched.getPrice(), 0.0001);
    }

    @Test
    public void testRemoveProduct() {
        Product p1 = new Product(1, "Laptop", 999.99);
        Product p2 = new Product(2, "Phone", 499.99);
        manager.addProduct(p1);
        manager.addProduct(p2);

        manager.removeProduct(2);
        assertNull(manager.getProduct(2));
        assertEquals(1, manager.listProducts().size());
    }

    @Test
    public void testListProductsReturnsCopy() {
        Product p1 = new Product(1, "Laptop", 999.99);
        Product p2 = new Product(2, "Phone", 499.99);
        manager.addProduct(p1);
        manager.addProduct(p2);

        List<Product> snapshot = manager.listProducts();
        int originalSize = snapshot.size();
        // modify returned list
        snapshot.remove(0);
        // internal inventory should be unaffected
        assertEquals(originalSize, manager.listProducts().size());
    }

    @Test
    public void testUpdateProductPrice() {
        Product p1 = new Product(1, "Laptop", 999.99);
        manager.addProduct(p1);

        manager.updateProductPrice(1, 899.99);
        Product updated = manager.getProduct(1);
        assertNotNull(updated);
        assertEquals(1, updated.getId());
        assertEquals("Laptop", updated.getName());
        assertEquals(899.99, updated.getPrice(), 0.0001);
    }

    @Test
    public void testUpdateNonExistentIdDoesNothing() {
        Product p1 = new Product(1, "Laptop", 999.99);
        manager.addProduct(p1);
        int before = manager.listProducts().size();

        manager.updateProductPrice(99, 5.0);
        assertEquals(before, manager.listProducts().size());
        // ensure existing product unchanged
        assertEquals(999.99, manager.getProduct(1).getPrice(), 0.0001);
    }

    @Test
    public void testAddDuplicateIdReplaces() {
        Product original = new Product(1, "Original", 10.0);
        Product replacement = new Product(1, "Replacement", 20.0);
        manager.addProduct(original);
        manager.addProduct(replacement);

        Product fetched = manager.getProduct(1);
        assertNotNull(fetched);
        assertEquals("Replacement", fetched.getName());
        assertEquals(20.0, fetched.getPrice(), 0.0001);
    }

    @Test
    public void testGetNonExistentReturnsNull() {
        assertNull(manager.getProduct(999));
    }
}