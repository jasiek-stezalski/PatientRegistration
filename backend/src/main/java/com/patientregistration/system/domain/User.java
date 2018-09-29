package com.patientregistration.system.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @NotEmpty
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty
    @Min(6)
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty
    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "pesel")
    private String pesel;

    @Email
    @NotEmpty
    @Column(name = "email")
    private String email;

    @Column(name = "blocked")
    private Boolean blocked;

    @Column(name = "phone_number")
    private String phoneNumber;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<VisitModel> visitModels;

//    @OneToMany
//    @JoinColumn(name = "id_user")
//    private List<VisitHour> visitHours;

    @OneToMany
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private List<Statement> statements;




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
