package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.User;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.junit.Before;
import org.junit.Test;

import static lunchVote.testData.UserData.*;
import static org.mockito.Mockito.*;

public class UserServiceTest extends SpringConfigOnTests {

    private UserService service;
    private UserCrud repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(UserCrud.class);
        service = new UserServiceImpl(repository);
    }

    @Test
    public void get() throws Exception {
        service.getById(USER.getId());
        verify(repository, times(1)).findById(USER.getId());
    }

    @Test
    public void getAll() throws Exception {
        service.getAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getByEmail() throws Exception {
        service.getByEmail(USER.getEmail());
        verify(repository, times(1)).getByEmail(USER.getEmail());
    }

    @Test
    public void delete() throws Exception {
        service.delete(JURA.getId());
        verify(repository, times(1)).delete(JURA.getId());
    }

    @Test
    public void save() throws Exception {
        service.save(new User(SAVE_NEW));
        verify(repository, times(1)).save(SAVE_NEW);
    }

    @Test
    public void update() throws Exception {
        User user = new User(ADMIN);
        user.setName("Pavel");
        service.save(user);
        verify(repository, times(1)).save(user);
    }


}