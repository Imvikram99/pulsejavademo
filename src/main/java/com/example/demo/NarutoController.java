package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/naruto")
public class NarutoController {

    private static final List<String> NINJA_VILLAGES = Arrays.asList("Konoha", "Suna", "Kiri", "Iwa", "Kumo");
    private static final List<String> CHARACTERS = Arrays.asList("Naruto Uzumaki", "Sasuke Uchiha", "Sakura Haruno", "Kakashi Hatake", "Hinata Hyuga", "Shikamaru Nara");
    private final AtomicInteger villageInfoCounter = new AtomicInteger(0);
    private static final List<String> FIRST_VILLAGES_INFO = NINJA_VILLAGES.subList(0, 3); // First three for consistent response


    private final AtomicInteger villageCounter = new AtomicInteger(0);
    private final AtomicInteger characterCounter = new AtomicInteger(0);
    private final AtomicInteger fightScenarioCounter = new AtomicInteger(0);
    private final AtomicInteger registrationCounter = new AtomicInteger(0);

    // Predefined responses for the first two hits
    private static final String FIRST_VILLAGE = NINJA_VILLAGES.get(0);
    private static final String FIRST_CHARACTER = CHARACTERS.get(0);
    private static final String SECOND_CHARACTER = CHARACTERS.get(1);
    private static final boolean FIRST_REGISTRATION_ACCEPTANCE = true;

    @GetMapping("/village/random")
    public ResponseEntity<Map<String, String>> getRandomVillage() {
        int count = villageCounter.incrementAndGet();
        String village;
        HttpStatus status;
        if (count <= 2) {
            village = FIRST_VILLAGE;
            status = HttpStatus.OK;
        } else {
            village = NINJA_VILLAGES.get(new Random().nextInt(NINJA_VILLAGES.size()));
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            if (count == 3) {
                villageCounter.set(0);
            }
        }

        Map<String, String> response = Collections.singletonMap("village", village);
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/character/random")
    public ResponseEntity<Map<String, String>> getRandomCharacter() {
        int count = characterCounter.incrementAndGet();
        String character;
        HttpStatus status;
        if (count <= 2) {
            character = FIRST_CHARACTER;
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            character = CHARACTERS.get(new Random().nextInt(CHARACTERS.size()));
            status = HttpStatus.OK;
            if (count == 3) {
                characterCounter.set(0);
            }
        }

        Map<String, String> response = Collections.singletonMap("character", character);
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/fight/scenario/random")
    public ResponseEntity<Map<String, Object>> getRandomFightScenario() {
        int count = fightScenarioCounter.incrementAndGet();
        Map<String, Object> response = new HashMap<>();

        if (count <= 2) {
            response.put("character1", FIRST_CHARACTER);
            response.put("character2", SECOND_CHARACTER);
            response.put("battleLocation", FIRST_VILLAGE);
            response.put("outcome", "Win"); // Assuming fixed outcome for the first two hits
        } else {
            response.put("character1", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
            response.put("character3", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
            response.put("character4", CHARACTERS.get(new Random().nextInt(CHARACTERS.size())));
            response.put("battleLocation", NINJA_VILLAGES.get(new Random().nextInt(NINJA_VILLAGES.size())));
            response.put("outcome", Arrays.asList("Win", "Lose", "Draw").get(new Random().nextInt(3)));
            if (count == 3) {
                fightScenarioCounter.set(0);
            }
        }

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
        int count = registrationCounter.incrementAndGet();
        Map<String, Object> response = new HashMap<>(ninjaData);

        if (count <= 2) {
            response.put("registrationStatus", "Success");
            response.put("timestamp", new Date());
            response.put("isAccepted", FIRST_REGISTRATION_ACCEPTANCE);
        } else {
            response.put("registrationStatus", "Success");
            response.put("timestamp", new Date());
            response.put("isAccepted", new Random().nextBoolean());
            if (count == 3) {
                registrationCounter.set(0);
            }
        }

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
        int count = villageInfoCounter.incrementAndGet();
        List<String> villages;
        HttpStatus status;

        if (count <= 2) {
            villages = FIRST_VILLAGES_INFO;
            status = HttpStatus.OK;
        } else {
            Collections.shuffle(NINJA_VILLAGES); // Shuffle the full list for randomization
            villages = NINJA_VILLAGES.subList(0, new Random().nextInt(NINJA_VILLAGES.size()) + 1); // Randomize the number of villages to return
            status = HttpStatus.INTERNAL_SERVER_ERROR; // On the third hit, we change the status
            villageInfoCounter.set(0); // Reset the counter after the third hit for the next cycle
        }

        return new ResponseEntity<>(villages, status);
    }

    @GetMapping("/info/characters")
    public ResponseEntity<List<String>> getCharactersInfo() {
        return ResponseEntity.ok(CHARACTERS);
    }

    @GetMapping("/error/always")
    public ResponseEntity<Map<String, String>> alwaysError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "An unexpected error occurred"));
    }

    @PostMapping("/api1")
    public ResponseEntity<Map<String, Object>> api1(@RequestBody Map<String, Object> request) {
        // Process the request and return a response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API 1 Response");
        response.put("previousMessage", request.get("previousMessage")); // Add previous message
        response.put("request", request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api2")
    public ResponseEntity<Map<String, Object>> api2(@RequestBody Map<String, Object> request) {
        // Process the request and return a response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API 2 Response");
        response.put("previousMessage", request.get("previousMessage")); // Add previous message
        response.put("request", request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api3")
    public ResponseEntity<Map<String, Object>> api3(@RequestBody Map<String, Object> request) {
        // Process the request and return a response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API 3 Response");
        response.put("previousMessage", request.get("previousMessage")); // Add previous message
        response.put("request", request);
        return ResponseEntity.ok(response);
    }
}
