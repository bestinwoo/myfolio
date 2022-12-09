package inhatc.project.myfolio.project;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.project.mapper.ProjectMapper;
import inhatc.project.myfolio.project.repository.ProjectRepository;
import inhatc.project.myfolio.tag.ProjectTagService;
import inhatc.project.myfolio.tag.TagService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final ProjectTagService projectTagService;

	public void createProject(ProjectDto.Request.Create projectDto) {
		Project project = ProjectMapper.INSTANCE.projectDtoToProject(projectDto);
		projectRepository.save(project);
		projectTagService.saveTags(project, projectDto.getTags());
	}
}

