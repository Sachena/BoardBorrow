package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.rent.RentCommentRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCommentRepository extends JpaRepository<RequestComment, Long>, RequestCommentRepositoryExtension {
}
