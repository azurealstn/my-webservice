package com.minsu.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * DB 계층 접근자
 * Posts 클래스로 DB를 접근하게 해줄 JpaRepository를 생성
 * 주의 : Entity 클래스와 기본 Entity Repository는 함께 위치해야 합니다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
