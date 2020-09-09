package com.minsu.springboot.service.posts;

import com.minsu.springboot.domain.posts.Posts;
import com.minsu.springboot.domain.posts.PostsRepository;
import com.minsu.springboot.web.dto.PostsListResponseDto;
import com.minsu.springboot.web.dto.PostsResponseDto;
import com.minsu.springboot.web.dto.PostsSaveRequestDto;
import com.minsu.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service에서 비즈니스 로직을 처리하는 것이 아닌, 트랜잭션, 도메인 간 순서 보장의 역할을 해야합니다.
 */
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    // (readOnly = true): 트랜잭션의 범위는 유지, 조회 기능만 남겨두어 조회 속도 개선
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                // postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해 변환후 List 반환
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

}
