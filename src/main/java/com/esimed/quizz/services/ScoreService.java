package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.score.TermineQuestionDTO;
import com.esimed.quizz.models.dtos.score.TermineQuizzDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Score;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.ScoreRepository;
import com.esimed.quizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = Exception.class)
    public Score termineQuestion(TermineQuestionDTO reponse) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(reponse.getCategorieId());
        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        Optional<User> user = userRepository.findById(reponse.getUserId());
        if(!user.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        Score score = scoreRepository.findByCategorieAndUser(categorie.get(), user.get());
        if(score == null) {
            score = buildDefautScore(categorie.get(), user.get());
        }

        return updateScore(score, reponse);
    }

    @Transactional(rollbackOn = Exception.class)
    public Score termineQuizz(TermineQuizzDTO resultat) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(resultat.getCategorieId());
        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        Optional<User> user = userRepository.findById(resultat.getUserId());
        if(!user.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        Score score = scoreRepository.findByCategorieAndUser(categorie.get(), user.get());
        if(score == null) {
            score = buildDefautScore(categorie.get(), user.get());
        }

        return updateScore(score, resultat);
    }

    public List<Score> getLadder(Long categorieId) throws Exception {
        if(categorieId == null) {
            return scoreRepository.findAll();
        }

        Optional<Categorie> categorie = categorieRepository.findById(categorieId);
        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        return scoreRepository.findAllByCategorie(categorie.get());
    }

    public Score getScore(Long userId) throws Exception {
        Optional<User> optUser = userRepository.findById(userId);

        if(!optUser.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        Score score = scoreRepository.findByUser(optUser.get());

        if(score == null) {
            score = Score.builder().user(optUser.get()).build();
        }

        return score;
    }

    private Score buildDefautScore(Categorie categorie, User user) {
        return Score.builder()
                .categorie(categorie)
                .user(user)
                .score(0)
                .nbMedailleOr(0)
                .nbMedailleArgent(0)
                .nbMedailleBronze(0)
                .build();
    }

    private Score updateScore(Score score, TermineQuestionDTO reponse) {
        if(score.getScore() == 0 && !reponse.isSuccess()) {
            return score;
        }

        score.setScore(reponse.isSuccess() ? score.getScore() + 1 : score.getScore() - 1 );
        return score;
    }

    private Score updateScore(Score score, TermineQuizzDTO resultat) {
        score.setNbMedailleOr(resultat.isMedailleOr() ? score.getNbMedailleOr() + 1 : score.getNbMedailleOr());
        score.setNbMedailleArgent(resultat.isMedailleArgent() ? score.getNbMedailleArgent() + 1 : score.getNbMedailleArgent());
        score.setNbMedailleBronze(resultat.isMedailleBronze() ? score.getNbMedailleBronze() + 1 : score.getNbMedailleBronze());
        return score;
    }
}
