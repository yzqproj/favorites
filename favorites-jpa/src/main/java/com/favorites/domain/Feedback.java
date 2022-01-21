package com.favorites.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户反馈javaBean类
 * Created by chenzhimin on 2017/2/23.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Feedback  extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private Long userId;
    @Column(nullable = false)
    private String feedbackAdvice;
    @Column(nullable = true)
    private String feedbackName;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private Timestamp createTime;
    @Column(nullable = false)
    private Timestamp lastModifyTime;

}
