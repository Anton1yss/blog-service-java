package by.AntonDemchuk.blog.unit.service;

import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.dto.PageDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionReadDto;
import by.AntonDemchuk.blog.mapper.reaction.ReactionMapper;
import by.AntonDemchuk.blog.mapper.reaction.ReactionReadMapper;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.ReactionRepository;
import by.AntonDemchuk.blog.service.ReactionService;
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
public class ReactionServiceTest extends BaseServiceTest {

    @InjectMocks
    private ReactionService reactionService;

    @Mock
    private ReactionRepository reactionRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ReactionMapper reactionMapper;

    @Mock
    private ReactionReadMapper reactionReadMapper;

    @Mock
    private SharedService sharedService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void create_shouldCreateReaction() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockedPost));
        when(reactionMapper.toEntity(reactionDto)).thenReturn(mockedReaction);
        when(reactionRepository.save(mockedReaction)).thenReturn(mockedReaction);
        when(reactionMapper.toDto(mockedReaction)).thenReturn(reactionDto);

        /* Acting */
        ReactionDto res = reactionService.create(reactionDto, postId);

        /* Asserting & Verifying */
        assertEquals(reactionDto, res);
        verify(postRepository).findById(postId);
        verify(reactionMapper).toDto(mockedReaction);
        verify(reactionRepository).save(mockedReaction);
        verify(reactionMapper).toDto(mockedReaction);
    }

    @Test
    public void delete_shouldDeleteReaction() {

        /* Arranging */
        when(sharedService.getCurrentUser()).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(userId);
        when(reactionRepository.findByPostIdAndUserId(postId, userId)).thenReturn(Optional.of(mockedReaction));

        /* Acting */
        reactionService.delete(postId);

        /* Verifying */
        verify(reactionRepository).findByPostIdAndUserId(postId, userId);
        verify(reactionRepository).delete(mockedReaction);
    }

    @Test
    public void findAllByPostId_shouldFindAllReactionsByPostId_returnPageDto() {

        /* Arranging */
        Page<Reaction> mockedPage = mock(Page.class);
        PageDto<ReactionReadDto> expectedPageDto = PageDto.<ReactionReadDto>builder()
                .content(List.of(reactionReadDto))
                .totalPages(1)
                .totalElements(2)
                .pageNumber(0)
                .pageSize(5)
                .build();

        when(postRepository.existsById(postId)).thenReturn(true);
        when(reactionRepository.findAllByPostId(postId, pageable)).thenReturn(mockedPage);
        when(reactionReadMapper.toPageDto(mockedPage)).thenReturn(expectedPageDto);

        /* Action */
        PageDto<ReactionReadDto> res = reactionService.findAllByPostId(postId, pageable);

        /* Asserting & Verifying */
        assertEquals(expectedPageDto, res);
        verify(postRepository).existsById(postId);
        verify(reactionRepository).findAllByPostId(postId, pageable);
        verify(reactionReadMapper).toPageDto(mockedPage);
    }

    @Test
    public void countAllByPostId_shouldCountAllReactionsByPostId_returnLong() {

        /* Arranging */
        Long count = 5L;
        when(postRepository.existsById(postId)).thenReturn(true);
        when(reactionRepository.countByPostId(postId)).thenReturn(count);

        /* Acting */
        Long res = reactionService.countAllByPostId(postId);

        /* Asserting & Verifying */
        assertEquals(count, res);
        verify(postRepository).existsById(postId);
        verify(reactionRepository).countByPostId(postId);
    }
}
