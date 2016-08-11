package next.service;

import next.CannotOperateException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by wymstar on 8/8/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {

    @Mock
    AnswerDao answerDao;
    @Mock
    QuestionDao questionDao;
    @InjectMocks
    QnaService qnaService = new QnaService(questionDao, answerDao);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        when(questionDao.findById(1)).thenReturn(
                new Question(1, "java", "궁금해요", "궁금해요", null, 1)
        );
        when(answerDao.findAllByQuestionId(1)).thenReturn(Arrays.asList(
                new Answer("wym", "잘했어요", 1)
                , new Answer("meme", "더 잘했어요", 1)
        ));

    }

    @Test
    public void test_findById() throws Exception {
        Question question = qnaService.findById(1);

        Assert.assertEquals("java", question.getWriter());
        Assert.assertEquals(1, question.getQuestionId());
    }

    @Test
    public void test_findByQuestionId() throws Exception {
        when(answerDao.findAllByQuestionId(1)).thenReturn(Arrays.asList(
                new Answer("wym", "잘했어요", 1)
                , new Answer("meme", "더 잘했어요", 1)
        ));

        List<Answer> answers = qnaService.findAllByQuestionId(1);

        Assert.assertEquals(2, answers.size());
        Assert.assertEquals("wym", answers.get(0).getWriter());
        Assert.assertEquals("meme", answers.get(1).getWriter());
    }

    public void test_deleteQuestion_다른사용자() throws Exception {
        expectedException.expect(CannotOperateException.class);
        expectedException.expectMessage("다른 사용자가 쓴 글을 삭제할 수 없습니다.");

        User user = new User();
        user.setName("wym");
        user.setPassword("1234");
        user.setUserId("1");
        user.setEmail("wym@mail.net");

        qnaService.deleteQuestion(1, user);

    }
}
