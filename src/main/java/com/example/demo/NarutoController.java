package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/naruto")
public class NarutoController {

    private static final List<String> NINJA_VILLAGES = Arrays.asList("Konoha", "Suna", "Kiri", "Iwa", "Kumo");
    private static final List<String> CHARACTERS = Arrays.asList("Naruto Uzumaki", "Sasuke Uchiha", "Sakura Haruno", "Kakashi Hatake", "Hinata Hyuga", "Shikamaru Nara");

    @GetMapping("/village/random")
    public ResponseEntity<Map<String, String>> getRandomVillage() {
        Map<String, String> response = Collections.singletonMap("village", NINJA_VILLAGES.get(new Random().nextInt(NINJA_VILLAGES.size())));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/character/random")
    public ResponseEntity<Map<String, String>> getRandomCharacter() {
        Map<String, String> response = Collections.singletonMap("character", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fight/scenario/random")
    public ResponseEntity<Map<String, Object>> getRandomFightScenario() {
        Map<String, Object> response = new HashMap<>();
        response.put("character1", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
        response.put("character2", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
        response.put("battleLocation", NINJA_VILLAGES.get(new Random().nextInt(NINJA_VILLAGES.size())));
        response.put("outcome", Arrays.asList("Win", "Lose", "Draw").get(new Random().nextInt(3)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchNinjas(@RequestParam(required = false) String village, @RequestParam(required = false) String clan) {
        Map<String, Object> response = new HashMap<>();
        response.put("searchCriteria", Map.of("village", village, "clan", clan));
        response.put("result", "Search results based on criteria");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chakra/increase/textResponse")
    public ResponseEntity<String> increaseChakra(@RequestParam String ninjaId, @RequestParam int amount) {
        return ResponseEntity.ok("Chakra of Ninja ID " + ninjaId + " increased by " + amount);
    }

    @PatchMapping("/ninja/updateChakra/{ninjaId}")
    public ResponseEntity<Map<String, Object>> updateChakraLevel(@PathVariable String ninjaId, @RequestBody Map<String, Object> chakraData) {
        chakraData.put("ninjaId", ninjaId);
        chakraData.put("updateStatus", "Chakra level updated successfully");
        return ResponseEntity.ok(chakraData);
    }

    @PostMapping("/ninja/batchActivate")
    public ResponseEntity<Map<String, Object>> batchActivateNinjas(@RequestBody List<String> ninjaIds) {
        Map<String, Object> response = new HashMap<>();
        response.put("activatedNinjas", ninjaIds);
        response.put("status", "Batch activation completed");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/ninja/register/randomAccepted")
    public ResponseEntity<Map<String, Object>> registerNinja(@RequestBody Map<String, String> ninjaData) {
        Map<String, Object> response = new HashMap<>(ninjaData);
        response.put("registrationStatus", "Success");
        response.put("timestamp", new Date());
        response.put("isAccepted", new Random().nextBoolean());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/ninja/update/{ninjaId}")
    public ResponseEntity<Map<String, Object>> updateNinja(@PathVariable String ninjaId, @RequestBody Map<String, Object> updateFields) {
        updateFields.put("ninjaId", ninjaId);
        updateFields.put("updateStatus", "Updated successfully");
        return ResponseEntity.ok(updateFields);
    }

    @DeleteMapping("/ninja/delete/{ninjaId}")
    public ResponseEntity<String> deleteNinja(@PathVariable String ninjaId) {
        return ResponseEntity.ok("Ninja with ID: " + ninjaId + " has been deleted.");
    }

    @GetMapping("/info/villages")
    public ResponseEntity<List<String>> getVillagesInfo() {
        return ResponseEntity.ok(NINJA_VILLAGES);
    }

    @GetMapping("/info/characters")
    public ResponseEntity<List<String>> getCharactersInfo() {
        return ResponseEntity.ok(CHARACTERS);
    }

    @GetMapping("/error/always")
    public ResponseEntity<Map<String, String>> alwaysError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An unexpected error occurred"));
    }
}
