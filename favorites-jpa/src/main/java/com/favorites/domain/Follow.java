package com.favorites.domain;

import com.favorites.domain.enums.FollowStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * 关注
 *
 * @author DingYS
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Follow   implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long followId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FollowStatus status;
    @Column(nullable = false)
    private Timestamp createTime;
    @Column(nullable = false)
    private Timestamp lastModifyTime;
    @Transient
    private String name;

}