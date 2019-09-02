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
    
}