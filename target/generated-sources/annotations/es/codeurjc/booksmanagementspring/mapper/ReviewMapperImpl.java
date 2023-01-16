package es.codeurjc.booksmanagementspring.mapper;

import es.codeurjc.booksmanagementspring.dto.ReviewBasicDTO;
import es.codeurjc.booksmanagementspring.dto.ReviewCreateDTO;
import es.codeurjc.booksmanagementspring.dto.ReviewDTO;
import es.codeurjc.booksmanagementspring.model.Book;
import es.codeurjc.booksmanagementspring.model.Review;
import es.codeurjc.booksmanagementspring.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-16T14:13:23+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewDTO toDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        long userId = 0L;
        long bookId = 0L;
        long id = 0L;
        String comment = null;
        int rate = 0;

        userId = reviewUsernameId( review );
        bookId = reviewBookId( review );
        id = review.getId();
        comment = review.getComment();
        rate = review.getRate();

        ReviewDTO reviewDTO = new ReviewDTO( id, comment, rate, userId, bookId );

        return reviewDTO;
    }

    @Override
    public List<ReviewDTO> toDTOs(Collection<Review> reviews) {
        if ( reviews == null ) {
            return null;
        }

        List<ReviewDTO> list = new ArrayList<ReviewDTO>( reviews.size() );
        for ( Review review : reviews ) {
            list.add( toDTO( review ) );
        }

        return list;
    }

    @Override
    public Review toDomain(ReviewDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewDTO.id() );
        review.setComment( reviewDTO.comment() );
        review.setRate( reviewDTO.rate() );

        return review;
    }

    @Override
    public Review toDomain(ReviewCreateDTO reviewDTO) {
        if ( reviewDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setComment( reviewDTO.comment() );
        review.setRate( reviewDTO.rate() );

        return review;
    }

    @Override
    public ReviewBasicDTO toBasicDTO(Review review) {
        if ( review == null ) {
            return null;
        }

        long userId = 0L;
        long id = 0L;
        String comment = null;
        int rate = 0;

        userId = reviewUsernameId( review );
        id = review.getId();
        comment = review.getComment();
        rate = review.getRate();

        ReviewBasicDTO reviewBasicDTO = new ReviewBasicDTO( id, comment, rate, userId );

        return reviewBasicDTO;
    }

    private long reviewUsernameId(Review review) {
        if ( review == null ) {
            return 0L;
        }
        User username = review.getUsername();
        if ( username == null ) {
            return 0L;
        }
        long id = username.getId();
        return id;
    }

    private long reviewBookId(Review review) {
        if ( review == null ) {
            return 0L;
        }
        Book book = review.getBook();
        if ( book == null ) {
            return 0L;
        }
        long id = book.getId();
        return id;
    }
}
