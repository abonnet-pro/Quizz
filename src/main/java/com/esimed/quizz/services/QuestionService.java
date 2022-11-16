package com.esimed.quizz.services;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.models.dtos.question.ReponseQuestionDTO;
import com.esimed.quizz.models.dtos.question.ValideQuestionDTO;
import com.esimed.quizz.models.dtos.score.ScoreDTO;
import com.esimed.quizz.models.dtos.score.TermineQuestionDTO;
import com.esimed.quizz.models.entities.Categorie;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.models.entities.Score;
import com.esimed.quizz.models.entities.User;
import com.esimed.quizz.models.mappers.ScoreMapper;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.QuestionRepository;
import com.esimed.quizz.repositories.ScoreRepository;
import com.esimed.quizz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserRepository userRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findAllByCategorie(Long id) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(id);

        if(!categorie.isPresent()) {
            throw new Exception("Categorie invalide");
        }

        return questionRepository.findAllByCategorie(categorie.get());
    }

    public void delete(Long id) throws Exception {
        Optional<Question> question = questionRepository.findById(id);

        if(!question.isPresent()) {
            throw new Exception("Question Invalide");
        }

        questionRepository.delete(question.get());
    }

    public Question create(CreateQuestionDTO question) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(question.getCategorieId());

        if(!categorie.isPresent()) {
            throw new Exception("Catégorie Invalide");
        }

        Question newQuestion = Question.builder()
                .categorie(categorie.get())
                .description(question.getDescription())
                .reponse1(question.getReponse1())
                .reponse2(question.getReponse2())
                .reponse3(question.getReponse3())
                .reponse4(question.getReponse4())
                .build();

        return questionRepository.save(newQuestion);
    }

    public Question update(Long id, CreateQuestionDTO newQuestion) throws Exception {
        Optional<Categorie> optCategorie = categorieRepository.findById(newQuestion.getCategorieId());

        if(!optCategorie.isPresent()) {
            throw new Exception("Catégorie Invalide");
        }

        Optional<Question> optQuestion = questionRepository.findById(id);

        if(!optQuestion.isPresent()) {
            throw new Exception("Question Invalide");
        }

        Question question = optQuestion.get();
        question.setDescription(newQuestion.getDescription());
        question.setReponse1(newQuestion.getReponse1());
        question.setReponse2(newQuestion.getReponse2());
        question.setReponse3(newQuestion.getReponse3());
        question.setReponse4(newQuestion.getReponse4());

        return questionRepository.save(question);
    }

    public List<Question> getByCategorie(Long categorieId) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(categorieId);

        if(!categorie.isPresent()) {
            throw new Exception("Catégorie Invalide");
        }

        List<Question> questions = questionRepository.findFirst10ByCategorie(categorie.get());

        if(questions.isEmpty()) {
            throw new Exception("Aucune questions disponibles");
        }

        return questions.stream().map(this::randomizeReponseOnQuestion).collect(Collectors.toList());
    }


    public List<Question> getRandomByCategorie(Long categorieId, int number) throws Exception {
        Optional<Categorie> categorie = categorieRepository.findById(categorieId);

        if(!categorie.isPresent()) {
            throw new Exception("Catégorie Invalide");
        }

        List<Question> questions = questionRepository.findAllByCategorie(categorie.get());

        if(questions.isEmpty()) {
            throw new Exception("Aucune questions disponibles");
        }

        if(number > questions.size()) {
            throw new Exception("Le nombre de questions demandées est supérieur au nombre de quesitons disponibles");
        }

        return getRandomQuestionsOnList(questions, number).stream().map(this::randomizeReponseOnQuestion).collect(Collectors.toList());
    }

    private Question randomizeReponseOnQuestion(Question question) {
        Random rand = new Random();
        int randomIndex;
        String reponse;
        List<String> reponses = new ArrayList<>(Arrays.asList(question.getReponse1(), question.getReponse2(), question.getReponse3(), question.getReponse4()));

        randomIndex = rand.nextInt(reponses.size());
        reponse = reponses.get(randomIndex);
        question.setReponse1(reponse);
        reponses.remove(randomIndex);

        randomIndex = rand.nextInt(reponses.size());
        reponse = reponses.get(randomIndex);
        question.setReponse2(reponse);
        reponses.remove(randomIndex);

        randomIndex = rand.nextInt(reponses.size());
        reponse = reponses.get(randomIndex);
        question.setReponse3(reponse);
        reponses.remove(randomIndex);

        randomIndex = rand.nextInt(reponses.size());
        reponse = reponses.get(randomIndex);
        question.setReponse4(reponse);
        reponses.remove(randomIndex);

        return question;
    }

    private List<Question> getRandomQuestionsOnList(List<Question> questions, int number) {
        Random rand = new Random();
        List<Question> randomQuestions = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int randomIndex = rand.nextInt(questions.size());
            Question randomQuestion = questions.get(randomIndex);
            randomQuestions.add(randomQuestion);
            questions.remove(randomIndex);
        }

        return randomQuestions;
    }

    @Transactional(rollbackOn = Exception.class)
    public ValideQuestionDTO valideQuestion(Long questionId, ReponseQuestionDTO reponse) throws Exception {
        Optional<Question> optQuestion = questionRepository.findById(questionId);

        if(!optQuestion.isPresent()) {
            throw new Exception("Quesiton invalide");
        }

        Optional<User> optUser = userRepository.findById(reponse.getUserId());

        if(!optUser.isPresent()) {
            throw new Exception("Utilisateur invalide");
        }

        Question question = optQuestion.get();
        User user = optUser.get();

        Score score = scoreService.getScore(reponse.getUserId(), question.getCategorie().getId());
        boolean success = question.getReponse1().equals(reponse.getReponse());

        TermineQuestionDTO termineQuestion = TermineQuestionDTO.builder()
                .categorieId(question.getCategorie().getId())
                .userId(user.getId())
                .success(success)
                .build();

        scoreService.updateScore(score, termineQuestion);

        return ValideQuestionDTO.builder()
                .success(success)
                .bonneReponse(question.getReponse1())
                .score(ScoreMapper.INSTANCE.scoreToDto(scoreService.getScore(user.getId())))
                .build();
    }
}
