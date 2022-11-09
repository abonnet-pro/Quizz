package com.esimed.quizz.models.mappers;

import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.entities.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper( QuestionMapper.class );

    QuestionDTO questionToDto(Question entity);
}
