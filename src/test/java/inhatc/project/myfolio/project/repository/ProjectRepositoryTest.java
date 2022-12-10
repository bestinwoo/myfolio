package inhatc.project.myfolio.project.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import inhatc.project.myfolio.member.domain.Member;
import inhatc.project.myfolio.member.repository.MemberRepository;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.repository.TagRepository;

@DataJpaTest
@TestPropertySource(locations="classpath:application-test.properties")
class ProjectRepositoryTest {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private MemberRepository memberRepository;

	public void createProjects() {
		ArrayList<Project> projects = new ArrayList<>();
		for(long i = 1; i <= 10; i++) {
			Tag tag = Tag.builder().name("Test Tag " + i).build();
			Member member = Member.builder().name("Test member " + i).build();
			tagRepository.save(tag);
			memberRepository.save(member);

			Project project = Project.builder()
					.id(i)
					.githubUrl("Test Github " + i)
					.thumbnailUrl("Test Thumbnail " + i)
					.content("Test Content " + i)
					.title("Test Title " + i)
					.webUrl("Test Web " + i)
					.member(member)
					.tags(Set.of(
							ProjectTag.builder().id(i).tag(tag).build()))
					.build();
			projects.add(project);
		}
		projectRepository.saveAll(projects);
	}

	@Test
	@DisplayName("프로젝트 제목 검색 테스트")
	public void searchProjectTitle() {
		createProjects();
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("createdDate"));
		Page<Project> projects = projectRepository.findByTitleContaining("Test", pageRequest);

		assertThat(projects.getContent().size()).isEqualTo(5);
		assertThat(projects.getTotalElements()).isEqualTo(10);
	}

}
