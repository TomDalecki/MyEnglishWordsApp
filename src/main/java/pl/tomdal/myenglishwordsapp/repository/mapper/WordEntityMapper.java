package pl.tomdal.myenglishwordsapp.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.tomdal.myenglishwordsapp.domain.Word;
import pl.tomdal.myenglishwordsapp.entity.WordEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WordEntityMapper {
    Word mapFromEntity(WordEntity wordEntity);

    WordEntity mapToEntity(Word word);
}
