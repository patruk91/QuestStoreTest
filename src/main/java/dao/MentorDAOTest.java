package dao;

import model.users.Mentor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MentorDAOTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void throwExceptionIfMentorIdIsWrong() {
        MentorDAO mentorDAO = new MentorDAO();
        assertThrows(Exception.class, () -> mentorDAO.getMentorById(-1));
    }

    @Test
    void checkIfMentorIsNotNull() {
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = null;
        try {
            mentor = mentorDAO.getMentorById(4);
        } catch (DBException e) {
            e.printStackTrace();
        }
        assertNotNull(mentor);
    }

    @Test
    void checkIfMentorIdIsFour() {
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = null;
        try {
            mentor = mentorDAO.getMentorById(4);
        } catch (DBException e) {
            e.printStackTrace();
        }
        assertEquals(4, mentor.getId());
    }
}