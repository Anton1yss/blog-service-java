package by.AntonDemchuk.blog.unit.service;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.dto.post.PostReadDto;
import by.AntonDemchuk.blog.dto.post.PostSearchParamsDto;
import by.AntonDemchuk.blog.mapper.post.PostMapper;
import by.AntonDemchuk.blog.mapper.post.PostReadMapper;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.PostSpecification;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.service.PostService;
import by.AntonDemchuk.blog.service.SharedService;
import by.AntonDemchuk.blog.unit.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest extends BaseServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private PostReadMapper postReadMapper;

    @Mock
    private SharedService sharedService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void create_shouldCreatePost() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(postMapper.toEntity(postDto)).thenReturn(mockedPost);
        when(postRepository.save(mockedPost)).thenReturn(mockedPost);
        when(postMapper.toDto(mockedPost)).thenReturn(postDto);

        /* Acting */
        PostDto res = postService.create(postDto);

        /* Asserting & Verifying */
        assertEquals(postDto, res);
        verify(postRepository).save(mockedPost);
        verify(postMapper).toEntity(postDto);
        verify(postRepository).save(mockedPost);
        verify(postMapper).toDto(mockedPost);
    }

    @Test
    public void delete_shouldDeletePost() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(postRepository.findByIdAndUserId(postId, userId)).thenReturn(Optional.of(mockedPost));

        /* Acting */
        postService.delete(postId);

        /* Verifying*/
        verify(postRepository).findByIdAndUserId(postId, userId);
        verify(postRepository).delete(mockedPost);
    }

    @Test
    public void update_shouldUpdatePost() {

        /* Arranging */
        PostDto postToUpdateDto = mock(PostDto.class);

        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(postRepository.findByIdAndUserId(postId, userId)).thenReturn(Optional.of(mockedPost));
        when(postMapper.update(postToUpdateDto, mockedPost)).thenReturn(mockedPost);
        when(postRepository.save(mockedPost)).thenReturn(mockedPost);
        when(postMapper.toDto(mockedPost)).thenReturn(postToUpdateDto);

        /* Acting */
        PostDto res = postService.update(postToUpdateDto, postId);

        /* Asserting & Verifying */
        assertEquals(postToUpdateDto, res);
        verify(postRepository).findByIdAndUserId(postId, userId);
        verify(postMapper).update(postToUpdateDto, mockedPost);
        verify(postRepository).save(mockedPost);
        verify(postMapper).toDto(mockedPost);
    }

    @Test
    public void findById_shouldFindPost() {

        /* Arranging */
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));
        when(postReadMapper.toDto(mockedPost)).thenReturn(postReadDto);

        /* Acting */
        PostReadDto res = postService.findById(postId);

        /* Asserting & Verifying */
        assertEquals(postReadDto, res);
        verify(postRepository).findById(postId);
        verify(postReadMapper).toDto(mockedPost);
    }

    @Test
    public void findAllByUserId_shouldFindAllPostByUser_returnPageDto() {

        /* Arranging */
        Page<Post> mockedPage = mock(Page.class);

        PageDto<PostReadDto> expectedPageDto = PageDto.<PostReadDto>builder()
                .content(List.of(postReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(postRepository.findAllByUserId(pageable, userId)).thenReturn(mockedPage);
        when(postReadMapper.toPageDto(mockedPage)).thenReturn(expectedPageDto);

        /* Acting */
        PageDto<PostReadDto> res = postService.findAllByUserId(pageable, userId);

        /* Asserting & Verifying */
        assertEquals(expectedPageDto, res);
        verify(userRepository).existsById(userId);
        verify(postRepository).findAllByUserId(pageable, userId);
        verify(postReadMapper).toPageDto(mockedPage);
    }

    @Test
    public void findAll_shouldFindAllPost_returnPageDto() {

        /* Arranging */
        Page<Post> mockedPage = mock(Page.class);
        PostSearchParamsDto postSearchParamsDto = mock(PostSearchParamsDto.class);
        Specification<Post> spec = PostSpecification.buildSpecification(postSearchParamsDto);

        PageDto<PostReadDto> expectedPageDto = PageDto.<PostReadDto>builder()
                .content(List.of(postReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(postRepository.findAll(spec, pageable)).thenReturn(mockedPage);
        when(postReadMapper.toPageDto(mockedPage)).thenReturn(expectedPageDto);

        /* Acting */
        PageDto<PostReadDto> res = postService.findAll(postSearchParamsDto, pageable);

        /* Asserting & Verifying */
        assertEquals(expectedPageDto, res);
        verify(postRepository).findAll(any(Specification.class), eq(pageable));
        verify(postReadMapper).toPageDto(mockedPage);
    }
}
