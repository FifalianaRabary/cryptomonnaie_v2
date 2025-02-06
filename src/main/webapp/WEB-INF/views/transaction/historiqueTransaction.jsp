<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Portefeuille" %>
<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Operation" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.TransactionCrypto" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - NiceAdmin Bootstrap Template</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="/assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="/assets/css/style.css" rel="stylesheet">

    <!-- =======================================================
    * Template Name: NiceAdmin
    * Updated: Nov 17 2023 with Bootstrap v5.3.2
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</head>

<%
    List<TransactionCrypto> transactionCryptos = (List<TransactionCrypto>) request.getAttribute("transactionCryptos");
    Map<Integer, String> imageUtilisateurs = (Map<Integer, String>) request.getAttribute("imageUtilisateurs");
%>

<body>
<jsp:include page="../static/header.jsp"/>
<jsp:include page="../static/sidebar.jsp"/>

<main id="main" class="main">

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Historique Transaction</h5>

                        <form method="post" action="historiqueTransactionFiltreDate" >
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="endDate">Date et heure max :</label>
                                    <input type="datetime-local" id="endDate" name="dateTransaction" class="form-control">
                                </div>
                                <div class="col-md-6">
                                    <input type="submit" value="Entrer">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section class="section">
        <div class="row">
            <div class="col-lg-12">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Liste</h5>

                        <!-- Default Table -->
                        <table class="table" id="cryptoTable">
                            <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">Type</th>
                                <th scope="col">Crypo</th>
                                <th scope="col">Quantite</th>
                                <th scope="col">Montant</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% for(TransactionCrypto transactionCrypto : transactionCryptos) { %>
                            <tr>
                                <td style="display: flex; align-items: center; gap: 10px;">
                                    <a href="historiquetransactionFiltreUtilisateur?idUtilisateur=<%= transactionCrypto.getUtilisateur().getId() %>">
                                        <img src="<%= imageUtilisateurs.get(transactionCrypto.getUtilisateur().getId()) %>" alt="P"
                                             class="rounded-circle" style="width: 50px; height: 50px; object-fit: cover;">
                                    </a>
                                    <div>
                                        <strong><%= transactionCrypto.getUtilisateur().getNom() %></strong>
                                        <br>
                                        <p style="margin: 0; font-size: 14px; color: gray;">
                                            <%
                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Le' yyyy-MM-dd ' a ' HH-mm");
                                            %>
                                            <%= transactionCrypto.getDateHeure().format(formatter) %>
                                        </p>
                                    </div>
                                </td>
                                <td><%= transactionCrypto.getTypeTransaction().name() %></td>
                                <td><%= transactionCrypto.getCryptoMonnaie().getSymbol() %></td>
                                <td><%= transactionCrypto.getQuantite() %></td>
                                <td><strong><%= (long) transactionCrypto.getPrixTotal().doubleValue() %></strong></td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                        <!-- End Default Table Example -->
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>


</body>
<jsp:include page="../static/footer.jsp"/>

</html>
