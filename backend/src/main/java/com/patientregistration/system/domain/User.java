package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.patientregistration.system.domain.View.Views;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(Views.Basic.class)
    private Long id;

    @NotEmpty
    @Column(name = "username", nullable = false, unique = true)
    @JsonView(Views.Basic.class)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false)
    @JsonView(Views.Basic.class)
    private String password;

    @Transient
    private String confirmPassword;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    @JsonView(Views.Basic.class)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    @JsonView(Views.Basic.class)
    private String lastName;

    @NotEmpty
    @Column(name = "role", nullable = false)
    @JsonView(Views.Basic.class)
    private String role;

    @Column(name = "pesel")
    @JsonView(Views.Basic.class)
    private String pesel;

    @Email
    @NotEmpty
    @Column(name = "email")
    @JsonView(Views.Basic.class)
    private String email;

    @Column(name = "blocked")
    @JsonView(Views.Basic.class)
    private Boolean blocked;

    @Column(name = "phone_number")
    @JsonView(Views.Basic.class)
    private String phoneNumber;

    @OneToMany
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private List<Statement> statements;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VisitModel> visitModels;

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role +
                ",]";
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
