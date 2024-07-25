<template>
  <div class="form-container">
    <h2>{{ isEdit ? 'Editar Pessoa' : 'Nova Pessoa' }}</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" v-model="localPerson.nome" required>
      </div>
      <div class="form-group">
        <label for="cpf">CPF:</label>
        <input type="text" id="cpf" v-model="localPerson.cpf" @input="validateCpf" required>
        <span v-if="cpfError" class="error">{{ cpfError }}</span>
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="localPerson.email" required>
      </div>
      <div class="form-group">
        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" id="dataNascimento" v-model="localPerson.dataNascimento" required>
      </div>
      <button type="submit">{{ isEdit ? 'Atualizar' : 'Cadastrar' }}</button>
    </form>
  </div>
</template>

<script>
export default {
  props: ['person', 'isEdit'],
  data() {
    return {
      cpfError: '',
      localPerson: { ...this.person }
    };
  },
  methods: {
    handleSubmit() {
    
      if (this.localPerson.dataNascimento) {
        this.localPerson.dataNascimento = this.formatDateToDDMMYYYY(new Date(this.localPerson.dataNascimento));
      }
      this.$emit('submit', { ...this.localPerson }); 
    },
    validateCpf() {
      const cpfPattern = /^\d{3}\.\d{3}\.\d{3}-\d{2}$/;
      if (!cpfPattern.test(this.localPerson.cpf)) {
        this.cpfError = 'CPF inválido. Formato correto: 000.000.000-00';
      } else {
        this.cpfError = '';
      }
    },
    formatDateToDDMMYYYY(date) {
      if (!(date instanceof Date)) {
        throw new Error('O parâmetro deve ser uma instância de Date.');
      }

      const day = String(date.getDate()).padStart(2, '0'); 
      const month = String(date.getMonth() + 1).padStart(2, '0'); 
      const year = date.getFullYear(); 

      return `${day}-${month}-${year}`;
    }
  },
  watch: {
    person(newVal) {
      this.localPerson = { ...newVal }; 
    }
  }
};
</script>

<style scoped>
.form-container {
  max-width: 500px;
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

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  font-size: 14px;
  margin-top: 5px;
  display: block;
}
</style>
