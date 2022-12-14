package inhatc.project.myfolio.tag;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import inhatc.project.myfolio.tag.service.ProjectTagService;
import inhatc.project.myfolio.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@Tag(name = "태그 API")
public class TagController {
	private final ProjectTagService projectTagService;

	@Operation(summary = "태그명 검색", description = "태그 자동완성을 위한 태그 검색 API")
	@GetMapping
	public ResponseEntity<Set<String>> autoCompleteTag(@RequestParam String tagName) {
		Set<String> tags = projectTagService.findTagNameByKeyword(tagName);
		return ResponseEntity.ok(tags);
	}
}
