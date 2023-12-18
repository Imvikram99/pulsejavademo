package com.example.demo;

import com.example.demo.dto.Product;
import com.example.demo.dto.Transaction;
import com.example.demo.dto.User;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/ecommerce")
public class EcommerceController {

    private final AtomicInteger userIdCounter = new AtomicInteger(0);
    private final AtomicInteger transactionIdCounter = new AtomicInteger(0);
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Product> products = new HashMap<>();
    private final Map<Integer, Transaction> transactions = new HashMap<>();

    private final Map<String, Integer> usernameToIdMap = new HashMap<>();
    private final Map<String, Integer> productNameToIdMap = new HashMap<>();


    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (usernameToIdMap.containsKey(user.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Username already exists"));
        }

        int userId = userIdCounter.incrementAndGet();
        user.setId(userId);
        users.put(userId, user);
        usernameToIdMap.put(user.getName(), userId);

        return ResponseEntity.ok(Collections.singletonMap("userId", userId));
    }

    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        if (productNameToIdMap.containsKey(product.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Product name already exists"));
        }

        int productId = transactionIdCounter.incrementAndGet();
        product.setId(productId);
        products.put(productId, product);
        productNameToIdMap.put(product.getName(), productId);

        return ResponseEntity.ok(Collections.singletonMap("productId", productId));
    }

    @PostMapping("/transaction/deposit")
    public ResponseEntity<?> deposit(@RequestParam int userId, @RequestParam double amount) {
        User user = users.get(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Updating user's balance
        user.setBalance(user.getBalance() + amount);

        // Recording the transaction
        int transactionId = transactionIdCounter.incrementAndGet();
        Transaction transaction = new Transaction(transactionId, userId, amount, "DEPOSIT");
        transactions.put(transactionId, transaction);

        return ResponseEntity.ok(Collections.singletonMap("txnId", transactionId));
    }

    @PostMapping("/transaction/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam int userId, @RequestParam double amount) {
        User user = users.get(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Check if the user has enough balance
        if (user.getBalance() < amount) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }

        // Updating user's balance
        user.setBalance(user.getBalance() - amount);

        // Recording the transaction
        int transactionId = transactionIdCounter.incrementAndGet();
        Transaction transaction = new Transaction(transactionId, userId, amount, "WITHDRAW");
        transactions.put(transactionId, transaction);

        return ResponseEntity.ok(Collections.singletonMap("txnId", transactionId));
    }


    @PutMapping("/user/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User updatedUser) {
        if (users.containsKey(userId)) {
            User user = users.get(userId);
            user.setName(updatedUser.getName());
            user.setBalance(updatedUser.getBalance());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        if (users.remove(userId) != null) {
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/product/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        if (products.remove(productId) != null) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = users.get(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(users.values());
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        Product product = products.get(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/products")
    public ResponseEntity<Collection<Product>> getAllProducts() {
        return ResponseEntity.ok(products.values());
    }
}
