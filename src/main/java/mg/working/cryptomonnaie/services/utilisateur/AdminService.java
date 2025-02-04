package mg.working.cryptomonnaie.services.utilisateur;

import mg.working.cryptomonnaie.model.user.Admin;
import mg.working.cryptomonnaie.repository.utilisateur.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Admin login(String username, String password) { return adminRepository.login(username, password); }
}
