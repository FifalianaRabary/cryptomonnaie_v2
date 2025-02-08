<%@ page import="mg.working.cryptomonnaie.model.user.Utilisateur" %>

<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <div class="d-flex align-items-center justify-content-between">
        <a href="index.html" class="logo d-flex align-items-center">
            <h4>CRYPTO HOUSE</h4>
            <% Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur"); 

            String urlImage = (String) session.getAttribute("urlImage");
            
            %>
        </a>
        <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">

            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                    <img src="<%= urlImage  %>" alt="Profile" class="rounded-circle">
                    <span class="d-none d-md-block dropdown-toggle ps-2"><%= utilisateur.getNom() %></span>
                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="/pageUpdateImageProfile">
                            <i class="bi bi-person"></i>
                            <span>Changer votre photo de profile</span>
                        </a>
                    </li>

                </ul><!-- End Profile Dropdown Items -->
            </li><!-- End Profile Nav -->

        </ul>
    </nav><!-- End Icons Navigation -->

</header><!-- End Header -->
