<template>
  <nav>
    <div class="logo">JDashboard</div>
    <ul>
      <li><router-link to="/incluir"><i class="fas fa-user-plus"></i> Incluir Cliente</router-link></li>
      <li><router-link to="/listar"><i class="fas fa-list"></i> Listar Clientes</router-link></li>
    </ul>
  </nav>
</template>

<style scoped>
nav {
  background-color: rgb(117, 205, 200);
  padding: 20px 40px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  z-index: 1100;
  align-items: center;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

/* Estilo do título "JDashboard" */
.logo {
  font-size: 26px;
  font-weight: bold;
  color: white;
  margin-right: 40px;
  text-transform: uppercase;
  letter-spacing: 1px;
  background: rgba(255, 255, 255, 0.2);
  padding: 8px 15px;
  border-radius: 6px;
}

/* Estilo do menu */
ul {
  list-style: none;
  display: flex;
  justify-content: flex-start;
  gap: 25px;
  padding: 0;
  margin: 0;
}

li {
  display: inline;
}

a {
  color: white;
  text-decoration: none;
  font-size: 20px;
  font-weight: bold;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 15px;
  border-radius: 6px;
  position: relative;
  transition: color 0.3s ease-in-out;
}

/* Linha decorativa abaixo dos itens ao passar o mouse */
a::after {
  content: "";
  position: absolute;
  bottom: -3px;
  left: 0;
  width: 100%;
  height: 3px;
  background-color: #a8edea;
  transform: scaleX(0);
  transition: transform 0.3s ease-in-out;
}

a:hover {
  color: black;
}

a:hover::after {
  transform: scaleX(1);
}

/* Ajuste para os ícones */
i {
  font-size: 22px;
}
</style>
