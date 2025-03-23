package br.com.challange.softDesign.application.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table
@Data
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private String cpf;

    private boolean vote;

}
