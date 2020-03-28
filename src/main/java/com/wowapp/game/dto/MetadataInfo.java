package com.wowapp.game.dto;

import com.wowapp.game.enums.GameType;
import lombok.*;

/**
 * MetadataInfo -class contains metadata info of game messages
 */
@ToString
@EqualsAndHashCode
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class  MetadataInfo {
    private Integer roundId;
    private GameType gameType;
    private String userName;
    private Integer userId;
    private Integer userWins;
    private Integer userLouse;
    private Integer userDraws;
}
