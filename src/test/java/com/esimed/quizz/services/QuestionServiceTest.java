package com.esimed.quizz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.esimed.quizz.models.dtos.question.CreateQuestionDTO;
import com.esimed.quizz.repositories.CategorieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private CategorieRepository categorieRepository;

    private static CreateQuestionDTO createQuestionDTO = CreateQuestionDTO.builder().description("test").build();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createUser_categorieInvalid_shouldThrowException() {
        when(categorieRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> questionService.create(createQuestionDTO));
        assertEquals("Catégorie Invalide", exception.getMessage());
    }
}
