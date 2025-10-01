package com.kentoes.revisionaudit.entities.master;

import com.kentoes.revisionaudit.entities.commons.IdsAbstract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "unique_username", columnNames = {"username"})
})
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Audited
public class Users extends IdsAbstract {
    private String username;
    private String password;
}
