package by.AntonDemchuk.blog.unit;

import by.AntonDemchuk.blog.database.entity.*;
import by.AntonDemchuk.blog.dto.comment.CommentDto;
import by.AntonDemchuk.blog.dto.comment.CommentReadDto;
import by.AntonDemchuk.blog.dto.post.PostDto;
import by.AntonDemchuk.blog.dto.post.PostReadDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionDto;
import by.AntonDemchuk.blog.dto.reaction.ReactionReadDto;
import by.AntonDemchuk.blog.dto.user.UserDetailedReadDto;
import by.AntonDemchuk.blog.dto.user.UserDto;
import by.AntonDemchuk.blog.dto.user.UserReadDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTest {

    protected final Long userId = 1L;
    protected final Long postId = 1L;
    protected final Long commentId = 1L;

    protected User mockedUser;
    protected Post mockedPost;
    protected Reaction mockedReaction;
    protected Comment mockedComment;

    protected Pageable pageable;

    protected UserDto userDto;
    protected UserDetailedReadDto userDetailedReadDto;
    protected PostDto postDto;
    protected ReactionDto reactionDto;
    protected CommentDto commentDto;

    protected UserReadDto userReadDto;
    protected PostReadDto postReadDto;
    protected ReactionReadDto reactionReadDto;
    protected CommentReadDto commentReadDto;

    @BeforeEach
    void setUp() {

        pageable = PageRequest.of(0, 5);

        /* Mocks */
        mockedUser = mock(User.class);
        mockedPost = mock(Post.class);
        mockedReaction = mock(Reaction.class);
        mockedComment = mock(Comment.class);


        /* DTO */
        userDto = UserDto.builder()
                .username("Username")
                .email("test@example.com")
                .build();

        postDto = PostDto.builder()
                .title("Title")
                .description("Description")
                .build();

        commentDto = CommentDto.builder()
                .comment("Comment")
                .build();

        reactionDto = ReactionDto.builder()
                .reactionType(ReactionType.HEART)
                .build();

        /* ReadDto */
        userReadDto = UserReadDto.builder()
                .id(userId)
                .username("Username")
                .email("test@example.com")
                .build();

        postReadDto = PostReadDto.builder()
                .id(postId)
                .title("Title")
                .build();

        commentReadDto = CommentReadDto.builder()
                .id(commentId)
                .postId(postId)
                .comment("Comment")
                .build();

        reactionReadDto = ReactionReadDto.builder()
                .userId(userId)
                .postId(postId)
                .build();

        userDetailedReadDto = UserDetailedReadDto.builder()
                .id(1L)
                .username("u1")
                .firstname("firstname")
                .lastname("lastname")
                .email("u1@email.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        
    }
}
