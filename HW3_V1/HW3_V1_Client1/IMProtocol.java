import java.util.HashMap;
import java.util.Map;

public class IMProtocol {
    // Named constant for each state of the conversation
    private static final int WAITING = 0;
    private static final int IN_CONVERSATION = 1;
    private static final int IN_GAME = 2;

    // Initial state
    private int state = WAITING;

    // Game state
    private int gameState = 0;

    // Game map to store prompts, decisions, and corresponding responses
    private Map<Integer, Map<String, GameResponse>> gameMap;

    public IMProtocol() {
        initializeGameMap();
    }

    private void initializeGameMap() {
        gameMap = new HashMap<>();

        // Initial game state
        Map<String, GameResponse> initialState = new HashMap<>();
        initialState.put("left", new GameResponse("You decide to go left... (continue/turn back)", 1));
        initialState.put("right", new GameResponse("You decide to go right... (continue/turn back)", 2));
        gameMap.put(0, initialState);

        // State 1
        Map<String, GameResponse> state1 = new HashMap<>();
        state1.put("continue", new GameResponse("You continue down the path... (keep going/turn back)", 3));
        state1.put("turn back", new GameResponse("You turn back... Ready to play? (yes/no)", 0));
        gameMap.put(1, state1);

        // State 2
        Map<String, GameResponse> state2 = new HashMap<>();
        state2.put("continue", new GameResponse("You continue down the path... (keep going/turn back)", 4));
        state2.put("turn back", new GameResponse("You turn back... Ready to play? (yes/no)", 0));
        gameMap.put(2, state2);

        // State 3
        Map<String, GameResponse> state3 = new HashMap<>();
        state3.put("keep going", new GameResponse("You keep going and find the treasure!", -1));
        gameMap.put(3, state3);

        // State 4
        Map<String, GameResponse> state4 = new HashMap<>();
        state4.put("keep going", new GameResponse("You keep going but find a dead end. Ready to play? (yes/no)", 0));
        gameMap.put(4, state4);
    }

    public String processInput(String theInput) {
        String theOutput = null;

        if (state == WAITING) {
            // Server lets the user know the connection has been made
            theOutput = "Connection Established. Would you like to play a game? (yes/no)";
            state = IN_CONVERSATION;
        } else if (state == IN_CONVERSATION) {
            if (theInput.equalsIgnoreCase("yes")) {
                theOutput = "Great! Let's begin the game...\n\n" +
                            "Instructions:\n" +
                            "You'll be presented with a series of prompts and decisions.\n" +
                            "Type your decision and press Enter to proceed.\n" +
                            "Your goal is to find the treasure by making the right choices.\n\n" +
                            "Ready to play? (yes/no)";
            } else if (theInput.equalsIgnoreCase("no")) {
                theOutput = "Server: Bye.";
                state = WAITING;
            } else {
                theOutput = "I didn't understand that. Please respond with 'yes' or 'no'.";
            }
        } else if (state == IN_GAME) {
            if (theInput.equalsIgnoreCase("yes")) {
                // Handle game logic here
                Map<String, GameResponse> currentState = gameMap.get(gameState);
                if (currentState != null) {
                    theOutput = currentState.get("left").response;
                } else {
                    theOutput = "Invalid game state. Ready to play? (yes/no)";
                    gameState = 0;
                }
                state = IN_GAME;
            } else if (theInput.equalsIgnoreCase("no")) {
                theOutput = "Server: Bye.";
                state = WAITING;
            } else {
                // Handle invalid input
                Map<String, GameResponse> currentState = gameMap.get(gameState);
                if (currentState != null && currentState.containsKey(theInput.toLowerCase())) {
                    GameResponse response = currentState.get(theInput.toLowerCase());
                    theOutput = response.response;
                    gameState = response.nextState;

                    // Check if the user has won the game
                    if (gameState == -1) {
                        theOutput = "Congratulations! You've found the treasure!";
                        state = WAITING;
                    }
                } else {
                    theOutput = "Invalid input. Please try again.";
                }
            }
        }

        return theOutput;
    }

    private static class GameResponse {
        private String response;
        private int nextState;

        public GameResponse(String response, int nextState) {
            this.response = response;
            this.nextState = nextState;
        }

        public String getResponse() {
            return response;
        }

        public int getNextState() {
            return nextState;
        }
    }
}
