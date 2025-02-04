<%@ page import="java.util.List" %>
<%@ page import="mg.working.cryptomonnaie.model.crypto.CryptoMonnaie" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Dashboard - NiceAdmin Bootstrap Template</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="assets/img/favicon.png" rel="icon">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="assets/css/style.css" rel="stylesheet">

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
    List<CryptoMonnaie> cryptoMonnaies = (List<CryptoMonnaie>) request.getAttribute("cryptos");
%>
<body>
<jsp:include page="../static/header.jsp"/>
<!-- End Header -->

<!-- ======= Sidebar ======= -->
<jsp:include page="../static/sidebar.jsp"/>
<main id="main" class="main">

    <div class="pagetitle">
        <h1>Crypto Money</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item active">Evolution <%= session.getAttribute("jeton") %></li>
            </ol>
        </nav>
    </div><!-- End Page Title -->
    <script>
        // Fonction pour charger les cryptos via l'API REST
        function loadCryptos() {
            $.ajax({
                url: '/api/crypto/lastMvtCrypto', // URL de l'API REST
                type: 'GET',
                dataType: 'json',
                success: function(mvts) {
                    // Vider le tableau avant d'ajouter les nouvelles données
                    $('#cryptoTable tbody').empty();

                    // Remplir le tableau avec les données
                    mvts.forEach(function(mvt) {
                        $('#cryptoTable tbody').append(
                            '<tr>' +
                            '<td>' + mvt.cryptoMonnaie.designation + '</td>' +
                            '<td>' + mvt.cryptoMonnaie.symbol + '</td>' +
                            '<td>' + mvt.variationValue + '</td>' +
                            '<td>' + mvt.variation + '</td>' +
                            '<td>' + mvt.cryptoMonnaie.prixUnitaire.toFixed(2) + '</td>' +
                            '</tr>'
                        );
                    });
                },
                error: function(error) {
                    console.error("Erreur lors de la récupération des données : ", error);
                }
            });
        }

        // Charger les cryptos dès le chargement de la page
        $(document).ready(function() {
            loadCryptos();
        });

        // Fonction pour actualiser les cryptos
        function refreshCryptos() {
            loadCryptos();
        }

        setInterval(loadCryptos, 10000);
        loadCryptos();
    </script>

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
                            <th scope="col">Symbole</th>
                            <th scope="col">Old prix</th>
                            <th scope="col">Variation %</th>
                            <th scope="col">Current prix</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    <!-- End Default Table Example -->
                </div>
            </div>
        </div>
    </div>

    <!-- Reports -->
    <div class="col-12">
        <div class="card">

            <div class="card-body">
                <div class="row mb-3">
                    <label class="col-sm-2 col-form-label">Select Crypto</label>
                    <div class="col-sm-10">
                        <select class="form-select" aria-label="Default select example" name="selectedCrypto" id="selectedCrypto">
                            <option selected>Open this select menu</option>
                            <% for (CryptoMonnaie cryptoMonnaie : cryptoMonnaies) { %>
                                <option value="<%= cryptoMonnaie.getId() %>"><%= cryptoMonnaie.getSymbol() %></option>
                                <% } %>
                        </select>
                    </div>
                </div>

                <!-- Line Chart -->
                <div id="reportsChart"></div>

                <script>
                    let selectedCryptoId = 0;
                    document.getElementById("selectedCrypto").addEventListener("change", function () {
                        selectedCryptoId = this.value; // Récupérer l'ID sélectionné
                    });

                        setInterval(function(){
                            if (selectedCryptoId !==0 ) {
                                const url = "/api/crypto/last7/"+selectedCryptoId
                                console.log(url);
                            fetch("/api/crypto/last7/"+selectedCryptoId )
                                    .then(response => response.json())
                                    .then(data => {
                                        let seriesData = [];
                                        let dates = [];

                                        data.forEach(mvt => {
                                            seriesData.push(mvt.variationValue);  // Prix à chaque mouvement
                                            dates.push(new Date(mvt.dateHeure).toISOString());  // Date du mouvement
                                            console.log(mvt.variationValue);
                                            console.log(new Date(mvt.dateHeure).toISOString());
                                        });

                                        renderChart(seriesData, dates);  // Affichage immédiat du graphique
                                    })
                                    .catch(error => console.error('Error fetching data:', error));
                            }
                        }, 10000)


                    function renderChart(prices, dates) {
                        // Calculer le prix moyen pour déterminer l'intervalle de l'axe Y
                        const maxPrice = Math.max(...prices);
                        const minPrice = Math.min(...prices);
                        const priceRange = maxPrice - minPrice;
                        const yAxisCenter = (maxPrice + minPrice) / 2;
                        const yAxisMin = 0;  // 50% de la plage sous le centre
                        const yAxisMax = yAxisCenter * 2;  // 50% de la plage au-dessus du centre

                        new ApexCharts(document.querySelector("#reportsChart"), {
                            series: [{
                                name: 'Crypto Price',
                                data: prices
                            }],
                            chart: {
                                height: 350,
                                type: 'line',
                                toolbar: {
                                    show: false
                                },
                            },
                            markers: {
                                size: 4
                            },
                            colors: ['#4154f1'],
                            fill: {
                                type: "gradient",
                                gradient: {
                                    shadeIntensity: 1,
                                    opacityFrom: 0.3,
                                    opacityTo: 0.4,
                                    stops: [0, 90, 100]
                                }
                            },
                            dataLabels: {
                                enabled: false
                            },
                            stroke: {
                                curve: 'smooth',
                                width: 2
                            },
                            xaxis: {
                                type: 'datetime',
                                categories: dates,
                                labels: {
                                    rotate: -45,  // Optionnel, pour améliorer la lisibilité des dates
                                }
                            },
                            yaxis: {
                                min: yAxisMin,
                                max: yAxisMax,
                                title: {
                                    text: 'Price'
                                },
                            },
                            tooltip: {
                                x: {
                                    format: 'dd/MM/yy HH:mm:ss'
                                }
                            }
                        }).render();
                    }

                </script>
                <!-- End Line Chart -->

            </div>

        </div>
    </div><!-- End Reports -->


</main><!-- End #main -->

</body>

<jsp:include page="../static/footer.jsp"/>

</html>