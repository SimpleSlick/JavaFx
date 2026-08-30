package com.gradecalc;

import java.util.ArrayList;
import java.util.List;

public class GradeCalculator {

    private final List<Subject> subjects;

    public GradeCalculator() {
        subjects = new ArrayList<>();
    }

    // Subject management
    public void addSubject(String name, double marks) {
        subjects.add(new Subject(name, marks));
    }

    public void removeSubject(int index) {
        if (index >= 0 && index < subjects.size()) {
            subjects.remove(index);
        }
    }

    public void clearSubjects() {
        subjects.clear();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public boolean hasSubjects() {
        return !subjects.isEmpty();
    }

    // Calculations
    public double calculateTotal() {
        double total = 0;

        for (Subject subject : subjects) {
            total += subject.getMarks();
        }

        return total;
    }

    public double calculateAverage() {
        if (subjects.isEmpty()) {
            return 0;
        }

        return calculateTotal() / subjects.size();
    }

    // Grade
    public String getGrade() {
        return getGrade(calculateAverage());
    }

    public String getGrade(double average) {
        if (average >= 90) {
            return "A+";
        } else if (average >= 80) {
            return "A";
        } else if (average >= 70) {
            return "B+";
        } else if (average >= 60) {
            return "B";
        } else if (average >= 50) {
            return "C+";
        } else if (average >= 40) {
            return "C";
        } else {
            return "F";
        }
    }

    // Grade description
    public String getGradeDescription() {
        return getGradeDescription(calculateAverage());
    }

    public String getGradeDescription(double average) {
        if (average >= 90) {
            return "🌟 Excellent! Outstanding performance!";
        } else if (average >= 80) {
            return "👏 Very Good! Great job!";
        } else if (average >= 70) {
            return "👍 Good! Keep it up!";
        } else if (average >= 60) {
            return "📖 Satisfactory! Can improve!";
        } else if (average >= 50) {
            return "📚 Average! Needs more effort!";
        } else if (average >= 40) {
            return "⚠️ Below Average! Work harder!";
        } else {
            return "❌ Fail! Need significant improvement!";
        }
    }

    // Grade CSS style
    public String getGradeStyle(double average) {
        if (average >= 90) {
            return "grade-a-plus";
        } else if (average >= 80) {
            return "grade-a";
        } else if (average >= 70) {
            return "grade-b-plus";
        } else if (average >= 60) {
            return "grade-b";
        } else if (average >= 50) {
            return "grade-c-plus";
        } else if (average >= 40) {
            return "grade-c";
        } else {
            return "grade-f";
        }
    }

    // Complete result
    public GradeResult calculateResult() {
        double total = calculateTotal();
        double average = calculateAverage();

        return new GradeResult(
                total,
                average,
                getGrade(average),
                getGradeDescription(average),
                getGradeStyle(average)
        );
    }

    public static class GradeResult {

        private final double total;
        private final double average;
        private final String grade;
        private final String description;
        private final String style;

        public GradeResult(
                double total,
                double average,
                String grade,
                String description,
                String style
        ) {
            this.total = total;
            this.average = average;
            this.grade = grade;
            this.description = description;
            this.style = style;
        }

        public double getTotal() {
            return total;
        }

        public double getAverage() {
            return average;
        }

        public String getGrade() {
            return grade;
        }

        public String getDescription() {
            return description;
        }

        public String getStyle() {
            return style;
        }
    }
}