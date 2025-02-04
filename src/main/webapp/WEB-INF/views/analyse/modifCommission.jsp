<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Portefeuille" %>
<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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
    // Récupérer les données de cryptomonnaies
    List<CryptoMonnaie> cryptoMonnaies = (List<CryptoMonnaie>) request.getAttribute("cryptos");

    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    int utilisateurId = utilisateur.getId();

    List<Portefeuille> portefeuilleUser = (List<Portefeuille>) request.getAttribute("portefeuilles");
%>

<body>
<jsp:include page="../static/header.jsp"/>
<jsp:include page="../static/sidebar.jsp"/>

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Page d'Achat</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
                <li class="breadcrumb-item active">Achat</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Modifier le pourcentage de commission :</h5>

                        <form action="insertCommission" method="post">
                            <%
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String dateHeureActuelle = formatter.format(new Date());
                            %>
                            <input type="hidden" name="dateHeure" value="<%= dateHeureActuelle %>">
                            <label for="cryptoSelect">Sélectionnez le type de transaction :</label>
                            <select id="cryptoSelect" name="typeTransaction">
                                <option value="VENTE">VENTE</option>
                                <option value="ACHAT">ACHAT</option>
                            </select>
                            <input type="number" step="0.01" name="pourcentage">
                            <input type="submit" id="submitAchat" value="valider">
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </section>

</main>

</body>
<jsp:include page="../static/footer.jsp"/>

</html>
