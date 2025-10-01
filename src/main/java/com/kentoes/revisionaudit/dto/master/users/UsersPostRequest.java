package com.kentoes.revisionaudit.dto.master.users;

import com.kentoes.revisionaudit.entities.master.Users;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.kentoes.revisionaudit.entities.master.Users}
 */
@Data
public class UsersPostRequest implements Serializable {
    String username;
    String password;

    public static Users toEntity(UsersPostRequest request) {
        Users entity = new Users();
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        return entity;
    }
}