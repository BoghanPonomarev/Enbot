package ua.nure.ponomarev.entity;

import lombok.*;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {
    private int id;
    private String name;
    private int wordCount;
}
