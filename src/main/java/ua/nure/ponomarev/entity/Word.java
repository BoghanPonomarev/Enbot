package ua.nure.ponomarev.entity;

import lombok.*;
/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {
    private String topic;
    private String ruWord;
    private String enWord;
}
