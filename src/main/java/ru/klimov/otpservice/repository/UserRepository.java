package ru.klimov.otpservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);

    List<User> findAllByRole(Role role);
}
