package com.esimed.quizz.models.mappers;

import com.esimed.quizz.models.dtos.question.QuestionDTO;
import com.esimed.quizz.models.dtos.score.ScoreDTO;
import com.esimed.quizz.models.entities.Question;
import com.esimed.quizz.models.entities.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScoreMapper {

    ScoreMapper INSTANCE = Mappers.getMapper( ScoreMapper.class );

    @Mapping(source = "user.username", target = "username")
    ScoreDTO scoreToDto(Score entity);
}
