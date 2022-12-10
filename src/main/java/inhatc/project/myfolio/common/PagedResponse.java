package inhatc.project.myfolio.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {
	private List<T> data;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean isLast;
}
