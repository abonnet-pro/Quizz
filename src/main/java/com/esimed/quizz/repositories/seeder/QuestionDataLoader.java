package com.esimed.quizz.repositories.seeder;

import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.repositories.CategorieRepository;
import com.esimed.quizz.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuestionDataLoader implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public void run(String... args) {
        loadQuestionsData();
    }

    private void loadQuestionsData() {
        if (questionRepository.count() == 0) {
            loadQuestion1();
            loadQuestion2();
            loadQuestion3();
            loadQuestion4();
            loadQuestion5();
            loadQuestion6();
            loadQuestion7();
            loadQuestion8();
            loadQuestion9();
            loadQuestion10();
            loadQuestion11();
            loadQuestion12();
            loadQuestion13();
            loadQuestion14();
            loadQuestion15();
            loadQuestion16();
            loadQuestion17();
            loadQuestion18();
            loadQuestion19();
            loadQuestion20();
        }
    }

    private void loadQuestion1() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("En quelle année le paquebot Titanic a-t-il sombré ?")
                .reponse1("1912")
                .reponse2("1923")
                .reponse3("1901")
                .reponse4("1934")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion2() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("D'après le film d'Etienne Chatiliez de 1988, comment est la vie ")
                .reponse1("Un long fleuve tranquille")
                .reponse2("Comme une boite de chocolat")
                .reponse3("Semée d'embuches")
                .reponse4("Vaut la peine d'être vécue")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion3() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Combien d'étoiles comporte le drapeau américain en 2020 ?")
                .reponse1("50")
                .reponse2("48")
                .reponse3("49")
                .reponse4("51")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion4() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Quel animal est aussi appelé goupil ?")
                .reponse1("Un renard")
                .reponse2("Un escargot")
                .reponse3("Un écureil")
                .reponse4("Un hamster")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion5() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("À quel écrivain doit-on le personnage de Boule-de-Suif ?")
                .reponse1("Guy de Maupassant")
                .reponse2("Molière")
                .reponse3("Albert Camus")
                .reponse4("Romain Gary")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion6() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Dans Astérix et Obélix : Mission Cléopâtre, qui incarne Numérobis ?")
                .reponse1("Jamel Debbouze")
                .reponse2("Dieudoné")
                .reponse3("Alain Chabat")
                .reponse4("Christian Clavier")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion7() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Dans quelle ville française se trouve la Cité de l'espace ?")
                .reponse1("Toulouse")
                .reponse2("Paris")
                .reponse3("Marseille")
                .reponse4("Lille")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion8() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("En quelle année, le conte de Cendrillon a-t-il été écrit par Charles Perrault ?")
                .reponse1("1697")
                .reponse2("1497")
                .reponse3("1897")
                .reponse4("1997")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion9() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Philippe Lucas est connu pour avoir été l'entraineur de...")
                .reponse1("Laure Manaudou")
                .reponse2("Florent Manaudou")
                .reponse3("Camille Muffat")
                .reponse4("Alain Bernard")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion10() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Culture générale"))
                .description("Combien y a-t-il de signes de feu en astrologie ?")
                .reponse1("3")
                .reponse2("5")
                .reponse3("7")
                .reponse4("10")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion11() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Quelle planète est soupconnée d'avoir autrefois abrité la vie ?")
                .reponse1("Mars")
                .reponse2("La Terre")
                .reponse3("Venus")
                .reponse4("Jupiter")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion12() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Une rotation de la Terre dure environ ...")
                .reponse1("24 heures")
                .reponse2("1 heure")
                .reponse3("365 jours")
                .reponse4("3 semaines")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion13() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Selon la définition de 2006, de combien de planètes est composé le Système Solaire ?")
                .reponse1("8")
                .reponse2("12")
                .reponse3("6")
                .reponse4("10")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion14() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Quelle est la signification étymologique du mot dinosaure ?")
                .reponse1("Terrible lézard")
                .reponse2("Monstre géant")
                .reponse3("Redoutable prédateur")
                .reponse4("Animal préhistorique")
                .build();
        questionRepository.save(question);
    }
    private void loadQuestion15() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Quand les premiers dinosaures seraient-ils apparus sur Terre ?")
                .reponse1("Il y a 230 millions d'années")
                .reponse2("Il y a 20 millions d'années")
                .reponse3("Il y a 5 millions d'années")
                .reponse4("Il y a 65 millions d'années")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion16() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Laquelle de ces périodes géologiques est la plus récente ?")
                .reponse1("Le crétacé")
                .reponse2("Le carbonifère")
                .reponse3("Le trias")
                .reponse4("Le jurassique")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion17() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Combien de poils comptabilise-t-on approximativement sur la surface de la peau d'un adulte ?")
                .reponse1("1 000 000")
                .reponse2("1000")
                .reponse3("10 000")
                .reponse4("100 000")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion18() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Combien de globules rouges peut-on retrouver dans une simple goutte de sang ?")
                .reponse1("5 000 000")
                .reponse2("80 000 000 000")
                .reponse3("100 000")
                .reponse4("50")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion19() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Combien de neurones un cerveau en pleine possession de ses capacités contient-il ?")
                .reponse1("85 Milliards")
                .reponse2("100 000")
                .reponse3("25 Millions")
                .reponse4("1 Trillions")
                .build();
        questionRepository.save(question);
    }

    private void loadQuestion20() {
        Question question = Question.builder()
                .categorie(categorieRepository.findByName("Sciences"))
                .description("Quel os du corps humain est à peine plus grand qu'un grain de riz ?")
                .reponse1("L'étrier")
                .reponse2("Le radius")
                .reponse3("L'occipital")
                .reponse4("Le mandibule")
                .build();
        questionRepository.save(question);
    }
}
