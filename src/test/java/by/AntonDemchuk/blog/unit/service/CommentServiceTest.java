package by.AntonDemchuk.blog.unit.service;

import by.AntonDemchuk.blog.database.entity.Comment;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.comment.CommentDto;
import by.AntonDemchuk.blog.dto.comment.CommentReadDto;
import by.AntonDemchuk.blog.mapper.comment.CommentMapper;
import by.AntonDemchuk.blog.mapper.comment.CommentReadMapper;
import by.AntonDemchuk.blog.repository.CommentRepository;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.service.CommentService;
import by.AntonDemchuk.blog.service.SharedService;
import by.AntonDemchuk.blog.unit.BaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest extends BaseServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentReadMapper commentReadMapper;

    @Mock
    private SharedService sharedService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void create_shouldCreateComment() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));
        when(commentMapper.toEntity(commentDto)).thenReturn(mockedComment);
        when(commentRepository.save(mockedComment)).thenReturn(mockedComment);
        when(commentMapper.toDto(mockedComment)).thenReturn(commentDto);

        /* Acting */
        CommentDto res = commentService.create(commentDto, postId);

        /* Asserting & Verifying */
        assertEquals(commentDto, res);
        verify(postRepository).findById(postId);
        verify(commentMapper).toDto(mockedComment);
        verify(commentRepository).save(mockedComment);
        verify(commentMapper).toDto(mockedComment);
    }

    @Test
    public void delete_shouldDeleteComment() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(commentRepository.findByIdAndUserId(commentId, userId)).thenReturn(Optional.of(mockedComment));

        /* Acting */
        commentService.delete(commentId);

        /* Verifying */
        verify(commentRepository).findByIdAndUserId(commentId, userId);
        verify(commentRepository).deleteById(commentId);
    }

    @Test
    public void update_shouldUpdateComment() {

        /* Acting */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(commentRepository.findByIdAndUserId(commentId, userId)).thenReturn(Optional.of(mockedComment));
        when(commentMapper.update(commentDto, mockedComment)).thenReturn(mockedComment);
        when(commentRepository.save(mockedComment)).thenReturn(mockedComment);
        when(commentMapper.toDto(mockedComment)).thenReturn(commentDto);

        /* Acting */
        CommentDto res = commentService.update(commentDto, commentId);

        /* Asserting */
        assertEquals(res, commentDto);

        /* Verifying */
        verify(commentRepository).findByIdAndUserId(commentId, userId);
        verify(commentMapper).update(commentDto, mockedComment);
        verify(commentRepository).save(mockedComment);
        verify(commentMapper).toDto(mockedComment);
    }

    @Test
    public void findById_shouldFindComment() {

        /* Arranging */
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(mockedComment));
        when(commentReadMapper.toDto(mockedComment)).thenReturn(commentReadDto);

        /* Acting */
        CommentReadDto res = commentService.findById(commentId);

        /* Asserting */
        assertEquals(commentReadDto, res);

        /* Verifying */
        verify(commentRepository).findById(commentId);
        verify(commentReadMapper).toDto(mockedComment);
    }

    @Test
    public void findALlByPostId_shouldFindAllCommentsByPostId() {

        /* Arranging */
        Page<Comment> commentPage = mock(Page.class);
        PageDto<CommentReadDto> expectedPageDto = PageDto.<CommentReadDto>builder()
                .content(List.of(commentReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(postRepository.existsById(postId)).thenReturn(true);
        when(commentRepository.findAllByPostId(postId, pageable)).thenReturn(commentPage);
        when(commentReadMapper.toPageDto(commentPage)).thenReturn(expectedPageDto);

        /* Acting */
        PageDto<CommentReadDto> res = commentService.findAllByPostId(postId, pageable);

        /* Asserting */
        assertEquals(expectedPageDto, res);

        /* Verifying */
        verify(postRepository).existsById(postId);
        verify(commentRepository).findAllByPostId(postId, pageable);
        verify(commentReadMapper).toPageDto(commentPage);
    }

    @Test
    public void findALlByUserId_shouldFindAllCommentsByUserId() {

        /* Arranging */
        Page<Comment> commentPage = mock(Page.class);
        PageDto<CommentReadDto> expectedPageDto = PageDto.<CommentReadDto>builder()
                .content(List.of(commentReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(userRepository.existsById(userId)).thenReturn(true);
        when(commentRepository.findAllByUserId(userId, pageable)).thenReturn(commentPage);
        when(commentReadMapper.toPageDto(commentPage)).thenReturn(expectedPageDto);

        /* Acting */
        PageDto<CommentReadDto> res = commentService.findAllByUserId(pageable, userId);

        /* Asserting & Verifying */
        assertEquals(expectedPageDto, res);
        verify(userRepository).existsById(userId);
        verify(commentRepository).findAllByUserId(userId, pageable);
        verify(commentReadMapper).toPageDto(commentPage);
    }
}
