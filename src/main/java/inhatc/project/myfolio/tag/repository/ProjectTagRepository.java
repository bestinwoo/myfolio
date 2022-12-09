package inhatc.project.myfolio.tag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.project.myfolio.tag.domain.ProjectTag;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {
	Optional<ProjectTag> findByProjectIdAndTagId(Long projectId, Long tagId);
}
