package com.kentoes.revisionaudit.repositories.master;

import com.kentoes.revisionaudit.entities.master.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface UsersRepository extends JpaRepository<Users, Long>, RevisionRepository<Users, Long, Integer> {
}