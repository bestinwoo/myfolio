package inhatc.project.myfolio.tag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import inhatc.project.myfolio.common.BaseTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
}
