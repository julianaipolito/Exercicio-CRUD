import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "vue3-toastify/dist/index.css";
import VueTheMask from 'vue-the-mask';

const app = createApp(App);

app.use(router);
app.use(VueTheMask); 
app.mount("#app");
