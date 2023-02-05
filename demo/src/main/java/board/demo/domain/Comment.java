package board.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment; // 댓글 내용
}
