<template>
    <div id="app">
    <router-view />
  
    <div>
   
      <PersonForm
        v-if="showForm"
        :person="selectedPerson"
        :isEdit="isEdit"
        @submit="handleFormSubmit"
      />
      <PersonList
        v-else
        @edit="handleEdit"
      />
      <button @click="toggleForm">{{ showForm ? 'Voltar' : 'Novo Cadastro' }}</button>
    </div>
  </div>
  </template>
  
  <script>
  import PersonForm from './PersonForm.vue';
  import PersonList from './PersonList.vue';
  import axios from 'axios';
  
  export default {
    name: 'HomePage',
    components: {
      PersonForm,
      PersonList
    },
    data() {
      return {
        showForm: false,
        selectedPerson: {
          id: '',
          nome: '',
          cpf: '',
          email: '',
          dataNascimento: ''
        },
        isEdit: false
      };
    },
    methods: {
      toggleForm() {
        this.showForm = !this.showForm;
        if (!this.showForm) {
          this.resetSelectedPerson();
        }
      },
      resetSelectedPerson() {
        this.selectedPerson = {
          id: '',
          nome: '',
          cpf: '',
          email: '',
          dataNascimento: ''
        };
        this.isEdit = false;
      },
      handleFormSubmit(person) {
        if (this.isEdit) {
          axios.put(`http://localhost:8081/api/pessoas/${person.id}`, person)
            .then(() => {
              this.toggleForm();
              this.$refs.personList.fetchPersons();
            });
        } else {
          axios.post('http://localhost:8081/api/pessoas', person)
            .then(() => {
              this.toggleForm();
              this.$refs.personList.fetchPersons();
            });
        }
      },
      handleEdit(person) {
        this.selectedPerson = { ...person };
        this.isEdit = true;
        this.showForm = true;
      }
    }
  };
  </script>
  