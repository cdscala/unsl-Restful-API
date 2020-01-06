package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.User;
import unsl.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import unsl.config.CacheConfig;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Cacheable(CacheConfig.USERS_CACHE)
    public List<User> getAll() {
        simulateSlowService();
        return userRepository.findAll();
    }

    @Cacheable(CacheConfig.USERS_CACHE)
    public User getUser(Long userId) {
        simulateSlowService();
        return userRepository.findById(userId);
    }

    @Cacheable(CacheConfig.USERS_CACHE)
    public User findByDni(Long dni) {
        simulateSlowService();
        return userRepository.findByDni(dni);
    }

    @CachePut(CacheConfig.USERS_CACHE)
    public User saveUser(User user) {
        simulateSlowService();
        return userRepository.save(user);
    }

    @CachePut(CacheConfig.USERS_CACHE)
    public User updateUser(User updatedUser){
        simulateSlowService();
        User user = userRepository.findById(updatedUser.getId());
        if (user ==  null){
            return null;
        }
        user.setNombre(updatedUser.getNombre());
        user.setApellido(updatedUser.getApellido());
        return userRepository.save(user);
    }

    @CachePut(CacheConfig.USERS_CACHE)
    public User deleteUser(Long userId) {
        simulateSlowService();
        User user = userRepository.findById(userId);
        if (user ==  null){
            return null;
        }
        user.setEstado(User.Status.BAJA);
        return userRepository.save(user);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
