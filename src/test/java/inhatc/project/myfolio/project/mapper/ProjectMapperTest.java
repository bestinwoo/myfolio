package inhatc.project.myfolio.project.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;

class ProjectMapperTest {

	@Test
	void projectDtoToProject() {
		ProjectDto.Create.CreateBuilder builder = ProjectDto.Create.builder();

		ProjectDto.Create projectDto = builder.content("Test Content")
				.githubUrl("Test Github")
				.tags(List.of("tag1", "tag2"))
				.memberId(1L)
				.thumbnailUrl("Test thumb")
				.title("Test Title")
				.webUrl("Test web")
				.build();

		Project project = ProjectMapper.INSTANCE.projectDtoToProject(projectDto);

		System.out.println(project.toString());

	}
}
