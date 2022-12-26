package inhatc.project.myfolio.project.repository;

import static inhatc.project.myfolio.project.domain.QProject.*;
import static inhatc.project.myfolio.tag.domain.QProjectTag.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import inhatc.project.myfolio.member.domain.QMember;
import inhatc.project.myfolio.project.domain.Project;

@Repository
public class ProjectRepositoryImpl extends QuerydslRepositorySupport implements ProjectRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	public ProjectRepositoryImpl(JPAQueryFactory queryFactory) {
		super(Project.class);
		this.queryFactory = queryFactory;
	}

	@Override
	public Page<Project> findPageByTagName(String tagName, Pageable pageable) {
		List<Long> projectTagIds = queryFactory
				.selectDistinct(projectTag.project.id)
				.from(projectTag)
				.leftJoin(projectTag.tag)
				.where(likeTagName(tagName))
				.fetch();

		JPQLQuery<Project> projectQuery = from(project)
				.distinct()
				.innerJoin(project.member, QMember.member)
				.innerJoin(project.tags, projectTag)
				.where(inProjectTag(projectTagIds));

		List<Project> projectList = getQuerydsl().applyPagination(pageable, projectQuery).fetch();
		return new PageImpl<>(projectList, pageable, projectQuery.fetchCount());
	}

	private BooleanExpression likeTagName(String tagName) {
		if (!StringUtils.hasText(tagName)) {
			return null;
		}

		return projectTag.tag.name.like("%" + tagName + "%");
	}

	private BooleanExpression inProjectTag(List<Long> projectTagIds) {
		return project.id.in(projectTagIds);
	}
}
