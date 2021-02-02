package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.rent.QRentComment;
import com.bb.boardborrow.modules.rent.RentComment;
import com.bb.boardborrow.modules.rent.RentCommentRepositoryExtension;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RequestCommentRepositoryExtensionImpl extends QuerydslRepositorySupport implements RequestCommentRepositoryExtension {
    public RequestCommentRepositoryExtensionImpl() {
        super(RequestComment.class);
    }

    @Override
    public Page<RequestComment> findAll(Pageable pageable, Long requestId) {


        QRequestComment requestComment = QRequestComment.requestComment;
        JPQLQuery<RequestComment> query = from(requestComment).where(requestComment.request.id.eq(requestId)).distinct();
        JPQLQuery<RequestComment> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<RequestComment> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }
}
