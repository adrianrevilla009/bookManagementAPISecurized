package es.codeurjc.booksmanagementspring.repository;

import es.codeurjc.booksmanagementspring.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class DatabaseInitializer {
    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public DatabaseInitializer(BookRepository bookRepository, ReviewRepository reviewRepository, UserRepository userRepository,
                               RoleRepository roleRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        User user1 = new User("Alex", "alex@gmail.com");
        User user2 = new User("Jesus", "jesus@gmail.com");
        user1.setPassword("1234");

        Book book1 = new Book("Tomás Nevinson",
                "Dos hombres, uno en la ficción y otro en la realidad...",
                "Javier Marías",
                "Alfaguara",
                "2021");

        Book book2 = new Book("Los vencejos",
                "Una espléndida novela humanista sobre la dignidad y la esperanza",
                "Fernando Aramburu",
                "TUSQUETS EDITORES",
                "2021");

        Review review1 = new Review("Me ha gustado", 4, user1, book1);
        Review review2 = new Review("No me ha gustado", 1, user1, book2);
        Review review3 = new Review( "Un libro muy interesante", 3, user2, book1);
        Review review4 = new Review("Muy aburrido", 1, user2, book2);

        book1.getReviews().add(review1);
        book1.getReviews().add(review3);
        book2.getReviews().add(review2);
        book2.getReviews().add(review4);

        user1.getReviews().add(review1);
        user1.getReviews().add(review2);
        user2.getReviews().add(review3);
        user2.getReviews().add(review4);

        userRepository.save(user1);
        userRepository.save(user2);

        bookRepository.save(book1);
        bookRepository.save(book2);

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);

        Role role1 = new Role(1, ERole.ROLE_USER);
        Role role2 = new Role(2, ERole.ROLE_ADMIN);
        Role role3 = new Role(3, ERole.ROLE_ANONYMOUS_USER);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        roleList.add(role3);

        this.roleRepository.saveAll(roleList);
    }
}
