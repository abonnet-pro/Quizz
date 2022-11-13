package com.esimed.quizz.models.entities;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="created")
    private LocalDateTime created;
    @Column(name="updated")
    private LocalDateTime updated;

    @Column(name="description")
    private String description;
    @ManyToOne
    @JoinColumn(name="categorie_id")
    private Categorie categorie;
    @Column(name="reponse_1")
    private String reponse1;
    @Column(name="reponse_2")
    private String reponse2;
    @Column(name="reponse_3")
    private String reponse3;
    @Column(name="reponse_4")
    private String reponse4;

    @PrePersist
    public void onPrePersit() {
        setCreated(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdated(LocalDateTime.now());
    }
}
