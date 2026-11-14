
import java.util.*;

// Abstract Question class
abstract class Question {

    protected int questionId;
    protected String questionText;
    protected int marks;

    public Question(int questionId, String questionText, int marks) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.marks = marks;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getMarks() {
        return marks;
    }

    // Different question types implement this differently
    public abstract boolean evaluate(String answer);
}

// Multiple Choice Question
class MultipleChoiceQuestion extends Question {

    private List<String> options;
    private String correctAnswer;

    public MultipleChoiceQuestion(
            int id, String text, List<String> options,
            String correctAnswer, int marks) {

        super(id, text, marks);
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

// True or False Question
class TrueFalseQuestion extends Question {

    private boolean correctAnswer;

    public TrueFalseQuestion(
            int id, String text, boolean correctAnswer, int marks) {

        super(id, text, marks);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer
                && (answer.equalsIgnoreCase("true")
                || answer.equalsIgnoreCase("false"));
    }
}

// Examination class
class Examination {

    private String examId;
    private String title;
    private List<Question> questions;

    public Examination(String examId, String title) {
        this.examId = examId;
        this.title = title;
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}

// Attempt class
class Attempt {

    private Student student;
    private Examination examination;

    private Map<Question, String> answers;
    private boolean submitted;
    private int score;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        answers = new HashMap<>();
        submitted = false;
        score = 0;
    }

    public Student getStudent() {
        return student;
    }

    public Examination getExamination() {
        return examination;
    }

    // Record an answer
    public void answerQuestion(int questionId, String answer) {

        if (submitted) {
            throw new IllegalStateException(
                    "Cannot change answers after submission");
        }

        for (Question q : examination.getQuestions()) {

            if (q.getQuestionId() == questionId) {
                answers.put(q, answer);

                System.out.println("Question " + questionId
                        + " answered with '" + answer + "'.");
                return;
            }
        }

        throw new IllegalArgumentException("Question not found");
    }

    // Submit examination and calculate result
    public void submit() {

        if (submitted) {
            throw new IllegalStateException(
                    "Attempt already submitted");
        }

        score = 0;

        for (Question q : examination.getQuestions()) {

            String answer = answers.get(q);

            if (answer != null && q.evaluate(answer)) {
                score += q.getMarks();
            }
        }

        submitted = true;

        System.out.println("Examination '"
                + examination.getTitle()
                + "' submitted successfully.");

        System.out.println("Result for '"
                + examination.getTitle()
                + "' attempt: " + getCorrectCount()
                + "/" + examination.getQuestions().size()
                + " correct");
    }

    public int getCorrectCount() {

        int count = 0;

        for (Question q : examination.getQuestions()) {

            String answer = answers.get(q);

            if (answer != null && q.evaluate(answer)) {
                count++;
            }
        }

        return count;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public int getScore() {
        return score;
    }
}

// Student class
class Student {

    private String studentId;
    private String name;
    private List<Attempt> attempts;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        attempts = new ArrayList<>();
    }

    // Start an examination
    public Attempt startExam(Examination examination) {

        for (Attempt a : attempts) {
            if (a.getExamination().getExamId()
                    .equals(examination.getExamId())
                    && !a.isSubmitted()) {

                return a;
            }
        }

        Attempt attempt = new Attempt(this, examination);
        attempts.add(attempt);

        System.out.println("Examination '"
                + examination.getTitle()
                + "' started by Student.");

        return attempt;
    }

    // Submit an examination
    public void submitExam(Attempt attempt) {

        if (attempt.getStudent() != this
                || !attempts.contains(attempt)) {
            throw new IllegalArgumentException(
                    "This attempt does not belong to this student");
        }

        for (Attempt a : attempts) {

            if (a != attempt
                    && a.isSubmitted()
                    && a.getExamination().getExamId()
                    .equals(attempt.getExamination().getExamId())) {

                throw new IllegalStateException(
                        "A submitted attempt already exists");
            }
        }

        attempt.submit();
    }
}

// Main class
public class Main1 {

    public static void main(String[] args) {

        // Create student
        Student student = new Student("S101", "Rahul");

        // Create examination
        Examination exam = new Examination("E101", "Math Quiz");

        // Add questions
        exam.addQuestion(new MultipleChoiceQuestion(
                1,
                "What is 2 + 2?",
                Arrays.asList("A", "B", "C", "D"),
                "A",
                1
        ));

        exam.addQuestion(new MultipleChoiceQuestion(
                2,
                "What is 5 + 5?",
                Arrays.asList("A", "B", "C", "D"),
                "B",
                1
        ));

        // Start examination
        Attempt attempt = student.startExam(exam);

        // Answer questions
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        // Submit examination
        student.submitExam(attempt);
    }
}

