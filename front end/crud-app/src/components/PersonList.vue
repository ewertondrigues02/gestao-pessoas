<template>
  <div class="list-container">
    <h2>Lista de Pessoas</h2>
    <ul class="person-list">
      <li v-for="person in persons" :key="person.id" class="person-item">
        <span>{{ person.nome }}</span>
        <span>{{ person.cpf }}</span>
        <span>{{ person.email }}</span>
        <span>{{ person.dataNascimento }}</span>
        <button @click="editPerson(person)" class="btn btn-edit">Editar</button>
        <button @click="deletePerson(person.id)" class="btn btn-delete">Excluir</button>
      </li>
    </ul>
    <p class="total-count">Total de Pessoas Cadastradas: {{ count }}</p>
    <div v-if="error" class="error">{{ error }}</div> 
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PersonList',
  data() {
    return {
      persons: [],
      count: 0,
      error: null 
    };
  },
  methods: {
    fetchPersons() {
      console.log('fetchPersons chamado');
      axios.get('http://localhost:8081/api/pessoas')
        .then(response => {
          console.log('Resposta recebida:', response);
          this.persons = response.data;
          this.count = this.persons.length;
          this.error = null; 
        })
        .catch(error => {
          console.error('Erro ao buscar pessoas!', error);
          this.error = 'Erro ao buscar pessoas: ' + (error.response?.data?.message || error.message);
        });
    },
    editPerson(person) {
      console.log('editPerson chamado com:', person);
      this.$emit('edit', person);
    },
    deletePerson(id) {
      console.log('deletePerson chamado com ID:', id);
      axios.delete(`http://localhost:8081/api/pessoas/${id}`)
        .then(response => {
          console.log('Delete person response:', response);
          this.fetchPersons();
          this.error = null;
        })
        .catch(error => {
          console.error('Erro ao deletar pessoa!', error);
          this.error = 'Erro ao deletar pessoa: ' + (error.response?.data?.message || error.message);
        });
    }
  },
  created() {
    console.log('Componente criado, chamando fetchPersons');
    this.fetchPersons();
  }
};
</script>

<style scoped>
.list-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f7f7f7;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.person-list {
  list-style-type: none;
  padding: 0;
}

.person-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.person-item span {
  flex: 1;
  text-align: center;
}

.person-item:last-child {
  border-bottom: none;
}

.btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-edit {
  background-color: #28a745;
  color: white;
  margin: 5px;
}

.btn-edit:hover {
  background-color: #218838;
}

.btn-delete {
  background-color: #dc3545;
  color: white;
}

.btn-delete:hover {
  background-color: #c82333;
}

.total-count {
  text-align: center;
  margin-top: 20px;
  font-size: 16px;
  color: #555;
}

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>
