package com.bb.boardborrow.modules.rent;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentCommentRepository extends JpaRepository<RentComment, Long>,RentCommentRepositoryExtension {


}
