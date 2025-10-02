package com.kentoes.revisionaudit.controllers.master;

import com.kentoes.revisionaudit.dto.master.users.UsersPostRequest;
import com.kentoes.revisionaudit.entities.master.Users;
import com.kentoes.revisionaudit.repositories.master.UsersRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/master/users")
@RequiredArgsConstructor
public class UsersController {
    private final UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
//        return usersRepository.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication.getPrincipal().toString());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UsersPostRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        Users user = UsersPostRequest.toEntity(request);
        usersRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UsersPostRequest request, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        usersRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        usersRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/revision/all/{id}")
    public ResponseEntity<?> getAllRevision(@PathVariable Long id) {
        Revisions<Integer, Users> revisions = usersRepository.findRevisions(id);
        if (revisions.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(revisions);
    }

    @GetMapping("/revision/{id}")
    public ResponseEntity<?> getRevision(@PathVariable Long id) {
//        Optional<Revision<Integer, Users>> lastChangeRevision = usersRepository.findLastChangeRevision(id);
//        if (lastChangeRevision.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
        Optional<Users> users = usersRepository.findById(id);
        if (users.isEmpty())
            return ResponseEntity.notFound().build();
        Optional<Revision<Integer, Users>> revision = usersRepository
                .findRevision(users.get().getId(), users.get().getVersion() - 1);
        return ResponseEntity.ok(revision);
    }
}
