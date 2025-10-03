package com.demo.alexandino.cuentas.infrastructure.persistence.entity;

import com.demo.alexandino.cuentas.domain.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@MappedSuperclass
@AllArgsConstructor
public class PersonPersistence {
        private String identifier;
        private String name;
        @Enumerated(EnumType.STRING)
        private Gender gender;
        private int age;
        private String address;
        private String phone;
}
