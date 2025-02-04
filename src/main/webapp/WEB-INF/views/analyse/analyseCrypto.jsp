<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Portefeuille" %>
<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>
<%@ page import="mg.working.cryptomonnaie.model.analyse.AnalyseCrypto" %>
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
    List<CryptoMonnaie> cryptoMonnaies = (List<CryptoMonnaie>) request.getAttribute("cryptoMonnaies");
    List<AnalyseCrypto> analyseCryptos = (List<AnalyseCrypto>) request.getAttribute("analyseCryptos");
%>

<body>
<jsp:include page="../static/header.jsp"/>
<jsp:include page="../static/sidebar.jsp"/>

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Analyse</h1>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Analyse crypto</h5>

                        <form action="analyseCryptoFiltre" method="post">
                            <label for="cryptoSelect">Sélectionnez une ou plusieurs cryptomonnaies :</label>
                            <div class="row mb-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="cryptoSelection"
                                           id="crypto_0" value="0">
                                    <label class="form-check-label" for="crypto_0">
                                        Tous
                                    </label>
                                </div>
                                <% for (CryptoMonnaie cryptoMonnaie : cryptoMonnaies) { %>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="cryptoSelection"
                                           id="crypto_<%= cryptoMonnaie.getId() %>" value="<%= cryptoMonnaie.getId() %>">
                                    <label class="form-check-label" for="crypto_<%= cryptoMonnaie.getId() %>">
                                        <%= cryptoMonnaie.getDesignation() %> (<%= cryptoMonnaie.getSymbol() %>)
                                    </label>
                                </div>
                                <% } %>
                            </div>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="startDate">Date et heure min :</label>
                                    <input type="datetime-local" id="startDate" name="dateMin" class="form-control">
                                </div>

                                <div class="col-md-6">
                                    <label for="endDate">Date et heure max :</label>
                                    <input type="datetime-local" id="endDate" name="dateMax" class="form-control">
                                </div>
                            </div>

                            <input type="submit" class="btn btn-primary" value="Valider">
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
                        <h5 class="card-title">Table analyse</h5>

                        <!-- Default Table -->
                        <table class="table" id="cryptoTable">
                            <thead>
                            <tr>
                                <th scope="col">Crypto</th>
                                <th scope="col">1er quartyle</th>
                                <th scope="col">Min</th>
                                <th scope="col">Max</th>
                                <th scope="col">Moyenne</th>
                                <th scope="col">Ecart type</th>
                            </tr>
                            </thead>
                            <tbody>
                                <% for (AnalyseCrypto analyseCrypto : analyseCryptos) { %>
                                    <tr>
                                        <th><%= analyseCrypto.getCryptoMonnaie().getSymbol() %></th>
                                        <td><%= analyseCrypto.getQuartile() %></td>
                                        <td><%= analyseCrypto.getMin() %></td>
                                        <td><%= analyseCrypto.getMax() %></td>
                                        <td><%= analyseCrypto.getMoyenne() %></td>
                                        <td><%= analyseCrypto.getEcartType() %></td>
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
