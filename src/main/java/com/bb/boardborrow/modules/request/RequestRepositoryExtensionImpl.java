package com.bb.boardborrow.modules.request;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RequestRepositoryExtensionImpl extends QuerydslRepositorySupport implements RequestRepositoryExtension {
    public RequestRepositoryExtensionImpl() {
        super(Request.class);
    }

    @Override
    public Page<Request> findAll(Pageable pageable) {


        QRequest request = QRequest.request;
        JPQLQuery<Request> query = from(request).distinct();
        JPQLQuery<Request> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Request> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }
}
