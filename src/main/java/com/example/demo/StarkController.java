package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@RestController
@RequestMapping("/stark")
public class StarkController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StarkController.class);

    @PostMapping("/users/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody Map<String, String> userDetails) {
        userDetails.put("status", "User registered successfully!");
        return new ResponseEntity<>(userDetails, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        credentials.put("status", "User logged in changed successfully!");
        return new ResponseEntity<>(credentials, HttpStatus.INTERNAL_SERVER_ERROR);
    }
enum d{
        d1,d2
    };
    @GetMapping("/suits")
    public ResponseEntity<Map<String, Object>> listSuits(@RequestParam Map<String, String> filters) {
        Map<String, Object> response = new HashMap<>();

        // Adding various data types to the response
        response.put("suitPrice", 9999.99); // double
        response.put("suitCount", 50); // int
        response.put("isAvailable", false); // boolean
        response.put("suitColor",d.d1);
        response.put("suitColor3",d.d1);
        response.put("suitColor4",d.d1);
        //response.put("suitModels", Arrays.asList("changed Mark I", "changed Mark II", "Mark III")); // array

        // Adding the filters and status to the response
        response.put("filters", filters);
        response.put("status", "Suits fetched successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/suits")
    public ResponseEntity<Map<String, Object>> addSuit(@RequestBody Map<String, Object> suitDetails) {
        suitDetails.put("status", "Suit added successfully!");
        suitDetails.put("changed", 1);
        return new ResponseEntity<>(suitDetails, HttpStatus.CREATED);
    }
    @PostMapping("/suits/test")
    public ResponseEntity<String> addSuit(@RequestBody String suitDetails) {
        return new ResponseEntity<>(suitDetails, HttpStatus.CREATED);
    }

    @PutMapping("/suits/{suitId}/details/{detailId}")
    public ResponseEntity<Map<String, String>> updateSuitDetails(@PathVariable Long suitId, @PathVariable Long detailId, @RequestParam Map<String, String> queryParams) {
        queryParams.put("status", "Suit details updated successfully!");
        return new ResponseEntity<>(queryParams, HttpStatus.OK);
    }

    @PatchMapping("/suits/{suitId}")
    public ResponseEntity<Map<String, String>> patchSuitDetails(@PathVariable Long suitId, @RequestBody Map<String, String> partialUpdates) {
        partialUpdates.put("status", "Suit details patched successfully!");
        partialUpdates.put("patchSuitDetailsChanged", "10");
        return new ResponseEntity<>(partialUpdates, HttpStatus.OK);
    }

    @DeleteMapping("/suits/{suitId}")
    public ResponseEntity<Map<String, String>> deleteSuit(@PathVariable Long suitId) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Suit with ID " + suitId + " deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody Map<String, String> cartDetails) {
        cartDetails.put("status", "Suit added to cart successfully!");
        return new ResponseEntity<>(cartDetails, HttpStatus.CREATED);
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<Map<String, String>> checkout(@RequestBody Map<String, String> paymentDetails) {
        paymentDetails.put("status", "Checkout successful!");
        return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
    }

    @GetMapping("/suits/{suitId}/ai")
    public ResponseEntity<Map<String, String>> getAIDetails(@PathVariable Long suitId, @RequestParam Map<String, String> filters) {
        filters.put("status", "AI details fetched successfully!");
        return new ResponseEntity<>(filters, HttpStatus.OK);
    }

    @PutMapping("/suits/{suitId}/ai/{aiId}")
    public ResponseEntity<Map<String, String>> updateAIDetails(@PathVariable Long suitId, @PathVariable Long aiId, @RequestParam Map<String, String> queryParams) {
        queryParams.put("status", "AI details updated successfully!");
        return new ResponseEntity<>(queryParams, HttpStatus.OK);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody Map<String, String> tokenDetails) {
        tokenDetails.put("status", "User logged out successfully!");
        return new ResponseEntity<>(tokenDetails, HttpStatus.OK);
    }

}
