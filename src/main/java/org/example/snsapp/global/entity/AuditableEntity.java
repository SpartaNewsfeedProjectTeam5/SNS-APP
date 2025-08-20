package org.example.snsapp.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class AuditableEntity extends BaseEntity {
    @LastModifiedDate
    private LocalDateTime lastModified;
}
