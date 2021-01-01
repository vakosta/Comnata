package tv.comnata.comnata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.comnata.comnata.entities.User;
import tv.comnata.comnata.repositories.UserRepository;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserById(long id) {
        return repository.findById(id).get();
    }
}
