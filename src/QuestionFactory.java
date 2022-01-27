import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionFactory
{
    Question question = null;
    public Question createQuestion(String questiontype, Element element)
    {
        switch (questiontype)
        {
            case ("short"):

                question = QuestionShortFactory.createQuestion(element);
                break;

            case ("truefalse"):
                question = QuestionTrueFalseFactory.createQuestion(element);
                break;

            case ("multichoice"):
                question = QuestionMultiChoiceFactory.createQuestion(element);
                break;

            case ("essay"):
                question = QuestionEssayFactory.createQuestion(element);
                break;
        }

        return question;
    }
}

class QuestionShortFactory extends QuestionFactory
{
    public static Question createQuestion(Element element)
    {

        String questiontext = element.getElementsByTagName("questiontext").item(0).getTextContent();
        String answer = element.getElementsByTagName("answers").item(0).getTextContent();
        List<String> answers = Arrays.asList(answer.split(","));
        Integer difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());

        return new QuestionShort(questiontext,answers,difficulty);
    }
}

class QuestionEssayFactory extends QuestionFactory
{
    public static Question createQuestion(Element element)
    {
        String questiontext = element.getElementsByTagName("questiontext").item(0).getTextContent();
        String answer = element.getElementsByTagName("answer").item(0).getTextContent();
        Integer difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());

        return new QuestionEssay(questiontext,answer,difficulty);
    }
}

class QuestionTrueFalseFactory extends QuestionFactory
{
    public static Question createQuestion(Element element)
    {
        String questiontext = element.getElementsByTagName("questiontext").item(0).getTextContent();
        String answer = element.getElementsByTagName("answer").item(0).getTextContent();
        Integer difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());

        return new QuestionTrueFalse(questiontext,answer,difficulty);
    }
}

class QuestionMultiChoiceFactory extends QuestionFactory
{
    public static Question createQuestion(Element element)
    {
        String questiontext = element.getElementsByTagName("questiontext").item(0).getTextContent();
        Integer difficulty = Integer.parseInt(element.getElementsByTagName("difficulty").item(0).getTextContent());
        List<Integer> solutions = Arrays.stream(element.getElementsByTagName("solution").item(0).getTextContent().split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List <String> answers= new ArrayList<>();

        for(int i = 0; i < ((Element)element.getElementsByTagName("options").item(0)).getElementsByTagName("answer").getLength(); i++)
        {
            answers.add(((Element)element.getElementsByTagName("options").item(0)).getElementsByTagName("answer").item(i).getTextContent() );
        }

        Boolean single = Boolean.parseBoolean(element.getElementsByTagName("single").item(0).getTextContent());

        return new QuestionMultiChoice(questiontext, answers, single, difficulty,solutions);
    }
}