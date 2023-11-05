package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RestController
//@RequestMapping("/api")
public class ItemController1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController1.class);

    // Single PathVariable
    @PutMapping("/items/{id}")
    public ResponseEntity<Map<String,String>> updateItem(@PathVariable Long id, @RequestParam String name) {
        LOGGER.info("Updating item with ID: {} and name: {}", id, name);
        Map<String,String>map = new HashMap<>();
        map.put("response","Single PathVariable - PUT");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // Multiple PathVariables

    // Multiple Query Parameters
    @PatchMapping("/items")
    public ResponseEntity<String> patchItem(@RequestParam String name, @RequestParam int quantity) {
        LOGGER.info("Patching item with name: {} and quantity: {}", name, quantity);
        return new ResponseEntity<>("Multiple Query Params - PATCH", HttpStatus.OK);
    }

    // Both PathVariable and Query Params
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id, @RequestParam(required = false) String reason, @RequestParam(required = false) String force) {
        LOGGER.info("Deleting item with ID: {}, reason: {}, force: {}", id, reason, force);
        return new ResponseEntity<>("Both PathVariable and Query Params - DELETE", HttpStatus.OK);
    }

    // All Combinations
    @PutMapping("/items/{id}/details/{detailId}")
    public ResponseEntity<String> updateComplexItem(@PathVariable Long id, @PathVariable Long detailId, @RequestParam Map<String, String> queryParams) {
        LOGGER.info("Updating complex item with ID: {}, detailId: {} and queryParams: {}", id, detailId, queryParams);
        return new ResponseEntity<>("All Combinations - PUT", HttpStatus.OK);
    }
}
