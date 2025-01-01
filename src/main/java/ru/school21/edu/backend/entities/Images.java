package ru.school21.edu.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Images {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "image")
    private byte[] image;
}
