package tv.comnata.comnata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tv.comnata.comnata.entities.User;
import tv.comnata.comnata.entities.UserPrincipal;
import tv.comnata.comnata.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserById(long id) {
        return repository.findById(id).get();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException("Invalid username.");

        return new UserPrincipal(user);
    }
}
