package jaefactory.newboard.domain.board;

import jaefactory.newboard.domain.BaseTimeEntity;
import jaefactory.newboard.domain.comment.Comment;
import jaefactory.newboard.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Board extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "board_id")
    private Long id;

    @NotBlank
    private String title;

    @Lob //용량 큰 데이터
    @NotBlank
    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void updateBoard(String title,String content) {
        this.title = title;
        this.content = content;
    }
}
