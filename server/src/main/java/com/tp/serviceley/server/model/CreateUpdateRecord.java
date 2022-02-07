package com.tp.serviceley.server.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
// If we don't apply @MappedSuperclass then even though we extend CreateUpdateRecord class to some other model class
// but following 2 fields won't be created in database table of inherited class model as inheritance in JPA don't
// work the same way as in normal Java classes.So we explicitly need to tell JPA that these fields needs to be added.
@Data
public class CreateUpdateRecord {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
