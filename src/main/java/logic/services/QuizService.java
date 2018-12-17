package logic.services;

import logic.abstractions.Service;
import org.hibernate.Hibernate;
import persistence.hibernate.HbnQuizRepository;
import shared.domain.Quiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuizService implements Service<Quiz> {
    private HbnQuizRepository quizRepository = new HbnQuizRepository();

    public Quiz getById(Integer quizId) {
        quizRepository.openCurrentSession();
        Quiz quiz = quizRepository.getById(quizId);
        Hibernate.initialize(quiz.getQuestions());
        quiz.getQuestions().forEach(question -> Hibernate.initialize(question.getAnswers()));
        quizRepository.closeCurrentSession();

        return quiz;
    }

    public List<Quiz> getAll() {
        quizRepository.openCurrentSession();
        List<Quiz> quizzes = new ArrayList<>(quizRepository.getAll());
        quizzes.forEach(quiz -> {
            Hibernate.initialize(quiz.getQuestions());
            quiz.getQuestions().forEach(question -> Hibernate.initialize(question.getAnswers()));
        });
        quizRepository.closeCurrentSession();

        return quizzes;
    }

    public void add(Quiz quiz) {
        quizRepository.openCurrentSessionWithTransaction();
        if (quizRepository.getByName(quiz.getName()) == null) {
            quizRepository.add(quiz);
        }
        quizRepository.closeCurrentSessionWithTransaction();
    }

    public void update(Quiz quiz) {
        quizRepository.openCurrentSessionWithTransaction();
        if (quizRepository.getByName(quiz.getName()) != null) {
            quizRepository.update(quiz);
        }
        quizRepository.closeCurrentSessionWithTransaction();
    }

    public void delete(Integer quizId) {
        quizRepository.openCurrentSessionWithTransaction();
        Quiz quiz = quizRepository.getById(quizId);
        if (quiz != null) {
            quizRepository.delete(quiz);
        }
        quizRepository.closeCurrentSessionWithTransaction();
    }
}
