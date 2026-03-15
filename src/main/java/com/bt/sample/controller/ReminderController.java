package com.bt.sample.controller;

import com.bt.sample.domain.ReminderDto;
import com.bt.sample.service.ReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    // 기본 CRUD

    @GetMapping
    public ResponseEntity<List<ReminderDto>> findByListId(@RequestParam Long listId) {
        return ResponseEntity.ok(reminderService.findByListId(listId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderDto> findById(@PathVariable Long id) {
        ReminderDto reminder = reminderService.findById(id);
        if (reminder == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reminder);
    }

    @PostMapping
    public ResponseEntity<ReminderDto> create(@RequestBody ReminderDto reminder) {
        reminderService.create(reminder);
        URI location = URI.create("/api/reminders/" + reminder.getId());
        return ResponseEntity.created(location).body(reminder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderDto> update(@PathVariable Long id, @RequestBody ReminderDto reminder) {
        reminder.setId(id);
        reminderService.update(reminder);
        ReminderDto updated = reminderService.findById(id);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ReminderDto> toggleComplete(@PathVariable Long id) {
        reminderService.toggleComplete(id);
        ReminderDto toggled = reminderService.findById(id);
        return ResponseEntity.ok(toggled);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 스마트 리스트

    @GetMapping("/today")
    public ResponseEntity<List<ReminderDto>> findToday() {
        return ResponseEntity.ok(reminderService.findToday());
    }

    @GetMapping("/scheduled")
    public ResponseEntity<List<ReminderDto>> findScheduled() {
        return ResponseEntity.ok(reminderService.findScheduled());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReminderDto>> findAllIncomplete() {
        return ResponseEntity.ok(reminderService.findAllIncomplete());
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<ReminderDto>> findFlagged() {
        return ResponseEntity.ok(reminderService.findFlagged());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<ReminderDto>> findCompleted() {
        return ResponseEntity.ok(reminderService.findCompleted());
    }

    // 검색

    @GetMapping("/search")
    public ResponseEntity<List<ReminderDto>> search(@RequestParam String q) {
        return ResponseEntity.ok(reminderService.search(q));
    }

    // 카운트

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Object>> getCounts() {
        return ResponseEntity.ok(reminderService.getCounts());
    }
}
