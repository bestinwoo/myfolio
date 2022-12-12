package inhatc.project.myfolio.project.mapper;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import inhatc.project.myfolio.member.domain.Member;
import inhatc.project.myfolio.project.ProjectDto;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;

class ProjectMapperTest {

	@Test
	@DisplayName("프로젝트 생성 DTO -> Entity 테스트")
	void projectDtoToProject() {
		ProjectDto.Request.Create.CreateBuilder builder = ProjectDto.Request.Create.builder();

		ProjectDto.Request.Create projectDto = builder.content("Test Content")
				.githubUrl("Test Github")
				.tags(Set.of("tag1", "tag2"))
				.memberId(1L)
				.thumbnailUrl("Test thumb")
				.title("Test Title")
				.webUrl("Test web")
				.build();

		Project project = ProjectMapper.INSTANCE.projectDtoToProject(projectDto);


		assertThat(project.getContent()).isEqualTo(projectDto.getContent());
	}
	public List<Project> createProjects(int size) {
		ArrayList<Project> projects = new ArrayList<>();
		for(long i = 0; i < size; i++) {
			Project project = Project.builder()
					.id(i)
					.githubUrl("Test Github " + i)
					.thumbnailUrl("Test Thumbnail " + i)
					.content("Test Content " + i)
					.title("Test Title " + i)
					.summary("Test Summary " + i)
					.webUrl("Test Web " + i)
					.member(Member.builder().id(i).name("Test member " + i).build())
					.tags(Set.of(
							ProjectTag.builder().id(i).tag(Tag.builder().id(i).name("Test Tag " + i).build()).build()))
					.build();
			projects.add(project);
		}
		return projects;
	}
	@Test
	@DisplayName("Project Entity -> Detail 테스트")
	public void projectToSummary() {
		Project project = createProjects(1).get(0);
		ProjectDto.Response.Detail detail = ProjectMapper.INSTANCE.projectToProjectDetail(project);

		assertThat(detail).isNotNull();
		assertThat(detail.getContent()).isEqualTo(project.getContent());
	}

	@Test
	@DisplayName("Project Entity List -> Summary Dto List")
	public void projectListToSummaries() {
		List<Project> projects = createProjects(10);
		List<ProjectDto.Response.Summary> summaries = ProjectMapper.INSTANCE.toProjectSummaries(projects);

		assertThat(summaries).isNotEmpty();
	}

	@Test
	@DisplayName("Project Entity Update From Dto")
	public void projectUpdateFromDto() {
		Project project = createProjects(1).get(0);
		ProjectDto.Request.Create dto = ProjectDto.Request.Create.builder()
				.memberId(2L)
				.tags(Set.of("Test Tag 0", "Test Tag1"))
				.title("Modify Test")
				.content("Modify Content")
				.summary("Modify Summary")
				.githubUrl("http://modify.com")
				.webUrl("http://modifyweb.com")
				.thumbnailUrl("modifiy thumnail")
				.build();
		ProjectMapper.INSTANCE.updateProjectFromDto(dto, project);

		assertThat(project.getContent()).isEqualTo(dto.getContent());
		assertThat(project.getId()).isEqualTo(project.getId());
	}
}
