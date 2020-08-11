package iDrive.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_login", referencedColumnName = "id")
    private Login login;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private Boolean active;
}
