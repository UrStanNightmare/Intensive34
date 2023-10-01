package ru.aston.oshchepkov_aa.task9.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "visited_place")
public class VisitedPlace {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Include
    @Column(name = "name")
    private String name;

    @ToString.Include
    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @ManyToMany(mappedBy = "visitedPlaces")
    private Set<User> users = new LinkedHashSet<>();

}