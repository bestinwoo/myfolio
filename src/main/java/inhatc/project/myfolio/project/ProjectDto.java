package inhatc.project.myfolio.project;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import inhatc.project.myfolio.member.dto.MemberDto;
import inhatc.project.myfolio.project.domain.FindType;
import inhatc.project.myfolio.tag.TagDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProjectDto {
	public static class Request {
		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		@Builder
		public static class Create {
			@NotNull(message = "회원 아이디는 필수 값입니다.")
			private Long memberId;
			@NotEmpty(message = "태그는 1개 이상 입력해야 합니다.")
			private Set<String> tags;
			@NotBlank(message = "제목은 필수 값입니다.")
			private String title;
			@NotBlank(message = "내용은 필수 값입니다.")
			private String content;
			private String githubUrl;
			private String webUrl;
			@NotBlank(message = "썸네일 링크는 필수 값입니다.")
			private String thumbnailUrl;
			@NotBlank(message = "프로젝트 개요는 필수 값입니다.")
			private String summary;
		}

		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		@Builder
		public static class Find {
			private String keyword;
			private FindType type;
			@Schema(defaultValue = "1")
			private int page = 1;
			@Schema(defaultValue = "5")
			private int size = 5;
		}

	}

	public static class Response {
		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		@Builder
		public static class Detail {
			private Long id;
			private MemberDto member;
			private Set<TagDto> tags;
			private String title;
			private String content;
			private String githubUrl;
			private String webUrl;
			private String summary;
			private LocalDateTime createdDate;
			private LocalDateTime modifiedDate;
			private String thumbnailUrl;
		}
		@Getter
		@Setter
		@AllArgsConstructor
		@NoArgsConstructor
		@Builder
		public static class Summary {
			private Long id;
			private Set<TagDto> tags;
			private String title;
			private String githubUrl;
			private String webUrl;
			private String summary;
			private LocalDateTime createdDate;
			private LocalDateTime modifiedDate;
			private String thumbnailUrl;
		}

	}

}
