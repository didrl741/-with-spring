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
    int fno;

    String filename;
    String fileOriName;
    String fileurl;
}