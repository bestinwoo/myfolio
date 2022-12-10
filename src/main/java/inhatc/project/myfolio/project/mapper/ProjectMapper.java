package inhatc.project.myfolio.project.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.TagDto;
import inhatc.project.myfolio.tag.domain.ProjectTag;

@Mapper
public interface ProjectMapper {
	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

	@Mapping(source = "memberId", target = "member.id")
	@Mapping(target = "tags", ignore = true)
	@Mapping(target = "id", ignore = true)
	Project projectDtoToProject(
			ProjectDto.Request.Create projectDto);


	ProjectDto.Response.Detail projectToProjectDetail(
			Project project);

	List<ProjectDto.Response.Summary> toProjectSummaries(List<Project> projects);

	default Set<TagDto> projectTagToTagDto(Set<ProjectTag> tags) {
		Set<TagDto> tagDtoSet = new HashSet<>();
		for (ProjectTag tag : tags) {
			tagDtoSet.add(new TagDto(tag.getTag().getId(), tag.getTag().getName()));
		}
		return tagDtoSet;
	}
}
