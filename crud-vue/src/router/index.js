import { createRouter, createWebHistory } from "vue-router";
import ListarClientes from "../views/ListarClientes.vue";
import IncluirCliente from "../views/IncluirCliente.vue";

const routes = [
  { path: "/", redirect: "/listar" },
  { path: "/listar", component: ListarClientes },
  { path: "/incluir", component: IncluirCliente }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
