import { Component, OnInit } from "@angular/core";
import { ProdutosService } from "../produtos.service";
import { Produtos } from "./produtos.model";

@Component({
  selector: "app-produtos-read",
  templateUrl: "./produtos-read.component.html",
  styleUrls: ["./produtos-read.component.css"],
})
export class ProdutosReadComponent implements OnInit {
  constructor(private service: ProdutosService) {}

  displayedColumns: string[] = [
    "id",
    "nomeProduto",
    "sabor",
    "preco",
    "quantidade",
    "dataValidade",
  ];

  produtos: Produtos[] = [];

  findAll() {
    this.service.findAll().subscribe((resposta) => {
      //console.log(resposta);
      this.produtos = resposta;
    });
  }

  ngOnInit(): void {
    this.findAll();
  }
}
