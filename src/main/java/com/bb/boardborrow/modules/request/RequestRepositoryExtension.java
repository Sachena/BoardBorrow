package com.bb.boardborrow.modules.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RequestRepositoryExtension {
    Page<Request> findAll(Pageable pageable);

}
