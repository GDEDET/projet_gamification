package fr.neosoft.todogame.auth.roles;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
