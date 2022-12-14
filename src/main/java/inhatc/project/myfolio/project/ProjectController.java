package inhatc.project.myfolio.project;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import inhatc.project.myfolio.common.PagedResponse;
import inhatc.project.myfolio.project.ProjectDto.Response.Summary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

	@Operation(summary = "프로젝트 목록 조회")
	@GetMapping
	public ResponseEntity<PagedResponse<Summary>> getProjectList(@Valid ProjectDto.Request.Find find) {
		PagedResponse<Summary> projectList = projectService.getProjectList(find);
		return ResponseEntity.ok(projectList);
	}

	@Operation(summary = "프로젝트 상세 조회")
	@GetMapping("/{id}")
	public ResponseEntity<ProjectDto.Response.Detail> getProjectDetail(@PathVariable Long id) {
		return ResponseEntity.ok(projectService.getProjectDetail(id));
	}

	@Operation(summary = "프로젝트 삭제")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "프로젝트 삭제 완료"),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 프로젝트")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProject(@PathVariable Long id) {
		projectService.deleteProject(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "프로젝트 수정")
	@PutMapping("/{id}")
	public ResponseEntity<?> modifyProject(@PathVariable Long id, @RequestBody ProjectDto.Request.Create modify) {
		projectService.modifyProject(modify, id);
		return ResponseEntity.noContent().build();
	}
}
