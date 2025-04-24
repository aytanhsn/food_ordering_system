//package jpaprojects.foodorderingsystem.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class PageController {
//
//    @GetMapping("/success")
//    public String success() {
//        return "success";
//    }
//
//    @GetMapping("/cancel")
//    public String cancel() {
//        return "cancel";
//    }
//}
package jpaprojects.foodorderingsystem.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPasswordPage(@RequestParam String token) {
        String html = """
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Şifrəni Sıfırla</title>
            <style>
                body {
                    background-color: #f4f6f8;
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                    margin: 0;
                }
                .container {
                    background-color: #fff;
                    padding: 30px 40px;
                    border-radius: 16px;
                    box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                    width: 100%%;
                    max-width: 400px;
                    text-align: center;
                }
                h2 {
                    margin-bottom: 24px;
                    color: #333;
                }
                input[type="password"] {
                    width: 100%%;
                    padding: 12px;
                    margin-bottom: 20px;
                    border: 1px solid #ccc;
                    border-radius: 8px;
                    font-size: 14px;
                }
                button {
                    background-color: #4caf50;
                    color: white;
                    padding: 12px 20px;
                    border: none;
                    border-radius: 8px;
                    font-size: 16px;
                    cursor: pointer;
                    transition: background-color 0.2s ease;
                }
                button:hover {
                    background-color: #45a049;
                }
                .message {
                    margin-top: 16px;
                    font-size: 14px;
                    font-weight: bold;
                    color: green;
                    display: none;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Yeni şifrəni daxil et</h2>
                <form id="resetForm">
                    <input type="hidden" name="token" value="%s">
                    <input type="password" name="newPassword" placeholder="Yeni şifrə" required>
                    <button type="submit">Şifrəni yenilə</button>
                </form>
                <div class="message" id="successMessage">Şifrə uğurla yeniləndi</div>
            </div>

            <script>
                document.getElementById("resetForm").addEventListener("submit", function (e) {
                    e.preventDefault();
                    const formData = new FormData(this);
                    fetch("/api/auth/reset-password", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/x-www-form-urlencoded"
                                },
                                body: new URLSearchParams(new FormData(document.getElementById("resetForm")))
                            }).then(response => {
                        if (response.ok) {
                            document.getElementById("successMessage").style.display = "block";
                        }
                    });
                });
            </script>
        </body>
        </html>
        """.formatted(token);

        return ResponseEntity.ok()
                .header("Content-Type", "text/html")
                .body(html);
    }



    @GetMapping("/success")
    public ResponseEntity<String> success() {
        String html = """
            <html>
            <head>
                <style>
                    body {
                        background: linear-gradient(to right, #43e97b, #38f9d7);
                        height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        margin: 0;
                        font-family: 'Segoe UI', sans-serif;
                    }
                    h1 {
                        color: white;
                        font-size: 36px;
                        background: rgba(0,0,0,0.2);
                        padding: 30px 60px;
                        border-radius: 12px;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
                    }
                </style>
            </head>
            <body>
                <h1>Payment completed successfully!</h1>
            </body>
            </html>
        """;
        return ResponseEntity.ok().header("Content-Type", "text/html").body(html);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        String html = """
            <html>
            <head>
                <style>
                    body {
                        background: linear-gradient(to right, #f857a6, #ff5858);
                        height: 100vh;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        margin: 0;
                        font-family: 'Segoe UI', sans-serif;
                    }
                    h1 {
                        color: white;
                        font-size: 36px;
                        background: rgba(0,0,0,0.2);
                        padding: 30px 60px;
                        border-radius: 12px;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.3);
                    }
                </style>
            </head>
            <body>
                <h1>Payment canceled.</h1>
            </body>
            </html>
        """;
        return ResponseEntity.ok().header("Content-Type", "text/html").body(html);
    }
}
