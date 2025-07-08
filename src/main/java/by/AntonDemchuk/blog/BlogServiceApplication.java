package by.AntonDemchuk.blog;

import by.AntonDemchuk.blog.database.entity.Post;
import by.AntonDemchuk.blog.database.entity.Reaction;
import by.AntonDemchuk.blog.database.entity.ReactionType;
import by.AntonDemchuk.blog.dto.CommentDto;
import by.AntonDemchuk.blog.dto.ReactionDto;
import by.AntonDemchuk.blog.repository.CommentRepository;
import by.AntonDemchuk.blog.repository.PostRepository;
import by.AntonDemchuk.blog.repository.ReactionRepository;
import by.AntonDemchuk.blog.repository.UserRepository;
import by.AntonDemchuk.blog.service.CommentService;
import by.AntonDemchuk.blog.service.PostService;
import by.AntonDemchuk.blog.service.ReactionService;
import by.AntonDemchuk.blog.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogServiceApplication implements CommandLineRunner {

    // CommandLineRunner - integral to Spring Boot's lifecycle, executing logic after the application context is fully initialized

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final ReactionService reactionService;

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceApplication.class, args);

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Repositories
//        userRepository.save(User.builder()
//                .username("user-2")
//                .firstname("firstname-1")
//                .lastname("lastname-1")
//                .birthDate(LocalDate.of(1980, 1, 1))
//                .email("email-2")
//                .password("password")
//                .personalInfo("1dasdasdvcvfsd")
//                .build()
//        );

//        postRepository.save(Post.builder()
//                .title("title-1")
//                .description("desc-1")
//                .category(PostCategory.MUSIC)
//                .creationDate(LocalDateTime.now())
//                .user(userRepository.findById(1L).get())
//                .build()
//        );
//
//        reactionRepository.save(Reaction.builder()
//                .user(userRepository.findById(1L).get())
//                .post(postRepository.findById(1L).get())
//                .reactionType(ReactionType.LIKE)
//                .build()
//        );
//
//        commentRepository.save(Comment.builder()
//                .user(userRepository.findById(2L).get())
//                .post(postRepository.findById(1L).get())
//                .postDate(LocalDateTime.now())
//                .comment("Message")
//                .build()
//        );

        // Services

//        UserDto userToUpdate = new UserDto("username-updated2" ,
//                "firstname-4",
//                "lastname-4",
//                LocalDate.of(1900, 3, 20),
//                "email-4@gmail.com",
//                "password-4",
//                "personal-info-4");

//        userService.create(newUser);

//        System.out.println(userService.findAll().toString());
//        System.out.println(userService.findById(1L));
//
//        userService.update(userToUpdate, 3L);

//        List<Post> posts = postRepository.findAllByUserId(1L);
//
//        System.out.println(posts);

//        postService.delete(1L);

//        userService.delete(5L);

//        PostDto postDto = new PostDto("title-1", "desc-1", PostCategory.NEWS);
//        PostDto postDto2 = new PostDto("title-2", "desc-2", PostCategory.FINANCES);

//        CommentDto commentDto = new CommentDto("cofdngjinadof", LocalDateTime.now());

//        postService.create(postDto2, 3L);

//        postService.update(postDto, 2L);

//        commentService.create(commentDto, 2L, 2L);

//        System.out.println(commentRepository.findAllByPostId(2L));
//        System.out.println(commentService.findAllByPostId(2L));


//        commentService.delete(3L);
//        CommentDto commentDto = new CommentDto("nnnnnn", LocalDateTime.now());
//
//        commentService.update( commentDto, 5L);

//        System.out.println(userService.findById(2L).toString());


//        ReactionDto reactionDto = new ReactionDto(ReactionType.DISLIKE);

//        ReactionDto r = reactionService.create(reactionDto, 2L, 1L);
//        reactionService.create(reactionDto, 2L, 2L);
//        reactionService.create(reactionDto, 2L, 3L);

        System.out.println(reactionService.findAllByPostId(2L));

        System.out.println(reactionService.findByPostIdAndUserId(2L, 2L));

//       System.out.println(postService.findAll());

    }
}
