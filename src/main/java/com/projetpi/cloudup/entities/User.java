package com.projetpi.cloudup.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.projetpi.cloudup.utilities.UniqueEmail;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.projetpi.cloudup.utilities.ValidEmailDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable, UserDetails, Principal {

    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "idUser")

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idUser;
    private String nom;
    private String prenom;

    private String email;
    private String motDePasse;
    private int nbr_pub;
    private int nbr_com;


    @OneToMany(mappedBy = "user")// ,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Publication> publications;

    @OneToMany(mappedBy = "user")//, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Commentary>commentaries;


    @NotNull
    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Phone number must be in valid international format")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role roles;


    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reclamation> reclamations;
    private boolean accountLocked;
    private boolean enabled;
    // Remove @CreatedDate from here
    // private List<TokenAuth> tokenAuths;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime LastModifiedDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate DateOfBirth;

    private String aboutMe;

    private String city;
    private String country;
    private int codePostal;

    @Enumerated(EnumType.STRING)
    private University college;

    @Enumerated(EnumType.STRING)
    private Classe degree;

    @Enumerated(EnumType.STRING)
    private Options option;

    private String membership;
    @Override
    @JsonIgnore

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String fullName() {
        return nom + " " + prenom;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return email;
    }

    private String location;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Education> educations;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Speciality> specialities;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Award> awards;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reactions> reactions;

}