import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios';

createApp(App).mount('#app')
axios.defaults.baseURL = 'http://localhost:8081/api/pessoas'; // URL base da API
axios.defaults.headers.common['Content-Type'] = 'application/json'; 
