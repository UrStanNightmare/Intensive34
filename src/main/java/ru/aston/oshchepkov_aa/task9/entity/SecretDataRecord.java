package ru.aston.oshchepkov_aa.task9.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.aston.oshchepkov_aa.task9.alien.AlienPersonalSecretData;
import ru.aston.oshchepkov_aa.task9.alien.AlienPersonalSecretDataConverter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "secret_data_record")
public class SecretDataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Convert(converter = AlienPersonalSecretDataConverter.class)
    private AlienPersonalSecretData data;


    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "alien_user_id")
    private AlienUser alienUser;

}
