package biv.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(nullable = false)
    private String surname;
    @NonNull
    @Column(nullable = false)
    private String name;
    private String patronymic;
    @NonNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @NonNull
    @Column(nullable = false)
    private String mobilePhone;
    private String additionalMobilePhone;
    @NonNull
    @Column(nullable = false)
    private String personalEmail;
    private String workEmail;
    @OneToMany
    private List<PassportRu> passport;
}
