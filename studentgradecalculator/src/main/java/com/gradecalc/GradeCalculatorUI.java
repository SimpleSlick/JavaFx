package com.gradecalc;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GradeCalculatorUI {

    private final GradeCalculator calculator;
    private final VBox mainLayout;

    // Input components
    private TextField subjectNameField;
    private TextField marksField;
    private ListView<String> subjectListView;

    // Result components
    private Text totalMarksText;
    private Text averageText;
    private Text gradeText;
    private Text gradeDescriptionText;

    // Buttons
    private Button addSubjectButton;
    private Button calculateButton;
    private Button clearButton;
    private Button removeButton;

    public GradeCalculatorUI() {
        calculator = new GradeCalculator();
        mainLayout = new VBox(18);

        createUI();
        addSampleSubjects();
    }

    public VBox getRoot() {
        return mainLayout;
    }

    private void createUI() {
        mainLayout.setPadding(new Insets(25));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getStyleClass().add("main-layout");

        Text titleText = new Text("📊 STUDENT GRADE CALCULATOR");
        titleText.getStyleClass().add("app-title");

        Text subtitleText = new Text(
                "Enter subjects and marks to calculate your final grade"
        );
        subtitleText.getStyleClass().add("app-subtitle");

        VBox inputSection = createInputSection();
        VBox resultsSection = createResultsSection();

        mainLayout.getChildren().addAll(
                titleText,
                subtitleText,
                inputSection,
                resultsSection
        );
    }

    private VBox createInputSection() {
        VBox section = new VBox(14);
        section.getStyleClass().add("section");

        Label sectionTitle = new Label("📝  Enter Subject Details");
        sectionTitle.getStyleClass().add("section-title");

        HBox inputRow = new HBox(12);
        inputRow.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Subject");
        nameLabel.getStyleClass().add("input-label");

        subjectNameField = new TextField();
        subjectNameField.setPromptText("e.g. Mathematics");
        subjectNameField.setPrefWidth(190);
        subjectNameField.getStyleClass().add("grade-text-field");

        Label marksLabel = new Label("Marks");
        marksLabel.getStyleClass().add("input-label");

        marksField = new TextField();
        marksField.setPromptText("0 - 100");
        marksField.setPrefWidth(90);
        marksField.getStyleClass().add("grade-text-field");

        addSubjectButton = new Button("+ Add Subject");
        addSubjectButton.getStyleClass().add("add-button");
        addSubjectButton.setOnAction(e -> addSubject());

        inputRow.getChildren().addAll(
                nameLabel,
                subjectNameField,
                marksLabel,
                marksField,
                addSubjectButton
        );

        subjectListView = new ListView<>();
        subjectListView.setPrefHeight(150);
        subjectListView.setMaxHeight(150);
        subjectListView.getStyleClass().add("subject-list");

        HBox buttonRow = new HBox(12);
        buttonRow.setAlignment(Pos.CENTER);

        removeButton = new Button("🗑 Remove Selected");
        removeButton.getStyleClass().add("remove-button");
        removeButton.setOnAction(e -> removeSubject());

        clearButton = new Button("↻ Clear All");
        clearButton.getStyleClass().add("clear-button");
        clearButton.setOnAction(e -> clearAll());

        calculateButton = new Button("▥ Calculate Grades");
        calculateButton.getStyleClass().add("calculate-button");
        calculateButton.setOnAction(e -> calculateGrades());

        buttonRow.getChildren().addAll(
                removeButton,
                clearButton,
                calculateButton
        );

        section.getChildren().addAll(
                sectionTitle,
                inputRow,
                subjectListView,
                buttonRow
        );

        return section;
    }

    private VBox createResultsSection() {
        VBox section = new VBox(18);
        section.setAlignment(Pos.CENTER);
        section.getStyleClass().add("result-section");

        Label sectionTitle = new Label("📊  Final Result");
        sectionTitle.getStyleClass().add("section-title");

        GridPane resultsGrid = new GridPane();
        resultsGrid.setHgap(45);
        resultsGrid.setVgap(14);
        resultsGrid.setAlignment(Pos.CENTER);

        Label totalLabel = new Label("Total Marks");
        totalLabel.getStyleClass().add("result-label");

        totalMarksText = new Text("0");
        totalMarksText.getStyleClass().add("total-result");

        Label averageLabel = new Label("Average");
        averageLabel.getStyleClass().add("result-label");

        averageText = new Text("0%");
        averageText.getStyleClass().add("average-result");

        Label gradeLabel = new Label("Final Grade");
        gradeLabel.getStyleClass().add("result-label");

        gradeText = new Text("-");
        gradeText.getStyleClass().add("grade-result");

        resultsGrid.add(totalLabel, 0, 0);
        resultsGrid.add(totalMarksText, 1, 0);

        resultsGrid.add(averageLabel, 0, 1);
        resultsGrid.add(averageText, 1, 1);

        resultsGrid.add(gradeLabel, 0, 2);
        resultsGrid.add(gradeText, 1, 2);

        gradeDescriptionText = new Text(
                "No subjects added yet"
        );
        gradeDescriptionText.getStyleClass().add(
                "grade-description"
        );

        section.getChildren().addAll(
                sectionTitle,
                resultsGrid,
                gradeDescriptionText
        );

        return section;
    }

    private void addSubject() {
        String name = subjectNameField.getText().trim();
        String marksText = marksField.getText().trim();

        if (name.isEmpty()) {
            showAlert(
                    "Error",
                    "Please enter a subject name!"
            );
            return;
        }

        if (marksText.isEmpty()) {
            showAlert(
                    "Error",
                    "Please enter marks!"
            );
            return;
        }

        try {
            double marks = Double.parseDouble(marksText);

            if (marks < 0 || marks > 100) {
                showAlert(
                        "Error",
                        "Marks must be between 0 and 100!"
                );
                return;
            }

            calculator.addSubject(name, marks);
            updateSubjectList();

            subjectNameField.clear();
            marksField.clear();
            subjectNameField.requestFocus();

        } catch (NumberFormatException e) {
            showAlert(
                    "Error",
                    "Please enter valid marks (numbers only)!"
            );
        }
    }

    private void removeSubject() {
        int selectedIndex =
                subjectListView
                        .getSelectionModel()
                        .getSelectedIndex();

        if (selectedIndex >= 0 &&
                selectedIndex < calculator.getSubjects().size()) {

            calculator.removeSubject(selectedIndex);
            updateSubjectList();

            if (calculator.hasSubjects()) {
                calculateGrades();
            } else {
                clearResults();
            }

        } else {
            showAlert(
                    "Info",
                    "Please select a subject to remove!"
            );
        }
    }

    private void clearAll() {
        calculator.clearSubjects();
        updateSubjectList();
        clearResults();
    }

    private void updateSubjectList() {
        subjectListView.getItems().clear();

        for (Subject subject : calculator.getSubjects()) {
            subjectListView.getItems().add(
                    subject.getName() +
                    "    •    " +
                    subject.getMarks() +
                    "/100"
            );
        }
    }

    private void calculateGrades() {
        if (!calculator.hasSubjects()) {
            clearResults();
            return;
        }

        GradeCalculator.GradeResult result =
                calculator.calculateResult();

        totalMarksText.setText(
                String.format(
                        "%.1f",
                        result.getTotal()
                )
        );

        averageText.setText(
                String.format(
                        "%.1f%%",
                        result.getAverage()
                )
        );

        gradeText.setText(result.getGrade());

        gradeDescriptionText.setText(
                result.getDescription()
        );

        updateGradeStyle(
                result.getStyle()
        );
    }

    private void updateGradeStyle(String gradeStyle) {
        removeGradeStyles();

        gradeText.getStyleClass().add(gradeStyle);
    }

    private void removeGradeStyles() {
        String[] styles = {
                "grade-a-plus",
                "grade-a",
                "grade-b-plus",
                "grade-b",
                "grade-c-plus",
                "grade-c",
                "grade-f"
        };

        gradeText
                .getStyleClass()
                .removeAll(styles);
    }

    private void clearResults() {
        totalMarksText.setText("0");
        averageText.setText("0%");
        gradeText.setText("-");
        gradeDescriptionText.setText(
                "No subjects added yet"
        );

        removeGradeStyles();
    }

    private void addSampleSubjects() {
        calculator.addSubject("Mathematics", 85);
        calculator.addSubject("Physics", 78);
        calculator.addSubject("Chemistry", 92);
        calculator.addSubject("Biology", 88);
        calculator.addSubject("English", 75);

        updateSubjectList();
        calculateGrades();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}