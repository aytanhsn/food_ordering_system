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

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

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
