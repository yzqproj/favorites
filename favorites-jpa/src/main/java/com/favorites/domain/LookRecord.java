package com.favorites.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *  浏览记录entity
 * Created by chenzhimin on 2017/1/5.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LookRecord extends Entitys implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long collectId;
    @Column(nullable = false)
    private Timestamp createTime;
    @Column(nullable = false)
    private Timestamp lastModifyTime;
 
}
