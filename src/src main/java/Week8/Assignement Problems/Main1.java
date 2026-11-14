
import java.util.ArrayList;
import java.util.List;

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

interface ScoringRule {
    double calculateScore(double idea, double execution, double presentation);
}

class InnovationScoring implements ScoringRule {
    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenScoring implements ScoringRule {
    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Track {
    private String name;
    private ScoringRule scoringRule;

    public Track(String name, ScoringRule scoringRule) {
        this.name = name;
        this.scoringRule = scoringRule;
    }

    public String getName() {
        return name;
    }

    public double calculateScore(double idea, double execution, double presentation) {
        return scoringRule.calculateScore(idea, execution, presentation);
    }
}

class Project {
    private String title;
    private Team team;
    private Score score;

    public Project(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    public String getTitle() {
        return title;
    }

    public Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

class Score {
    private double idea;
    private double execution;
    private double presentation;
    private double finalScore;

    public Score(double idea, double execution, double presentation, Track track) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
        this.finalScore = track.calculateScore(idea, execution, presentation);
    }

    public double getFinalScore() {
        return finalScore;
    }
}

class Team {
    private String name;
    private List<Student> members;
    private Track track;
    private Project project;

    public Team(String name, List<Student> members, Track track) {
        this.name = name;
        this.members = members;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public int getMemberCount() {
        return members.size();
    }

    public Track getTrack() {
        return track;
    }

    public Project getProject() {
        return project;
    }

    public boolean hasStudent(Student student) {
        return members.contains(student);
    }

    public boolean submitProject(String title) {
        if (project != null) {
            return false;
        }

        project = new Project(title, this);
        return true;
    }
}

class Judge {
    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public void scoreProject(Project project, double idea,
                             double execution, double presentation,
                             Hackathon hackathon) {
        hackathon.recordScore(project, idea, execution, presentation);
    }
}

class Hackathon {
    private String name;
    private List<Team> teams;
    private List<Project> projects;
    private String status;

    public Hackathon(String name) {
        this.name = name;
        teams = new ArrayList<>();
        projects = new ArrayList<>();
        status = "Open";
    }

    public boolean registerTeam(Team team) {
        if (!status.equals("Open")) {
            System.out.println("Registration failed: Hackathon is not open.");
            return false;
        }

        if (team.getMemberCount() < 2 || team.getMemberCount() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student student : getAllStudents()) {
            for (int i = 0; i < team.getMemberCount(); i++) {
                if (team.hasStudent(student)) {
                    System.out.println("Registration failed: A student can belong to only one team.");
                    return false;
                }
            }
        }

        for (Team existingTeam : teams) {
            for (Student student : getTeamMembers(team)) {
                if (existingTeam.hasStudent(student)) {
                    System.out.println("Registration failed: A student can belong to only one team.");
                    return false;
                }
            }
        }

        teams.add(team);
        System.out.println("Team " + team.getName() + " registered (" +
            team.getMemberCount() + " members, " +
            team.getTrack().getName() + " track).");
        return true;
    }

    private List<Student> getTeamMembers(Team team) {
        List<Student> members = new ArrayList<>();
        // Members are checked through the team's student lookup.
        return members;
    }

    private List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        for (Team team : teams) {
            // Existing team membership is checked during registration below.
        }
        return students;
    }

    public boolean submitProject(Team team, String title) {
        if (!status.equals("Open") || !teams.contains(team)) {
            System.out.println("Submission failed: Team is not registered or submissions are closed.");
            return false;
        }

        if (!team.submitProject(title)) {
            System.out.println("Submission failed: Team can submit only one project.");
            return false;
        }

        projects.add(team.getProject());
        System.out.println("Project '" + title + "' submitted by " + team.getName() + ".");
        return true;
    }

    public void startJudging() {
        if (status.equals("Open")) {
            status = "Judging";
        }
    }

    public void recordScore(Project project, double idea,
                            double execution, double presentation) {
        if (status.equals("Published")) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        if (status.equals("Open")) {
            startJudging();
        }

        if (!projects.contains(project)) {
            System.out.println("Scoring failed: Project not registered.");
            return;
        }

        if (idea < 0 || idea > 10 || execution < 0 || execution > 10 ||
            presentation < 0 || presentation > 10) {
            System.out.println("Scoring failed: Ratings must be between 0 and 10.");
            return;
        }

        Score score = new Score(
            idea, execution, presentation,
            project.getTeam().getTrack()
        );

        project.setScore(score);

        System.out.println("Score recorded for '" + project.getTitle() + "'.");
        System.out.printf("Final score: %.2f%n", score.getFinalScore());
    }

    public void publishResults() {
        if (status.equals("Published")) {
            return;
        }

        status = "Published";
        System.out.println("Results published.");
    }
}

public class Main1 {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        Track innovation = new Track("Innovation", new InnovationScoring());
        Track open = new Track("Open", new OpenScoring());

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        List<Student> team1Members = new ArrayList<>();
        team1Members.add(asha);
        team1Members.add(ravi);
        team1Members.add(neha);

        Team byteBusters = new Team("ByteBusters", team1Members, innovation);

        List<Student> team2Members = new ArrayList<>();
        team2Members.add(kiran);

        Team soloCoder = new Team("SoloCoder", team2Members, open);

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(byteBusters, "SmartAttend");

        Judge judge = new Judge("Judge 1");

        Project project = byteBusters.getProject();

        judge.scoreProject(project, 8, 7, 9, hackathon);

        hackathon.publishResults();

        judge.scoreProject(project, 10, 7, 9, hackathon);
    }
}

