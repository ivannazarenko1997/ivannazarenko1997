package com.wowapp.game.dto;

import lombok.*;
import org.joda.time.DateTime;


/**
 * MetadataInfo -class contains   info of user
 */
@EqualsAndHashCode
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    private Integer roundid;
    private String login;
    private String password;
    private Integer wins;
    private Integer loses;
    private Integer draws;

}
