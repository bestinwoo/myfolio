package inhatc.project.myfolio.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;

public interface ProjectRepositoryCustom {
	Page<Project> findPageByTagName(String tagName, Pageable pageable);
}
