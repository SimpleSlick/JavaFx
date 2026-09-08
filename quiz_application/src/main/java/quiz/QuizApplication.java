package quiz;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class QuizApplication {
    private final List<Question> questions;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private int unansweredCount = 0;

    private boolean isAnswered = false;

    // TIMER
    private Timeline timer;

    private final int timeLimit = 15;
    private int timeRemaining;

    // MAIN UI
    private final VBox mainLayout;

    private VBox questionPanel;
    private VBox resultPanel;

    // QUESTION UI
    private Text questionNumberText;
    private Text questionText;
    private Text timerText;

    private ProgressBar timerProgress;

    private ToggleGroup optionsGroup;
    private RadioButton[] optionButtons;

    private Button submitButton;
    private Button nextButton;

    private Label feedbackLabel;

    // RESULT UI
    private Text resultTitle;
    private Text scoreText;
    private Text correctText;
    private Text incorrectText;
    private Text unansweredText;
    private Text resultFeedback;

    private Button restartButton;

    // CONSTRUCTOR 
    public QuizApplication() {
        questions = new ArrayList<>();
        initializeQuestions();
        mainLayout = new VBox(20);
        createUI();
    }

    // GET ROOT
    public VBox getRoot() {
        return mainLayout;
    }
    
    // CREATE MAIN UI
    private void createUI() {
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getStyleClass().add("main-layout");
        createQuestionPanel();
        createResultPanel();
        mainLayout.getChildren().addAll(questionPanel, resultPanel);
        showQuestionPanel();
    }

    // QUESTION PANEL
    private void createQuestionPanel() {
        questionPanel = new VBox(18);
        questionPanel.setAlignment(Pos.TOP_CENTER);
        questionPanel.setPadding(new Insets(25));
        questionPanel.getStyleClass().add("question-panel");
 
        // TITLE
        Text title = new Text("📝 QUIZ CHALLENGE");
        title.getStyleClass().add("quiz-title");

        // TIMER
        HBox timerBox = new HBox(15);
        timerBox.setAlignment(Pos.CENTER);

        timerText = new Text("⏱ Time: 15s");
        timerText.getStyleClass().add("timer-text");

        timerProgress = new ProgressBar(1);
        timerProgress.setPrefWidth(300);
        timerProgress.setPrefHeight(12);
        timerProgress.getStyleClass().add("timer-progress");

        timerBox.getChildren().addAll(timerText, timerProgress);

        // QUESTION NUMBER
        questionNumberText = new Text();
        questionNumberText.getStyleClass().add("question-number");

        // QUESTION
        questionText = new Text();
        questionText.setWrappingWidth(600);
        questionText.setTextAlignment(TextAlignment.CENTER);
        questionText.getStyleClass().add("question-text");

        // OPTIONS
        VBox optionsBox = new VBox(10);
        optionsBox.setAlignment(Pos.CENTER);
        optionsGroup = new ToggleGroup();
        optionButtons = new RadioButton[4];

        for (int i = 0; i < optionButtons.length; i++) {
            RadioButton button = new RadioButton();
            button.setToggleGroup(optionsGroup);
            button.setMaxWidth(Double.MAX_VALUE);
            button.getStyleClass().add("answer-option");
            optionButtons[i] = button;
            optionsBox.getChildren().add(button);
        }

        // FEEDBACK
        feedbackLabel = new Label();
        feedbackLabel.setWrapText(true);
        feedbackLabel.setMaxWidth(600);

        feedbackLabel.setAlignment(Pos.CENTER);

        feedbackLabel.getStyleClass().add("feedback-label");

        feedbackLabel.setVisible(false);
        feedbackLabel.setManaged(false);

        // BUTTONS
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        submitButton = new Button("✓ Submit Answer");
        submitButton.setPrefWidth(170);
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> submitAnswer());


        nextButton = new Button("→ Next Question");
        nextButton.setPrefWidth(170);
        nextButton.setVisible(false);
        nextButton.setManaged(false);
        nextButton.getStyleClass().add("next-button");
        nextButton.setOnAction(e -> nextQuestion());

        buttonBox.getChildren().addAll(submitButton, nextButton);

        // ADD EVERYTHING
        questionPanel.getChildren().addAll(
                title,
                timerBox,
                questionNumberText,
                questionText,
                optionsBox,
                feedbackLabel,
                buttonBox
        );
    }
    
    // RESULT PANEL
    private void createResultPanel() {
        resultPanel = new VBox(20);
        resultPanel.setAlignment(Pos.CENTER);
        resultPanel.setPadding(new Insets(30));
        resultPanel.getStyleClass().add("result-panel");

        // TITLE
        resultTitle = new Text("🏆 QUIZ COMPLETE!");
        resultTitle.getStyleClass().add("result-title");

        // SCORE
        scoreText = new Text();
        scoreText.getStyleClass().add("score-text");

        // STATISTICS
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(35);
        statsGrid.setVgap(15);
        statsGrid.setAlignment(Pos.CENTER);

        Label correctLabel = new Label("✓ Correct");
        correctLabel.getStyleClass().add("stat-label");


        correctText = new Text();
        correctText.getStyleClass().add("correct-value");

        Label incorrectLabel = new Label("✕ Incorrect");
        incorrectLabel.getStyleClass().add("stat-label");

        incorrectText = new Text();
        incorrectText.getStyleClass().add("incorrect-value");

        Label unansweredLabel = new Label("— Unanswered");
        unansweredLabel.getStyleClass().add("stat-label");

        unansweredText = new Text();
        unansweredText.getStyleClass().add("unanswered-value");

        statsGrid.add(correctLabel, 0, 0);
        statsGrid.add(correctText, 1, 0);
        statsGrid.add(incorrectLabel, 0, 1);
        statsGrid.add(incorrectText, 1, 1);
        statsGrid.add(unansweredLabel, 0, 2);
        statsGrid.add(unansweredText, 1, 2);

        // RESULT FEEDBACK       
        resultFeedback = new Text();
        resultFeedback.setWrappingWidth(500);
        resultFeedback.setTextAlignment(TextAlignment.CENTER);
        resultFeedback.getStyleClass().add("result-feedback");

        // RESTART
        restartButton = new Button("↻ Play Again");
        restartButton.setPrefWidth(200);
        restartButton.setPrefHeight(48);
        restartButton.getStyleClass().add("restart-button");
        restartButton.setOnAction(e -> restartQuiz());

        resultPanel.getChildren().addAll(
                resultTitle,
                scoreText,
                statsGrid,
                resultFeedback,
                restartButton
        );
    }

    // QUESTIONS
    private void initializeQuestions() {
        questions.add(
                new Question(
                        "What is the capital of France?",
                        new String[]{
                                "London",
                                "Paris",
                                "Berlin",
                                "Madrid"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "Which planet is known as the Red Planet?",
                        new String[]{
                                "Venus",
                                "Mars",
                                "Jupiter",
                                "Saturn"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "What is the largest ocean on Earth?",
                        new String[]{
                                "Atlantic Ocean",
                                "Indian Ocean",
                                "Arctic Ocean",
                                "Pacific Ocean"
                        },
                        3
                )
        );

        questions.add(
                new Question(
                        "Who wrote 'Romeo and Juliet'?",
                        new String[]{
                                "Charles Dickens",
                                "William Shakespeare",
                                "Mark Twain",
                                "Jane Austen"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "What is the chemical symbol for water?",
                        new String[]{
                                "H2O",
                                "CO2",
                                "NaCl",
                                "HCl"
                        },
                        0
                )
        );

        questions.add(
                new Question(
                        "Which country has the largest population?",
                        new String[]{
                                "India",
                                "USA",
                                "China",
                                "Indonesia"
                        },
                        0
                )
        );

        questions.add(
                new Question(
                        "What is the speed of light approximately?",
                        new String[]{
                                "300,000 km/s",
                                "150,000 km/s",
                                "500,000 km/s",
                                "100,000 km/s"
                        },
                        0
                )
        );

        questions.add(
                new Question(
                        "Who painted the Mona Lisa?",
                        new String[]{
                                "Michelangelo",
                                "Leonardo da Vinci",
                                "Raphael",
                                "Donatello"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "What is the smallest country in the world?",
                        new String[]{
                                "Monaco",
                                "Vatican City",
                                "San Marino",
                                "Liechtenstein"
                        },
                        1
                )
        );

        questions.add(
                new Question(
                        "Which element is essential for human respiration?",
                        new String[]{
                                "Nitrogen",
                                "Oxygen",
                                "Carbon Dioxide",
                                "Hydrogen"
                        },
                        1
                )
        );
    }

    // LOAD QUESTION
    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showResults();
            return;
        }

        isAnswered = false;

        Question question =
                questions.get(currentQuestionIndex);

        questionNumberText.setText(
                "Question "
                        + (currentQuestionIndex + 1)
                        + "/"
                        + questions.size()
        );

        questionText.setText(
                question.getQuestion()
        );

        String[] options = question.getOptions();

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisible(true);
            optionButtons[i].setManaged(true);
            resetOptionStyle(optionButtons[i]);
        }

        optionsGroup.selectToggle(null);

        feedbackLabel.setVisible(false);
        feedbackLabel.setManaged(false);
        feedbackLabel.setText("");

        submitButton.setVisible(true);
        submitButton.setManaged(true);
        submitButton.setDisable(false);

        nextButton.setVisible(false);
        nextButton.setManaged(false);

        startTimer();
    }
    
    // TIMER
    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timeRemaining = timeLimit;
        updateTimerDisplay();

        timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> {
                            timeRemaining--;
                            updateTimerDisplay();
                            if (timeRemaining <= 0) {
                                timer.stop();
                                handleTimeUp();
                            }
                        }
                )
        );

        timer.setCycleCount(timeLimit);
        timer.play();
    }

    // UPDATE TIMER
    private void updateTimerDisplay() {
        timerText.setText("⏱ Time: " + timeRemaining + "s");

        timerProgress.setProgress((double) timeRemaining / timeLimit);

        timerText.getStyleClass().removeAll(
                "timer-normal",
                "timer-warning",
                "timer-danger"
        );

        timerProgress.getStyleClass().removeAll(
                "timer-normal",
                "timer-warning",
                "timer-danger"
        );

        if (timeRemaining <= 5) {
            timerText.getStyleClass().add("timer-danger");

            timerProgress.getStyleClass().add("timer-danger");

        } else if (timeRemaining <= 10) {
            timerText.getStyleClass().add("timer-warning");
            timerProgress.getStyleClass().add("timer-warning");
        } else {
            timerText.getStyleClass().add("timer-normal");
            timerProgress.getStyleClass().add("timer-normal");
        }
    }

    // TIME UP
    private void handleTimeUp() {
        if (isAnswered) {
            return;
        }

        isAnswered = true;
        unansweredCount++;
        submitButton.setDisable(true);

        Question question = questions.get(currentQuestionIndex);

        int correctIndex = question.getCorrectAnswer();

        optionButtons[correctIndex]
                .getStyleClass()
                .add("answer-correct");

        showFeedback(
                "⏰ Time's up! Correct answer: "
                        + question.getOptions()[correctIndex],
                "feedback-timeout"
        );

        showNextButton();
    }
    
    // SUBMIT ANSWER
    private void submitAnswer() {
        if (isAnswered) {
            return;
        }

        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();

        if (selected == null) {
            showFeedback(
                    "⚠ Please select an answer!",
                    "feedback-incorrect"
            );
            return;
        }

        isAnswered = true;
        if (timer != null) {
            timer.stop();
        }

        int selectedIndex = -1;

        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                selectedIndex = i;
                break;
            }
        }

        Question question = questions.get(currentQuestionIndex);
        int correctIndex = question.getCorrectAnswer();

        // HIGHLIGHT ANSWERS
        optionButtons[correctIndex]
                .getStyleClass()
                .add("answer-correct");

        if (selectedIndex != correctIndex) {
            optionButtons[selectedIndex]
                    .getStyleClass()
                    .add("answer-incorrect");
        }

        // UPDATE SCORE
        if (selectedIndex == correctIndex) {
            score++;
            correctAnswers++;
            showFeedback(
                    "✓ Correct! Well done!",
                    "feedback-correct"
            );
        } else {
            incorrectAnswers++;
            showFeedback(
                    "✕ Incorrect! Correct answer: "
                            + question.getOptions()[correctIndex],
                    "feedback-incorrect"
            );
        }

        submitButton.setDisable(true);
        showNextButton();
    }

    // SHOW NEXT BUTTON
    private void showNextButton() {
        nextButton.setVisible(true);
        nextButton.setManaged(true);

        if (currentQuestionIndex < questions.size() - 1) {
            nextButton.setText("→ Next Question");
        } else {
            nextButton.setText("📊 Show Results");
        }
    }

    // NEXT QUESTION
    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= questions.size()) {
            showResults();
        } else {
            loadQuestion();
        }
    }

    // FEEDBACK
    private void showFeedback(String message, String styleClass) {
        feedbackLabel.setText(message);
        feedbackLabel.getStyleClass().removeAll(
                "feedback-correct",
                "feedback-incorrect",
                "feedback-timeout"
        );
        feedbackLabel.getStyleClass().add(styleClass);
        feedbackLabel.setVisible(true);
        feedbackLabel.setManaged(true);
    }
    
    // SHOW RESULTS
    private void showResults() {
        if (timer != null) {
            timer.stop();
        }

        questionPanel.setVisible(false);
        questionPanel.setManaged(false);

        resultPanel.setVisible(true);
        resultPanel.setManaged(true);
        scoreText.setText("Your Score: " + score + "/" + questions.size());

        correctText.setText(String.valueOf(correctAnswers));

        incorrectText.setText(String.valueOf(incorrectAnswers));

        unansweredText.setText(String.valueOf(unansweredCount));

        double percentage =(double) score / questions.size() * 100;

        if (percentage >= 90) {
            resultTitle.setText("🏆 EXCELLENT!");
            resultFeedback.setText("🌟 Excellent! You're a quiz master!");
        } else if (percentage >= 70) {
            resultTitle.setText("👏 GREAT JOB!");
            resultFeedback.setText("👏 Great job! Keep learning!");
        } else if (percentage >= 50) {
            resultTitle.setText("👍 GOOD JOB!");
            resultFeedback.setText("📖 Good effort! Review the topics you missed.");
        } else if (percentage >= 30) {
            resultTitle.setText("📚 KEEP PRACTICING!");
            resultFeedback.setText("📚 Need more practice. Don't give up!");
        } else {
            resultTitle.setText("💪 DON'T GIVE UP!");
            resultFeedback.setText("💪 Keep trying! Practice makes perfect!");
        }
    }

    // RESTART QUIZ
    private void restartQuiz() {
        if (timer != null) {
            timer.stop();
        }
        currentQuestionIndex = 0;
        score = 0;
        correctAnswers = 0;
        incorrectAnswers = 0;
        unansweredCount = 0;
        isAnswered = false;

        resultPanel.setVisible(false);
        resultPanel.setManaged(false);

        questionPanel.setVisible(true);
        questionPanel.setManaged(true);

        loadQuestion();
    }
    
    // RESET OPTION STYLE
    private void resetOptionStyle(RadioButton button) {
        button.getStyleClass().removeAll(
                "answer-correct",
                "answer-incorrect"
        );
        button.setDisable(false);
    }

    // SHOW QUESTION PANEL
    private void showQuestionPanel() {
        questionPanel.setVisible(true);
        questionPanel.setManaged(true);

        resultPanel.setVisible(false);
        resultPanel.setManaged(false);

        loadQuestion();
    }
}