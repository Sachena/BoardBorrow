package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.request.QRequest;
import com.bb.boardborrow.modules.request.Request;
import com.bb.boardborrow.modules.request.RequestRepositoryExtension;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class RentRepositoryExtensionImpl  extends QuerydslRepositorySupport implements RentRepositoryExtension {
    public RentRepositoryExtensionImpl() {
        super(Rent.class);
    }

    @Override
    public Page<Rent> findAll(Pageable pageable) {
        QRent rent = QRent.rent;
        JPQLQuery<Rent> query = from(rent).distinct();
        JPQLQuery<Rent> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<Rent> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }
}
