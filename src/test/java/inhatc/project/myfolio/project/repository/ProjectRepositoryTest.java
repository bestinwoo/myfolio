package inhatc.project.myfolio.project.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.aspectj.lang.annotation.Before;
import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;

import inhatc.project.myfolio.TestConfig;
import inhatc.project.myfolio.member.domain.Member;
import inhatc.project.myfolio.member.repository.MemberRepository;
import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.repository.ProjectTagRepository;
import inhatc.project.myfolio.tag.repository.TagRepository;

@DataJpaTest
@TestPropertySource(locations="classpath:application-test.properties")
@Import(TestConfig.class)
class ProjectRepositoryTest {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private ProjectTagRepository projectTagRepository;
	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void createProjects() {
		for(long i = 1; i <= 10; i++) {
			String prefix = i % 3 == 0 ? "Before" : "After";
			Tag tag = Tag.builder().name(prefix + " Tag " + i).build();
			Member member = Member.builder().name("Test member " + i).build();
			tagRepository.save(tag);
			memberRepository.save(member);

			Project project = Project.builder()
					.githubUrl("Test Github " + i)
					.thumbnailUrl("Test Thumbnail " + i)
					.content("Test Content " + i)
					.title(prefix + " Title " + i)
					.webUrl("Test Web " + i)
					.member(member)
					.build();
			projectRepository.save(project);

			ProjectTag pt = ProjectTag.builder().tag(tag).project(project).build();
			projectTagRepository.save(pt);
			project.setTags(Set.of(pt));
		}
	}

	@Test
	@DisplayName("프로젝트 제목 검색 테스트")
	public void searchProjectTitle() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("createdDate"));
		Page<Project> projects = projectRepository.findByTitleContaining("After", pageRequest);

		assertThat(projects.getContent().size()).isEqualTo(5);
		assertThat(projects.getTotalElements()).isEqualTo(7);
	}

	@Test
	@DisplayName("프로젝트 태그 검색 테스트")
	public void searchProjectTag() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("createdDate"));
		Page<Project> projects = projectRepository.findPageByTagName("After", pageRequest);

		assertThat(projects.getContent().size()).isEqualTo(5);
		assertThat(projects.getTotalElements()).isEqualTo(7);
	}

}
