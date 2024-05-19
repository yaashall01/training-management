package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NaturalId @Email @NotNull
    private String email;

    @NaturalId
    private String phone;

    @NaturalId @NotNull
    private String userName;

    @NotNull
    private String password;

    private String gender;

    private String address;

    private LocalDate dob;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Lob
    @Column(name = "profile_image", columnDefinition = "LONGBLOB")
    private byte[] profileImage;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
