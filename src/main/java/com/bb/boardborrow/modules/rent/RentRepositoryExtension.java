package com.bb.boardborrow.modules.rent;


import com.bb.boardborrow.modules.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RentRepositoryExtension {

    Page<Rent> findAll(Pageable pageable);
}
