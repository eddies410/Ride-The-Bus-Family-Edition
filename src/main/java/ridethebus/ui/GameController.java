package ridethebus.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ridethebus.GuessesStrategy.HigherLowerGuess;
import ridethebus.GuessesStrategy.InsideOutsideGuess;
import ridethebus.GuessesStrategy.RedBlackGuess;
import ridethebus.GuessesStrategy.SuitGuess;
import ridethebus.PlayerStrategy.IPlayerStrategy;
import ridethebus.cards.ICard;
import ridethebus.characters.HumanPlayer;
import ridethebus.characters.IPlayer;
import ridethebus.game.*;
import ridethebus.PlayerStrategy.BotStrategy;
import ridethebus.game.PlayerState;

import java.util.*;

/**
 * REST controller for the credit-based Ride the Bus game.
 *
 * Endpoints:
 *   POST /api/game/create          — create game, choose mode and deck type
 *   GET  /api/game/{id}            — get full game state
 *   POST /api/game/{id}/wager      — place wager before round
 *   POST /api/game/{id}/guess      — submit a guess for current question
 *   POST /api/game/{id}/cashout    — cash out after a correct guess
 *   POST /api/game/{id}/next-round — advance to next round
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GameController {

    private final IPlayerStrategy botStrategy = new BotStrategy();
    private final Map<String, CreditGame> games = new HashMap<>();
    private final Map<String, IPlayer> botPlayers = new HashMap<>();

    @PostMapping("/game/create")
    public ResponseEntity<?> createGame(@RequestBody Map<String, String> body) {
        try {
            String p1Name  = body.getOrDefault("player1", "Player 1").trim();
            String mode    = body.getOrDefault("mode", "vs-bot");
            String deckType = body.getOrDefault("deckType", "standard");

            String gameId = UUID.randomUUID().toString().substring(0, 8);
            CreditGame game = new CreditGame(deckType);

            IPlayer p1 = new HumanPlayer(p1Name);
            game.addPlayer(p1);

            if ("vs-bot".equals(mode)) {
                IPlayer bot = new HumanPlayer("Bot");
                game.addPlayer(bot);
                botPlayers.put(gameId, bot);
            } else {
                String p2Name = body.getOrDefault("player2", "Player 2").trim();
                game.addPlayer(new HumanPlayer(p2Name));
            }

            game.startNextRound();
            games.put(gameId, game);

            return ResponseEntity.ok(buildState(gameId, game, null, null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<?> getGame(@PathVariable String gameId) {
        CreditGame game = games.get(gameId);
        if (game == null) return notFound(gameId);
        return ResponseEntity.ok(buildState(gameId, game, null, null, null));
    }

    @PostMapping("/game/{gameId}/wager")
    public ResponseEntity<?> placeWager(@PathVariable String gameId,
                                        @RequestBody Map<String, Object> body) {
        CreditGame game = games.get(gameId);
        if (game == null) return notFound(gameId);

        String playerName = (String) body.get("playerName");
        int wager = ((Number) body.get("wager")).intValue();

        PlayerState ps = game.getStateByName(playerName);
        if (ps == null) return ResponseEntity.badRequest().body(Map.of("error", "Player not found"));

        boolean ok = game.placeWager(ps.getPlayer(), wager);
        if (!ok) return ResponseEntity.badRequest().body(Map.of("error", "Invalid wager"));

        // Auto-wager for bot
        IPlayer bot = botPlayers.get(gameId);
        if (bot != null) {
            PlayerState botState = game.getState(bot);
            if (botState != null && !botState.isRoundDone() && botState.getWager() == 0) {
                game.placeWager(bot, botStrategy.decideWager(botState.getCredits()));
            }
        }

        return ResponseEntity.ok(buildState(gameId, game, null, null, null));
    }

    @PostMapping("/game/{gameId}/guess")
    public ResponseEntity<?> submitGuess(@PathVariable String gameId,
                                         @RequestBody Map<String, String> body) {
        CreditGame game = games.get(gameId);
        if (game == null) return notFound(gameId);

        String playerName = body.get("playerName");
        String guessValue = body.get("guess");

        PlayerState ps = game.getStateByName(playerName);
        if (ps == null) return ResponseEntity.badRequest().body(Map.of("error", "Player not found"));
        if (ps.isRoundDone()) return ResponseEntity.badRequest().body(Map.of("error", "Round already done"));
        if (ps.getWager() == 0) return ResponseEntity.badRequest().body(Map.of("error", "Place a wager first"));

        int question = ps.getCurrentQuestion();
        IGuess guess = buildGuess(question, guessValue, ps.getDealtCards());
        if (guess == null) return ResponseEntity.badRequest().body(Map.of("error", "Invalid guess"));

        ICard card = game.processGuess(ps.getPlayer(), guess);
        boolean correct = guess.isCorrect(card);

        // Check for joker — always a loss regardless of guess
        boolean joker = card.getSuit().equals("Joker");

        Integer winnings = null;
        if (!joker && correct && ps.getCurrentQuestion() >= 4) {
            winnings = game.autoWin(ps.getPlayer());
        }

        String botEvent = runBotTurn(gameId, game);
        Map<String, Object> state = buildState(gameId, game, card, joker ? false : correct, winnings);
        if (joker) state.put("joker", true);
        if (botEvent != null) state.put("botEvent", botEvent);
        return ResponseEntity.ok(state);
    }

    @PostMapping("/game/{gameId}/cashout")
    public ResponseEntity<?> cashOut(@PathVariable String gameId,
                                     @RequestBody Map<String, String> body) {
        CreditGame game = games.get(gameId);
        if (game == null) return notFound(gameId);
        PlayerState ps = game.getStateByName(body.get("playerName"));
        if (ps == null) return ResponseEntity.badRequest().body(Map.of("error", "Player not found"));
        if (ps.isRoundDone()) return ResponseEntity.ok(buildState(gameId, game, null, null, null));
        int winnings = game.cashOut(ps.getPlayer());
        return ResponseEntity.ok(buildState(gameId, game, null, null, winnings));
    }

    @PostMapping("/game/{gameId}/next-round")
    public ResponseEntity<?> nextRound(@PathVariable String gameId) {
        CreditGame game = games.get(gameId);
        if (game == null) return notFound(gameId);
        if (!game.isRoundComplete()) return ResponseEntity.badRequest().body(Map.of("error", "Round not complete"));
        if (game.isGameOver()) return ResponseEntity.ok(buildState(gameId, game, null, null, null));
        game.startNextRound();
        return ResponseEntity.ok(buildState(gameId, game, null, null, null));
    }

    // ---- Helpers ----

    private String runBotTurn(String gameId, CreditGame game) {
        IPlayer bot = botPlayers.get(gameId);
        if (bot == null) return null;
        PlayerState bs = game.getState(bot);
        if (bs == null || bs.isRoundDone() || bs.getWager() == 0) return null;

        StringBuilder log = new StringBuilder();
        while (!bs.isRoundDone()) {
            int q = bs.getCurrentQuestion();
            IGuess guess = botStrategy.makeGuess(q, bs.getDealtCards());
            ICard card = game.processGuess(bot, guess);
            boolean joker = card.getSuit().equals("Joker");
            boolean correct = !joker && guess.isCorrect(card);

            if (joker) {
                log.append("Bot drew a Joker — instant loss! ");
                break;
            }

            log.append("Bot: ").append(guess.getGuessDescription())
                    .append(" → ").append(card.getDisplayName())
                    .append(correct ? " ✓" : " ✗").append(". ");

            if (correct && bs.getCurrentQuestion() >= 4) { game.autoWin(bot); break; }
            if (correct && botStrategy.shouldCashOut(bs.getCurrentQuestion())) {
                int w = game.cashOut(bot);
                log.append("Cashed out ").append(w).append(" credits.");
                break;
            }
        }
        return log.toString();
    }

    private IGuess buildGuess(int question, String value, List<ICard> dealt) {
        return switch (question) {
            case 0 -> new RedBlackGuess(value);
            case 1 -> dealt.size() >= 1 ? new HigherLowerGuess(value, dealt.get(0)) : null;
            case 2 -> dealt.size() >= 2 ? new InsideOutsideGuess(value, dealt.get(0), dealt.get(1)) : null;
            case 3 -> new SuitGuess(value);
            default -> null;
        };
    }

    private Map<String, Object> buildState(String gameId, CreditGame game,
                                           ICard lastCard, Boolean lastCorrect, Integer lastWinnings) {
        Map<String, Object> s = new LinkedHashMap<>();
        s.put("gameId", gameId);
        s.put("round", game.getCurrentRound());
        s.put("totalRounds", CreditGame.TOTAL_ROUNDS);
        s.put("isOver", game.isGameOver());
        s.put("roundComplete", game.isRoundComplete());
        if (game.isGameOver() && game.getWinner() != null) s.put("winner", game.getWinner().getName());

        List<Map<String, Object>> players = new ArrayList<>();
        for (PlayerState ps : game.getPlayerStates()) {
            Map<String, Object> p = new LinkedHashMap<>();
            p.put("name", ps.getPlayer().getName());
            p.put("credits", ps.getCredits());
            p.put("wager", ps.getWager());
            p.put("currentQuestion", ps.getCurrentQuestion());
            p.put("roundDone", ps.isRoundDone());
            p.put("bankrupt", ps.isBankrupt());
            p.put("isBot", botPlayers.containsKey(gameId) &&
                    botPlayers.get(gameId) == ps.getPlayer());
            players.add(p);
        }
        s.put("players", players);
        s.put("multipliers", CreditGame.MULTIPLIERS);

        if (lastCard != null) {
            s.put("lastCard", Map.of("name", lastCard.getDisplayName(),
                    "suit", lastCard.getSuit(), "value", lastCard.getValue()));
            s.put("lastCorrect", lastCorrect);
        }
        if (lastWinnings != null) s.put("lastWinnings", lastWinnings);
        return s;
    }

    private ResponseEntity<Map<String, String>> notFound(String id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Game not found: " + id));
    }
}