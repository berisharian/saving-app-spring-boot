package finki.ukim.mk.savingapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "bank_user")
public class User implements UserDetails {

    @Id
    @Column(unique = true)
    private String username;

    private String name;

    private String surname;

    private String password;

    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(nullable = true)
    private LocalDate birthDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BankAccount bank;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SavingsTarget> savingsTargetList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User(String email, String name, String surname, String password, String phone, LocalDate birthDate) {
        this.username = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        savingsTargetList = new ArrayList<>();
    }
}
