package ru.aston.oshchepkov_aa.task9.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Table(name = "human_user", schema = "public")
public class HumanUser extends User {
    @ToString.Include
    @Column(name = "foot_size")
    private Integer footSize;

}