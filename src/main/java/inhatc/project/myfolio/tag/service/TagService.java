package inhatc.project.myfolio.tag.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository;

	public Tag findOrCreateTag(String tagName) {
		return tagRepository.findByName(tagName).orElseGet(() -> createTag(tagName));
	}

	private Tag createTag(String tagName) {
		Tag tag = Tag.builder().name(tagName).build();
		return tagRepository.save(tag);
	}

	public List<String> findByTagName(String tagName) {
		return tagRepository.findByNameContaining(tagName).stream().map(Tag::getName).collect(Collectors.toList());
	}
}
