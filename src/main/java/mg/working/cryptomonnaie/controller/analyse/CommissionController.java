package mg.working.cryptomonnaie.controller.analyse;

import jakarta.servlet.http.HttpServletRequest;
import mg.working.cryptomonnaie.model.analyse.AnalyseCommission;
import mg.working.cryptomonnaie.model.analyse.MvtCommission;
import mg.working.cryptomonnaie.model.crypto.CryptoMonnaie;
import mg.working.cryptomonnaie.model.transaction.TransactionCrypto;
import mg.working.cryptomonnaie.services.analyse.MvtCommissionService;
import mg.working.cryptomonnaie.services.crypto.CryptoMonnaieService;
import mg.working.cryptomonnaie.services.transaction.TransactionCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CommissionController {

    @Autowired
    MvtCommissionService mvtCommissionService;

    @Autowired
    CryptoMonnaieService cryptoMonnaieService;

    @Autowired
    TransactionCryptoService transactionCryptoService;

    @GetMapping("modifCommission")
    public String modifCommission() {
        return "analyse/modifCommission";
    }

    @PostMapping("insertCommission")
    public String insertCommission(@RequestParam String dateHeure, @RequestParam String typeTransaction, @RequestParam double pourcentage) {
        MvtCommission mvtCommission = new MvtCommission();
        mvtCommission.setDateHeure(dateHeure);
        mvtCommission.setPourcentage_commission(BigDecimal.valueOf(pourcentage));
        mvtCommission.setTypeTransaction(MvtCommission.TypeTransaction.valueOf(typeTransaction));
        mvtCommissionService.save(mvtCommission);
        return "redirect:/modifCommission";
    }

    @GetMapping("analyseCommission")
    public String analyseCommission (HttpServletRequest request) {
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieService.getAllCrypto();
        List<AnalyseCommission> analyseCommissions = new ArrayList<>();
        request.setAttribute("cryptoMonnaies", cryptoMonnaies);
        request.setAttribute("analyseCommissions", analyseCommissions);
        return "analyse/analyseCommission";
    }

    @PostMapping("analyseCommissionFiltre")
    public String analyseCommissionFiltre (HttpServletRequest request, @RequestParam List<Integer> cryptoSelection, String dateMin, String dateMax) {
        List<CryptoMonnaie> cryptoMonnaies = cryptoMonnaieService.getAllCrypto();
        List<AnalyseCommission> analyseCommissions = new ArrayList<>();
        if (cryptoSelection.contains(0)) {
            cryptoSelection.clear();
            cryptoSelection = cryptoMonnaieService.getAllId();
        }
        for (Integer id : cryptoSelection) {
            AnalyseCommission analyseCommission = new AnalyseCommission();
            CryptoMonnaie cryptoMonnaie = cryptoMonnaieService.getCryptoById(id);
            List<TransactionCrypto> transactionCryptos = transactionCryptoService.findByIdCrypto(id);
            analyseCommission.setCryptoMonnaie(cryptoMonnaie);
            analyseCommission.setTransactions(transactionCryptos);
            analyseCommission.setSomme();
            analyseCommission.setMoyenne();
            analyseCommissions.add(analyseCommission);
        }

        request.setAttribute("cryptoMonnaies", cryptoMonnaies);
        request.setAttribute("analyseCommissions", analyseCommissions);
        return "analyse/analyseCommission";
    }
}
