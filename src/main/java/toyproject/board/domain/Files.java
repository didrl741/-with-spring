package toyproject.board.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Getter @Setter
public class Files {
    @Id
    @GeneratedValue()
    private Long id;

    private String filename;
    private String fileOriName;
    private String fileurl;
}