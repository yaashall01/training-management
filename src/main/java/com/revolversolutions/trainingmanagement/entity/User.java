package com.revolversolutions.trainingmanagement.entity;


import com.revolversolutions.trainingmanagement.enums.UserGender;
import com.revolversolutions.trainingmanagement.enums.UserRole;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User implements UserDetails, Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    @NaturalId
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String userId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NaturalId @Email @NotNull
    private String email;

    @Column(unique = true)
    private String phone;

    @NaturalId @NotNull
    private String userName;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Embedded
    private Address address;

    private LocalDate dob;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne
    private FileDB profileImage;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private transient List<Token> tokens;

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private transient List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private transient List<Attendance> sessions = new ArrayList<>();


    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Certificate> certificates = new ArrayList<>();

    public User(){
        this.userId = UUID.randomUUID().toString();
    }

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

    public void addEnrolment(Enrollment enrolment) {
        if (!enrollments.contains(enrolment)) {
            enrollments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrollment enrolment) {
        enrollments.remove(enrolment);
    }

}
