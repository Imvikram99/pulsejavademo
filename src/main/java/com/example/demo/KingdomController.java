package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

import java.util.*;

@RestController
@RequestMapping("/kingdom")
public class KingdomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KingdomController.class);

    @PostMapping("/knights/enlist")
    public ResponseEntity<Map<String, String>> enlistKnight(@RequestBody Map<String, String> knightDetails) {
        knightDetails.put("status", "Knight enlisted successfully!");
        knightDetails.put("changed","1");
        return new ResponseEntity<>(knightDetails, HttpStatus.CREATED);
    }

    @GetMapping("/castles/r")
    public ResponseEntity<Map<String, Object>> listCastles(@RequestParam Map<String, String> filters) {
        Map<String, Object> response = new HashMap<>();
        response.put("castleNames", Arrays.asList("Castle Black", "Dragonstone", "Winterfell"));
        response.put("filters", filters);
        response.put("status", "Castles fetched successfully!");
        response.put("changed","1");

        // Occasionally return a different status code
        if (new Random().nextBoolean()) {
            return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT); // 206 Partial Content
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/dragons")
    public ResponseEntity<List<String>> listDragons() {
        List<String> dragons = Arrays.asList("Drogon", "Rhaegal", "Viserion");
        return new ResponseEntity<>(dragons, HttpStatus.OK);
    }

    @PostMapping("/quests/start")
    public ResponseEntity<Map<String, String>> startQuest(@RequestBody Map<String, String> questDetails) {
        questDetails.put("status", "Quest started!");
        questDetails.put("changedApi","1");
        return new ResponseEntity<>(questDetails, HttpStatus.ACCEPTED); // 202 Accepted
    }

    @PutMapping("/quests/{questId}/update")
    public ResponseEntity<Map<String, String>> updateQuest(@PathVariable Long questId, @RequestBody Map<String, String> questUpdates) {
        questUpdates.put("statusChanged", "Quest updated!");
        questUpdates.put("changedApi","1");
        return new ResponseEntity<>(questUpdates, HttpStatus.OK);
    }

    @DeleteMapping("/knights/{knightId}/banish")
    public ResponseEntity<Map<String, Object>> banishKnight(@PathVariable Long knightId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Knight with ID " + knightId + " banished from the kingdom!");
        response.put("banishChanged",1);
        return new ResponseEntity<>(response, HttpStatus.GONE); // 410 Gone
    }

    @GetMapping("/quests/{questId}/status")
    public ResponseEntity<Map<String, Object>> getQuestStatus(@PathVariable Long questId) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Quest in progress!");
        response.put("changed", 1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/castles/{castleId}")
    public ResponseEntity<Map<String, Object>> getCastleDetails(@PathVariable Long castleId, @RequestHeader(name = "desired-status", required = false) Integer desiredStatus) {
        Map<String, Object> response = new HashMap<>();

        if (desiredStatus != null && desiredStatus == 500) {
            response.put("error", "Failed to fetch castle details!");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("castleName", "Castle Black");
        response.put("location", "North");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/quests/assign")
    public ResponseEntity<Map<String, String>> assignQuest(@RequestBody Map<String, String> questDetails, @RequestParam(name = "status", required = false) String status) {
        if ("bad_request".equals(status) && (!questDetails.containsKey("knightName") || !questDetails.containsKey("questName"))) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Missing required quest details!");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        questDetails.put("status", "Quest assigned successfully!");
        return new ResponseEntity<>(questDetails, HttpStatus.CREATED);
    }

}
