package mg.working.cryptomonnaie.controller.firebase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.working.cryptomonnaie.services.firebase.FirestoreService;

@RestController
@RequestMapping("/sync")
public class FirestoreSyncController {

    private final FirestoreService firestoreSyncService;

    public FirestoreSyncController(FirestoreService firestoreSyncService) {
        this.firestoreSyncService = firestoreSyncService;
    }

    @GetMapping
    public String triggerSync() {
        System.out.println("AVANT SYNC ");
        firestoreSyncService.syncCryptoMonnaiesToFirestore();
        return "Synchronisation lancée avec succès !";
    }
}
