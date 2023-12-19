package com.ll.sbjpa.domain.member.member.entity;

import com.ll.sbjpa.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;
}
