package ru.aston.oshchepkov_aa.task9.alien;

import lombok.*;

import java.util.Map;
import java.util.StringJoiner;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlienPersonalSecretData {
    private Map<String, String> header;
    private Map<String, String> payload;
    private Map<String, String> secret;

    @Override
    public String toString() {
        return new StringJoiner(", ", AlienPersonalSecretData.class.getSimpleName() + "[", "]")
                .add("header=" + header)
                .add("payload=" + payload)
                .add("secret=" + secret)
                .toString();
    }
}
