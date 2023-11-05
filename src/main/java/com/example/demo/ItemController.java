package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//@RestController
//@RequestMapping("/api/v1")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @PutMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> updateItem(@PathVariable Long id, @RequestParam String name) {
        LOGGER.info("Updating item with ID: {} and name: {}", id, name);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Single PathVariable - PUT");
        response.put("id", id);
        response.put("name", name);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/items")
    public ResponseEntity<Map<String, Object>> patchItem(@RequestParam String name, @RequestParam int quantity) {
        LOGGER.info("Patching item with name: {} and quantity: {}", name, quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Multiple Query Params - PATCH");
        response.put("name", name);
        response.put("quantity", quantity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> deleteItem(@PathVariable Long id, @RequestParam(required = false) String reason, @RequestParam(required = false) String force) {
        LOGGER.info("Deleting item with ID: {}, reason: {}, force: {}", id, reason, force);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Both PathVariable and Query Params - DELETE");
        response.put("id", id);
        response.put("reason", reason);
        response.put("force", force);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/items/{id}/details/{detailId}")
    public ResponseEntity<Map<String, Object>> updateComplexItem(@PathVariable Long id, @PathVariable Long detailId, @RequestParam Map<String, String> queryParams) {
        LOGGER.info("Updating complex item with ID: {}, detailId: {} and queryParams: {}", id, detailId, queryParams);

        Map<String, Object> nestedJson = new HashMap<>();
        nestedJson.put("detailId", detailId);
        nestedJson.put("queryParams", queryParams);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "All CombinKKations - PUT");
        response.put("id", id);
        response.put("details", nestedJson);  // Nested JSON

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/consumeComplexItem")
    public ResponseEntity<Map<String, Object>> consumeComplexItem(@RequestBody Map<String, Object> input) {
        LOGGER.info("Received complex item: {}", input);
        input.put("output","Successfully consumed complex item");
        return new ResponseEntity<>(input, HttpStatus.OK);
    }
    @PostMapping("/consumeComplexItem2")
    public ResponseEntity<Map<String, Object>> consumeComplexItem2(@RequestBody Map<String, Object> input) {
        LOGGER.info("Received complex item: {}", input);
        input.put("output","Successfully consumed complex item");
        return new ResponseEntity<>(input, HttpStatus.OK);
    }
}
