package inhatc.project.myfolio.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inhatc.project.myfolio.project.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
