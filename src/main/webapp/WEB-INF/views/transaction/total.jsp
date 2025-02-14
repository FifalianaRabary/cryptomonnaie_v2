<%@ page import="org.springframework.aop.target.LazyInitTargetSource" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Portefeuille" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>
<%@ page import="mg.working.cryptomonnaie.services.transaction.TransactionCryptoService" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Total" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Tables / General - NiceAdmin Bootstrap Template</title>
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
    * Updated: Aug 30 2023 with Bootstrap v5.3.1
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<body>

<jsp:include page="../static/header.jsp" />
<jsp:include page="../static/sidebar.jsp" />
<main id="main" class="main">
    <div class="pagetitle">
        <h1>Total</h1>
<%
    List<Total> totals = (List<Total>) request.getAttribute("totals");
%>
    </div><!-- End Page Title -->
    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <form method="post" action="getTotalFiltre">
                            <label for="dateMax">Date Heure Max</label>
                            <input type="datetime-local" name="dateMax" id="dateMax" required>

                            <input type="submit" class="btn btn-primary" value="valider">
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
                        <h5 class="card-title">Default Table</h5>

                        <!-- Default Table -->
                        <table class="table" id="cryptoTable">
                            <thead>
                            <tr>
                                <th scope="col">Utilisateur</th>
                                <th scope="col">Total Achat</th>
                                <th scope="col">Total Vente</th>
                                <th scope="col">Total Portefeuille</th>

                            </tr>
                            </thead>
                            <tbody>
                                <% for(Total total : totals) { %>
                                <tr>
                                    <td><%=total.getUtilisateur().getNom() %></td>
                                    <td><%= total.getTotalAchat() %></td>
                                    <td><%= total.getTotalVente() %></td>
                                    <td><%= total.getValeurPortefeuille() %></td>
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
</main><!-- End #main -->
<jsp:include page="../static/footer.jsp"/>
</body>

</html>