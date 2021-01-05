package com.bb.boardborrow.modules.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RequestRepository extends JpaRepository<Request, Long> ,RequestRepositoryExtension{



}
