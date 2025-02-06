<template>
  <div class="background">
    <div class="container">
      <h1>Gerenciamento de Clientes</h1>

      <!-- Escolha entre Listar ou Buscar -->
      <div class="options">
        <button @click="listarClientes">Listar Clientes</button>
        <button @click="modoBusca = true">Buscar Cliente</button>
      </div>

            <!-- Modal de Confirmação de Exclusão -->
            <div v-if="showDeleteModal" class="modal-overlay">
        <div class="modal-content">
          <h2>Você deseja excluir esse cliente?</h2>
          <p>Essa ação não pode ser desfeita.</p>
          <div class="container-buttons">
            <button @click="excluirCliente(clienteParaExcluir)">Sim, Excluir</button>
            <button @click="cancelarExclusao">Cancelar</button>
          </div>
        </div>
      </div>

      <div v-if="modoEdicao" class="modal-overlay">
        <div class="modal-content">
          <h2>Editar Cliente</h2>
          <form @submit.prevent="salvarEdicao">
            <label for="nome">Nome:</label>
            <input type="text" v-model="cliente.nome" id="nome" />

            <label for="email">Email:</label>
            <input type="email" v-model="cliente.email" id="email" />

            <label for="telefone">Telefone:</label>
            <input type="text" v-model="cliente.telefone" id="telefone" />
            <div class="container-buttons">
            <button type="submit">Salvar Alterações</button>
            <button @click="modoEdicao = false">Cancelar</button>
          </div>
          </form>
        </div>
      </div>

      <!-- Componente de Busca -->
      <div v-if="modoBusca || clientes.length === 0" class="search-container">
        <input v-model="filtroNome" placeholder="Digite o nome do cliente..." @keyup.enter="buscarClientes" />
        <button @click="buscarClientes">Buscar</button>
      </div>

      <!-- Mensagem se não houver clientes encontrados com o nome buscado -->
      <p v-if="clientes.length === 0 && filtroNome && !clientesEncontrados">Nenhum cliente com este nome</p>

      <!-- Mensagem se não houver clientes cadastrados -->
      <p v-if="clientes.length === 0 && !filtroNome">Nenhum cliente cadastrado</p>

      <!-- Tabela de Clientes -->
      <table v-if="clientes.length > 0">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in clientesPaginados" :key="cliente.id">
            <td>{{ cliente.nome }}</td>
            <td>{{ cliente.email }}</td>
            <td>{{ cliente.telefone }}</td>
            <td>
              <div class="action-buttons">
                <button class="edit-btn" @click="editarCliente(cliente)">
                  ✏️
                </button>
                <button class="delete-btn" @click="confirmarExclusao(cliente.id)">
                  🗑️
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Paginação -->
      <div v-if="totalPaginas > 1" class="pagination">
        <button @click="paginaAtual--" :disabled="paginaAtual === 1">Anterior</button>
        <span>Página {{ paginaAtual }} de {{ totalPaginas }}</span>
        <button @click="paginaAtual++" :disabled="paginaAtual >= totalPaginas">Próxima</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { toast } from "vue3-toastify";
const API_URL = process.env.VUE_APP_API_URL;

export default {
  name: "GerenciarClientes",
  data() {
    return {
      clientes: [],
      filtroNome: "",
      clientesEncontrados: true,
      paginaAtual: 1,
      clientesPorPagina: 10,
      modoBusca: false,
      modoEdicao: false,
      cliente: {},
      showDeleteModal: false,
      clienteParaExcluir: null, 
    };
  },
  computed: {
    totalPaginas() {
      return Math.ceil(this.clientes.length / this.clientesPorPagina);
    },
    clientesPaginados() {
      const inicio = (this.paginaAtual - 1) * this.clientesPorPagina;
      return this.clientes.slice(inicio, inicio + this.clientesPorPagina);
    },
  },
  methods: {
    async listarClientes() {
      try {
        const response = await fetch(`${API_URL}/users`);
        this.clientes = await response.json();
        this.clientes.sort((a, b) => a.nome.localeCompare(b.nome));
        this.clientesEncontrados = true; 
      } catch (error) {
        toast.error("Erro ao buscar clientes.");
        console.error("Erro ao buscar clientes:", error);
      }
      this.modoBusca = false;
    },

    async buscarClientes() {
      try {
        const response = await fetch(`${API_URL}/users/search?nome=${this.filtroNome}`);
        this.clientes = await response.json();
        this.clientes.sort((a, b) => a.nome.localeCompare(b.nome));
        
        if (this.clientes.length === 0) {
          this.clientesEncontrados = false;
        } else {
          this.clientesEncontrados = true;
        }
      } catch (error) {
        toast.error("Erro ao buscar clientes.");
        console.error("Erro ao buscar clientes:", error);
      }
      this.paginaAtual = 1;
    },

    async editarCliente(cliente) {
      this.cliente = { ...cliente };
      this.modoEdicao = true;
    },

    async salvarEdicao() {
      try {
        await axios.put(`${API_URL}/users/${this.cliente.id}`, this.cliente);
        toast.success("Cliente editado com sucesso!");
        this.listarClientes();
        this.modoEdicao = false;
      } catch (error) {
        toast.error("Erro ao editar cliente.");
        console.error("Erro ao editar cliente:", error);
      }
    },

    confirmarExclusao(id) {
      this.clienteParaExcluir = id;
      this.showDeleteModal = true;
    },

    cancelarExclusao() {
      this.showDeleteModal = false;
    },

    async excluirCliente(id) {
      try {
        await axios.delete(`${API_URL}/users/${id}`);
        toast.success("Cliente excluído com sucesso!");
        this.listarClientes();
        this.showDeleteModal = false; 
      } catch (error) {
        toast.error("Erro ao excluir cliente.");
        console.error("Erro ao excluir cliente:", error);
      }
    },
  },
};
</script>

<style scoped>

html, body {
  margin: 0;
  padding: 0;
  overflow-x: hidden; 
  width: 100%;
}

.background {
  width: 100vw;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: linear-gradient(to top, #a8edea 0%, #fed6e3 100%);
  padding: 20px;
  box-sizing: border-box;
  overflow-x: hidden;
}

/* Limitando tamanho do container */
.container {
  background: rgba(255, 255, 255, 0.2);
  padding: 40px;
  border-radius: 15px;
  backdrop-filter: blur(10px);
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  text-align: center;
  overflow-y: auto;
  width: 80%;
  overflow-x: hidden;
  max-width: 1000px;
  margin-top: 120px; 
}

/* Tabela responsiva */
table {
  border-collapse: collapse;
  margin-top: 20px;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 10px;
  overflow: hidden;
  max-height: 300px; 
  width: 100%;
  overflow-x: auto;
}

/* Envolver a tabela para permitir rolagem */
.table-container {
  overflow-y: auto;
}

/* Ajuste para colunas da tabela */
th, td {
  border: 1px solid rgba(255, 255, 255, 0.5);
  padding: 10px;
  text-align: left;
  color: rgb(0, 0, 0);
  white-space: nowrap; 
}

/* Título */
h1 {
  font-size: 28px;
  color: rgb(0, 0, 0);
  font-weight: bold;
  text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.3);
  margin-bottom: 20px;
}

/* Botões principais */
.options {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 15px;
}

/* Container dos botões */
.container-buttons {
  display: flex;
  justify-content: center; 
  gap: 17px;
}

/* Estilo dos botões */
button {
  width: 40%;
  padding: 12px;
  background: rgb(117, 205, 200);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s ease;
  margin: 0 auto 5px auto; 
}


button:hover {
  background: rgb(95, 180, 172);
}

/* Campo de busca */
.search-container {
  width: 100%;
  max-width: 500px;
  margin: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

input {
  width: 90%;
  padding: 12px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.1);
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: rgba(255, 255, 255, 0.9);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
  width: 400px; 
}

.modal-content label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}
.search-container-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 80%;  
  max-width: 600px; 
  height: auto;
  background-color: rgba(0, 0, 0, 0.9);
  z-index: 1000;
  padding: 20px;
  border-radius: 8px;
}

/* Botão de busca */
.search-container button {
  width: 50%;
}

/* Mensagem sem clientes */
p {
  font-size: 16px;
  color: #000000;
  font-weight: bold;
  text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.2);
}

/* Paginação */
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}
.edit-form {
  margin-top: 20px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}

.edit-form label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}

.edit-form input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.edit-form button {
  width: 100%;
  padding: 12px;
  background: rgb(117, 205, 200);
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: 0.3s ease;
}

.edit-form button:hover {
  background: rgb(95, 180, 172);
}
.action-buttons {
  display: flex;
  gap: 5px; 
}

.action-buttons button {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  font-size: 16px;
  transition: 0.3s ease;
}

.edit-btn {
  background-color: #fed6e3;
  color: #fff;
}

.delete-btn {
  background-color: #fed6e3 ;
  color: #fff;
}

.edit-btn:hover {
  background-color: #fed6e3 ;
}

.delete-btn:hover {
  background-color: #fed6e3 ;
}
</style>
