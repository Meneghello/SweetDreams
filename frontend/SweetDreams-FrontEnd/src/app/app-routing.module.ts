import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomeComponent } from "./components/views/home/home.component";
import { ProdutosReadComponent } from "./components/views/produtos/produtos-read/produtos-read.component";

const routes: Routes = [
  {
    path: "",
    component: HomeComponent,
  },
  {
    path: "produtos",
    component: ProdutosReadComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
