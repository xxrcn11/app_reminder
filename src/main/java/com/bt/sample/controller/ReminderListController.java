package com.bt.sample.controller;

import com.bt.sample.domain.ReminderListDto;
import com.bt.sample.service.ReminderListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/lists")
public class ReminderListController {

    private final ReminderListService reminderListService;

    public ReminderListController(ReminderListService reminderListService) {
        this.reminderListService = reminderListService;
    }

    @GetMapping
    public ResponseEntity<List<ReminderListDto>> findAll() {
        return ResponseEntity.ok(reminderListService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderListDto> findById(@PathVariable Long id) {
        ReminderListDto list = reminderListService.findById(id);
        if (list == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<ReminderListDto> create(@RequestBody ReminderListDto list) {
        reminderListService.create(list);
        URI location = URI.create("/api/lists/" + list.getId());
        return ResponseEntity.created(location).body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderListDto> update(@PathVariable Long id, @RequestBody ReminderListDto list) {
        list.setId(id);
        reminderListService.update(list);
        ReminderListDto updated = reminderListService.findById(id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reminderListService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
