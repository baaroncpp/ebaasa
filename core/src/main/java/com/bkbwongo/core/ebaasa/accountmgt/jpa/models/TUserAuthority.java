package com.bkbwongo.core.ebaasa.accountmgt.jpa.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author bkaaron
 * @created on 23/04/2021
 * @project ebaasa-sms
 */

@Entity
@Table(name = "t_user_authority", schema = "core")
public class TUserAuthority implements Serializable {

    private Integer id;
    private String username;
    private String authority;

    @NaturalId
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "authority")
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
