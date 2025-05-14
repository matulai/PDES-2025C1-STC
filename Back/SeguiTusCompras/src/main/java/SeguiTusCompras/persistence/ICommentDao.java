package SeguiTusCompras.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import SeguiTusCompras.model.Comment;

public interface ICommentDao extends JpaRepository<Comment, Long> {

}
