package inhatc.project.myfolio.project;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.project.myfolio.common.PagedResponse;
import inhatc.project.myfolio.common.exception.CustomException;
import inhatc.project.myfolio.common.exception.ErrorCode;
import inhatc.project.myfolio.jwt.SecurityUtil;
import inhatc.project.myfolio.project.ProjectDto.Response.Summary;
import inhatc.project.myfolio.project.domain.FindType;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.project.mapper.ProjectMapper;
import inhatc.project.myfolio.project.repository.ProjectRepository;
import inhatc.project.myfolio.tag.ProjectTagService;
import inhatc.project.myfolio.tag.TagService;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;
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

	public PagedResponse<Summary> getProjectList(ProjectDto.Request.Find find) {
		PageRequest pageRequest = PageRequest.of(find.getPage() - 1, find.getSize(), Sort.by("createdDate"));

		Page<Project> projects = new PageImpl<Project>(List.of());
		if(find.getType() == FindType.TITLE) {
			projects = projectRepository.findByTitleContaining(find.getKeyword(), pageRequest);
		} else if(find.getType() == FindType.TAG) {
			projects = projectRepository.findPageByTagName(find.getKeyword(), pageRequest);
		} else {
			projects = projectRepository.findAll(pageRequest);
		}

		List<Summary> summaries = ProjectMapper.INSTANCE.toProjectSummaries(projects.getContent());
		return new PagedResponse<Summary>(summaries, find.getPage(), find.getSize(), projects.getTotalElements(), projects.getTotalPages(), projects.isLast());
	}

	public ProjectDto.Response.Detail getProjectDetail(Long projectId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PROJECT));

		return ProjectMapper.INSTANCE.projectToProjectDetail(project);
	}

	public void deleteProject(Long projectId) {
		Long currentMemberId = SecurityUtil.getCurrentMemberId();

		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PROJECT));

		if(!project.getMember().getId().equals(currentMemberId)) {
			throw new CustomException(ErrorCode.NO_PERMISSION);
		}
		projectRepository.delete(project);
	}

	public void modifyProject(ProjectDto.Request.Create projectDto, Long projectId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PROJECT));

		Long currentMemberId = SecurityUtil.getCurrentMemberId();
		if(!project.getMember().getId().equals(currentMemberId)) {
			throw new CustomException(ErrorCode.NO_PERMISSION);
		}

		Set<ProjectTag> deleteTags = project.getTags()
				.stream()
				.filter(old -> {
					if(!projectDto.getTags().contains(old.getTag().getName())) {
						return true;
					} else {
						projectDto.getTags().remove(old.getTag().getName());
						return false;
					}
				})
				.collect(
						Collectors.toSet());


		project.getTags().removeAll(deleteTags);
		projectTagService.saveTags(project, projectDto.getTags());
		ProjectMapper.INSTANCE.updateProjectFromDto(projectDto, project);
	}
}

