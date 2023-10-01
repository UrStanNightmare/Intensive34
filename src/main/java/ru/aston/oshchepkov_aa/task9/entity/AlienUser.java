package ru.aston.oshchepkov_aa.task9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "alien_users", schema = "public")
public class AlienUser extends User {

    @ToString.Include
    @Column(name = "body_color")
    private String bodyColor;

    @OneToOne(mappedBy = "alienUser", orphanRemoval = true)
    private SecretDataRecord secretDataRecord;

}