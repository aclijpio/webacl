package ru.aclij.webacl.security.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;
}
