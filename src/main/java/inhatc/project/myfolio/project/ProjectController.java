package inhatc.project.myfolio.project;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
@Tag(name = "프로젝트 API")
public class ProjectController {
	private final ProjectService projectService;

	@Operation(summary = "프로젝트 생성")
	@ApiResponse(responseCode = "201", description = "프로젝트 생성 완료")
	@PostMapping
	public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDto.Request.Create create) {
		projectService.createProject(create);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	//public ResponseEntity<Page<ProjectDto.Response>>
}
