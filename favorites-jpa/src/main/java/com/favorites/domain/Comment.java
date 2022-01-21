package com.favorites.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 评论
 *
 * @author DingYS
 */
@Entity
@Getter
@Setter


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long collectId;
    @Column(nullable = false, length = 65535, columnDefinition = "Text")
    private String content;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = true)
    private Long replyUserId;
    @Column(nullable = false)
    private Timestamp createTime;
    @Transient
    private String commentTime;
    @Transient
    private String username;
    @Transient
    private String replyUserName;
    @Transient
    private String profilePicture;

}