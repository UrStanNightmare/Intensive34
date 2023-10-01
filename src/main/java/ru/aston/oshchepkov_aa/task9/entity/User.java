package ru.aston.oshchepkov_aa.task9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.aston.oshchepkov_aa.task9.enums.UserType;

import java.util.LinkedHashSet;
import java.util.Set;

@SuperBuilder
@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Include
    @Column(name = "name", length = 50)
    private String name;

    @ToString.Include
    @Column(name = "surname")
    private String surname;

    @ToString.Include
    @Column(name = "last_name")
    private String lastName;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @ToString.Exclude
    @OneToMany(mappedBy = "orderedUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_visited_place",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "visitedPlaces_id"))
    private Set<VisitedPlace> visitedPlaces = new LinkedHashSet<>();

}
