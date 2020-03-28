package com.wowapp.game.RockScissorsPapep;

import com.wowapp.game.controller.IGameController;
import com.wowapp.game.controller.impl.GameController;
import com.wowapp.game.dto.MetadataInfo;
import com.wowapp.game.dto.User;
import com.wowapp.game.enums.GameType;
import com.wowapp.game.enums.RoundTypeResult;
import com.wowapp.game.service.IMainGameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by pek on 2/22/2019.
 */


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest()

public class GameControllerTest {

    @Mock
    private IMainGameService mainGameService;
    @InjectMocks
    private IGameController controller = new GameController();
    private static final GameType MANUAL_GAME = GameType.MANUAL;
    private static final GameType AUTO_GAME = GameType.AUTHOMATIC;
    private static final Integer WINS = 5;
    private static final Integer ROUND_WINS = 5;
    private static final Integer LOSES = 7;
    private static final Integer ROUND_LOSES = 7;
    private static final Integer DRAWS = 9;
    private static final Integer ROUND_DRAWS = 9;
    private static final Integer FREE_ROUNDS = 3;
    private static final Boolean PLAY_NEXT_GAME_ROUND = true;
    private static final Boolean EXIT_GAME_POSITIVE_DECCISION = false;
    private static final String LOGIN = "test1";
    private static final String PASSWORD = "passw1";
    private static final Integer ZERO_VALUE = 0;

    @Test
    public void testGameControllerShouldWinUserThenAuthomaticSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_WINS);
        for (int i = 1; i < ROUND_WINS + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.WIN);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        Mockito.verify(mainGameService, Mockito.times(1)).printHelloMessage();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenUserWin(GAME_INFO, AUTO_GAME, WINS, ROUND_WINS);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinUserThenAuthomaticMultiThreeGamesRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 3;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_WINS).thenReturn(ROUND_WINS).thenReturn(ROUND_WINS);
        for (int i = 1; i < ROUND_WINS + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.WIN)
                    .thenReturn(RoundTypeResult.WIN)
                    .thenReturn(RoundTypeResult.WIN);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        MetadataInfo CORRECT_GAME_INFO =
                correctGameMetadataInfoThenUserWin(GAME_INFO, AUTO_GAME, WINS * multiGameCount, ROUND_WINS * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinComputerThenAuthomaticSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_LOSES);
        for (int i = 1; i < ROUND_LOSES + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.LOSE);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);
        controller.startGame();
        Mockito.verify(mainGameService, Mockito.times(1)).printHelloMessage();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenComputerrWin(GAME_INFO, AUTO_GAME, LOSES, ROUND_LOSES);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinComputerThenAuthomaticMultiFourGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 4;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame())
                .thenReturn(AUTO_GAME).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_LOSES)
                .thenReturn(ROUND_LOSES).thenReturn(ROUND_LOSES).thenReturn(ROUND_LOSES);
        for (int i = 1; i < ROUND_LOSES + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.LOSE)
                    .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.LOSE);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenComputerrWin(GAME_INFO, AUTO_GAME,
                LOSES * multiGameCount, ROUND_LOSES * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldDrawsThenAuthomaticSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_DRAWS);

        for (int i = 1; i < ROUND_DRAWS + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.DRAW);
        }

        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenDraws(GAME_INFO, AUTO_GAME, DRAWS, ROUND_DRAWS);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldDrawsThenAuthomaticMultiFiveGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 5;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME)
                .thenReturn(AUTO_GAME).thenReturn(AUTO_GAME).thenReturn(AUTO_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS)
                .thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS);

        for (int i = 1; i < ROUND_DRAWS + 1; i++) {
            Mockito.when(mainGameService.startPlayAuthomaticRound(i)).thenReturn(RoundTypeResult.DRAW)
                    .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.DRAW)
                    .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.DRAW);
        }

        Mockito.when(mainGameService.isPlayNextGame())
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenDraws(GAME_INFO, AUTO_GAME,
                DRAWS * multiGameCount, ROUND_DRAWS * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinUserThenManualSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(GameType.MANUAL);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_WINS);
        for (int i = 1; i < ROUND_WINS + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.WIN);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenUserWin(GAME_INFO, MANUAL_GAME, WINS, ROUND_WINS);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinUserThenManualMultiThreeGamesRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 3;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_WINS).thenReturn(ROUND_WINS).thenReturn(ROUND_WINS);
        for (int i = 1; i < ROUND_WINS + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.WIN)
                    .thenReturn(RoundTypeResult.WIN)
                    .thenReturn(RoundTypeResult.WIN);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        MetadataInfo CORRECT_GAME_INFO =
                correctGameMetadataInfoThenUserWin(GAME_INFO, MANUAL_GAME, WINS * multiGameCount, ROUND_WINS * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinComputerThenManualSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_LOSES);
        for (int i = 1; i < ROUND_LOSES + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.LOSE);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);
        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenComputerrWin(GAME_INFO, MANUAL_GAME, LOSES, ROUND_LOSES);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldWinComputerThenManualMultiFourGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 4;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame())
                .thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_LOSES)
                .thenReturn(ROUND_LOSES).thenReturn(ROUND_LOSES).thenReturn(ROUND_LOSES);
        for (int i = 1; i < ROUND_LOSES + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.LOSE)
                    .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.LOSE);
        }
        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenComputerrWin(GAME_INFO, MANUAL_GAME,
                LOSES * multiGameCount, ROUND_LOSES * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldDrawsThenManualSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_DRAWS);

        for (int i = 1; i < ROUND_DRAWS + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.DRAW);
        }

        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        Mockito.verify(mainGameService, Mockito.times(1)).printHelloMessage();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenDraws(GAME_INFO, MANUAL_GAME, DRAWS, ROUND_DRAWS);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldDrawsThenManualMultiFiveGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 5;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME)
                .thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS)
                .thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS).thenReturn(ROUND_DRAWS);

        for (int i = 1; i < ROUND_DRAWS + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.DRAW)
                    .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.DRAW)
                    .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.DRAW);
        }

        Mockito.when(mainGameService.isPlayNextGame())
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenDraws(GAME_INFO, MANUAL_GAME,
                DRAWS * multiGameCount, ROUND_DRAWS * multiGameCount);
        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldHaveDrawScoreThenManualSingleGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer gameRounds = 12;
        Integer draws = 3;
        Integer loses = 4;
        Integer wins = 5;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(gameRounds);

        Mockito.when(mainGameService.startPlayManualRound(1)).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(2)).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(3)).thenReturn(RoundTypeResult.DRAW);

        Mockito.when(mainGameService.startPlayManualRound(4)).thenReturn(RoundTypeResult.LOSE);
        Mockito.when(mainGameService.startPlayManualRound(5)).thenReturn(RoundTypeResult.LOSE);
        Mockito.when(mainGameService.startPlayManualRound(6)).thenReturn(RoundTypeResult.LOSE);
        Mockito.when(mainGameService.startPlayManualRound(7)).thenReturn(RoundTypeResult.LOSE);

        Mockito.when(mainGameService.startPlayManualRound(8)).thenReturn(RoundTypeResult.WIN);
        Mockito.when(mainGameService.startPlayManualRound(9)).thenReturn(RoundTypeResult.WIN);
        Mockito.when(mainGameService.startPlayManualRound(10)).thenReturn(RoundTypeResult.WIN);
        Mockito.when(mainGameService.startPlayManualRound(11)).thenReturn(RoundTypeResult.WIN);
        Mockito.when(mainGameService.startPlayManualRound(12)).thenReturn(RoundTypeResult.WIN);

        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoManualScore(GAME_INFO, MANUAL_GAME, wins, loses, draws, gameRounds);

        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldHaveDrawScoreThenManualMultiTwoGamesRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer gameCount = 2;
        Integer gameRounds = 12;
        Integer draws = 3 + 4 + 5;
        Integer loses = 4 + 3;
        Integer wins = 5;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame()).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(gameRounds);

        Mockito.when(mainGameService.startPlayManualRound(1))
                .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.LOSE);
        Mockito.when(mainGameService.startPlayManualRound(2))
                .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.LOSE);
        Mockito.when(mainGameService.startPlayManualRound(3))
                .thenReturn(RoundTypeResult.DRAW).thenReturn(RoundTypeResult.LOSE);

        Mockito.when(mainGameService.startPlayManualRound(4))
                .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(5))
                .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(6))
                .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(7))
                .thenReturn(RoundTypeResult.LOSE).thenReturn(RoundTypeResult.DRAW);

        Mockito.when(mainGameService.startPlayManualRound(8))
                .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(9))
                .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(10))
                .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(11))
                .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.DRAW);
        Mockito.when(mainGameService.startPlayManualRound(12))
                .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.DRAW);

        Mockito.when(mainGameService.isPlayNextGame()).
                thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();

        MetadataInfo CORRECT_GAME_INFO =
                correctGameMetadataInfoManualScore(GAME_INFO, MANUAL_GAME, wins, loses, draws, gameCount * gameRounds);

        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    @Test
    public void testGameControllerShouldHaveDrawScoreThenManualMultiThreeGameRunning() {
        User userInfo = getUserInfo();
        MetadataInfo GAME_INFO = getGameMetadataInfo(userInfo);
        Integer multiGameCount = 3;
        Integer draws = 3;
        Integer loses = 3;
        Integer wins = 3;
        Mockito.when(mainGameService.loginOrRegisterUser()).thenReturn(userInfo);
        Mockito.when(mainGameService.getTypeOfGame())
                .thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME).thenReturn(MANUAL_GAME);
        Mockito.when(mainGameService.getRoundCountUserInput()).thenReturn(FREE_ROUNDS);

        for (int i = 1; i < FREE_ROUNDS + 1; i++) {
            Mockito.when(mainGameService.startPlayManualRound(i)).thenReturn(RoundTypeResult.DRAW)
                    .thenReturn(RoundTypeResult.WIN).thenReturn(RoundTypeResult.LOSE);
        }

        Mockito.when(mainGameService.isPlayNextGame()).thenReturn(PLAY_NEXT_GAME_ROUND)
                .thenReturn(PLAY_NEXT_GAME_ROUND).thenReturn(EXIT_GAME_POSITIVE_DECCISION);

        controller.startGame();
        Mockito.verify(mainGameService, Mockito.times(1)).printHelloMessage();

        MetadataInfo CORRECT_GAME_INFO = correctGameMetadataInfoThenDraws(GAME_INFO, MANUAL_GAME, DRAWS, ROUND_DRAWS);
        correctGameMetadataInfoManualScore(GAME_INFO, MANUAL_GAME, wins, loses, draws, multiGameCount * FREE_ROUNDS);

        Mockito.verify(mainGameService, Mockito.times(1)).saveGameInfo(Mockito.eq(CORRECT_GAME_INFO));
    }

    private User getUserInfo() {
        return new User(ZERO_VALUE, LOGIN, PASSWORD, ZERO_VALUE, ZERO_VALUE, ZERO_VALUE);
    }

    private MetadataInfo getGameMetadataInfo(User infoUser) {
        MetadataInfo GAME_INFO = new MetadataInfo();
        GAME_INFO.setUserName(infoUser.getLogin());
        GAME_INFO.setUserLouse(infoUser.getLoses());
        GAME_INFO.setUserDraws(infoUser.getDraws());
        GAME_INFO.setUserWins(infoUser.getWins());
        GAME_INFO.setRoundId(infoUser.getRoundid());
        return GAME_INFO;
    }

    private MetadataInfo correctGameMetadataInfoManualScore(MetadataInfo GAME_INFO,
                                                            GameType gameType, Integer wins, Integer loses, Integer draws, Integer rounds) {
        GAME_INFO.setGameType(gameType);
        GAME_INFO.setUserWins(wins);
        GAME_INFO.setUserLouse(loses);
        GAME_INFO.setUserDraws(draws);
        GAME_INFO.setRoundId(rounds);
        return GAME_INFO;
    }

    private MetadataInfo correctGameMetadataInfoThenUserWin(
            MetadataInfo GAME_INFO, GameType gameType, Integer wins, Integer rounds) {
        GAME_INFO.setGameType(gameType);
        GAME_INFO.setUserWins(wins);
        GAME_INFO.setRoundId(rounds);
        return GAME_INFO;
    }

    private MetadataInfo correctGameMetadataInfoThenComputerrWin(
            MetadataInfo GAME_INFO, GameType gameType, Integer loses, Integer rounds) {
        GAME_INFO.setGameType(gameType);
        GAME_INFO.setUserLouse(loses);
        GAME_INFO.setRoundId(rounds);
        return GAME_INFO;
    }

    private MetadataInfo correctGameMetadataInfoThenDraws(
            MetadataInfo GAME_INFO, GameType gameType, Integer draws, Integer rounds) {
        GAME_INFO.setGameType(gameType);
        GAME_INFO.setUserDraws(draws);
        GAME_INFO.setRoundId(rounds);
        return GAME_INFO;
    }
}
