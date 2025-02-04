package mg.working.cryptomonnaie.controller.transaction;

import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.MvtCrypto;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import mg.working.cryptomonnaie.services.transaction.MvtCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/crypto")
public class MvtCryptoController {
    @Autowired
    MvtCryptoService mvtCryptoService;

    @Autowired
    CryptoMonnaieService cryptoMonnaieService;

    @GetMapping("listCrypto")
    public List<CryptoMonnaie> getList (){
        return cryptoMonnaieService.getAllCrypto();
    }

    @GetMapping("lastMvtCrypto")
    public List<MvtCrypto> lastMvtCrypto (){
        return mvtCryptoService.getAllLastMvtCrypto();
    }

    @GetMapping("/last7/{cryptoId}")
    public List<MvtCrypto> getLast7Movements(@PathVariable int cryptoId) {
        return mvtCryptoService.get7LastMvtCryptoByCrypto(cryptoId);
    }
}
