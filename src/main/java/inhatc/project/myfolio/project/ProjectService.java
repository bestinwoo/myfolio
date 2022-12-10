package inhatc.project.myfolio.project;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.project.myfolio.project.domain.FindType;
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

	public List<ProjectDto.Response.Summary> getProjectList(ProjectDto.Request.Find find) {
		PageRequest pageRequest = PageRequest.of(find.getPage() - 1, find.getSize(), Sort.by("createdDate"));
	//	if(find.getType() == FindType.TITLE) {
			Page<Project> projects = projectRepository.findByTitleContaining(find.getKeyword(), pageRequest);
		//}
		return ProjectMapper.INSTANCE.toProjectSummaries(projects.getContent());
	}
}

