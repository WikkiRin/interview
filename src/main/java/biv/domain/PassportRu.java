package biv.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Data
@NoArgsConstructor
public class PassportRu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NonNull
    @Column(nullable = false)
    private String passportSerial;
    @NonNull
    @Column(nullable = false)
    private String passportNumber;
    @NonNull
    @Column(nullable = false)
    private LocalDate dateOfIssue;
    @NonNull
    @Column(nullable = false)
    private String issuerCode;
    @NonNull
    @Column(nullable = false)
    private boolean actual;
    @NonNull
    @Column(nullable = false)
    private String locale;
    @JoinColumn(nullable = false)
    private long userAccountId;
}
