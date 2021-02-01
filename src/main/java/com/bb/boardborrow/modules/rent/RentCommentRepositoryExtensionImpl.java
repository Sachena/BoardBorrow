package com.bb.boardborrow.modules.rent;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RentCommentRepositoryExtensionImpl extends QuerydslRepositorySupport implements RentCommentRepositoryExtension{

    public RentCommentRepositoryExtensionImpl() {
        super(RentComment.class);
    }
    @Override
    public Page<RentComment> findAll(Pageable pageable,Long rentId) {


        QRentComment rentComment = QRentComment.rentComment;
        JPQLQuery<RentComment> query = from(rentComment).where(rentComment.rent.id.eq(rentId)).distinct();
        JPQLQuery<RentComment> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<RentComment> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());


    }
}
