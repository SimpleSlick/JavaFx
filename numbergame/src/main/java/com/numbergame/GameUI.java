package com.numbergame;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GameUI {    
    // GAME
    private final NumberGame game;

    // ROOT
    private final StackPane root;

    // UI COMPONENTS
    private Label roundLabel;
    private Label attemptsLabel;
    private Label scoreLabel;

    private Label feedbackLabel;
    private Label statusLabel;

    private TextField guessField;

    private Button guessButton;
    private Button newRoundButton;
    private Button resetButton;

    private ProgressBar progressBar;

    // COLORS
    private static final String BACKGROUND = "#0B1020";
    private static final String CARD = "#151C32";
    private static final String CARD_LIGHT = "#1C2541";

    private static final String PRIMARY = "#6C63FF";
    private static final String PRIMARY_HOVER = "#8179FF";

    private static final String TEXT = "#F5F7FF";
    private static final String SECONDARY_TEXT = "#9CA6C0";

    private static final String SUCCESS = "#4ADE80";
    private static final String WARNING = "#FBBF24";
    private static final String ERROR = "#FB7185";

    // CONSTRUCTOR
    public GameUI() {
        game = new NumberGame();
        root = new StackPane();
        createUI();
        game.startNewRound();
        updateUI();
    }

    // GET ROOT
    public StackPane getRoot() {
        return root;
    }

    // CREATE UI
    private void createUI() {
        root.setStyle(
                "-fx-background-color: " + BACKGROUND + ";"
        );

        // MAIN CARD
        VBox card = new VBox(22);
        card.setMaxWidth(520);
        card.setPrefWidth(520);
        card.setPadding(
                new Insets(32)
        );
        card.setAlignment(
                Pos.TOP_CENTER
        );
        card.setStyle(
                "-fx-background-color: " + CARD + ";" +
                "-fx-background-radius: 24px;"
        );
        DropShadow shadow = new DropShadow();
        shadow.setRadius(30);
        shadow.setOffsetY(12);
        shadow.setColor(
                Color.rgb(0, 0, 0, 0.45)
        );
        card.setEffect(shadow);
        
        // HEADER
        Label title = new Label(
                "NUMBER GUESS"
        );
        title.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        30
                )
        );
        title.setTextFill(
                Color.web(TEXT)
        );
        Label subtitle = new Label(
                "Can you find the secret number?"
        );
        subtitle.setFont(
                Font.font(
                        "Arial",
                        14
                )
        );
        subtitle.setTextFill(
                Color.web(SECONDARY_TEXT)
        );
        VBox header = new VBox(6);

        header.setAlignment(
                Pos.CENTER
        );

        header.getChildren().addAll(
                title,
                subtitle
        );
        
        // STATS
        HBox statsBox = new HBox(12);
        statsBox.setAlignment(
                Pos.CENTER
        );
        VBox roundCard = createStatCard(
                "ROUND",
                "1"
        );
        VBox attemptsCard = createStatCard(
                "ATTEMPTS",
                "7"
        );
        VBox scoreCard = createStatCard(
                "WINS",
                "0"
        );
        roundLabel =
                (Label) roundCard.getChildren().get(1);

        attemptsLabel =
                (Label) attemptsCard.getChildren().get(1);

        scoreLabel =
                (Label) scoreCard.getChildren().get(1);
        statsBox.getChildren().addAll(
                roundCard,
                attemptsCard,
                scoreCard
        );

        // FEEDBACK
        VBox feedbackBox = new VBox(8);
        feedbackBox.setAlignment(
                Pos.CENTER
        );
        feedbackBox.setPadding(
                new Insets(20)
        );
        feedbackBox.setStyle(
                "-fx-background-color: " + CARD_LIGHT + ";" +
                "-fx-background-radius: 16px;"
        );
        statusLabel = new Label(
                "READY"
        );
        statusLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        12
                )
        );
        statusLabel.setTextFill(
                Color.web(PRIMARY)
        );
        feedbackLabel = new Label(
                "Find the secret number"
        );
        feedbackLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        17
                )
        );

        feedbackLabel.setTextFill(
                Color.web(TEXT)
        );

        feedbackLabel.setWrapText(true);


        Label hintLabel = new Label(
                "You have 7 attempts to get it right."
        );

        hintLabel.setFont(
                Font.font(
                        "Arial",
                        12
                )
        );

        hintLabel.setTextFill(
                Color.web(SECONDARY_TEXT)
        );


        feedbackBox.getChildren().addAll(
                statusLabel,
                feedbackLabel,
                hintLabel
        );


        
        // PROGRESS BAR
        

        progressBar = new ProgressBar(
                1.0
        );

        progressBar.setPrefWidth(
                400
        );

        progressBar.setPrefHeight(
                8
        );

        progressBar.setStyle(
                "-fx-accent: " + PRIMARY + ";"
        );

        // INPUT LABEL
        Label inputLabel = new Label(
                "YOUR GUESS"
        );

        inputLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        11
                )
        );

        inputLabel.setTextFill(
                Color.web(SECONDARY_TEXT)
        );

        // INPUT
        guessField = new TextField();

        guessField.setPromptText(
                "Enter a number..."
        );

        guessField.setPrefHeight(48);

        guessField.setPrefWidth(280);

        guessField.setFont(
                Font.font(
                        "Arial",
                        16
                )
        );

        guessButton = new Button(
                "GUESS"
        );

        stylePrimaryButton(
                guessButton
        );

        guessButton.setPrefHeight(48);

        guessButton.setPrefWidth(110);

        HBox inputBox = new HBox(10);

        inputBox.setAlignment(
                Pos.CENTER
        );

        inputBox.getChildren().addAll(
                guessField,
                guessButton
        );
        
        // BOTTOM BUTTONS
        newRoundButton = new Button(
                "NEW ROUND"
        );

        styleSecondaryButton(
                newRoundButton
        );

        resetButton = new Button(
                "RESET GAME"
        );

        styleDangerButton(
                resetButton
        );

        HBox bottomButtons = new HBox(10);

        bottomButtons.setAlignment(
                Pos.CENTER
        );

        bottomButtons.getChildren().addAll(
                newRoundButton,
                resetButton
        );

        newRoundButton.setVisible(false);

        newRoundButton.setManaged(false);
        
        // EVENT HANDLERS
        guessButton.setOnAction(
                e -> handleGuess()
        );

        guessField.setOnAction(
                e -> handleGuess()
        );

        newRoundButton.setOnAction(
                e -> startNewRound()
        );

        resetButton.setOnAction(
                e -> resetGame()
        );

        // ADD TO CARD
        card.getChildren().addAll(
                header,
                statsBox,
                feedbackBox,
                progressBar,
                inputLabel,
                inputBox,
                bottomButtons
        );

        root.getChildren().add(
                card
        );
    }
    
    // STAT CARD
    private VBox createStatCard(
            String title,
            String value
    ) {

        VBox card = new VBox(5);

        card.setAlignment(
                Pos.CENTER
        );

        card.setPrefWidth(145);

        card.setPadding(
                new Insets(
                        12,
                        20,
                        12,
                        20
                )
        );

        card.setStyle(
                "-fx-background-color: " +
                CARD_LIGHT +
                ";" +
                "-fx-background-radius: 14px;"
        );


        Label titleLabel = new Label(
                title
        );

        titleLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        10
                )
        );

        titleLabel.setTextFill(
                Color.web(SECONDARY_TEXT)
        );


        Label valueLabel = new Label(
                value
        );

        valueLabel.setFont(
                Font.font(
                        "Arial",
                        FontWeight.BOLD,
                        22
                )
        );

        valueLabel.setTextFill(
                Color.web(TEXT)
        );


        card.getChildren().addAll(
                titleLabel,
                valueLabel
        );


        return card;
    }

    // HANDLE GUESS
    private void handleGuess() {

        String input =
                guessField.getText().trim();


        if (input.isEmpty()) {

            showError(
                    "Enter a number first."
            );

            return;
        }


        int guess;
        try {

            guess =
                    Integer.parseInt(input);

        } catch (NumberFormatException e) {

            showError(
                    "That's not a valid number."
            );

            guessField.clear();

            return;
        }

        if (guess < 1 || guess > 100) {

            showError(
                    "Your number must be between 1 and 100."
            );

            guessField.clear();

            return;
        }

        NumberGame.GuessResult result =
                game.makeGuess(guess);


        switch (result) {

            case CORRECT : 
            handleCorrect();
            break;
            
            case TOO_LOW : 
            handleTooLow();
            break;
            
            case TOO_HIGH : 
            handleTooHigh();
            break;
            
            case GAME_OVER : 
            handleGameOver();
            break;
            
            case INVALID_RANGE : 
            showError(
                "Your number must be between 1 and 100."
            );
            break;
            
            case INACTIVE : 
            showError(
                "Start a new round first."
            );
            break;
        }

        updateUI();
    }

    // CORRECT
    private void handleCorrect() {

        statusLabel.setText(
                "YOU GOT IT!"
        );

        statusLabel.setTextFill(
                Color.web(SUCCESS)
        );


        feedbackLabel.setText(
                "Correct! The number was " +
                game.getSecretNumber() +
                "."
        );

        feedbackLabel.setTextFill(
                Color.web(SUCCESS)
        );

        guessField.setDisable(true);

        guessButton.setDisable(true);

        newRoundButton.setVisible(true);

        newRoundButton.setManaged(true);

        showWinAnimation();
    }
    
    // TOO LOW
    private void handleTooLow() {

        statusLabel.setText(
                "TOO LOW"
        );

        statusLabel.setTextFill(
                Color.web(WARNING)
        );


        feedbackLabel.setText(
                "Go higher."
        );

        feedbackLabel.setTextFill(
                Color.web(TEXT)
        );


        guessField.clear();

        guessField.requestFocus();
    }

    // TOO HIGH
    private void handleTooHigh() {

        statusLabel.setText(
                "TOO HIGH"
        );

        statusLabel.setTextFill(
                Color.web(WARNING)
        );


        feedbackLabel.setText(
                "Go lower."
        );

        feedbackLabel.setTextFill(
                Color.web(TEXT)
        );


        guessField.clear();

        guessField.requestFocus();
    }

    // GAME OVER
    private void handleGameOver() {

        statusLabel.setText(
                "GAME OVER"
        );

        statusLabel.setTextFill(
                Color.web(ERROR)
        );


        feedbackLabel.setText(
                "The number was " +
                game.getSecretNumber() +
                "."
        );

        feedbackLabel.setTextFill(
                Color.web(ERROR)
        );


        guessField.setDisable(true);

        guessButton.setDisable(true);

        newRoundButton.setVisible(true);

        newRoundButton.setManaged(true);
    }

    // ERROR
    private void showError(
            String message
    ) {

        statusLabel.setText(
                "INVALID INPUT"
        );

        statusLabel.setTextFill(
                Color.web(ERROR)
        );


        feedbackLabel.setText(
                message
        );

        feedbackLabel.setTextFill(
                Color.web(TEXT)
        );
    }

    // UPDATE UI
    private void updateUI() {

        roundLabel.setText(
                String.valueOf(
                        game.getRound()
                )
        );


        attemptsLabel.setText(
                String.valueOf(
                        game.getAttemptsLeft()
                )
        );


        scoreLabel.setText(
                String.valueOf(
                        game.getTotalRoundsWon()
                )
        );


        progressBar.setProgress(
                (double) game.getAttemptsLeft() /
                game.getMaxAttempts()
        );
    }

    // NEW ROUND
    private void startNewRound() {

        game.startNewRound();


        statusLabel.setText(
                "GUESSING"
        );

        statusLabel.setTextFill(
                Color.web(PRIMARY)
        );


        feedbackLabel.setText(
                "Find the secret number"
        );

        feedbackLabel.setTextFill(
                Color.web(TEXT)
        );

        guessField.clear();

        guessField.setDisable(false);

        guessButton.setDisable(false);

        guessField.requestFocus();

        newRoundButton.setVisible(false);

        newRoundButton.setManaged(false);

        updateUI();
    }
    
    // RESET GAME
    private void resetGame() {

        game.resetGame();

        startNewRound();
    }

    // WIN ANIMATION
    private void showWinAnimation() {

        PauseTransition pause =
                new PauseTransition(
                        Duration.millis(250)
                );

        pause.setOnFinished(
                e -> {
                    feedbackLabel.setTextFill(
                            Color.web("#A7F3D0")
                    );
                    PauseTransition pause2 =
                            new PauseTransition(
                                    Duration.millis(250)
                            );
                    pause2.setOnFinished(
                            e2 ->
                                    feedbackLabel.setTextFill(
                                            Color.web(SUCCESS)
                                    )
                    );
                    pause2.play();
                }
        );

        pause.play();
    }

    // PRIMARY BUTTON
    private void stylePrimaryButton(
            Button button
    ) {

        button.setStyle(
                "-fx-background-color: " +
                PRIMARY +
                ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-radius: 12px;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        "-fx-background-color: " +
                        PRIMARY_HOVER +
                        ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        "-fx-background-color: " +
                        PRIMARY +
                        ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-radius: 12px;" +
                        "-fx-cursor: hand;"
                )
        );
    }
    
    // SECONDARY BUTTON
    private void styleSecondaryButton(
            Button button
    ) {

        button.setStyle(
                "-fx-background-color: #252E4A;" +
                "-fx-text-fill: #D8DEEF;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-radius: 10px;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(
                e -> button.setStyle(
                        "-fx-background-color: #303B5E;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(
                e -> button.setStyle(
                        "-fx-background-color: #252E4A;" +
                        "-fx-text-fill: #D8DEEF;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-cursor: hand;"
                )
        );
    }

    // DANGER BUTTON
    private void styleDangerButton(
            Button button
    ) {
        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #7F8AA5;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10px;" +
                "-fx-border-color: #303A5A;" +
                "-fx-border-radius: 10px;" +
                "-fx-cursor: hand;"
        );
        button.setOnMouseEntered(
                e -> button.setStyle(
                        "-fx-background-color: #302238;" +
                        "-fx-text-fill: " +
                        ERROR +
                        ";" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: " +
                        ERROR +
                        ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-cursor: hand;"
                )
        );
        button.setOnMouseExited(
                e -> button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #7F8AA5;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #303A5A;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-cursor: hand;"
                )
        );
    }
}