package pl.tomdal.myenglishwordsapp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tomdal.myenglishwordsapp.entity.SentenceEntity;

public interface SentenceJpaRepository extends JpaRepository<SentenceEntity, Long> {
}