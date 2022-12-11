package inhatc.project.myfolio.tag;

import org.springframework.stereotype.Service;

import inhatc.project.myfolio.tag.domain.Tag;
import inhatc.project.myfolio.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
	private final TagRepository tagRepository;

	public Tag findOrCreateTag(String tagName) {
		Tag tag = tagRepository.findByName(tagName)
				.orElseGet(() -> {
					Tag newTag = Tag.builder()
									.name(tagName)
									.build();
					return tagRepository.save(newTag);
					}
				);

		return tag;
	}
}
