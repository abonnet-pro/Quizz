package com.esimed.quizz.models.mappers;

import com.esimed.quizz.models.dtos.categorie.CategorieDTO;
import com.esimed.quizz.models.entities.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategorieMapper {

    CategorieMapper INSTANCE = Mappers.getMapper( CategorieMapper.class );

    CategorieDTO categorieToDto(Categorie categorie);
}
