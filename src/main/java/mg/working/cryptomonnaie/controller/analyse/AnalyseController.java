package mg.working.cryptomonnaie.controller.analyse;

import jakarta.servlet.http.HttpServletRequest;
import mg.working.cryptomonnaie.model.analyse.AnalyseCrypto;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.MvtCrypto;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import mg.working.cryptomonnaie.services.transaction.MvtCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AnalyseController {
    @Autowired
    CryptoMonnaieService cryptoMonnaieService;

    @Autowired
    MvtCryptoService mvtCryptoService;

    @GetMapping("analyseCrypto")
    public String analyseCrypto(HttpServletRequest request) {
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieService.getAllCrypto();
        List<AnalyseCrypto> analyseCryptos = new ArrayList<>();
        request.setAttribute("cryptoMonnaies", cryptoMonnaies);
        request.setAttribute("analyseCryptos", analyseCryptos);
        return "analyse/analyseCrypto";
    }

    @PostMapping("analyseCryptoFiltre")
    public String analyseCryptoFiltre(HttpServletRequest request, @RequestParam List<Integer> cryptoSelection, String dateMin, String dateMax) {
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieService.getAllCrypto();
        List<AnalyseCrypto> analyseCryptos = new ArrayList<>();
        if (cryptoSelection.contains(0)) {
            cryptoSelection.clear();
            cryptoSelection = cryptoMonnaieService.getAllId();
        }
        for (Integer cryptoSelectionId : cryptoSelection) {
            AnalyseCrypto analyseCrypto = new AnalyseCrypto();
            List<MvtCrypto> mvtCryptos = mvtCryptoService.getByCryptoMonnaie(cryptoSelectionId);
            analyseCrypto.setCryptoMonnaie(cryptoMonnaieService.getCryptoById(cryptoSelectionId));
            analyseCrypto.setMvtCryptos(mvtCryptos);
            analyseCrypto.setMax();
            analyseCrypto.setMin();
            analyseCrypto.setMoyenne();
            analyseCrypto.setEcartType();
            analyseCrypto.setQuartile();
            analyseCryptos.add(analyseCrypto);
        }
        request.setAttribute("cryptoMonnaies", cryptoMonnaies);
        request.setAttribute("analyseCryptos", analyseCryptos);
        return "analyse/analyseCrypto";
    }

}
