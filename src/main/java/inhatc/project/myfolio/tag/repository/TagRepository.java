package inhatc.project.myfolio.tag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.project.myfolio.tag.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	Optional<Tag> findByName(String name);
	List<Tag> findByNameContaining(String name);
}
