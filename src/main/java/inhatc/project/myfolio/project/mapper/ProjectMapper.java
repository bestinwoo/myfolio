package inhatc.project.myfolio.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;

@Mapper
public interface ProjectMapper {
	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

	@Mapping(source = "memberId", target = "member.id")
	@Mapping(target = "tags", ignore = true)
	@Mapping(target = "id", ignore = true)
	Project projectDtoToProject(
			ProjectDto.Request.Create projectDto);

	ProjectDto.Response projectToProjectDto(
			Project project);

}
