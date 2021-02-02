package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.rent.RentComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RequestCommentRepositoryExtension {

    Page<RequestComment> findAll(Pageable pageable, Long requestId);
}
