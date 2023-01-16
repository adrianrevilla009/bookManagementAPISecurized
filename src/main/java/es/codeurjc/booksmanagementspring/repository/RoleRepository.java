package es.codeurjc.booksmanagementspring.repository;

import es.codeurjc.booksmanagementspring.model.ERole;
import es.codeurjc.booksmanagementspring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
