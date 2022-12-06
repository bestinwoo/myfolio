package inhatc.project.myfolio.project;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import inhatc.project.myfolio.member.dto.MemberDto;
import inhatc.project.myfolio.tag.TagDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProjectDto {
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Create {
		private Long memberId;
		private Set<String> tags;
		private String title;
		private String content;
		private String githubUrl;
		private String webUrl;
		private String thumbnailUrl;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Response {
		private Long id;
		private MemberDto member;
		private List<TagDto> tags;
		private String title;
		private String content;
		private String githubUrl;
		private String webUrl;
		private LocalDateTime createDate;
		private LocalDateTime updateDate;
		private String thumbnailUrl;
	}

}
