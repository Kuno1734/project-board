package com.fastcampus.projectboard.dmain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "content"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createAt"),
        @Index(columnList = "createBy")
})

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter @Column(nullable = false) private String title;//제목
    @Setter @Column(nullable = false, length = 3000) private String content;
    @Setter private String hashtag;//헤시테크

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) @OrderBy("id")
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();



    @CreatedDate @Column(nullable = false) private LocalDateTime createAt;//생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createBy;//생성자  @CreatedBy를 통해 누가 만들었는지는 아직 제공하지 않음 -> JPAconfig
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;//수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;//수정자


    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content ,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id); //아직 영속화 되지 않은 엔티티는 동등성 검사 자체가 의미가 없으므로 처리하지 않겠다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
