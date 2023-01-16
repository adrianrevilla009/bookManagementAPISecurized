package es.codeurjc.booksmanagementspring.controller;

import es.codeurjc.booksmanagementspring.dto.BookCreateDTO;
import es.codeurjc.booksmanagementspring.dto.BookDTO;
import es.codeurjc.booksmanagementspring.security.jwt.AuthTokenFilter;
import es.codeurjc.booksmanagementspring.security.jwt.JwtUtils;
import es.codeurjc.booksmanagementspring.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/books")
public class BookRestController {
    private final BookService bookService;

    private final AuthTokenFilter authTokenFilter;

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    public BookRestController(BookService bookService, AuthTokenFilter authTokenFilter,
                              JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.bookService = bookService;
        this.authTokenFilter = authTokenFilter;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Operation(summary = "Get all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books found",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = BookDTO.class))) })})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ANONYMOUS_USER')")
    @GetMapping("/")
    public ResponseEntity<Page<?>> getBooks(Pageable pageable, @RequestParam(required = false) String view,
                                            HttpServletRequest request) {
        if("basic".equals(view)){
            return ResponseEntity.ok(bookService.findAllBasic(pageable));
        } else {
            // get basic book bto in case role anonymous
            String jwt = this.authTokenFilter.parseJwt(request);
            if (jwt != null && this.jwtUtils.validateJwtToken(jwt)) {
                String username = this.jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (userDetails.getAuthorities().size() == 0) {
                    return ResponseEntity.ok(bookService.findAllBasic(pageable));
                }
            }
            // get full book dto in other cases
            return ResponseEntity.ok(bookService.findAll(pageable));
        }
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('ANONYMOUS_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookDetail(@PathVariable long id) {
        return ResponseEntity.ok(bookService.findByIdDTO(id));
    }

    @Operation(summary = "Create new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) })})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookCreateDTO bookCreateDTO) {
        BookDTO bookDTO = bookService.save(bookCreateDTO);
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(bookDTO.id()).toUri();
        return ResponseEntity.created(location).body(bookDTO);
    }

    @Operation(summary = "Update a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> replaceBook(@RequestBody BookCreateDTO newBook, @PathVariable long id) {
        return ResponseEntity.ok(bookService.replace(newBook, id));
    }

    @Operation(summary = "Delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> deleteBook( @PathVariable long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

}