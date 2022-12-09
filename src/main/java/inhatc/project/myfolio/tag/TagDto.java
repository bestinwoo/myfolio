package inhatc.project.myfolio.tag;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagDto implements Serializable {
	private final Long id;
	private final String name;
}
