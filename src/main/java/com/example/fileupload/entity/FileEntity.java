package com.example.fileupload.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicUpdate
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    private String fileName;
    private String filePath;
    private String fileExtension;
    private Long size;

    public FileEntity(String fileName, String filePath, String fileExtension, long size) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FileEntity that = (FileEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
