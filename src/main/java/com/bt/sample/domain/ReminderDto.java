package com.bt.sample.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReminderDto {

    private Long id;
    private Long listId;
    private String title;
    private String memo;
    private LocalDateTime dueDate;
    private Integer priority;
    private Boolean isFlagged;
    private Boolean isCompleted;
    private LocalDateTime completedAt;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
