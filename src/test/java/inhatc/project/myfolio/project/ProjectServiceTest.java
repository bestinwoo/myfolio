package inhatc.project.myfolio.project;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import inhatc.project.myfolio.common.PagedResponse;
import inhatc.project.myfolio.member.domain.Member;
import inhatc.project.myfolio.project.ProjectDto.Request;
import inhatc.project.myfolio.project.ProjectDto.Response.Summary;
import inhatc.project.myfolio.project.domain.FindType;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.project.mapper.ProjectMapper;
import inhatc.project.myfolio.project.repository.ProjectRepository;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.service.ProjectTagService;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
	@Mock
	private ProjectRepository projectRepository;
	@Mock
	private ProjectTagService projectTagService;

	@InjectMocks
	private ProjectService projectService;

	private List<Project> createSampleProjectList() {
		List<Project> projectList = new ArrayList<>();
		for(int i = 1; i <= 10; i++) {
			Project project = Project.builder()
					.summary("Test Summary : " + i)
					.id((long)i)
					.member(Member.builder()
							.id((long)i)
							.name("Test Member : " + i)
							.email("Test Email : " + i)
							.oauth2Id("Test oAuth2Id : " + i)
							.build())
					.webUrl("Test Web URL : " + i)
					.title("Test Title : " + i)
					.content("Test Content : " + i)
					.thumbnailUrl("Test Thumbnail : " + i)
					.githubUrl("Test Github : " + i)
					.tags(Set.of(ProjectTag.builder()
									.id((long) i)
									.tag(Tag.builder()
											.id((long) i)
											.name("Test Tag : " + i).build())
							.build()))
					.build();

			projectList.add(project);
		}
		return projectList;
	}
	@Test
	@DisplayName("프로젝트 목록 조회 (전체)")
	public void getProjectList() {
		//given
		List<Project> sampleProjectList = createSampleProjectList();
		Request.Find find = new Request.Find();
		PageRequest of = PageRequest.of(find.getPage(), find.getSize());


		doReturn(new PageImpl<Project>(sampleProjectList, of, sampleProjectList.size()))
				.when(projectRepository)
				.findAll(any(Pageable.class));

		//when
		PagedResponse<Summary> projectList = projectService.getProjectList(find);

		//then
		assertThat(projectList).isNotNull();
		assertThat(projectList.getData()).isNotEmpty();
	}

	@Test
	@DisplayName("프로젝트 목록 조회(태그)")
	public void getProjectListByTagName() {
		//given
		Request.Find find = new Request.Find();
		find.setType(FindType.TAG);
		find.setKeyword("Test Tag : 1");

		List<Project> sampleProjectList = createSampleProjectList().stream().filter(p -> {
			return p.getTags().stream().anyMatch(t -> t.getTag().getName().equals(find.getKeyword()));
		}).collect(Collectors.toList());

		PageRequest of = PageRequest.of(find.getPage(), find.getSize());

		doReturn(new PageImpl<Project>(sampleProjectList, of, sampleProjectList.size()))
				.when(projectRepository)
				.findPageByTagName(any(String.class), any(Pageable.class));

		//when
		PagedResponse<Summary> projectList = projectService.getProjectList(find);

		//then
		assertThat(projectList).isNotNull();
		assertThat(projectList.getData()).hasSize(1);
	}

	@Test
	@DisplayName("프로젝트 목록 조회(제목)")
	public void getProjectListByTitle() {
		//given
		Request.Find find = new Request.Find();
		find.setType(FindType.TITLE);
		find.setKeyword("1");

		List<Project> sampleProjectList = createSampleProjectList().stream().filter(p -> p.getTitle().contains(find.getKeyword())).collect(Collectors.toList());

		doReturn(new PageImpl<Project>(sampleProjectList, PageRequest.of(find.getPage(), find.getSize()), sampleProjectList.size()))
				.when(projectRepository)
				.findByTitleContaining(any(String.class), any(Pageable.class));
		//when
		PagedResponse<Summary> projectList = projectService.getProjectList(find);

		//then
		assertThat(projectList).isNotNull();
		assertThat(projectList.getData()).hasSize(2);
	}


}
