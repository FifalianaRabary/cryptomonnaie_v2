<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Entrer le Code PIN</title>
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
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f4;
        }
        .container {
            text-align: center;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .pin-input {
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .pin-input input {
            font-size: 20px;
            text-align: center;
            width: 30px;
            height: 40px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            margin-top: 10px;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Entrez votre code PIN</h2>
        <form action="checkPin" method="post" onsubmit="combinePin(event)">
            <div class="pin-input">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 1)" onkeydown="moveToPrevious(event, this, 0)">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 2)" onkeydown="moveToPrevious(event, this, 1)">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 3)" onkeydown="moveToPrevious(event, this, 2)">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 4)" onkeydown="moveToPrevious(event, this, 3)">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 5)" onkeydown="moveToPrevious(event, this, 4)">
                <input type="text" maxlength="1" inputmode="numeric" oninput="moveToNext(this, 6)" onkeydown="moveToPrevious(event, this, 5)">
            </div>
            <input type="hidden" name="pin" id="pin">
            <br>
            <input type="submit" class="btn btn-primary" value="valider">
        </form>
    </div>

    <script>
        function moveToNext(current, index) {
            const inputs = document.querySelectorAll('.pin-input input');
            if (current.value && index < inputs.length) {
                inputs[index].focus();
            }
        }

        function moveToPrevious(event, current, index) {
            const inputs = document.querySelectorAll('.pin-input input');
            if (event.key === 'Backspace' && !current.value && index > 0) {
                inputs[index - 1].focus();
                inputs[index - 1].value = '';
            }
        }

        function combinePin(event) {
            let pin = '';
            document.querySelectorAll('.pin-input input').forEach(input => {
                pin += input.value;
            });
            document.getElementById('pin').value = pin;
            if (pin.length !== 6 || isNaN(pin)) {
                event.preventDefault();
                alert("Veuillez entrer un code PIN valide à 6 chiffres.");
            }
        }
    </script>
</body>
</html>
