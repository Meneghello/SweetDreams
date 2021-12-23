import { HttpClient } from "@angular/common/http";
import { Produtos } from "./produtos-read/produtos.model";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
  providedIn: "root",
})
export class ProdutosService {
  baseUrl: String = "https://sweet-dreams-loja.herokuapp.com/";

  constructor(private http: HttpClient) {}

  findAll(): Observable<Produtos[]> {
    const listarProduto = this.baseUrl + "produto/";
    return this.http.get<Produtos[]>(listarProduto);
  }
}
