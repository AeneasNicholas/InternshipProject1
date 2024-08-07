package InternshipProj.api.user_keys;


import InternshipProj.api.users.UserIDRepository;
import InternshipProj.api.users.Userid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeysTableService {
    @Autowired
    private KeysTableRepository keysTableRepository;

    @Autowired
    private UserIDRepository userIDRepository;

    public KeysTable createKey(long userid, String api) {
        Userid user = userIDRepository.findById(userid).orElseThrow();
        // Fetch the number of keys the user already has
        long keyCount = keysTableRepository.countByUser(user);

        // Generate the new key
        String newKey = String.format("%d.%03d", user.getId(), keyCount + 1);

        // Create the new key entity
        KeysTable newKeysTable = new KeysTable();
        newKeysTable.setUser(user);
        newKeysTable.setApi(api);
        newKeysTable.setKey(newKey);
        newKeysTable.setIsActive(true); // Assuming new keys are active by default

        // Save the new key entity to the database
        return keysTableRepository.save(newKeysTable);

    }
    public boolean isKeyForAPI(String key, String api) {
        Optional<KeysTable> optionalKeysTable = keysTableRepository.findByKeyAndApi(key, api);
        return optionalKeysTable.isPresent();
    }

    public boolean isUserRegistered(Long userId) {
        return userIDRepository.existsById(userId);
    }

    public List<KeysTable> getAPIKeysByUserId(Long userId) {
        return keysTableRepository.findByUserId(userId);
    }

    public boolean deleteAPIKey(Long keysTableId) {
        if (keysTableRepository.existsById(keysTableId)) {
            keysTableRepository.deleteById(keysTableId);
            return true;
        }
        return false;
    }

    public KeysTable saveAPIKey(KeysTable keysTable) {
        return keysTableRepository.save(keysTable);
    }

    public boolean toggleKeyActivation(Long keysTableId) {
        Optional<KeysTable> optionalKey = keysTableRepository.findById(keysTableId);
        if (optionalKey.isPresent()) {
            KeysTable key = optionalKey.get();
            key.setIsActive(!key.getIsActive());
            keysTableRepository.save(key);
            return true;
        } else {
            return false;
        }
    }

    public List<KeysTable> getKeysByActive(Long userId, boolean isActive) {
        Integer activeStatus = isActive ? 1 : 0;
        return keysTableRepository.findByUserIdAndIsActive(userId, activeStatus);
    }
}
