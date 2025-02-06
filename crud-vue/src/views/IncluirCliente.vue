<template>
  <div class="container">
    <h1>Incluir Cliente</h1>
    <form @submit.prevent="adicionar">
      <input v-model="cliente.nome" placeholder="Nome" required class="input-field" />
      <input v-model="cliente.email" placeholder="Email" required class="input-field" type="email" />
      <input 
        v-model="cliente.telefone" 
        v-mask="'(##) #####-####'" 
        placeholder="Telefone" 
        required 
        class="input-field" 
        type="text"
      />
      <button type="submit" class="submit-btn">Cadastrar</button>
    </form>
  </div>
</template>

<script>
import { mask } from "vue-the-mask";
import axios from "axios";
import { toast } from "vue3-toastify"; 
const API_URL = process.env.VUE_APP_API_URL

export default {
  directives: { mask },
  data() {
    return {
      cliente: { nome: '', email: '', telefone: '' }
    };
  },
  methods: {
    async adicionar() {
      if (!this.validarTelefone()) {
        alert("Telefone inválido! Use o formato correto: (99) 99999-9999");
        return;
      }

      try {
       
        await axios.post(`${API_URL}/users`, this.cliente);
        toast.success("Adicionado com sucesso!");

        this.cliente = { nome: "", email: "", telefone: "" };

      } catch (error) {
        console.error("Erro ao adicionar cliente:", error);
        toast.error("Erro ao adicionar cliente. Tente novamente.");
      }
    },
    validarTelefone() {
      const regex = /^\(\d{2}\) \d{5}-\d{4}$/;
      return regex.test(this.cliente.telefone);
    },
  },
};
</script>

<style scoped>
/* Estilos principais */
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-image: linear-gradient(to top, #a8edea 0%, #fed6e3 100%);
  padding: 20px;
}

h1 {
  color: black;
  font-size: 2.5rem;
  margin-bottom: 20px;
  font-weight: bold;
}

/* Estilização do formulário */
form {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 450px;
  padding: 25px;
  background: rgba(255, 255, 255, 0.25);
  border-radius: 15px;
  backdrop-filter: blur(15px);
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

/* Campos de entrada */
.input-field {
  width: 100%;
  padding: 18px;
  margin: 12px 0;
  border: none;
  border-radius: 12px;
  font-size: 1.1rem;
  background: rgba(255, 255, 255, 0.8);
  box-shadow: inset 2px 2px 5px rgba(0, 0, 0, 0.1);
}

.input-field:focus {
  outline: none;
  background: rgba(255, 255, 255, 1);
  box-shadow: 0px 0px 10px rgba(0, 123, 255, 0.5);
}


.submit-btn {
  width: 100%;
  padding: 18px;
  background: rgb(117, 205, 200);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: 0.3s;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.submit-btn:hover {
  background: rgb(95, 175, 175); 
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.2);
}

/* Navegação */
nav {
  background-color: #007bff;
  padding: 15px 0;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
}
</style>
