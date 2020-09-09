package com.minsu.springboot.domain.posts;

import com.minsu.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Entity 클래스 (테이블과 링크될 클래스)
 * @NoArgsConstructor: 기본 생성자 자동 추가
 * Entity 클래스에서는 절대 Setter 메소드를 만들지 않고, 그 목적과 의도를 나타낼 수 있는 메소드를 추가
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    // @Id: 테이블의 PK 필드
    // @GeneratedValue: IDENTITY옵션을 추가해 auto_increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column: 필드는 모두 칼럼
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
