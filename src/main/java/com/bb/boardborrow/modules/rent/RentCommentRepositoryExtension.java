package com.bb.boardborrow.modules.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RentCommentRepositoryExtension {

    Page<RentComment> findAll(Pageable pageable,Long rentId);

}
