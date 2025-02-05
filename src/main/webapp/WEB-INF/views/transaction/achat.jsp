<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<%@ page import="mg.working.cryptomonnaie.model.transaction.Portefeuille" %>
<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>
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
                            <h5 class="card-title">Acheter une cryptomonnaie</h5>
    
                            <form id="achatForm">
                                <input type="hidden" id="userId" value="<%= utilisateurId %>">
                        
                                <label for="cryptoSelect">Sélectionnez une cryptomonnaie :</label>
                                <select id="cryptoSelect">
                                    <% for (CryptoMonnaie crypto : cryptoMonnaies) { %>
                                        <option value="<%= crypto.getId() %>">
                                            <%= crypto.getDesignation() %> (<%= crypto.getSymbol() %>)
                                        </option>
                                    <% } %>
                                </select>
                        
                                <label for="quantiteInput">Quantité :</label>
                                <input type="number" id="quantiteInput" required>
                        
                                <button type="button" id="submitAchat">Acheter</button>
                            </form>
                        
                            <div id="resultMessage" class="mt-3"></div>
                            <div class="mt-3">Votre Solde : <h1><%=utilisateur.getSolde()%></h1> </div>
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
                                    <th scope="col">Crypto</th>
                                    <th scope="col">Qantite</th>
                                    <th scope="col">Prix Unitaire</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% for(Portefeuille portefeuille : portefeuilleUser) { %>
                                <tr>
                                    <td><%=portefeuille.getCryptoMonnaie().getSymbol()%></td>
                                    <td><%=portefeuille.getQuantite()%></td>
                                    <td><%=portefeuille.getCryptoMonnaie().getPrixUnitaire()%></td>
    
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

    <script>
        $(document).ready(function () {
            $("#submitAchat").on("click", function () {
                const userId = $("#userId").val();
                const cryptoId = $("#cryptoSelect").val();
                const quantite = $("#quantiteInput").val();
    
                if (quantite > 0) {
                    $.ajax({
                        url: "/api/achat/achat",
                        type: "POST",
                        data: {
                            userId: userId,
                            cryptoId: cryptoId,
                            quantite: quantite
                        },
                        success: function (response) {
                            $("#resultMessage").html('<div class="alert alert-success">' + response + '</div>');
                        },
                        error: function (xhr, status, error) {
                            $("#resultMessage").html('<div class="alert alert-danger">Erreur : ' + xhr.responseText + '</div>');
                        }
                    });
                } else {
                    $("#resultMessage").html('<div class="alert alert-warning">Veuillez saisir une quantité valide.</div>');
                }
            });
        });
    </script>
    
</body>
<jsp:include page="../static/footer.jsp"/>

</html>
