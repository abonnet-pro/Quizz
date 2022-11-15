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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private UserRepository userRepository;

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

        updateScore(score, resultat);

        return getScore(user.get().getId());
    }

    public List<Score> getLadder(Long categorieId) throws Exception {
        if(categorieId == null) {
            List<Long> ids = scoreRepository.findAllAvailable();
            return sortByScore(ids.stream().map(i -> {
                try {
                    return getScore(i);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList()));
        }

        Optional<Categorie> categorie = categorieRepository.findById(categorieId);
        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        return sortByScore(scoreRepository.findAllByCategorie(categorie.get()));
    }

    private List<Score> sortByScore(List<Score> scores) {
        return scores.stream()
                .sorted(Comparator.comparingInt(Score::getScore).reversed())
                .collect(Collectors.toList());
    }

    public Score getScore(Long userId) throws Exception {
        Optional<User> optUser = userRepository.findById(userId);

        if(!optUser.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        List<Score> scores = scoreRepository.findAllByUser(optUser.get());

        int scoreTotal = 0;
        int nbMedailleOrTotal = 0;
        int nbMedailleArgentTotal = 0;
        int nbMedailleBronzeTotal = 0;

        if(scores.isEmpty()) {
            return Score.builder().user(optUser.get()).build();
        }

        for(Score s : scores) {
            scoreTotal += s.getScore();
            nbMedailleOrTotal += s.getNbMedailleOr();
            nbMedailleArgentTotal += s.getNbMedailleArgent();
            nbMedailleBronzeTotal += s.getNbMedailleBronze();
        }

        return Score.builder()
                .user(optUser.get())
                .score(scoreTotal)
                .nbMedailleOr(nbMedailleOrTotal)
                .nbMedailleArgent(nbMedailleArgentTotal)
                .nbMedailleBronze(nbMedailleBronzeTotal)
                .build();
    }

    public Score getScore(Long userId, Long categorieId) throws Exception {
        Optional<User> optUser = userRepository.findById(userId);

        if(!optUser.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        Optional<Categorie> optCategorie = categorieRepository.findById(categorieId);

        if(!optCategorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        Score score = scoreRepository.findByCategorieAndUser(optCategorie.get(), optUser.get());

        if(score == null) {
            score = Score.builder()
                    .categorie(optCategorie.get())
                    .user(optUser.get())
                    .build();
        }

        return score;
    }

    public Score buildDefautScore(Categorie categorie, User user) {
        return Score.builder()
                .categorie(categorie)
                .user(user)
                .score(0)
                .nbMedailleOr(0)
                .nbMedailleArgent(0)
                .nbMedailleBronze(0)
                .build();
    }

    public void updateScore(Score score, TermineQuestionDTO reponse) {
        if(score.getScore() == 0 && !reponse.isSuccess()) {
            return;
        }

        score.setScore(reponse.isSuccess() ? score.getScore() + 1 : score.getScore() - 1 );
        scoreRepository.save(score);
    }

    public void updateScore(Score score, TermineQuizzDTO resultat) {
        score.setNbMedailleOr(resultat.isMedailleOr() ? score.getNbMedailleOr() + 1 : score.getNbMedailleOr());
        score.setNbMedailleArgent(resultat.isMedailleArgent() ? score.getNbMedailleArgent() + 1 : score.getNbMedailleArgent());
        score.setNbMedailleBronze(resultat.isMedailleBronze() ? score.getNbMedailleBronze() + 1 : score.getNbMedailleBronze());
        scoreRepository.save(score);
    }
}
