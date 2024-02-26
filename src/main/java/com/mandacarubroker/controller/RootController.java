package com.mandacarubroker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String documentationReadme() {
        String apiInfo = "Bem-vindo à API Mandacaru Broker!\n\n"
                + "Esta API fornece operações CRUD para gerenciar informações sobre ações (stocks).\n"
                + "Para mais informações, consulte a documentação completa em:\n"
                + "https://github.com/derleymad/mandacarubroker#readme";

        return apiInfo;
    }
}