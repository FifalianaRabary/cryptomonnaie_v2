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
</head>

<body>

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

                        <div class="d-flex justify-content-center py-4">
                            <a href="index.html" class="logo d-flex align-items-center w-auto">
                                <img src="https://i.imgur.com/DaqW3Wz.png" alt="">
                                <span class="d-none d-lg-block">Inscription</span>
                            </a>
                        </div><!-- End Logo -->

                        <div class="card mb-3">

                            <div class="card-body">

                                <div class="pt-4 pb-2">
                                    <h5 class="card-title text-center pb-0 fs-4">Bienvenu(e)</h5>
                                </div>

                                <form class="row g-3 needs-validation" novalidate="" method="post" action="incription">

                                    <div class="col-12">
                                        <label for="yourUsername" class="form-label">Nom d'utilisateur</label>
                                        <div class="input-group has-validation">
                                            <span class="input-group-text" id="inputGroupPrepend">@</span>
                                            <input type="text" name="nom" class="form-control" id="yourUsername" required="">
                                            <div class="invalid-feedback">Please enter your username.</div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourUsermail" class="form-label">Mail</label>
                                        <div class="input-group has-validation">
                                            <input type="text" name="mail" class="form-control" id="yourUsermail" required="">
                                            <div class="invalid-feedback">Please enter your mail.</div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourUserdtn" class="form-label">Date de naissance </label>
                                        <div class="input-group has-validation">
                                            <input type="date" name="dtn" class="form-control" id="yourUserdtn" required="">
                                            <div class="invalid-feedback">Please enter your dtn.</div>
                                        </div>
                                    </div>

                                    <div class="col-12">
                                        <label for="yourPassword" class="form-label">Mot de passe</label>
                                        <input type="password" name="mdp" class="form-control" id="yourPassword" required="">
                                        <div class="invalid-feedback">Please enter your password!</div>
                                    </div>

    
                                    <div class="col-12">
                                        <button class="btn btn-primary w-100" type="submit">s'inscrire</button>
                                    </div>
                                    <div class="col-12">
                                        <p class="small mb-0">Vous avez un compte? <a href="loginForm">Se connecter</a></p>
                                    </div>
                                    <div class="col-12">
                                        <p class="small mb-0">Etes vous un admin? <a href="loginAdminForm">Se connecter en tant qu'admin</a></p>
                                    </div>
                                </form>

                            </div>
                        </div>

                        <div class="credits">
                            <!-- All the links in the footer should remain intact. -->
                            <!-- You can delete the links only if you purchased the pro version. -->
                            <!-- Licensing information: https://bootstrapmade.com/license/ -->
                            <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
                            Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
                        </div>

                    </div>
                </div>
            </div>

        </section>

    </div>
</main>

</body>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const passwordInput = document.getElementById("yourPassword");
        const submitButton = document.querySelector("button[type='submit']");

        function validatePassword() {
            const password = passwordInput.value;
            const isValid = password.length >= 6 && !password.includes(" ");
            submitButton.disabled = !isValid;
        }

        passwordInput.addEventListener("input", validatePassword);
        validatePassword(); // Valider dès le chargement de la page
    });
</script>


</html>


