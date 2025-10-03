package com.demo.alexandino.clientes.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class Person {
        private String identifier;
        private String name;
        @Enumerated(EnumType.STRING)
        private Gender gender;
        private int age;
        private String address;
        private String phone;
}
