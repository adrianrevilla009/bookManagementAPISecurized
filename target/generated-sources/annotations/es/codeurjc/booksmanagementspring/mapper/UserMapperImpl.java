package es.codeurjc.booksmanagementspring.mapper;

import es.codeurjc.booksmanagementspring.dto.UserCreateDTO;
import es.codeurjc.booksmanagementspring.dto.UserDTO;
import es.codeurjc.booksmanagementspring.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-16T14:13:23+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        long id = 0L;
        String email = null;

        id = user.getId();
        email = user.getEmail();

        String nick = null;

        UserDTO userDTO = new UserDTO( id, nick, email );

        return userDTO;
    }

    @Override
    public User toDomain(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDTO.id() );
        user.setEmail( userDTO.email() );

        return user;
    }

    @Override
    public User toDomain(UserCreateDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userDTO.email() );

        return user;
    }
}
