package com.favorites.domain;

import com.favorites.domain.enums.LetterType;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 私信
 *
 * @author DingYS
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Letter extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long sendUserId;
    @Column(nullable = false, length = 65535, columnDefinition = "Text")
    private String content;
    @Column(nullable = false)
    private Long receiveUserId;
    @Column(nullable = true)
    private Long pid;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LetterType type;
    @Column(nullable = false)
    private Timestamp createTime;
    @Transient
    private String sendType;


}