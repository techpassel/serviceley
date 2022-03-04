package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.Religion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_provider")
public class ServiceProvider extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer age;

    private Religion religion;

    @ElementCollection(targetClass = String.class)
    private List<String> languages;

    private String qualification;

    //Following all data are String type, but it will contain file location of documents(i.e. URL)
    private String qualificationCertificate;

    private String image1;

    private String image2;

    private String image3;

    private String idProof;

    private String addressProof;
}
