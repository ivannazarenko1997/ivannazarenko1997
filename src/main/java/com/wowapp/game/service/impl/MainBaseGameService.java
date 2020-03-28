package com.wowapp.game.service.impl;

import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.PlayAttribute;
import com.wowapp.game.enums.RoundTypeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * MainBaseGameService - class contains
 * functionality for Main Game Service
 as play next game, next round and game exit  from game.
 */
public class MainBaseGameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainBaseGameService.class);
    private static final Random STEP_RANDOM_GENERATOR = new Random();
    private static final Integer COMPUTER_RANDOM_GENERATOR_VALUE = 3;
    private static final Integer BAD_USER_RANDOM_GENERATOR_VALUE = 20;

    private static final Map<Integer, PlayAttribute> STEP_COMPUTER = new HashMap<Integer, PlayAttribute>() {
        {
            put(1, PlayAttribute.ROCK);
            put(2, PlayAttribute.SCISSOR);
            put(3, PlayAttribute.PAPER);
        }
    };
    //bad user step
    private static final Map<Integer, PlayAttribute> STEP_USER = new HashMap<Integer, PlayAttribute>() {
        {
            for (int i = 1; i < 13; i++) {
                put(i, PlayAttribute.ROCK);
            }
            for (int i = 13; i < 19; i++) {
                put(i, PlayAttribute.SCISSOR);
            }
            for (int i = 19; i < 21; i++) {
                put(i, PlayAttribute.PAPER);
            }
        }
    };
    private static final Map<PlayAttribute, Integer> MAP_WONNER_CALCULATION = new HashMap<PlayAttribute, Integer>() {
        {
            put(PlayAttribute.ROCK, 1);
            put(PlayAttribute.PAPER, 2);
            put(PlayAttribute.SCISSOR, 3);
        }
    };

    protected PlayAttribute getComputerStep() {
        int step = STEP_RANDOM_GENERATOR.nextInt(COMPUTER_RANDOM_GENERATOR_VALUE) + 1;
        return STEP_COMPUTER.get(step);
    }

    protected PlayAttribute getUserBadAuthomaticStep() {
        int step = STEP_RANDOM_GENERATOR.nextInt(BAD_USER_RANDOM_GENERATOR_VALUE) + 1;
        return STEP_USER.get(step);
    }

    protected RoundTypeResult getRoundWinner(PlayAttribute userStep, PlayAttribute computerStep) {
        LOGGER.info("<< getGameWinner");
        int userScore = MAP_WONNER_CALCULATION.get(userStep);
        int computerScore = MAP_WONNER_CALCULATION.get(computerStep) % 3;

        if (userScore % 3 == computerScore) {
            return RoundTypeResult.DRAW;
        } else if (userScore == computerScore - 1) {
            return RoundTypeResult.WIN;
        } else {
            return RoundTypeResult.LOSE;
        }
    }

    protected User convertMetaDataToUser(MetadataInfo gameInfo) {
        User updaterUser = new User();
        updaterUser.setDraws(gameInfo.getUserDraws());
        updaterUser.setLoses(gameInfo.getUserLouse());
        updaterUser.setWins(gameInfo.getUserWins());
        updaterUser.setRoundid(gameInfo.getRoundId());
        updaterUser.setLogin(gameInfo.getUserName());
        return updaterUser;
    }

}
