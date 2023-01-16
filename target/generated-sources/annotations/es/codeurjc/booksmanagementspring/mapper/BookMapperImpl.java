package es.codeurjc.booksmanagementspring.mapper;

import es.codeurjc.booksmanagementspring.dto.BookBasicDTO;
import es.codeurjc.booksmanagementspring.dto.BookCreateDTO;
import es.codeurjc.booksmanagementspring.dto.BookDTO;
import es.codeurjc.booksmanagementspring.dto.ReviewBasicDTO;
import es.codeurjc.booksmanagementspring.model.Book;
import es.codeurjc.booksmanagementspring.model.Review;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-16T14:13:23+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public BookDTO toDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        long id = 0L;
        String title = null;
        String resume = null;
        String author = null;
        String publishing = null;
        String year = null;
        List<ReviewBasicDTO> reviews = null;

        id = book.getId();
        title = book.getTitle();
        resume = book.getResume();
        author = book.getAuthor();
        publishing = book.getPublishing();
        year = book.getYear();
        reviews = reviewListToReviewBasicDTOList( book.getReviews() );

        BookDTO bookDTO = new BookDTO( id, title, resume, author, publishing, year, reviews );

        return bookDTO;
    }

    @Override
    public Book toDomain(BookDTO bookDTO) {
        if ( bookDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDTO.id() );
        book.setTitle( bookDTO.title() );
        book.setResume( bookDTO.resume() );
        book.setAuthor( bookDTO.author() );
        book.setPublishing( bookDTO.publishing() );
        book.setYear( bookDTO.year() );
        book.setReviews( reviewBasicDTOListToReviewList( bookDTO.reviews() ) );

        return book;
    }

    @Override
    public Book toDomain(BookCreateDTO bookCreateDTO) {
        if ( bookCreateDTO == null ) {
            return null;
        }

        Book book = new Book();

        book.setTitle( bookCreateDTO.title() );
        book.setResume( bookCreateDTO.resume() );
        book.setAuthor( bookCreateDTO.author() );
        book.setPublishing( bookCreateDTO.publishing() );
        book.setYear( bookCreateDTO.year() );

        return book;
    }

    @Override
    public List<BookDTO> toDTOs(Collection<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookDTO> list = new ArrayList<BookDTO>( books.size() );
        for ( Book book : books ) {
            list.add( toDTO( book ) );
        }

        return list;
    }

    @Override
    public BookBasicDTO toBasicDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        Long id = null;
        String title = null;

        id = book.getId();
        title = book.getTitle();

        BookBasicDTO bookBasicDTO = new BookBasicDTO( id, title );

        return bookBasicDTO;
    }

    protected List<ReviewBasicDTO> reviewListToReviewBasicDTOList(List<Review> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewBasicDTO> list1 = new ArrayList<ReviewBasicDTO>( list.size() );
        for ( Review review : list ) {
            list1.add( reviewMapper.toBasicDTO( review ) );
        }

        return list1;
    }

    protected Review reviewBasicDTOToReview(ReviewBasicDTO reviewBasicDTO) {
        if ( reviewBasicDTO == null ) {
            return null;
        }

        Review review = new Review();

        review.setId( reviewBasicDTO.id() );
        review.setComment( reviewBasicDTO.comment() );
        review.setRate( reviewBasicDTO.rate() );

        return review;
    }

    protected List<Review> reviewBasicDTOListToReviewList(List<ReviewBasicDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Review> list1 = new ArrayList<Review>( list.size() );
        for ( ReviewBasicDTO reviewBasicDTO : list ) {
            list1.add( reviewBasicDTOToReview( reviewBasicDTO ) );
        }

        return list1;
    }
}
