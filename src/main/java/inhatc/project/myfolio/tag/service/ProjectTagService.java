package inhatc.project.myfolio.tag.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inhatc.project.myfolio.project.domain.Project;
import inhatc.project.myfolio.tag.domain.ProjectTag;
import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.repository.ProjectTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProjectTagService {
	private final ProjectTagRepository projectTagRepository;
	private final TagService tagService;

	public void saveTags(Project project, Set<String> tags) {
		Set<ProjectTag> projectTags = tags.stream().map(tagName -> {
			Tag tag = tagService.findOrCreateTag(tagName);
			return findOrCreateProjectTag(project, tag);
		}).collect(Collectors.toSet());
		if(project.getTags() == null) {
			project.setTags(projectTags);
		} else {
			project.getTags().addAll(projectTags);
		}
	}

	public ProjectTag findOrCreateProjectTag(Project project, Tag tag) {
		return projectTagRepository.findByProjectIdAndTagId(project.getId(), tag.getId())
				.orElseGet(() -> {
					ProjectTag createdProjectTag = ProjectTag.builder().project(project).tag(tag).build();
					return projectTagRepository.save(createdProjectTag);
				});
	}

	public Set<String> findTagNameByKeyword(String keyword) {
		return projectTagRepository.findByTagNameContaining(keyword).stream().map(ProjectTag::getTag).map(Tag::getName).collect(
				Collectors.toSet());
	}
}
