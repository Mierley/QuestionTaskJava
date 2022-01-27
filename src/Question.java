import java.util.Comparator;
import java.util.List;

public abstract class Question
{
    protected String questionText;
    protected Integer difficulty;
    public static void reorderQuestions(List<Question> questions)
    {
        questions.sort(QUESTION_COMPARATOR);
    }

    public static final Comparator<Question> QUESTION_COMPARATOR = new Comparator<Question>()
    {
        @Override
        public int compare(Question o1, Question o2)
        {
            return Integer.compare(o1.difficulty, o2.difficulty);
        }
    };

    public static String print(List<Question> questions)
    {
        String task = "";
     for (int i =0 ; i < questions.size(); i++)
     {
         task += ((i+1) + ") " + questions.get(i).printForEveryQuestionType() + "\n\n");
     }
     return task;
    }

    public static String printWithAnswers(List<Question> questions)
    {
        String task = "";
        for (int i =0 ; i < questions.size(); i++)
        {
            task += ((i+1) + ") " + questions.get(i).printWithAnswerForEveryQuestionType() + "\n");
            if (i != questions.size() - 1)
                task += "\n";
        }
        return task;
    }

    protected abstract String printForEveryQuestionType();
    protected abstract String printWithAnswerForEveryQuestionType();

}

class QuestionShort extends Question
{
    protected List<String> answers;
    public QuestionShort(String text, List<String> answers, Integer difficulty)
    {
        this.questionText = text;
        this.answers = answers;
        this.difficulty = difficulty;
    }

    @Override
    protected String printForEveryQuestionType()
    {
        return (questionText + "\nAnswer: ____________________");
    }

    @Override
    protected String printWithAnswerForEveryQuestionType()
    {
        String answer = "";
        for(String i : answers)
            answer += i + ", ";
        return (questionText + "\nAccepted answers: [" + answer.substring(0, answer.length() - 2) + "]");
    }
}

class QuestionEssay extends Question
{
    protected String answer;
    public QuestionEssay(String text, String answer, Integer difficulty)
    {
        this.questionText = text;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    @Override
    protected String printForEveryQuestionType()
    {
        return (questionText + "\n\n\n\n\n");
    }

    @Override
    protected String printWithAnswerForEveryQuestionType()
    {
        return (questionText + "\n" + answer + "\nNote: To be checked manually");
    }
}

class QuestionTrueFalse extends Question
{
    protected String answer;
    public QuestionTrueFalse(String text, String answer, Integer difficulty)
    {
        this.questionText = text;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    @Override
    protected String printForEveryQuestionType()
    {
        return (questionText + "\nAnswer: true false (circle the right answer)");
    }

    @Override
    protected String printWithAnswerForEveryQuestionType()
    {
        return (questionText + "\nAnswer: " + answer);
    }
}

class QuestionMultiChoice extends Question
{
    protected List<String> options;
    protected Boolean single;
    protected List<Integer> solution;

    public QuestionMultiChoice(String text, List<String> options, Boolean single, Integer difficulty, List<Integer> solution)
    {
        this.questionText = text;
        this.options = options;
        this.single = single;
        this.difficulty = difficulty;
        this.solution = solution;
    }

    @Override
    protected String printForEveryQuestionType()
    {
        String task = questionText;
        for(int i = 0; i  < options.size(); i++)
        {
            task += "\n\t" + (i+1) + ") " + options.get(i);
        }
        return task;
    }

    @Override
    protected String printWithAnswerForEveryQuestionType()
    {
        String task = questionText;
        for(int i = 0; i  < options.size(); i++)
        {
            if ( solution.contains(i+1))
            task += "\n -> " + (i+1) + ") " + options.get(i);
            else
            task += "\n\t" + (i+1) + ") " + options.get(i);
        }
        return task;
    }
}