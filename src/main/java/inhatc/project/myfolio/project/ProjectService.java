package inhatc.project.myfolio.project;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.project.mapper.ProjectMapper;
import inhatc.project.myfolio.project.repository.ProjectRepository;
import inhatc.project.myfolio.tag.TagService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final TagService tagService;

	public void createProject(ProjectDto.Create projectDto) {
		Project project = ProjectMapper.INSTANCE.projectDtoToProject(projectDto);

		project.setTags(project.getTags().stream()
				.map(t -> tagService.findOrCreateTag(t.getName()))
				.collect(Collectors.toSet()));

		projectRepository.save(project);
	}
}

