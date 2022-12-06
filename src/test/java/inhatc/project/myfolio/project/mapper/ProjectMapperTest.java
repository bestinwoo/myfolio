package inhatc.project.myfolio.project.mapper;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;

class ProjectMapperTest {

	@Test
	@DisplayName("프로젝트 생성 DTO -> Entity 테스트")
	void projectDtoToProject() {
		ProjectDto.Create.CreateBuilder builder = ProjectDto.Create.builder();

		ProjectDto.Create projectDto = builder.content("Test Content")
				.githubUrl("Test Github")
				.tags(Set.of("tag1", "tag2"))
				.memberId(1L)
				.thumbnailUrl("Test thumb")
				.title("Test Title")
				.webUrl("Test web")
				.build();

		Project project = ProjectMapper.INSTANCE.projectDtoToProject(projectDto);

		assertThat(project.getTags()).isNotEmpty();
		assertThat(project.getContent()).isEqualTo(projectDto.getContent());
	}
}
