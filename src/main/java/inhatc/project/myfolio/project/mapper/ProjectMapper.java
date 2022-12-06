package inhatc.project.myfolio.project.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.domain.Tag;

@Mapper
public interface ProjectMapper {
	ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);


	@Mapping(source = "projectDto.memberId", target = "member.id")
	Project projectDtoToProject(
			ProjectDto.Create projectDto);

	ProjectDto.Response projectToProjectDto(
			Project project);

	default Set<Tag> mappingTags(Set<String> tags) {
		return tags.stream().map(t -> {
			return Tag.builder().name(t).build();
		}).collect(Collectors.toSet());
	}
}
